package JUnitTest;

import Item.Task;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import Item.Column;
import org.junit.Test;

public class columnTester {
    private Column column;

    @Before
    public void setUp() {column =  new Column("to do");
    }

    @After
    public void tearDown() {column = null;}

    @Test
    public void test_addTask(){
        /*
        Testing column.addTask function adds correct numbers of column.
         */
        Task task = new Task("readiing", "Read the project brief");
        Task task_1 = new Task("programing", "Backend");
        this.column.addTasks(task.getTask_name(), task);
        this.column.addTasks(task_1.getTask_name(), task_1);
        Assert.assertEquals(2, this.column.getTasks().size());
    }

    @Test
    public void test_newTask_withEmptyInput(){
        /*
        Testing project.newTask function with empty input
        --> If the task name is empty will print "New task need a name"
        --> New task will not be create
        */
        this.column.newTask("     ", "reading articles online");
        Assert.assertEquals(0, this.column.getTasks().size());
    }

    @Test
    public void test_newColumn_withInput(){
        /*
        Testing project.newColumn function with input
        --> New column will be create
        */
        this.column.newTask("reading  ", "reading article online");
        this.column.newTask("     ", "reading articles online");
        this.column.newTask("writing     ", "     ");
        Assert.assertEquals(2, this.column.getTasks().size());
    }

    @Test
    public void test_deleteTask_withEmptyInput_deleteColumnNotExist(){
        /*
        Testing project.deleteColumn function with empty input
        --> This will print "The column you want to delete does not exist"
        */
        this.column.newTask("reading  ", "reading article online");
        this.column.newTask("     ", "reading articles online");
        this.column.deleteTask("     ");
        Assert.assertEquals(1, this.column.getTasks().size());
    }

    @Test
    public void test_deleteTask_withoutEmptyInput_deleteColumnExist(){
        /*
        Testing project.deleteColumn function with input
        Delete column exists
        --> This will remove the column
        */
        this.column.newTask("reading  ", "reading article online");
        this.column.newTask("reading_1", "reading articles online");
        this.column.deleteTask("reading");
        Assert.assertEquals(1, this.column.getTasks().size());
    }

    @Test
    public void test_editTaskNameAndDescription_editTaskExist(){
        /*
        Testing column.editTaskNameAndDescription
        Task exists.
        --> Change task name and task description
         */
        this.column.newTask("reading  ", "reading article online");
        this.column.newTask("reading_1", "reading articles online");
        this.column.editTaskName_andDescription("reading     ", "reading_2", "    ");
        Assert.assertEquals("",this.column.getTasks().get("reading_2").getTask_description());
    }

    @Test
    public void test_editTaskNameAndDescription_editTaskNotExist(){
        /*
        Testing column.editTaskNameAndDescription
        Task does not exist.
        --> This will print "The task you want to edit does not exist"
         */
        this.column.newTask("reading  ", "reading article online");
        this.column.newTask("reading_1", "reading articles online");
        this.column.editTaskName_andDescription("reading 1", "reading_2", "    ");
        Assert.assertEquals("reading articles online",this.column.getTasks().get("reading_1").getTask_description());
    }

    @Test
    public void test_editTaskNameAndDescription_editTaskExist_newTaskNameIsEmpty(){
        /*
        Testing column.editTaskNameAndDescription
        Task exists.
        --> This will print "Task name cannot be empty"
         */
        this.column.newTask("reading  ", "reading article online");
        this.column.newTask("reading_1", "reading articles online");
        this.column.editTaskName_andDescription("reading_1", "   ", "    ");
        Assert.assertEquals("reading articles online",this.column.getTasks().get("reading_1").getTask_description());
    }
}
