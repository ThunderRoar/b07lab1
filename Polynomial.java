import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Polynomial {
	// Fields
	double coefficients[];
	int exponents[];

	// Constructors
	public Polynomial() {
		coefficients = new double[] {};
		exponents = new int[] { 0 };
	}

	public Polynomial(double arr[], int exp[]) {
		coefficients = arr;
		exponents = exp;
	}

	public Polynomial(File f) {
		try {
			Scanner scanner = new Scanner(new File(f.getName()));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] poly = line.split("[+-]");
				double[] cof = new double[poly.length];
				int[] exp = new int[poly.length];
				String[] term;
				String[] sign = new String[poly.length];
				int counter = 1;

				if (line.charAt(0) != '-') {
					sign[0] = "+";
				}

				for (int j = 0; j < line.length(); j++) {
					if (line.charAt(j) == '+') {
						sign[counter] = "+";
						counter++;
					} else if (line.charAt(j) == '-') {
						sign[counter] = "-";
						counter++;
					}
				}

				for (int i = 0; i < poly.length; i++) {
					if (poly[i].contains("x")) {
						term = poly[i].split("x");

						cof[i] = Double.parseDouble(sign[i] + term[0]);
						if (term.length == 1) {
							exp[i] = 1;
						} else {
							exp[i] = Integer.parseInt(term[1]);
						}
					} else if (poly[i].equals("x")) {
						cof[i] = 1;
						exp[i] = 1;
					} else if (poly[i].equals("")) {
						continue;
					} else {
						cof[i] = Double.parseDouble(sign[i] + poly[i]);
						exp[i] = 0;
					}
				}

				coefficients = cof;
				exponents = exp;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Class Methods
	public Polynomial add(Polynomial p) {

		// Check validity of polynomial
		if (p == null) {
			System.out.println("This is an invalid polynomial...");
			return null;
		}

		double[] newcof = new double[coefficients.length + p.coefficients.length];
		int[] newexp = new int[coefficients.length + p.coefficients.length];
		int x = 0, y = 0, z = 0;

		while (x < coefficients.length && y < p.coefficients.length) {
			if (exponents[x] == p.exponents[y]) {
				if ((coefficients[x] + p.coefficients[y]) != 0) {
					newcof[z] = coefficients[x] + p.coefficients[y];
					newexp[z] = exponents[x];
					z++;
				}
				x++;
				y++;
			} else if (exponents[x] > p.exponents[y]) {
				newcof[z] = coefficients[x];
				newexp[z] = exponents[x];
				x++;
				z++;
			} else {
				newcof[z] = p.coefficients[y];
				newexp[z] = p.exponents[y];
				y++;
				z++;
			}
		}

		while (x < coefficients.length) {
			newcof[z] = coefficients[x];
			newexp[z] = exponents[x];
			x++;
			z++;
		}

		while (y < p.coefficients.length) {
			newcof[z] = p.coefficients[y];
			newexp[z] = p.exponents[y];
			y++;
			z++;
		}

		double[] sumcof = new double[z];
		int[] sumexp = new int[z];
		for (int i = 0; i < z; i++) {
			sumcof[i] = newcof[i];
			sumexp[i] = newexp[i];
		}

		Polynomial sum = new Polynomial(sumcof, sumexp);
		return sum;
	}

	public double evaluate(double x) {
		double eval = 0;
		for (int i = 0; i < coefficients.length; i++) {
			eval += coefficients[i] * Math.pow(x, exponents[i]);
		}
		return eval;
	}

	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}

	public Polynomial clean(Polynomial product) {
		int len = 0;

		for (int i=0; i < product.coefficients.length; i++) {
			if (product.coefficients[i] != 0) {
				len++;
			}
		}

		double [] cof = new double[len];
		int [] exp = new int[len];
		int counter = 0;
		
		for (int j=0; j < product.coefficients.length; j++) {
			if (product.coefficients[j] != 0) {
				cof[counter] = product.coefficients[j];
				exp[counter] = product.exponents[j];
				counter++;
			} else {
				continue;
			}
		}

		return new Polynomial(cof, exp);
	}

	public Polynomial inner_sum(Polynomial poly) {
		int len = poly.exponents.length;
		for (int k=0; k < len; k++) {
			for (int j=0; j < len; j++) {
				if (poly.exponents[k] == poly.exponents[j] && k != j) {
					poly.coefficients[k] += poly.coefficients[j];
					poly.coefficients[j] = 0;
					poly.exponents[j] = 0;
				}
			}
		}

		Polynomial summed = new Polynomial(poly.coefficients, poly.exponents);
		summed = clean(summed);
		return summed;
	}

	public Polynomial multiply(Polynomial p) {
		Polynomial result = new Polynomial();

		for (int i = 0; i < coefficients.length; i++) {
			double[] product = new double[p.coefficients.length];
			int[] sum = new int[p.exponents.length];
			for (int j = 0; j < p.coefficients.length; j++) {
				product[j] = coefficients[i] * p.coefficients[j];
				sum[j] = exponents[i] + p.exponents[j];
			}
			result = result.add(new Polynomial(product, sum));
		}
		result = clean(result);
		result = inner_sum(result);
		return result;
	}

	public void saveToFile(String file) {
		try {
			PrintStream output = new PrintStream(file);
			String poly = "";
			for (int i = 0; i < coefficients.length; i++) {
				String cof = Double.toString(coefficients[i]);
				String exp = Integer.toString(exponents[i]);
				if (poly == "") {
					if (coefficients[i] > 0 && exponents[i] > 0) {
						poly += cof + "x" + exp;
					} else if (coefficients[i] < 0 && exponents[i] > 0) {
						poly += "-" + cof + "x" + exp;
					} else if (coefficients[i] > 0 && exponents[i] == 0) {
						poly += cof;
					} else if (coefficients[i] < 0 && exponents[i] == 0) {
						poly += "-" + cof;
					}
				} else {
					if (coefficients[i] > 0 && exponents[i] > 0) {
						poly += "+" + cof + "x" + exp;
					} else if (coefficients[i] < 0 && exponents[i] > 0) {
						poly += cof + "x" + exp;
					} else if (coefficients[i] > 0 && exponents[i] == 0) {
						poly += "+" + cof;
					} else if (coefficients[i] < 0 && exponents[i] == 0) {
						poly += cof;
					}
				}
			}
			output.print(poly);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}