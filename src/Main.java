import java.util.Scanner;
import java.util.InputMismatchException;

class MyCalculator {
    double x;
    double eps;
    double sum;

    MyCalculator(double arg, double e) {
        x = arg;
        eps = e;
        sum = 0;
    }

    void calculate() {
        double a = 1.0;
        sum = a;
        int n = 1;

        while (Math.abs(a) >= eps) {
            a = a * (x / n);
            sum = sum + a;
            n = n + 1;
        }
    }

    double getSum() {
        return sum;
    }

    double getExact() {
        return Math.exp(x);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double x = 0;
        int k = 0;

        while (true) {
            try {
                System.out.print("введите число x : ");
                x = scanner.nextDouble();

                System.out.print("введите  натуральное число k (точность = 10^(-k)): ");
                k = scanner.nextInt();

                if (k <= 0) {
                    System.out.println("k должно быть > 0, повторите ввод.");
                    continue;
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("ошибка, введите число, а не текст!");
                scanner.nextLine();
            }
        }

        double eps = Math.pow(10, -k);

        MyCalculator calc = new MyCalculator(x, eps);
        calc.calculate();

        double mySum = calc.getSum();
        double exact = calc.getExact();

        System.out.println("сумма ряда: " + String.format("%.3f", mySum));
        System.out.println("точное значение: " + String.format("%.3f", exact));
        System.out.println("разница: " + String.format("%.3f", Math.abs(mySum - exact)));

        scanner.close();
    }
}
