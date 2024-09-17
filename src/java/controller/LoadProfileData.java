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
import entity.Order_item;
import entity.Orders;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
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
@WebServlet(name = "LoadProfileData", urlPatterns = {"/LoadProfileData"})
public class LoadProfileData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("success", false);

        HttpSession httpSession = request.getSession();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            if (httpSession.getAttribute("user") != null) {

                User_DTO user_DTO = (User_DTO) httpSession.getAttribute("user");

                //get user from DB
                Criteria criteria1 = session.createCriteria(User.class);
                criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));
                User user = (User) criteria1.uniqueResult();

                if (user == null) {
                    System.out.println("No user found with the specified email.");

                } else {
//                   get user's last address from DB
                    Criteria criteria2 = session.createCriteria(Address.class);
                    criteria2.add(Restrictions.eq("user", user));
                    criteria2.addOrder(Order.desc("id"));
                    criteria2.setMaxResults(1);
                    Address address = (Address) criteria2.uniqueResult();

                    if (address == null) {
                        jsonObject.add("user", gson.toJsonTree(user));
                    } else {

                        jsonObject.add("address", gson.toJsonTree(address));
                    }

                }

                //get cities from DB
                Criteria criteria3 = session.createCriteria(City.class);
                criteria3.addOrder(Order.asc("name"));
                List<City> cityList = criteria3.list();

                jsonObject.add("cityList", gson.toJsonTree(cityList));
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
