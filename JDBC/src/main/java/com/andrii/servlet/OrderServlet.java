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

    private static OrderDAO orderDAO = OrderDAO.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        List<Integer> itemsIdList = new ArrayList<>();

        JSONArray arr = data.getJSONArray("item_id");
        for (int i = 0; i < arr.length(); i++) {
            itemsIdList.add(arr.getJSONObject(i).getInt("id"));
        }
        orderDAO.createOrder(new Order(
                data.getInt("user_id"),
                itemsIdList
        ));

        returnJsonResponse("ok", response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        User u = (User) (session.getAttribute("currentSessionUser"));

        orderDAO.closeOrder(
                data.getInt("order_id"),
                u.getId(),
                data.getInt("item_id"),
                true
        );

        returnJsonResponse("ok", response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        User u = (User) (session.getAttribute("currentSessionUser"));
        String message = "unknown action";

        orderDAO.closeOrder(
                data.getInt("order_id"),
                u.getId(),
                data.getInt("item_id"),
                false
        );
        message = "ok";

        returnJsonResponse("ok", response);
    }
}
