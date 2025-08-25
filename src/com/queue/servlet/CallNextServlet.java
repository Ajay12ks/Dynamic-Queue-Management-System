package com.queue.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.queue.util.DBConnection;

public class CallNextServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int queueId = Integer.parseInt(request.getParameter("queueId"));

        try (Connection conn = DBConnection.getConnection()) {

            //  Step 1: If current token is CALLED, mark it as COMPLETED
            String checkQuery = "SELECT token_id FROM Tokens WHERE queue_id = ? AND position = 1 AND status = 'CALLED'";
            PreparedStatement pst = conn.prepareStatement(checkQuery);
            pst.setInt(1, queueId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Mark current as completed
                String updateComplete = "UPDATE Tokens SET status = 'COMPLETED' WHERE queue_id = ? AND position = 1";
                pst = conn.prepareStatement(updateComplete);
                pst.setInt(1, queueId);
                pst.executeUpdate();

                // Shift all others forward
                String shiftQuery = "UPDATE Tokens SET position = position - 1 WHERE queue_id = ? AND position > 1";
                pst = conn.prepareStatement(shiftQuery);
                pst.setInt(1, queueId);
                pst.executeUpdate();
            }

            // Step 2: Call the next waiting token (smallest position)
            String callNext =
                    "UPDATE Tokens " +
                            "SET status = 'CALLED', position = 1 " +
                            "WHERE queue_id = ? AND status = 'WAITING' " +
                            "ORDER BY position " +
                            "LIMIT 1";
            pst = conn.prepareStatement(callNext);
            pst.setInt(1, queueId);
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect back to view queue
        response.sendRedirect("viewQueue?queueId=" + queueId);
    }
}
