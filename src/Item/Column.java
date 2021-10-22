package Item;

import java.util.LinkedHashMap;

public class Column {
    private String column_name;
    private final LinkedHashMap<String, Task> tasks = new LinkedHashMap<>();

    public Column(String column_name){
         this.column_name = column_name;
    }

    public String getColumn_name() {
        return column_name;
    }

    public LinkedHashMap<String,Task> getTasks() {
        return tasks;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public void addTasks(String task_name, Task task) {
        this.tasks.put(task_name, task);
    }



    public void newTask(String task_name, String description) {
        if (!task_name.strip().isEmpty()) {
            Task newTask = new Task(task_name.strip(), description.strip());
            this.addTasks(newTask.getTask_name(), newTask);
        }
        else{
            System.out.println("New task need a name");
        }
    }

    public void deleteTask(String task_name){
        if (this.getTasks().containsKey(task_name.strip())) {
            this.getTasks().remove(task_name.strip());
        }
    }

    public void editTaskName_andDescription(String task_name, String new_task_name, String new_description){
        if (this.getTasks().containsKey(task_name.strip())) {
            Task task_edited = this.getTasks().get(task_name.strip());
            if (!new_task_name.strip().isEmpty()){
                task_edited.setTask_name(new_task_name.strip());
                task_edited.setTask_description(new_description.strip());
                this.addTasks(task_edited.getTask_name(), task_edited);
                this.getTasks().remove(task_name.strip());
            }
            else{
                System.out.println("Task name cannot be empty");
            }
        } else {
            System.out.println("The task you want to edit does not exist");
        }
    }

}
