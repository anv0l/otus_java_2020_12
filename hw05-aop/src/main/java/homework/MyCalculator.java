package homework;

public class MyCalculator implements Calculator {
    @Override
    @Log
    public void calculate(long a) {
        System.out.println(a);
    }

    @Override
    public void calculate(long a, String b) {
        System.out.println(b + ": " + a);
    }

    @Override
    @Log
    public void calculate(long a, float b, String c) {
        System.out.println(c + ": " + (a + b));
    }

    @Override
    public void calculate(String a, String b) {
        System.out.println(a + ":" + b);
    }

    @Override
    @Log
    public long sum(long a, long b) {
        return a + b;
    }

}
