package com.queue.servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.queue.util.DBConnection;

public class BookTokenServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        int queueId = Integer.parseInt(request.getParameter("queueId"));

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Check if user already has an active token in this queue
            String checkActive = "SELECT t.token_id, t.position " +
                    "FROM Tokens t " +
                    "JOIN Users u ON t.user_id = u.user_id " +
                    "WHERE u.phone = ? AND t.queue_id = ? AND t.status IN ('WAITING','CALLED')";
            PreparedStatement psCheck = conn.prepareStatement(checkActive);
            psCheck.setString(1, phone);
            psCheck.setInt(2, queueId);
            ResultSet rsCheck = psCheck.executeQuery();

            HttpSession session = request.getSession();

            if (rsCheck.next()) {
                //User already has an active token → don’t insert again
                int tokenId = rsCheck.getInt("token_id");
                int pos = rsCheck.getInt("position");

                session.setAttribute("userName", name);
                session.setAttribute("tokenId", tokenId);
                session.setAttribute("queueId", queueId);
                session.setAttribute("position", pos);

                conn.commit();
                response.sendRedirect("success.jsp");
                return;
            }

            //  Insert or fetch User
            String checkUser = "SELECT user_id FROM Users WHERE phone = ?";
            PreparedStatement pstUser = conn.prepareStatement(checkUser);
            pstUser.setString(1, phone);
            ResultSet rsUser = pstUser.executeQuery();

            int userId;
            if (rsUser.next()) {
                userId = rsUser.getInt("user_id");
            } else {
                String insertUser = "INSERT INTO Users (name, phone) VALUES (?, ?)";
                PreparedStatement insUser = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
                insUser.setString(1, name);
                insUser.setString(2, phone);
                insUser.executeUpdate();
                ResultSet genKeys = insUser.getGeneratedKeys();
                genKeys.next();
                userId = genKeys.getInt(1);
            }

            //  Find next position in this queue
            String maxPosQuery = "SELECT COALESCE(MAX(position), 0) FROM Tokens WHERE queue_id = ?";
            PreparedStatement pstMax = conn.prepareStatement(maxPosQuery);
            pstMax.setInt(1, queueId);
            ResultSet rsMax = pstMax.executeQuery();
            rsMax.next();
            int nextPos = rsMax.getInt(1) + 1;

            //  Insert token
            String insertToken = "INSERT INTO Tokens (queue_id, user_id, status, position) VALUES (?, ?, 'WAITING', ?)";
            PreparedStatement pstToken = conn.prepareStatement(insertToken, Statement.RETURN_GENERATED_KEYS);
            pstToken.setInt(1, queueId);
            pstToken.setInt(2, userId);
            pstToken.setInt(3, nextPos);
            pstToken.executeUpdate();
            ResultSet rsToken = pstToken.getGeneratedKeys();
            rsToken.next();
            int tokenId = rsToken.getInt(1);

            // Save details in session (for success.jsp)
            session.setAttribute("userName", name);
            session.setAttribute("tokenId", tokenId);
            session.setAttribute("queueId", queueId);
            session.setAttribute("position", nextPos);

            conn.commit();
            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error booking token", e);
        }
    }
}
