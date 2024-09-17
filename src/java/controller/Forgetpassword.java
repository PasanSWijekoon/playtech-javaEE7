/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dto.Response_DTO;
import entity.User;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Mail;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author pasan
 */
@WebServlet(name = "Forgetpassword", urlPatterns = {"/Forgetpassword"})
public class Forgetpassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response_DTO response_DTO = new Response_DTO();
        String email = req.getParameter("e");
        Gson gson = new Gson();
        System.out.println(email);

        if (email.isEmpty()) {
            response_DTO.setContent("Please enter your Email");
        } else {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("email", email));

            if (!criteria1.list().isEmpty()) {
                User user = (User) criteria1.list().get(0);

                int code = (int) (Math.random() * 1000000);
                user.setVerification(String.valueOf(code));

                // Load the email template
                String emailTemplate = loadEmailTemplate();
                // Replace the placeholder with the actual verification code
                String emailContent = emailTemplate.replace("{{code}}", String.valueOf(code));

                // Send verification email
                Thread sendMailThread = new Thread(() -> Mail.sendMail(email, "Smart Trade Verification", emailContent, true));
                sendMailThread.start();

                session.update(user);
                session.beginTransaction().commit();
                req.getSession().setAttribute("email", email);
                response_DTO.setSuccess(true);
                response_DTO.setContent("Verification success");

            } else {
                response_DTO.setContent("Invalid details!Please try again");
            }

        }

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(response_DTO));
        System.out.println(gson.toJson(response_DTO));

    }

    private String loadEmailTemplate() throws IOException {
        InputStream inputStream = getServletContext().getResourceAsStream("/assets/emailtemplates/forgetpassword.html");

        if (inputStream == null) {
            throw new FileNotFoundException("Email template not found at specified path.");
        }

        // Use StringBuilder to read the input stream line by line
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }
        }

        return contentBuilder.toString();
    }

}
