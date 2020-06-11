package com.andrii.servlet;

import com.andrii.dao.ItemDAO;
import com.andrii.model.Item;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("userRole");
        String message = "unknown action";

        if (ifAdmin(role) || ifManager(role)) {
            ItemDAO.insert(
                    new Item(
                            data.getString("itemName"),
                            data.getInt("itemGroupId"),
                            (float) data.getDouble("itemPrice"),
                            data.getInt("itemQuantity")
                    )
            );
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
        List<Item> itemsList = ItemDAO.getItemsByGroupId(data.getInt("groupId"));

        for (Item i:
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

        if (ifAdmin(role) || ifManager(role)) {
            ItemDAO.removeItem(
                    data.getInt("itemId")
            );
            message = "ok";
        }

        returnJsonResponse(message, response);
    }
}
