import java.util.*;
public class GradeBook {
    private Student student;
    private List<Record> records = new ArrayList<>();
    public GradeBook(Student student) {
        this.student = student;
    }
    public Student getStudent() { return student; }
    public List<Record> getRecords() { return records; }
    public void addRecord(Record record) {
        records.add(record);
    }
    public class Record {
        private int session;
        private String subject;
        private boolean exam;
        private int grade;
        private boolean passed;
        public Record(int session, String subject, int grade) {
            this.session = session;
            this.subject = subject;
            this.exam = true;
            this.grade = grade;
            this.passed = false;
        }
        public Record(int session, String subject, boolean passed) {
            this.session = session;
            this.subject = subject;
            this.exam = false;
            this.grade = 0;
            this.passed = passed;
        }
        public int getSession() { return session; }
        public String getSubject() { return subject; }
        public boolean isExam() { return exam; }
        public int getGrade() { return grade; }
        public boolean isPassed() { return passed; }
        public boolean isExamPassed() { return exam && grade >= 4; }
        public boolean isExamExcellent() { return exam && grade >= 9; }
        public boolean isCreditPassed() { return !exam && passed; }
    }
    public boolean allExamsExcellent() {
        boolean hasExam = false;
        for (Record r : records) {
            if (r.isExam()) {
                hasExam = true;
                if (r.getGrade() < 9) return false;
            }
        }
        return hasExam;
    }
    public boolean allCreditsPassed() {
        for (Record r : records) {
            if (!r.isExam() && !r.isPassed()) return false;
        }
        return true;
    }
    public boolean isPassing() {
        for (Record r : records) {
            if (r.isExam()) {
                if (r.getGrade() < 4) return false;
            } else {
                if (!r.isPassed()) return false;
            }
        }
        return true;
    }
    public Map<Integer, Double> getAverageBySession() {
        Map<Integer, List<Integer>> bySession = new TreeMap<>();
        for (Record r : records) {
            if (r.isExam() && r.getGrade() >= 4) {
                bySession.computeIfAbsent(r.getSession(), k -> new ArrayList<>()).add(r.getGrade());
            }
        }
        Map<Integer, Double> result = new TreeMap<>();
        for (Map.Entry<Integer, List<Integer>> e : bySession.entrySet()) {
            double avg = e.getValue().stream().mapToInt(Integer::intValue).average().orElse(0);
            result.put(e.getKey(), avg);
        }
        return result;
    }
}