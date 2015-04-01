package com.troystudios.wackgame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Wise on 22.03.2015.
 */
public enum  DBHandler {

    GET;

    private Connection conn = null;
    private Statement stmt = null;
    private  ResultSet rs = null;

    DBHandler() {
        try {
//			new com.mysql.jdbc.Driver();
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net/sql371414";
            String connectionUser = "sql371414";
            String connectionPassword = "wT1!fJ1*";
            conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
            stmt = conn.createStatement();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
        ResultSet getAll()
        {
            try {
                rs = stmt.executeQuery("SELECT * FROM users");

//            while (rs.next()) {
//                String id = rs.getString("id");
//                String firstName = rs.getString("name");
//                String lastName = rs.getString("password");
//                System.out.println("ID: " + id + ", First Name: " + firstName
//                        + ", Last Name: " + lastName);
//            }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rs;
        }

        void insertUsers(String nickname,String email,String password,String results)
        {
            String sql = "INSERT INTO `sql371414`.`players` ( `nickname`, `email`, `password`, `results`) " +
                    "VALUES ( "+nickname+", "+email+", "+password+", "+results+");";
            try {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    void updateResults(String email, String results)
    {
        String sql = "UPDATE sql371414`.`players` " +
                "SET results = "+results+" WHERE email ="+email;
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//
//        try {
////			new com.mysql.jdbc.Driver();
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//// conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase?user=testuser&password=testpassword");
//            String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net/sql371414"; //"jdbc:mysql://mysql12.000webhost.com/a3299918_db";
//            String connectionUser = "sql371414";//"a3299918_admin";
//            String connectionPassword = "wT1!fJ1*";// "admin01";
//            conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
//            stmt = conn.createStatement();
//            rs = stmt.executeQuery("SELECT * FROM users");
//            while (rs.next()) {
//                String id = rs.getString("id");
//                String firstName = rs.getString("name");
//                String lastName = rs.getString("password");
//                System.out.println("ID: " + id + ", First Name: " + firstName
//                        + ", Last Name: " + lastName);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
//        }
//    }
}
