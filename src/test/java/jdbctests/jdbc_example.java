package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;

public class jdbc_example {

    String dbUrl = "jdbc:oracle:thin:@3.87.241.127:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void test1() throws SQLException {

        //create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        //TYPE_SCROLL_INSENSITIVE => going up and down  within rows in table
        //CONCUR_READ_ONLY=> it will not change anything, not update any data in database
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from departments");

        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //========================================

        // after a connection you can create f\different queries, you dont need to create seperate connection and statement
        resultSet = statement.executeQuery("select * from regions");
        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }


    @Test
    public void countNavigate() throws SQLException {

        //create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        //TYPE_SCROLL_INSENSITIVE => going up and down  within rows in table
        //CONCUR_READ_ONLY=> it will not change anything, not update any data in database
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from departments");

        //how to find how many rows we have for the query
        //go to last row
        resultSet.last();
        //get the row count
        int rowCount = resultSet.getRow();
        System.out.println(rowCount); //rowCount = 27

        //we need move before first row to get full list since we are at the last row right now.
        resultSet.beforeFirst();
        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }




    @Test
    public void MetaDataExample() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from regions");

        //get the database related data inside the dbMetadata object
        DatabaseMetaData dbMetaData = connection.getMetaData();

        System.out.println("User = " + dbMetaData.getUserName());
        System.out.println("dbMetaData.getDatabaseProductName() = " + dbMetaData.getDatabaseProductName());
        System.out.println("dbMetaData.getDatabaseProductVersion() = " + dbMetaData.getDatabaseProductVersion());
        System.out.println("dbMetaData.getDriverName() = " + dbMetaData.getDriverName());
        System.out.println("dbMetaData.getDriverVersion() = " + dbMetaData.getDriverVersion());

        //get the resultset object metadata
        //rsmd
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        //how many columns we have ?
        int colCount = rsMetaData.getColumnCount();
        System.out.println(colCount); //11

        // rsMetadata.getColumnName(1); --> Column Name
        // resultSet.getString(1); --> column value

        //column names
//        System.out.println(rsMetaData.getColumnName(1)); //EMPLOYEE_ID
//        System.out.println(rsMetaData.getColumnName(2)); //FIRST_NAME


        //print all the column names dynamically
        for (int i = 1; i <= colCount; i++) {
            System.out.println(rsMetaData.getColumnName(i));
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }


}
