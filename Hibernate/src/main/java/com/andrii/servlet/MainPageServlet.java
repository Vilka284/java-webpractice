package com.andrii.servlet;


import com.andrii.dao.*;
import com.andrii.entity.ItemEntity;
import com.andrii.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonObject;
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
import java.util.List;


@WebServlet(name = "MainServlet")
public class MainPageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        JsonObject json = new JsonObject();
        UserEntity u = (UserEntity) (session.getAttribute("currentSessionUser"));
        String role = (String) session.getAttribute("userRole");
        String action = data.getString("action");
        String message = "unknown action";

        boolean admin = u.getRoleByRoleId().equals(RoleDAO.getRoleAdmin());
        boolean manager = u.getRoleByRoleId().equals(RoleDAO.getRoleManager());

        switch (action) {
            case "add_item":
                if (admin || manager) {
                    ItemEntity i = new ItemEntity();
                    i.setItemName(data.getString("itemName"));
                    i.setGroupByGroupId(GroupDAO.getGroupById(data.getInt("itemGroupId")));
                    i.setPrice((float) data.getDouble("itemPrice"));
                    i.setQuantity(data.getInt("itemQuantity"));
                    ItemDAO.insertItem(i);
                    message = "ok";
                }
                break;
            case "remove_item":
                if (admin || manager) {
                    ItemDAO.removeItem(
                            data.getInt("itemId")
                    );
                    message = "ok";
                }
                break;
            case "add_manager":
                if (admin) {
                    UserDAO.setRole(
                            data.getInt("userId"),
                            2
                    );
                    message = "ok";
                }
                break;
            case "remove_manager":
                if (admin) {
                    UserDAO.setRole(
                            data.getInt("userId"),
                            1
                    );
                    message = "ok";
                }
                break;
            case "cancel_order":
                // buy = false
                OrderDAO.closeOrder(
                        data.getInt("item_id"),
                        false
                );
                break;
            case "finish_order":
                OrderDAO.closeOrder(
                        data.getInt("item_id"),
                        true
                );
                break;
            case "get_items":
                List<ItemEntity> itemsList = ItemDAO.getItemsByGroupId(data.getInt("groupId"));
                for (ItemEntity i:
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



}
