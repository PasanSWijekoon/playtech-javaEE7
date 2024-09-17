/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author pasan
 */
@WebServlet(name = "Sessionchecker", urlPatterns = {"/Sessionchecker"})
public class Sessionchecker extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        HttpSession httpSession = request.getSession();
        Session session = HibernateUtil.getSessionFactory().openSession();

        JsonObject jsonObject = new JsonObject();

        try {

            if (httpSession.getAttribute("user") != null) {

                jsonObject.addProperty("success", true);

            } else {
                jsonObject.addProperty("success", false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        System.out.println("content type set");

        response.getWriter().write(gson.toJson(jsonObject));
        System.out.println("response sent");

    }

}
