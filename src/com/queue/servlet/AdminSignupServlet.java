package com.queue.servlet;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.queue.util.DBConnection;

public class AdminSignupServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");

        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        try(Connection conn = DBConnection.getConnection()){
            String query = "SELECT * FROM admins WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,userName);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                request.setAttribute("errorMsg","Username Already Exists!");
                RequestDispatcher rd = request.getRequestDispatcher("adminSignup.jsp");
                rd.forward(request,response);
                return;
            }
            if(!password.equals(confirmPassword)){
                request.setAttribute("errorMsg","Password Not Matching!");
                RequestDispatcher rd = request.getRequestDispatcher("adminSignup.jsp");
                rd.forward(request,response);
                return;
            }
            query = "INSERT INTO admins(username,password) VALUES(?, ?)";
            pst = conn.prepareStatement(query);
            pst.setString(1,userName);
            pst.setString(2,password);
            pst.executeUpdate();
            response.sendRedirect("adminLogin.jsp");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
