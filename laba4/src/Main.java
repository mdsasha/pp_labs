import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<Student, GradeBook> gradeBooks = new LinkedHashMap<>();
        readExams("exams.txt", gradeBooks);
        readCredits("credits.txt", gradeBooks);
        writeExcellent("excellent.txt", gradeBooks.values());
        writeAverages("averages.txt", gradeBooks.values());
        System.out.println("Готово. Проверьте файлы excellent.txt и averages.txt");
    }
    private static void readExams(String filename, Map<Student, GradeBook> map) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(" ");
                if (parts.length < 8) continue;
                String lastName = parts[0].trim();
                String firstName = parts[1].trim();
                String middleName = parts[2].trim();
                int course = Integer.parseInt(parts[3].trim());
                String group = parts[4].trim();
                int session = Integer.parseInt(parts[5].trim());
                String subject = parts[6].trim();
                int grade;
                try {
                    grade = Integer.parseInt(parts[7].trim());   // 1..10
                } catch (NumberFormatException e) {
                    continue;
                }
                Student student = new Student(lastName, firstName, middleName, course, group);
                GradeBook gb = map.get(student);
                if (gb == null) {
                    gb = new GradeBook(student);
                    map.put(student, gb);
                }
                gb.addRecord(gb.new Record(session, subject, grade)); // экзамен
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения " + filename + ": " + e.getMessage());
        }
    }
    private static void readCredits(String filename, Map<Student, GradeBook> map) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(" ");
                if (parts.length < 8) continue;
                String lastName = parts[0].trim();
                String firstName = parts[1].trim();
                String middleName = parts[2].trim();
                int course = Integer.parseInt(parts[3].trim());
                String group = parts[4].trim();
                int session = Integer.parseInt(parts[5].trim());
                String subject = parts[6].trim();
                boolean passed = parts[7].trim().equalsIgnoreCase("зачтено");
                Student student = new Student(lastName, firstName, middleName, course, group);
                GradeBook gb = map.get(student);
                if (gb == null) {
                    gb = new GradeBook(student);
                    map.put(student, gb);
                }
                gb.addRecord(gb.new Record(session, subject, passed)); // зачёт
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения " + filename + ": " + e.getMessage());
        }
    }
    private static void writeExcellent(String filename, Collection<GradeBook> books) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (GradeBook gb : books) {
                if (gb.allExamsExcellent() && gb.allCreditsPassed()) {
                    Student s = gb.getStudent();
                    for (GradeBook.Record r : gb.getRecords()) {
                        String gradeStr = r.isExam() ? String.valueOf(r.getGrade()) : "";
                        pw.printf("%s %s %s %d %s %d %s %s%n", s.getLastName(), s.getFirstName(), s.getMiddleName(), s.getCourse(), s.getGroup(), r.getSession(), r.getSubject(), gradeStr);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи " + filename + ": " + e.getMessage());
        }
    }
    private static void writeAverages(String filename, Collection<GradeBook> books) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (GradeBook gb : books) {
                if (!gb.isPassing()) continue;
                Student s = gb.getStudent();
                Map<Integer, Double> avgMap = gb.getAverageBySession();
                if (avgMap.isEmpty()) continue;
                pw.printf("%s %s %s %d курс %s%n", s.getLastName(), s.getFirstName(), s.getMiddleName(), s.getCourse(), s.getGroup());
                for (Map.Entry<Integer, Double> e : avgMap.entrySet()) {
                    pw.printf("  Сессия %d: средний балл = %.2f%n", e.getKey(), e.getValue());
                }
                pw.println();
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи " + filename + ": " + e.getMessage());
        }
    }
}