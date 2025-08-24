package com.queue.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AuthFilter implements Filter{
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException{
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession sess = req.getSession(false);
        if(sess !=null && sess.getAttribute("adminUser")!=null){
            chain.doFilter(request,response);
        }
        else {
            res.sendRedirect("adminLogin.jsp");
        }


    }
}
