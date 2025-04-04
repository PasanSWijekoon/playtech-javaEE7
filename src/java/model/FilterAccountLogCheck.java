/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pasan
 */
@WebFilter (urlPatterns = {"/sign-in.html","/sign-up.html","/verify-account.html","/SignIn","/SignUp","/Verification"})
public class FilterAccountLogCheck implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
                

        if (req.getSession().getAttribute("user") == null) {
            chain.doFilter(request, response);
            //System.out.println("user object ek nh");
        } else {
            res.sendRedirect("index.html");
            //System.out.println("user object ek tiyenwa");
        }
    }

    @Override
    public void destroy() {
    }

}
