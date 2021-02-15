package homework;

public class Calculations {
    public static void main(String[] args) {
        Calculator calculator = CalculatorProxy.createCalculator();

        System.out.println("Вызываем методы с @Log:");
        calculator.calculate(4);
        System.out.println(calculator.sum(6, 56));
        calculator.calculate(3,3f, "Long Float");

        System.out.println("\nСледующие методы без @Log:");
        calculator.calculate("Hello", "Bye");
        calculator.calculate(4, "Float");
    }
}
