package Item;

public class Task {
    private String task_name;
    private String task_description;
    public Task(String task_name, String task_description){
        this.task_name = task_name;
        this.task_description = task_description;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }
}

