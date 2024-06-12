public class Student {

    Program program = new Program();
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Program getProgram() {
        return program;
    }

}
