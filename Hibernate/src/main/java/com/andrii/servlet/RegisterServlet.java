package com.andrii.servlet;


import com.andrii.dao.RoleDAO;
import com.andrii.dao.UserDAO;
import com.andrii.entity.UserEntity;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserEntity u = new UserEntity();
        u.setUserName(request.getParameter("name"));
        u.setPassword(request.getParameter("pswd"));
        u.setRoleByRoleId(RoleDAO.getRoleUser());

        if (UserDAO.register(u)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("currentSessionUser", u);
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
