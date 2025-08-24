package com.queue.servlet;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.queue.util.DBConnection;

public class AdminLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        try(Connection conn = DBConnection.getConnection()){
            String query = "SELECT * FROM admins WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,userName);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                if(rs.getString("password").equals(password)){
                    HttpSession session = request.getSession();
                    session.setAttribute("adminUser",userName);
                    response.sendRedirect("adminDashboard.jsp");
                }
                else {
                    request.setAttribute("errorMsg","Invalid Password");
                    RequestDispatcher rd = request.getRequestDispatcher("adminLogin.jsp");
                    rd.forward(request,response);
                    return;
                }
            }
            else {
                request.setAttribute("errorMsg","User Not Found!");
                RequestDispatcher rd = request.getRequestDispatcher("adminLogin.jsp");
                rd.forward(request,response);
                return;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
