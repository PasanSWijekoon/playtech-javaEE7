/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dto.Response_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Mail;

/**
 *
 * @author pasan
 */

@MultipartConfig
@WebServlet(name = "process_form", urlPatterns = {"/process_form"})
public class process_form extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response_DTO response_DTO = new Response_DTO();

        Gson gson = new Gson();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");
        
        String formmessage = "Hello ! My Name Is " +name+ " <br> My Email "+email+" <br> My Message<br>" +message+" ";
        
        Thread sendMailThread = new Thread(() ->   Mail.sendMail("shopwebtoons@gmail.com", "Customer Contact", formmessage, true));
        sendMailThread.start();
      
        
        response_DTO.setSuccess(true);
        response_DTO.setContent("Message Send To Our Contact Department Please Wait Mail From Us");

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(response_DTO));

    }

}
