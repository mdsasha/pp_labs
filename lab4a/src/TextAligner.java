import java.util.ArrayList;
import java.util.List;

public final class TextAligner {
    public static final int INDENT = 4;
    private TextAligner() {
    }
    public static List<String> collectParagraphs(List<String> lines) {
        List<String> paragraphs = new ArrayList<>();
        StringBuilder paragraph = new StringBuilder();
        boolean paragraphStart = true;
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                addParagraph(paragraphs, paragraph);
                paragraphStart = true;
                continue;
            }

            boolean startsWithIndent = Character.isWhitespace(line.charAt(0));
            if (paragraphStart || startsWithIndent) {
                addParagraph(paragraphs, paragraph);
            }

            if (paragraph.length() > 0) {
                paragraph.append(' ');
            }
            paragraph.append(line.trim());
            paragraphStart = false;
        }

        addParagraph(paragraphs, paragraph);
        return paragraphs;
    }

    private static void addParagraph(List<String> paragraphs, StringBuilder paragraph) {
        if (paragraph.length() == 0) {
            return;
        }
        paragraphs.add(paragraph.toString().replaceAll("\\s+", " ").trim());
        paragraph.setLength(0);
    }
    public static List<String> align(List<String> paragraphs, int width) {
        List<String> result = new ArrayList<>();
        for (String paragraph : paragraphs) {
            result.addAll(alignParagraph(paragraph, width));
        }
        return result;
    }
    private static List<String> alignParagraph(String paragraph, int width) {
        String[] words = paragraph.split("\\s+");
        int indentSize = Math.min(INDENT, Math.max(width - 1, 0));
        String indent = " ".repeat(indentSize);

        List<String> lines = new ArrayList<>();
        int wordIndex = 0;
        boolean firstLine = true;

        while (wordIndex < words.length) {
            String prefix = firstLine ? indent : "";
            int available = width - prefix.length();

            List<String> lineWords = new ArrayList<>();
            int lineLength = 0;
            while (wordIndex < words.length) {
                String word = words[wordIndex];
                int addedLength = lineWords.isEmpty() ? word.length() : word.length() + 1;
                if (!lineWords.isEmpty() && lineLength + addedLength > available) {
                    break;
                }
                lineWords.add(word);
                lineLength += addedLength;
                wordIndex++;
                if (lineWords.size() == 1 && word.length() > available) {
                    break;
                }
            }
            boolean lastLine = wordIndex >= words.length;
            if (lastLine) {
                lines.add(prefix + String.join(" ", lineWords));
            } else {
                lines.add(prefix + justify(lineWords, available));
            }
            firstLine = false;
        }

        return lines;
    }
    private static String justify(List<String> words, int width) {
        if (words.size() == 1) {
            return words.get(0);
        }

        int wordsLength = 0;
        for (String word : words) {
            wordsLength += word.length();
        }

        int gaps = words.size() - 1;
        int spaces = width - wordsLength;
        int base = spaces / gaps;
        int remainder = spaces % gaps;

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            line.append(words.get(i));
            if (i < gaps) {
                int gapSize = base + (i < remainder ? 1 : 0);
                line.append(" ".repeat(Math.max(gapSize, 1)));
            }
        }
        return line.toString();
    }
}
