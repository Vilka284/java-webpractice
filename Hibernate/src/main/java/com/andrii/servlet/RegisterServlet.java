package com.andrii.servlet;


import com.andrii.dao.RoleDAO;
import com.andrii.dao.UserDAO;
import com.andrii.entity.User;
import com.google.gson.JsonObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static RoleDAO roleDAO = RoleDAO.getInstance();
    private static UserDAO userDAO = UserDAO.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setPassword(request.getParameter("pswd"));
        user.setRole(roleDAO.getRoleByName("user"));

        if (userDAO.register(user)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("currentSessionUser", user);
            session.setAttribute("userRole", "user");
            response.sendRedirect("http://localhost:8080/store");
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            JsonObject json = new JsonObject();
            json.addProperty("message", "user already exist");
            out.print(json.toString());
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("register.jsp");
        view.forward(request, response);
    }
}
