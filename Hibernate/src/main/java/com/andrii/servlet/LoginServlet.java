package com.andrii.servlet;

import com.andrii.dao.UserDAO;
import com.andrii.entity.UserEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserEntity u = new UserEntity();
        u.setUserName(request.getParameter("name"));
        u.setPassword(request.getParameter("pswd"));

        if (UserDAO.login(u)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("currentSessionUser", u);
            session.setAttribute("userRole", u.getRoleByRoleId().getRoleName());
            response.sendRedirect("http://localhost:8080/store");
        } else {
            response.sendRedirect("register.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("login.jsp");
        view.forward(request, response);
    }
}