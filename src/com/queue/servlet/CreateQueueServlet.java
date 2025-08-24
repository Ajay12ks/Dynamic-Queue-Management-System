package com.queue.servlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.queue.util.DBConnection;

public class CreateQueueServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String queueName = request.getParameter("queueName");
        String userName = (String) request.getSession().getAttribute("adminUser");
        int queueId=0;
        try(Connection conn=DBConnection.getConnection()){
            String adminQuery = "SELECT admin_id FROM Admins WHERE username = ?";
            PreparedStatement pst1 = conn.prepareStatement(adminQuery);
            pst1.setString(1, userName);
            ResultSet rs1 = pst1.executeQuery();
            if (rs1.next()) {
                int adminId = rs1.getInt("admin_id");
                String insertQueue = "INSERT INTO Queues(name, admin_id) VALUES(?, ?)";
                PreparedStatement pst2 = conn.prepareStatement(insertQueue, Statement.RETURN_GENERATED_KEYS);
                pst2.setString(1, queueName);
                pst2.setInt(2, adminId);

                int rows = pst2.executeUpdate();

                if (rows > 0) {
                    ResultSet rsKeys = pst2.getGeneratedKeys();
                    if (rsKeys.next()) {
                        queueId = rsKeys.getInt(1);
                    }
                }
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("queueId",queueId);
        request.setAttribute("queueName",queueName);
        RequestDispatcher rd = request.getRequestDispatcher("queueInfo.jsp");
        rd.forward(request,response);

    }
}
