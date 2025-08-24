package com.queue.servlet;

import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.queue.util.DBConnection;


public class ViewQueueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> tokenList = new ArrayList<>();
        int queueId = Integer.parseInt(request.getParameter("queueId"));


        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT t.token_id, u.name, t.status, t.issued_time " +
                    "FROM Tokens t JOIN Users u ON t.user_id = u.user_id " +
                    "WHERE t.queue_id = ? ORDER BY t.issued_time ASC";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, queueId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Map<String, Object> token = new HashMap<>();
                token.put("tokenId", rs.getInt("token_id"));
                token.put("userName", rs.getString("name"));
                token.put("status", rs.getString("status"));
                token.put("issuedTime", rs.getTimestamp("issued_time"));
                tokenList.add(token);
            }


        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }


        request.setAttribute("tokenList", tokenList);
        RequestDispatcher rd = request.getRequestDispatcher("viewQueue.jsp");
        rd.forward(request, response);
    }
}
