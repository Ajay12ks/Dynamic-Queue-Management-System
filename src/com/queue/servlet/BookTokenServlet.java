package com.queue.servlet;

import javax.servlet.*;
import java.sql.*;
import com.queue.util.DBConnection;
import javax.servlet.http.*;
import java.io.*;
import javax.servlet.annotation.WebServlet;




public class BookTokenServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int queueId = Integer.parseInt(request.getParameter("queueId"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        try(Connection conn = DBConnection.getConnection()){
            String userQuery = "INSERT INTO Users(name, phone, email) VALUES(?, ?, ?)";
            PreparedStatement psUser = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);

            psUser.setString(1,name);
            psUser.setString(2,phone);
            psUser.setString(3,email);
            psUser.executeUpdate();

            ResultSet rs = psUser.getGeneratedKeys();
            int userId = 0;
            if(rs.next()){
                userId = rs.getInt(1);
            }

            String tokenQuery = "INSERT INTO Tokens(user_id, queue_id) VALUES(?, ?)";
            PreparedStatement psToken = conn.prepareStatement(tokenQuery, Statement.RETURN_GENERATED_KEYS);

            psToken.setInt(1,userId);
            psToken.setInt(2, queueId);
            psToken.executeUpdate();

            rs = psToken.getGeneratedKeys();
            int tokenId = 0;
            if(rs.next()){
                tokenId = rs.getInt(1);
            }

            request.setAttribute("userName",name);
            request.setAttribute("tokenId",tokenId);
            request.setAttribute("queueId",queueId);

            RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
            rd.forward(request,response);
        }
        catch(Exception e){
            out.println("<h2> Booking failed!</h2>");
            e.printStackTrace(out);
        }

    }
}
