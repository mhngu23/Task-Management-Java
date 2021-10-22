package JUnitTest;

import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import Item.Project;
import Item.Column;
import Item.Task;

public class projectTester {
    private Project project;

    @Before
    public void setUp() {project =  new Project("Assignment 1", "mhngu23", "default");
    }

    @After
    public void tearDown() {project = null;}


    @Test
    public void test_addColumn(){
        /*
        Testing project.addColumn function adds correct numbers of column.
         */
        Column column = new Column("to do");
        Column column_1 = new Column("in progress");
        this.project.addColumns(column.getColumn_name(), column);
        this.project.addColumns(column_1.getColumn_name(), column_1);
        Assert.assertEquals(2, this.project.getColumns().size());
    }
    @Test
    public void test_newColumn_withEmptyInput(){
        /*
        Testing project.newColumn function with empty input
        --> If the column name is empty will print "New column need a name"
        --> New column will not be create
        */
        this.project.newColumn("     ");
        Assert.assertEquals(0, this.project.getColumns().size());
    }

    @Test
    public void test_newColumn_withInput(){
        /*
        Testing project.newColumn function with input
        --> New column will be create
        */
        this.project.newColumn("to do");
        this.project.newColumn("     ");
        Assert.assertEquals(1, this.project.getColumns().size());
    }

    @Test
    public void test_deleteColumn_withEmptyInput(){
        /*
        Testing project.deleteColumn function with empty input
        --> This will print "The column you want to delete does not exist"
        */
        this.project.newColumn("to do");
        this.project.newColumn("in progress");
        this.project.deleteColumn("     ");
        Assert.assertEquals(2, this.project.getColumns().size());
    }

    @Test
    public void test_deleteColumn_withoutEmptyInput_deleteColumnNotExist(){
        /*
        Testing project.deleteColumn function with input
        Delete column does not exist
        --> This will print "The column you want to delete does not exist"
        */
        this.project.newColumn("to do");
        this.project.newColumn("in progress");
        this.project.deleteColumn("  sadas   ");
        Assert.assertEquals(2, this.project.getColumns().size());
    }

    @Test
    public void test_deleteColumn_withoutEmptyInput_deleteColumnExist(){
        /*
        Testing project.deleteColumn function with input
        Delete column exists
        --> This will remove the column from the project.columnHashMap
        */
        this.project.newColumn("to do");
        this.project.newColumn("in progress");
        this.project.deleteColumn("to do");
        Assert.assertEquals(1, this.project.getColumns().size());
    }

    @Test
    public void test_editColumnName_columnEditNotExist(){
        /*
        Testing project.editColumn function with input
        Edit column does not exist
        --> This will print "The column you want to edit does not exist"
        */
        this.project.newColumn("to do");
        this.project.editColumnName("in progressss","inprogress");
        this.project.editColumnName("     ","inprogress");
        Assert.assertEquals(1, this.project.getColumns().size());
    }

    @Test
    public void test_editColumnName_columnEditExist_newColumnNameIsEmpty(){
        /*
        Testing project.editColumn function with input
        Edit column exists
        --> This will print "New column name cannot be empty"
        */
        this.project.newColumn("to do");
        this.project.editColumnName("to do","    ");
        Assert.assertEquals("to do", this.project.getColumns().get("to do").getColumn_name());
    }

    @Test
    public void test_editColumnName_columnEditExist_newColumnNameIsNotEmpty(){
        /*
        Testing project.editColumn function with input
        Edit column exists
        --> This will change the name of the column to the new name
        --> The column object should be the same
        */
        this.project.newColumn("to do");
        Column column = this.project.getColumns().get("to do");
        this.project.editColumnName("to do","new column name");
        Assert.assertEquals("new column name", this.project.getColumns().get("new column name").getColumn_name());
        Assert.assertEquals(column, this.project.getColumns().get("new column name"));
    }

    @Test
    public void test_moveTaskFromColumnToColumn_taskExist(){
        /*
        Testing project.moveTask_fromColumn_toColumn
        Edit column exists and task exists in this column
        --> Original column will delete task and new column will add task
         */
        this.project.newColumn("to do");
        this.project.newColumn("in progress");
        this.project.getColumns().get("to do").newTask("reading", "read the project Brief");
        this.project.moveTask_fromColumn_toColumn("reading", "to do", "in progress");
        Assert.assertEquals(0, this.project.getColumns().get("to do").getTasks().size());
        Assert.assertEquals(1, this.project.getColumns().get("in progress").getTasks().size());
        Assert.assertFalse(this.project.getColumns().get("to do").getTasks().containsKey("reading"));
        Assert.assertTrue(this.project.getColumns().get("in progress").getTasks().containsKey("reading"));
    }

    @Test
    public void test_moveTaskFromColumnToColumn_taskNotExist(){
        /*
        Testing project.moveTask_fromColumn_toColumn
        Edit column exists and task does not exist in this column
        --> This will print "The task you want to edit does not exist in the edit column"
         */
        this.project.newColumn("to do");
        this.project.newColumn("in progress");
        this.project.getColumns().get("to do").newTask("reading", "read the project Brief");
        this.project.moveTask_fromColumn_toColumn("writing", "to do", "in progress");
        Assert.assertEquals(1, this.project.getColumns().get("to do").getTasks().size());
        Assert.assertEquals(0, this.project.getColumns().get("in progress").getTasks().size());
        Assert.assertTrue(this.project.getColumns().get("to do").getTasks().containsKey("reading"));
        Assert.assertFalse(this.project.getColumns().get("in progress").getTasks().containsKey("reading"));
    }

    @Test
    public void test_moveTaskFromColumnToColumn_columnNotExist(){
        /*
        Testing project.moveTask_fromColumn_toColumn
        Edit column does not exist
        --> This will print "The column you want to edit does not exist"
         */
        this.project.newColumn("to do");
        this.project.newColumn("in progress");
        this.project.getColumns().get("to do").newTask("reading", "read the project Brief");
        this.project.moveTask_fromColumn_toColumn("writing", "to dooo", "in progress");
        this.project.moveTask_fromColumn_toColumn("writing", "to do", "in progressss");
        Assert.assertEquals(1, this.project.getColumns().get("to do").getTasks().size());
        Assert.assertEquals(0, this.project.getColumns().get("in progress").getTasks().size());
        Assert.assertTrue(this.project.getColumns().get("to do").getTasks().containsKey("reading"));
        Assert.assertFalse(this.project.getColumns().get("in progress").getTasks().containsKey("reading"));
    }



}
