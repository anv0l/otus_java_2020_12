package homework;

public interface Calculator {
    @Log
    long sum(long a, long b);

    @Log
    double cubeRoot(double a);

    @Log
    double quadraticPolynomial(double a, double b, double c, double x);

    double willNotLog(double a);
}
