package homework;

public class Calculations {
    public static void main(String[] args) {
        Calculator calculator = Logger.CreateCalculator();
        System.out.println("Вызываем методы с @Log:");
        System.out.println(calculator.cubeRoot(9));
        System.out.println(calculator.quadraticPolynomial(4,3,2,4));
        System.out.println(calculator.sum(6, 56));
        System.out.println("\nСледующий метод без @Log:");
        System.out.println(calculator.willNotLog(999));
    }
}
