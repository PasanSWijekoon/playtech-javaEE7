/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.User_DTO;
import entity.Address;
import entity.City;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author pasan
 */
@MultipartConfig
@WebServlet(name = "UpdateProfileData", urlPatterns = {"/UpdateProfileData"})
public class UpdateProfileData extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();

        JsonObject responseJsonObject = new JsonObject();
        responseJsonObject.addProperty("success", false);

        HttpSession httpSession = request.getSession();

        Session session = HibernateUtil.getSessionFactory().openSession();

        String firstName = request.getParameter("fn");
        String lastName = request.getParameter("ln");
        String email = request.getParameter("p");
        String password = request.getParameter("d");
        String cityId = request.getParameter("c");
        String address1 = request.getParameter("l1");
        String address2 = request.getParameter("l2");
        String postalCode = request.getParameter("pc");
        String mobile = request.getParameter("m");

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

                Criteria criteria2 = session.createCriteria(Address.class);
                criteria2.add(Restrictions.eq("user", user));
                criteria2.addOrder(Order.desc("id"));
                criteria2.setMaxResults(1);

                Criteria criteria3 = session.createCriteria(City.class);
                criteria3.add(Restrictions.eq("id", Integer.parseInt(cityId)));
                City city = (City) criteria3.list().get(0);

                if (criteria2.list().isEmpty()) {

                    Address address = new Address();
                    address.setCity(city);
                    address.setFirst_name(firstName);
                    address.setLast_name(lastName);
                    address.setLine1(address1);
                    address.setLine2(address2);
                    address.setMobile(mobile);
                    address.setPostal_code(postalCode);
                    address.setUser(user);

                    session.save(address);
                    session.beginTransaction().commit();

                    responseJsonObject.addProperty("success", true);

                } else {
                    //get city

                    //get the current address
                    Address address = (Address) criteria2.list().get(0);
                    address.setLine1(address1);
                    address.setLine2(address2);
                    address.setMobile(mobile);
                    address.setPostal_code(postalCode);
                    address.setCity(city);

                    session.update(address);
                    session.beginTransaction().commit();

                    responseJsonObject.addProperty("success", true);
                }

            }

        }

        session.close();
        System.out.println("session closed");
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseJsonObject));

    }

}
