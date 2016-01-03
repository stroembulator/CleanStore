package de.exaxway.cleanstore;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FotoServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
        String photoid = request.getParameter("txtid");
        Blob photo = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query = "select oimage from orderform where  cuname = '" + photoid + "'";
        ServletOutputStream out = response.getOutputStream();

        try {
            conn = getMySqlConnection();
        } catch (Exception e) {
            response.setContentType("text/html");
            out.println("<html><head><title>Person Photo</title></head>");
            out.println("<body><h1>Database Connection Problem.</h1></body></html>");
            return;
        }

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                photo = rs.getBlob(9);

            } else {
                response.setContentType("text/html");
                out.println("<html><head><title>Person Photo</title></head>");
                out.println("<body><h1>No photo found for id= 001 </h1></body></html>");
                return;
            }

            response.setContentType("image/gif");
            InputStream in = photo.getBinaryStream();
            int length = (int) photo.length();

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            while ((length = in.read(buffer)) != -1) {
                System.out.println("writing " + length + " bytes");
                out.write(buffer, 0, length);
            }

            in.close();
            out.flush();
        } catch (SQLException e) {
            response.setContentType("text/html");
            out.println("<html><head><title>Error: Person Photo</title></head>");
            out.println("<body><h1>Error=" + e.getMessage() + "</h1></body></html>");
            return;
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*private static Connection getHSQLConnection() throws Exception {
    Class.forName("jdbc.jdbcDriver");
    System.out.println("Driver Loaded.");
    String url = "jdbc:hsqldb:data/tutorial";
    return DriverManager.getConnection(url, "sa", "");
    }*/
    public static Connection getMySqlConnection() throws Exception {
        String driver = "com.jdbc.mysql.Driver";
        String url = "jdbc:mysql://localhost/studio";
        String username = "root";
        String password = " ";

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    /*public static Connection getOracleConnection() throws Exception {
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:databaseName";
    String username = "username";
    String password = "password";

    Class.forName(driver); // load Oracle driver
    Connection conn = DriverManager.getConnection(url, username, password);
    return conn;
    }*/
}