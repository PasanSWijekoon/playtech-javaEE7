/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dto.User_DTO;

/**
 *
 * @author pasan
 */
@WebServlet(name = "GET_User", urlPatterns = {"/GET_User"})
public class GET_User extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    User_DTO user = (User_DTO) request.getSession().getAttribute("user");
    
    Gson gson = new Gson();
    response.setContentType("application/json");
    response.getWriter().write(gson.toJson(user));
    
        
    }
    
    
    
}