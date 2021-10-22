package Item;

import java.util.LinkedHashMap;

public class Project {
    private String project_name;
    private String status;
    private String username;
    private final LinkedHashMap<String, Column> columns = new LinkedHashMap<>();


    public Project(String project_name, String username, String status) {
        this.project_name = project_name;
        this.status = status;
        this.username = username;
    }

    public String getProject_name() {
        return project_name;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public LinkedHashMap<String, Column> getColumns() {
        return columns;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addColumns(String column_name, Column column) {
        this.columns.put(column_name, column);
    }

    public boolean isColumnExist(String column_name){
        return this.columns.containsKey(column_name);
    }

    public boolean isTaskExist(String task_name){
        for (String column_name: this.columns.keySet()){
            if(this.columns.get(column_name).getTasks().containsKey(task_name)){
                return true;
            }
        }
        return false;
    }

    public void newColumn(String column_name) {
        if(!column_name.strip().isEmpty()){
            Column newColumn = new Column(column_name.strip());
            this.addColumns(newColumn.getColumn_name(), newColumn);
        }
        else{
            System.out.println("New column need a name");
        }

    }

    public void deleteColumn(String column_name) {
        if (this.getColumns().containsKey(column_name.strip())) {
            this.getColumns().remove(column_name.strip());
        }
    }

    public void editColumnName(String column_name, String new_column_name){
        if (this.getColumns().containsKey(column_name.strip())) {
            if(!new_column_name.strip().isEmpty()) {
                Column column_edited = this.getColumns().get(column_name.strip());
                column_edited.setColumn_name(new_column_name.strip());
                this.addColumns(column_edited.getColumn_name(), column_edited);
                this.getColumns().remove(column_name.strip());
            }
            else{
                System.out.println("New column name cannot be empty");
            }
        } else {
            System.out.println("The column you want to edit does not exist");
        }
    }


    //TODO Think about how to re-arrange column. Is it only on front-end or just backend
    // reorder the task on back end
    //TODO Where will the new task be added to? Bottom or top of the task list?
    public void moveTask_fromColumn_toColumn(String task_name, String old_column_name, String new_column_name){
        if (this.getColumns().containsKey(old_column_name.strip()) && this.getColumns().containsKey(new_column_name.strip())){
            if (this.getColumns().get(old_column_name).getTasks().containsKey(task_name.strip())){
                Task moving_task = this.getColumns().get(old_column_name).getTasks().get(task_name.strip());
                this.getColumns().get(new_column_name).addTasks(moving_task.getTask_name(), moving_task);
                this.getColumns().get(old_column_name).getTasks().remove(task_name.strip());
            }
            else{
                System.out.println("The task you want to edit does not exist in the edit column");
            }
        }else{
            System.out.println("The column you want to edit does not exist");
        }
    }


}



