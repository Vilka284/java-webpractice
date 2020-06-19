package com.andrii.servlet;

import com.andrii.dao.OrderDAO;
import com.andrii.entity.Order;
import com.andrii.entity.User;
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

    private static OrderDAO orderDAO = OrderDAO.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        User user = (User) (session.getAttribute("currentSessionUser"));
        List<Integer> itemsIdList = new ArrayList<>();

        JSONArray arr = data.getJSONArray("item_id");
        for (int i = 0; i < arr.length(); i++) {
            itemsIdList.add(arr.getJSONObject(i).getInt("id"));
        }
        Order order = new Order();
        order.setUser(user);
        orderDAO.createOrder(order, itemsIdList);

        returnJsonResponse("ok", response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        User user = (User) (session.getAttribute("currentSessionUser"));

        orderDAO.deleteOrder(
                data.getInt("order_id"),
                data.getInt("item_id"),
                true
        );

        returnJsonResponse("ok", response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        User user = (User) (session.getAttribute("currentSessionUser"));
        String message = "unknown action";

        orderDAO.deleteOrder(
                data.getInt("order_id"),
                data.getInt("item_id"),
                false
        );
        message = "ok";

        returnJsonResponse("ok", response);
    }
}
