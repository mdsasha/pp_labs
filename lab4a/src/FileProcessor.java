import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {
    private final Path inputPath;
    private final Path outputPath;

    public FileProcessor(String inputFileName, String outputFileName) {
        this.inputPath = Paths.get(inputFileName);
        this.outputPath = Paths.get(outputFileName);
    }
    public List<String> readLines() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        }
        return lines;
    }
    public void writeLines(List<String> lines) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
    public boolean inputExists() {
        return Files.exists(inputPath);
    }
    public Path getInputPath() {
        return inputPath;
    }
    public Path getOutputPath() {
        return outputPath;
    }
}
