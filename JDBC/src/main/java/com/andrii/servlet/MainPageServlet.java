package com.andrii.servlet;

import com.andrii.dao.ItemDAO;
import com.andrii.dao.OrderDAO;
import com.andrii.dao.UserDAO;
import com.andrii.module.item.Item;
import com.andrii.module.order.Order;
import com.andrii.module.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
        JsonObject json = new JsonObject();
        User u = (User) (session.getAttribute("currentSessionUser"));
        String role = (String) session.getAttribute("userRole");
        String action = data.getString("action");
        String message = "unknown action";

        switch (action) {
            case "add_item":
                if (ifAdmin(role) || ifManager(role)) {
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
                if (ifAdmin(role) || ifManager(role)) {
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
                            2
                    );
                    message = "ok";
                }
                break;
            case "remove_manager":
                if (ifAdmin(role)) {
                    UserDAO.setRole(
                            data.getInt("userId"),
                            1
                    );
                    message = "ok";
                }
                break;
            case "add_order":
                List<Integer> itemsIdList = new ArrayList<>();
                JSONArray arr = data.getJSONArray("item_id");
                for (int i = 0; i < arr.length(); i++) {
                    itemsIdList.add(arr.getJSONObject(i).getInt("id"));
                }
                OrderDAO.createOrder(new Order(
                        data.getInt("user_id"),
                        itemsIdList
                ));
                message = "ok";
                break;
            case "cancel_order":
                // buy = false
                OrderDAO.removeItemById(
                        u.getId(),
                        data.getInt("item_id"),
                        false
                );
                message = "ok";
                break;
            case "finish_order":
                // buy = true
                OrderDAO.removeItemById(
                        u.getId(),
                        data.getInt("item_id"),
                        true
                );
                message = "ok";
                break;
            case "get_items":
                List<Item> itemsList = ItemDAO.getItemsByGroupId(data.getInt("groupId"));
                for (Item i:
                     itemsList) {
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    String jsonItem = ow.writeValueAsString(i);
                    json.addProperty(i.getItemName(), jsonItem);
                }
                message = "ok";
                break;
            default:
                break;
        }

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

        if (role != null) {
            switch (role) {
                case "admin":
                case "manager":
                    view = request.getRequestDispatcher("admin_page.jsp");
                    break;
                case "user":
                    view = request.getRequestDispatcher("user_page.jsp");
                    break;
                default:
                    response.sendRedirect("http://localhost:8080/login");
                    break;
            }
        } else {
            response.sendRedirect("http://localhost:8080/login");
        }

        if (view != null)
            view.forward(request, response);

    }


    private boolean ifAdmin(String role) {
        if (role != null)
            return role.equals("admin");
        return false;
    }

    private boolean ifManager(String role) {
        if (role != null)
            return role.equals("manager");
        return false;
    }

}
