package homework;

public class MyCalculator implements Calculator {
    @Override
    public long sum(long a, long b) {
        return a + b;
    }

    @Override
    public double cubeRoot(double a) {
        return Math.pow(a, 1/3d);
    }

    @Override
    public double quadraticPolynomial(double a, double b, double c, double x) {
        return (a * Math.pow(x, 2) + b * x + c);
    }

    // для этого метода не включено логгирование
    @Override
    public double willNotLog(double a) {
        return a;
    }
}
