package com.andrii.servlet;

import com.andrii.dao.GroupDAO;
import com.andrii.dao.ItemDAO;
import com.andrii.dao.RoleDAO;
import com.andrii.entity.Item;
import com.andrii.entity.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.andrii.servlet.MainPageServlet.*;

@WebServlet(name = "ItemServlet")
public class ItemServlet extends HttpServlet {

    private static ItemDAO itemDAO = ItemDAO.getInstance();
    private static GroupDAO groupDAO = GroupDAO.getInstance();
    private static RoleDAO roleDAO = RoleDAO.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("userRole");
        String message = "unknown action";
        boolean admin = role.equals(roleDAO.getRoleByName("admin").toString());
        boolean manager = role.equals(roleDAO.getRoleByName("manager").toString());

        if (admin || manager) {
            Item item = new Item();
            item.setName(data.getString("itemName"));
            item.setGroup(groupDAO.getGroupById(data.getInt("itemGroupId")));
            item.setPrice((float) data.getDouble("itemPrice"));
            item.setQuantity(data.getInt("itemQuantity"));
            itemDAO.insertItem(item);
            message = "ok";
        }

        returnJsonResponse(message, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        JsonObject json = new JsonObject();
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("userRole");
        List<Item> itemsList = itemDAO.getItemsByGroupId(data.getInt("groupId"));

        for (Item i :
                itemsList) {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonItem = ow.writeValueAsString(i);
            json.addProperty(i.getName(), jsonItem);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("userRole");
        String message = "unknown action";
        boolean admin = role.equals(roleDAO.getRoleByName("admin").toString());
        boolean manager = role.equals(roleDAO.getRoleByName("manager").toString());

        if (admin || manager) {
            itemDAO.removeItem(
                    data.getInt("itemId")
            );
            message = "ok";
        }

        returnJsonResponse(message, response);
    }
}
