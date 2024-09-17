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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author pasan
 */
@MultipartConfig
@WebServlet(name = "UserImageUpdate", urlPatterns = {"/UserImageUpdate"})
public class UserImageUpdate extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response_DTO response_DTO = new Response_DTO();

        Gson gson = new Gson();

        Part image1 = request.getPart("image1");

        HttpSession httpSession = request.getSession();

        Session session = HibernateUtil.getSessionFactory().openSession();

        if (httpSession.getAttribute("user") != null) {
            //user signed in

            //get user from db
            User_DTO user_DTO = (User_DTO) httpSession.getAttribute("user");
            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));
            User user = (User) criteria1.uniqueResult();

            if (user == null) {
                System.out.println("No user found with the specified email.");
            } else {

                int pid = user.getId();
             

                String applicationPath = request.getServletContext().getRealPath("");
                String newApplicationPath = applicationPath.replace("build" + File.separator + "web", "web");

                File folder = new File(newApplicationPath + File.separator + "profile-images");
                

                File file1 = new File(folder, pid+"image.png");
                InputStream inputStream = image1.getInputStream();
                Files.copy(inputStream, file1.toPath(), StandardCopyOption.REPLACE_EXISTING);

                response_DTO.setSuccess(true);
                response_DTO.setContent("Profile Photo Successfully!");

            }

        }

        session.close();
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(response_DTO));

    }
}
