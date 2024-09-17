package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Category;
import entity.Color;
import entity.Model;
import entity.Product;
import entity.Product_Condition;
import entity.Storage;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "SearchProducts", urlPatterns = {"/SearchProducts"})
public class SearchProducts extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();

        JsonObject responseJsonObject = new JsonObject();
        responseJsonObject.addProperty("success", false);

        //get request json
        JsonObject requestJsonObject = gson.fromJson(request.getReader(), JsonObject.class);

        Session session = HibernateUtil.getSessionFactory().openSession();

        //search all products
        Criteria criteria1 = session.createCriteria(Product.class);
        int category_name = Integer.parseInt(requestJsonObject.get("category_name").getAsString());
        
        
        String searchText = requestJsonObject.get("searchText").getAsString(); 
        
           if (searchText != null && !searchText.isEmpty()) {
            criteria1.add(Restrictions.like("title", "%" + searchText + "%").ignoreCase());
        }

//        add category filter
        if (category_name != 0) {
            //category selected

            //get category list from Db
            Criteria criteria2 = session.createCriteria(Category.class);
            criteria2.add(Restrictions.eq("id", category_name));
            List<Category> categoryList = criteria2.list();

            System.out.println("Category List:");
            for (Category category : categoryList) {
                System.out.println("Category ID: " + category.getId() + ", Name: " + category.getName());
            }

            //get models by category from DB
            Criteria criteria3 = session.createCriteria(Model.class);
            criteria3.add(Restrictions.in("category", categoryList));
            List<Model> modelList = criteria3.list();

            // Print Model List
            System.out.println("Model List:");
            for (Model model : modelList) {
                System.out.println("Model ID: " + model.getId() + ", Name: " + model.getName() + ", Category ID: " + model.getCategory().getId());
            }

            //filter products by model list from DB
            criteria1.add(Restrictions.in("model", modelList));
        }

        int condition = Integer.parseInt(requestJsonObject.get("condition").getAsString());

        if (condition != 0) {
            //condition selected

            //get condition from Db
            Criteria criteria4 = session.createCriteria(Product_Condition.class);
            criteria4.add(Restrictions.eq("id", condition));
            Product_Condition product_Condition = (Product_Condition) criteria4.uniqueResult();

            //filter products by condition from DB
            criteria1.add(Restrictions.eq("product_condition", product_Condition));

        }

        int colorname = Integer.parseInt(requestJsonObject.get("color").getAsString());

        if (colorname != 0) {

            //get condition from Db
            Criteria criteria5 = session.createCriteria(Color.class);
            criteria5.add(Restrictions.eq("id", colorname));
            Color productColor = (Color) criteria5.uniqueResult();

            //filter products by condition from DB
            criteria1.add(Restrictions.eq("color", productColor));

        }

        int storage = Integer.parseInt(requestJsonObject.get("storage").getAsString());

        if (storage != 0) {
            //color selected

            //get condition from Db
            Criteria criteria6 = session.createCriteria(Storage.class);
            criteria6.add(Restrictions.eq("id", storage));
            Storage storageValue = (Storage) criteria6.uniqueResult();

            //filter products by condition from DB
            criteria1.add(Restrictions.eq("storage", storageValue));

        }

        String price_range_start = requestJsonObject.get("price_range_start").getAsString();
        String price_range_end = requestJsonObject.get("price_range_end").getAsString();

        if (!price_range_start.isEmpty()) {

            double startPrice = Double.parseDouble(price_range_start);
            System.out.println(startPrice);
            criteria1.add(Restrictions.ge("price", startPrice));
        }

        if (!price_range_end.isEmpty()) {

            double endPrice = Double.parseDouble(price_range_end);
            System.out.println(endPrice);
            criteria1.add(Restrictions.le("price", endPrice));

        }

       

//filter products by sort from Db
        int sortText = Integer.parseInt(requestJsonObject.get("sort_text").getAsString());

        if (sortText == 0) {
            criteria1.addOrder(Order.desc("id"));

        } else if (sortText == 1) {
            criteria1.addOrder(Order.asc("id"));

        } else if (sortText == 2) {
            criteria1.addOrder(Order.asc("title"));

        } else if (sortText == 3) {
            criteria1.addOrder(Order.asc("price"));

        }
        //get all product count
        responseJsonObject.addProperty("allProductCount", criteria1.list().size());

        //set product range
        int firstResult = requestJsonObject.get("firstResult").getAsInt();
        criteria1.setFirstResult(firstResult);
        criteria1.setMaxResults(6);

        //get product list
        List<Product> productList = criteria1.list();
        System.out.println(productList.size());

        //get product list
        for (Product product : productList) {
            product.setUser(null);
            System.out.println("Product ID: " + product.getId() + ", Name: " + product.getTitle() + ", Model ID: " + product.getModel().getId());
        }

        responseJsonObject.addProperty("success", true);
        responseJsonObject.add("productList", gson.toJsonTree(productList));

        //send response
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseJsonObject));

    }

}
