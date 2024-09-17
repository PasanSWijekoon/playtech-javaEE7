/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_DTO;
import dto.User_DTO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author pasan
 */
@WebServlet(name = "ResetPassword", urlPatterns = {"/ResetPassword"})
public class ResetPassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Response_DTO response_DTO = new Response_DTO();
        Gson gson = new Gson();

        JsonObject dto = gson.fromJson(req.getReader(), JsonObject.class);

        String newpassword = dto.get("newpassword").getAsString();
        String confirmpassword = dto.get("confirmpassword").getAsString();
        String verificationcode = dto.get("newverificationcode").getAsString();

        if (newpassword.isEmpty()) {
            response_DTO.setContent("Please enter New Password");
        } else if (confirmpassword.isEmpty()) {
            response_DTO.setContent("Please enter Confirm Password");
        } else if (verificationcode.isEmpty()) {
            response_DTO.setContent("Please enter Verification code");
        } else if (!newpassword.equals(confirmpassword)) {
            response_DTO.setContent("New Password and Confirm Password are different");
        } else if (!Validations.isPasswordValid(newpassword)) {
            response_DTO.setContent("Password must include atleast one uppercase letter, a number, a special character and be 8 characters long");
        } else {
            
            if (req.getSession().getAttribute("email") != null) {
                //email session available

                String email = req.getSession().getAttribute("email").toString();

                Session session = HibernateUtil.getSessionFactory().openSession();
                Criteria criteria1 = session.createCriteria(User.class);
                criteria1.add(Restrictions.eq("email", email));
                criteria1.add(Restrictions.eq("verification", verificationcode));

                if (!criteria1.list().isEmpty()) {
                    //verified

                    User user = (User) criteria1.list().get(0);
                    user.setVerification("Verified");
                    user.setPassword(newpassword);

                    session.update(user);
                    session.beginTransaction().commit();

                    req.getSession().removeAttribute("email");

                    response_DTO.setSuccess(true);
                    response_DTO.setContent("Verification success");

                } else {
                    //verification wrong
                    response_DTO.setContent("Invalid verification code!");

                }
            }

        }

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(response_DTO));
        System.out.println(gson.toJson(response_DTO));

    }
}
