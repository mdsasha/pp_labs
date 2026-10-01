import java.util.Objects;
public class Student {
    private String lastName, firstName, middleName;
    private int course;
    private String group;
    public Student(String lastName, String firstName, String middleName, int course, String group) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.course = course;
        this.group = group;
    }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public int getCourse() { return course; }
    public String getGroup() { return group; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student s = (Student) o;
        return course == s.course && Objects.equals(lastName, s.lastName) && Objects.equals(firstName, s.firstName) && Objects.equals(middleName, s.middleName) && Objects.equals(group, s.group);
    }
    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, middleName, course, group);
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName + " " + course + " курс " + group;
    }
}