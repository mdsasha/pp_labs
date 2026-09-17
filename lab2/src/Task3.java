public class Task3 {
    public static void task3(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        for (int j = 0; j < m - 1; j++) {
            for (int k = 0; k < m - 1 - j; k++) {
                if (matrix[0][k] > matrix[0][k + 1]) {
                    for (int i = 0; i < n; i++) {
                        int temp = matrix[i][k];
                        matrix[i][k] = matrix[i][k + 1];
                        matrix[i][k + 1] = temp;
                    }
                }
            }
        }
        System.out.println("матрица после сортировки столбцов по неубыванию первых элементов:");
        MatrixLab.printMatrix(matrix);
    }
}