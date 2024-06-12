import java.util.HashMap;

public class Program {

    protected enum Name {
        AQE,
        JAVADEV
    }

    private String programName;

    HashMap<String, Integer> courses;

    protected HashMap<String, Integer> setCourse(Name name) {
        this.programName = String.valueOf(name);
        courses = new HashMap<>();
        switch (name) {
            case JAVADEV: {
                courses.put("Java", 16);
                courses.put("JDBC", 24);
                courses.put("Spring", 16);
                break;
            }
            case AQE: {
                courses.put("Test design", 10);
                courses.put("Page Object", 16);
                courses.put("Selenium", 16);
                break;
            }
        }
        return courses;
    }

    public void printCourses() {
        courses.forEach((key, value) -> System.out.println(key + " " + value));
    }

    public int programDuration() {
        return courses.values().stream().reduce(0, Integer::sum);
    }

    public String getProgramName() {
        return programName;
    }

}


