/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.User_DTO;
import entity.Order_item;
import entity.Orders;
import entity.Product;
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
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author pasan
 */
@WebServlet(name = "LoadInvoiceData", urlPatterns = {"/LoadInvoiceData"})
public class LoadInvoiceData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        HttpSession httpSession = request.getSession();
        Session session = HibernateUtil.getSessionFactory().openSession();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("success", false);

        String orderid = request.getParameter("orderid");

        Orders orders = (Orders) session.get(Orders.class, Integer.parseInt(orderid));

        User_DTO user_DTO = (User_DTO) httpSession.getAttribute("user");

        //search user
        Criteria criteria1 = session.createCriteria(User.class);
        criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));
        User user = (User) criteria1.uniqueResult();

        if (user != null) {
            user.setPassword(null); // Nullify password before serialization
        }

        Criteria criteria2 = session.createCriteria(Orders.class);
        criteria2.add(Restrictions.eq("user", user));
        criteria2.add(Restrictions.eq("id", orders.getId()));
        Orders orders1 = (Orders) criteria2.uniqueResult();

        Criteria criteria3 = session.createCriteria(Order_item.class);
        criteria3.add(Restrictions.eq("order", orders1));
        List<Order_item> Orderitemlist = criteria3.list();

        // Nullify the password in any user-related objects in Order items
        for (Order_item orderItem : Orderitemlist) {
            if (orderItem.getProduct().getUser() != null) {
                orderItem.getProduct().getUser().setPassword(null); // Nullify in product's user
            }
            if (orderItem.getOrder().getUser() != null) {
                orderItem.getOrder().getUser().setPassword(null); // Nullify in order's user
            }
        }

        jsonObject.add("Orderlist", gson.toJsonTree(Orderitemlist));
        jsonObject.addProperty("success", true);

        session.close();
        System.out.println("session closed");

        response.setContentType("application/json");
        System.out.println("content type set");

        response.getWriter().write(gson.toJson(jsonObject));
        System.out.println("response sent");

    }

}
