package buptspirit.spm.rest.filter;

public enum Role {
    Student("student"),
    Teacher("teacher"),
    Administrator("administrator");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
