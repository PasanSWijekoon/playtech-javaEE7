/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dto.Cart_DTO;
import dto.User_DTO;
import entity.Cart;
import entity.Category;
import entity.Color;
import entity.Order_item;
import entity.Orders;
import entity.Product;
import entity.Product_Condition;
import entity.Storage;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "LoadPurchaseData", urlPatterns = {"/LoadPurchaseData"})
public class LoadPurchaseData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        HttpSession httpSession = request.getSession();
        Session session = HibernateUtil.getSessionFactory().openSession();

        JsonObject jsonObject = new JsonObject();
        JsonArray orderListJsonArray = new JsonArray();
        jsonObject.addProperty("success", false);

        try {

            if (httpSession.getAttribute("user") != null) {
                //DB Cart

                User_DTO user_DTO = (User_DTO) httpSession.getAttribute("user");

                //search user
                Criteria criteria1 = session.createCriteria(User.class);
                criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));
                User user = (User) criteria1.uniqueResult();

                System.out.println(user.getFirst_name());

                if (user != null) {
                    user.setPassword(null); // Nullify password before serialization
                }

                Criteria criteria = session.createCriteria(Orders.class);
                criteria.add(Restrictions.eq("user", user)); // Assuming you have a `user` object to filter by
                List<Orders> ordersList = criteria.list();

                // Convert the entire list of Orders to JSON
                for (Orders order : ordersList) {
                    // Serialize the Order object to JSON
                    JsonObject orderJson = gson.toJsonTree(order).getAsJsonObject();

                    // Fetch Order_items for the current Order
                    Criteria criteria3 = session.createCriteria(Order_item.class);
                    criteria3.add(Restrictions.eq("order.id", order.getId()));
                    List<Order_item> orderItemsList = criteria3.list();

                    // Create a JsonArray for Order_items
                    JsonArray orderItemsJsonArray = new JsonArray();

                    for (Order_item item : orderItemsList) {
                        // Serialize the Order_item object to JSON and add it to the JsonArray
                        JsonObject itemJson = gson.toJsonTree(item).getAsJsonObject();
                        orderItemsJsonArray.add(itemJson);
                    }

                    // Add the Order_items JsonArray to the Order JsonObject
                    orderJson.add("OrderItems", orderItemsJsonArray);

                    // Add the Order JsonObject to the main orderList JsonArray
                    orderListJsonArray.add(orderJson);
                }

                // Add the orderList JsonArray to the main JsonObject
                jsonObject.add("OrderList", orderListJsonArray);
                jsonObject.addProperty("success", true);

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();
        System.out.println("session closed");

        response.setContentType("application/json");
        System.out.println("content type set");

        response.getWriter().write(gson.toJson(jsonObject));
        System.out.println("response sent");

    }

}
