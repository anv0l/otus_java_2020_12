package homework;

public interface Calculator {
    void calculate(long a);

    void calculate(long a, String b);

    void calculate(long a, float b, String c);

    void calculate(String a, String b);

    long sum(long a, long b);
}
