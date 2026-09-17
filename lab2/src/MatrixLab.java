import java.io.*;
public class MatrixLab {
    public static void main(String[] args) {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        try {
            System.out.print("введите количество строк матрицы: ");
            int row = Integer.parseInt(br.readLine());
            System.out.print("введите количество столбцов матрицы: ");
            int column = Integer.parseInt(br.readLine());
            int[][] matrix = new int[row][column];
            System.out.println("введите элементы матрицы:");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    System.out.print("элемент [" + i + "][" + j + "]: ");
                    matrix[i][j] = Integer.parseInt(br.readLine());
                }
            }
            System.out.println("исходная матрица:");
            printMatrix(matrix);
            System.out.println("задание 1");
            Task1.task1(matrix);
            System.out.println("задание 2");
            Task2.task2(matrix);
            System.out.println("задание 3");
            Task3.task3(matrix);
        } catch (NumberFormatException e) {
            System.out.println("Не целое число");
        } catch (IOException e) {
            System.out.println("Ошибка чтения с клавиатуры");
        }
    }
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}