import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReportPrinter {

    static ArrayList<Student> listOfStudents;
    static Student student1;
    static Student student2;

    static HoursCalculator calculator = new HoursCalculator();

    public static void createListOfStudents() {
        student1 = new Student("Zdzichu");
        student2 = new Student("Rychu");
        student1.program.setCourse(Program.Name.JAVADEV);
        student2.program.setCourse(Program.Name.AQE);
        listOfStudents = new ArrayList<>();
        listOfStudents.add(student1);
        listOfStudents.add(student2);
    }

    public static String getFormattedDate(LocalDateTime date) {
        int startDayOfMonth = date.getDayOfMonth();
        String startDayOfWeek = String.valueOf(date.getDayOfWeek());
        String startMonth = String.valueOf(date.getMonth());
        return startDayOfMonth + " " + startMonth + " - " + startDayOfWeek;
    }

    public static void report(LocalDateTime startDate) {
        for (Student student : listOfStudents) {
            System.out.println("Student: " + student.getName());
            System.out.println("Curriculum: " + student.program.getProgramName());
            int duration = student.program.programDuration();
            System.out.println("Duration: " + duration);
            System.out.println("Start date: " + getFormattedDate(startDate));
            LocalDateTime endDate = calculator.endDate(startDate, duration);
            System.out.println("End date: " + getFormattedDate(endDate));
            int hours = calculator.hoursCompletion(startDate, duration);
            System.out.println(calculator.whenCompletion(hours));
            System.out.println();
        }
    }

    public static void report(LocalDateTime startDate, int extra) {
        for (Student student : listOfStudents) {
            String studentName = student.getName();
            String programName = student.program.getProgramName();
            int duration = student.program.programDuration();
            int hours = calculator.hoursCompletion(startDate, duration);
            String when = calculator.whenCompletion(hours);
            System.out.println(studentName + " (" + programName + ") - " + when);
        }
    }

    public static void main(String[] args) {

        final LocalDateTime timeStart = LocalDateTime.of(LocalDate.of(2024, 6, 3), LocalTime.of(10, 0, 0));
        createListOfStudents();
        ReportPrinter.report(timeStart);
        ReportPrinter.report(timeStart, 0);
    }
}
