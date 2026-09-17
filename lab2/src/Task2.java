public class Task2 {
    public static void task2(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[] temp = new int[n * m];
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp[counter++] = matrix[i][j];
            }
        }
        Integer maxTwice = null;
        for (int i = 0; i < temp.length; i++) {
            int count = 0;
            for (int j = 0; j < temp.length; j++) {
                if (temp[i] == temp[j]) count++;
            }
            if (count == 2) {
                if (maxTwice == null || temp[i] > maxTwice) {
                    maxTwice = temp[i];
                }
            }
        }
        if (maxTwice != null) {
            System.out.println("максимальное из чисел, встречающихся ровно два раза: " + maxTwice);
        } else {
            System.out.println("нет чисел, встречающихся ровно два раза.");
        }
    }
}
