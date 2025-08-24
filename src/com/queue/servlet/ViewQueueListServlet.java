package com.queue.servlet;

import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.queue.util.DBConnection;


public class ViewQueueListServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username = (String) request.getSession().getAttribute("adminUser");
        List<Map<String,Object>> queueList = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection()){
            String query = "SELECT q.queue_id , q.name, q.created_at FROM Queues q WHERE q.admin_id = ( SELECT admin_id FROM Admins WHERE username = ? ) ";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,username);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Map<String,Object> queue = new HashMap<>();
                queue.put("queueId", rs.getInt("queue_id"));
                queue.put("queueName", rs.getString("name"));
                queue.put("createdAt", rs.getTimestamp("created_at"));
                queueList.add(queue);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("queueList",queueList);
        RequestDispatcher rd = request.getRequestDispatcher("viewQueueList.jsp");
        rd.forward(request,response);

    }
}
