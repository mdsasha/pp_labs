import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int width = readWidth(scanner);
        if (width <= 0) {
            System.out.println("некорректная ширина");
            return;
        }

        FileProcessor fileProcessor = new FileProcessor("input.txt", "output.txt");
        if (!fileProcessor.inputExists()) {
            System.out.println("Не найден файл " + fileProcessor.getInputPath().toAbsolutePath());
            return;
        }

        try {
            List<String> sourceLines = fileProcessor.readLines();
            List<String> paragraphs = TextAligner.collectParagraphs(sourceLines);
            List<String> result = TextAligner.align(paragraphs, width);
            fileProcessor.writeLines(result);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
    private static int readWidth(Scanner scanner) {
        System.out.print("Введите ширину текста: ");
        while (true) {
            if (!scanner.hasNextLine()) {
                return -1;
            }
            String line = scanner.nextLine().trim();
            try {
                int width = Integer.parseInt(line);
                if (width > 0) {
                    return width;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Ширина должна быть >0 ");
        }
    }
}
