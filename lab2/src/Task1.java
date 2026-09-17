public class Task1 {
    public static void task1(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int maxVal = matrix[0][0], minVal = matrix[0][0];
        int maxRow = 0, minRow = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 && j == 0) continue;
                if (matrix[i][j] > maxVal) {
                    maxVal = matrix[i][j];
                    maxRow = i;
                }
                if (matrix[i][j] < minVal) {
                    minVal = matrix[i][j];
                    minRow = i;
                }
            }
        }
        System.out.println("максимум: " + maxVal + " (строка " + maxRow + ")");
        System.out.println("минимум: " + minVal + " (строка " + minRow + ")");
        if (maxRow != minRow) {
            int[] temp = matrix[maxRow];
            matrix[maxRow] = matrix[minRow];
            matrix[minRow] = temp;
            System.out.println("матрица после перестановки строк:");
            MatrixLab.printMatrix(matrix);
        }
        else {
            System.out.println("максимум и минимум в одной строке.");
        }
        System.out.println("строки с нулевым элементом на главной диагонали:");
        boolean found = false;
        for (int i = 0; i < Math.min(n, m); i++) {
            if (matrix[i][i] == 0) {
                found = true;
                int rowMax = matrix[i][0];
                for (int j = 1; j < m; j++) {
                    if (matrix[i][j] > rowMax) rowMax = matrix[i][j];
                }
                System.out.println("строка " + i + ": наибольший элемент = " + rowMax);
            }
        }
        if (!found) {
            System.out.println("таких строк нет.");
        }
    }
}