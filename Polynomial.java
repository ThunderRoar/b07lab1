
public class Polynomial {
	// Fields
	double coefficients[];
	
	// Constructor
	public Polynomial() {
		coefficients = new double[] {0};
	}

	public Polynomial(double arr[]) {
		coefficients = arr;
	}

	// Class Methods
	public Polynomial add(Polynomial p) {
		int maxLength = Math.max(coefficients.length, p.coefficients.length);
		double newArr[] = new double[maxLength];

		for (int i = 0; i < maxLength; i++) {
			// Checks if index does not go out of bounds
			// as the polynomial arrays can be of different lengths
			if (i < coefficients.length) {
				newArr[i] += coefficients[i];
			} 
			if (i < p.coefficients.length) {
				newArr[i] += p.coefficients[i];
			}
		}

		Polynomial sum = new Polynomial(newArr); // Creating a new Polynomial object to return

		return sum;
	}

	public double evaluate(double x) {
		double eval = 0;
		for (int i = 0; i < coefficients.length; i++) {
			eval += coefficients[i] * Math.pow(x, i);
		}
		return eval;
	}

	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}
}