package com.andrii.servlet;

import com.andrii.dao.OrderDAO;
import com.andrii.model.Order;
import com.andrii.model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.andrii.servlet.MainPageServlet.returnJsonResponse;

@WebServlet(name = "OrderServlet")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        List<Integer> itemsIdList = new ArrayList<>();

        JSONArray arr = data.getJSONArray("item_id");
        for (int i = 0; i < arr.length(); i++) {
            itemsIdList.add(arr.getJSONObject(i).getInt("id"));
        }
        OrderDAO.createOrder(new Order(
                data.getInt("user_id"),
                itemsIdList
        ));

        returnJsonResponse("ok", response);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        User u = (User) (session.getAttribute("currentSessionUser"));

        OrderDAO.closeOrder(
                u.getId(),
                data.getInt("item_id"),
                true
        );

        returnJsonResponse("ok", response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        User u = (User) (session.getAttribute("currentSessionUser"));
        String message = "unknown action";

        OrderDAO.closeOrder(
                u.getId(),
                data.getInt("item_id"),
                false
        );
        message = "ok";

        returnJsonResponse("ok", response);
    }
}
