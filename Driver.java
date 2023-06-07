import java.io.File;
import java.util.Arrays;

public class Driver {
	public static void main(String [] args) {
		// // TEST CASE 1 ////////////////////////////////////////////
		// Polynomial p = new Polynomial();
		// System.out.println(p.evaluate(3));

		// double [] c1 = {6,0,0,5};
		// int [] e1 = {0, 1, 2, 3};
		// Polynomial p1 = new Polynomial(c1, e1);

		// double [] c2 = {0, -2,0,0,-9};
		// int [] e2 = {0, 1, 2, 3, 4};
		// Polynomial p2 = new Polynomial(c2, e2);

		// Polynomial s = p1.add(p2);
		// System.out.println("s(0.1) = " + s.evaluate(0.1));
		// if(s.hasRoot(1))
		// 	System.out.println("1 is a root of s");
		// else
		// 	System.out.println("1 is not a root of s");

		// System.out.println("Sum polynomial coef: " + Arrays.toString(s.coefficients));
		// System.out.println("Sum polynomial exp: " + Arrays.toString(s.exponents));

		// s.saveToFile("eq-out.txt");
		// Polynomial f = new Polynomial(new File("eq-out.txt"));
        // System.out.println("File Polynomial coef: " + Arrays.toString(f.coefficients));
        // System.out.println("File Polynomial exp: " + Arrays.toString(f.exponents));

		// TEST CASE 2 ////////////////////////////////////////////////
		Polynomial p = new Polynomial();
        System.out.println("Evaluate at 3: " + p.evaluate(3));

		// Add test
        double[] c1 = { 6, 0, 0, 5 };
        int[] e1 = { 0, 2, 3, 5 };
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = { 0, -2, 0, 0, -9 };
        int[] e2 = { 0, 1, 2, 3, 6 };
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
		Polynomial sum_0 = p1.add(new Polynomial());
		System.out.println("Sum with 0 polynomial coeff: " + Arrays.toString(sum_0.coefficients));
		System.out.println("Sum with 0 polynomial exp: " + Arrays.toString(sum_0.exponents));		
		
		// Multiply test
		Polynomial m = p1.multiply(p2);
		System.out.println("Multiplication 1 coeff: " + Arrays.toString(m.coefficients));
		System.out.println("Multiplication 1 exp" + Arrays.toString(m.exponents));
		Polynomial m1 = p1.multiply(new Polynomial());
		System.out.println("Multiplication with 0 coeff: " + Arrays.toString(m1.coefficients));
		System.out.println("Multiplication with 0 exp: " + Arrays.toString(m1.exponents));

		// Evaluate test
        System.out.println("s(0.1) = " + s.evaluate(0.1));
		
		// hasRoot test
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

		// Save to file test
        s.saveToFile("eq-out.txt");

		// Read from file constructor test
        Polynomial f = new Polynomial(new File("eq-out.txt"));
        System.out.println("File Polynomial coef: " + Arrays.toString(f.coefficients));
        System.out.println("File Polynomial exp: " + Arrays.toString(f.exponents));
	}
}