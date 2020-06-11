package com.andrii.servlet;

import com.andrii.dao.UserDAO;
import com.andrii.model.User;
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

    private static UserDAO userDAO;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User u = new User();
        u.setUsername(request.getParameter("name"));
        u.setPassword(request.getParameter("pswd"));
        u.setRole("user");

        boolean valid = userDAO.register(u);
        u.setValid(valid);

        if (valid) {
            HttpSession session = request.getSession(true);
            session.setAttribute("currentSessionUser", u);
            session.setAttribute("userRole", u.getRole());
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
