package com.queue.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.queue.util.DBConnection;
import java.sql.Connection;
import javax.servlet.annotation.WebServlet;


//"C:\Program Files\Java\jdk-17\bin\jar.exe" -cvf DynamicQueueManagement.war *



public class DBConnectionTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try (Connection conn = DBConnection.getConnection()) {
            out.println("<h2> Database connection successful!</h2>");
        } catch (Exception e) {
            out.println("<h2> Database connection failed!</h2>");
            e.printStackTrace(out);
        }
    }
}
