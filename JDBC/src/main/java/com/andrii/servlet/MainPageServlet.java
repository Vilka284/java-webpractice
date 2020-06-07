package com.andrii.servlet;

import com.andrii.dao.ItemDAO;
import com.andrii.dao.OrderDAO;
import com.andrii.dao.UserDAO;
import com.andrii.module.item.Item;
import com.andrii.module.order.Order;
import com.andrii.module.user.User;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "MainServlet")
public class MainPageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        User u = (User) (session.getAttribute("currentSessionUser"));
        String role = (String) session.getAttribute("userRole");
        String action = data.getString("action");
        String message = "unknown action";

        switch (action) {
            case "add_item":
                if (ifAdmin(role)) {
                    ItemDAO.insertItem(
                            new Item(
                                    data.getString("itemName"),
                                    data.getInt("itemGroupId"),
                                    (float) data.getDouble("itemPrice"),
                                    data.getInt("itemQuantity")
                            )
                    );
                    message = "ok";
                }
                break;
            case "remove_item":
                if (ifAdmin(role)) {
                    ItemDAO.removeItem(
                            data.getInt("itemId")
                    );
                    message = "ok";
                }
                break;
            case "add_manager":
                if (ifAdmin(role)) {
                    UserDAO.setRole(
                            data.getInt("userId"),
                            data.getInt("roleId")
                    );
                    message = "ok";
                }
                break;
            case "add_order":
                List<Integer> itemsList = new ArrayList<>();
                JSONArray arr = data.getJSONArray("item_id");
                for(int i = 0; i < arr.length(); i++){
                    itemsList.add(arr.getJSONObject(i).getInt("id"));
                }
                OrderDAO.createOrder(new Order(
                        data.getInt("user_id"),
                        itemsList
                ));
                break;
            case "remove_item_from_order":
                OrderDAO.removeItemById(
                        u.getId(),
                        data.getInt("item_id")
                );
                break;
            default:
                break;
        }

        JsonObject json = new JsonObject();
        json.addProperty("message", message);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = null;
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("userRole");

        if (ifAdmin(role))
            view = request.getRequestDispatcher("admin_page.jsp");
        else if (role.equals("user"))
            view = request.getRequestDispatcher("user_page.jsp");
        else
            response.sendRedirect("register.jsp");

        assert view != null;
        view.forward(request, response);

    }

    private boolean ifAdmin(String role){
        if (role != null)
            return role.equals("admin") || role.equals("manager");
        return false;
    }

}
