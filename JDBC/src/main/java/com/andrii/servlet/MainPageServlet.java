package com.andrii.servlet;

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



@WebServlet(name = "MainServlet")
public class MainPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redirect(request, response);
    }

    static void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

    static void returnJsonResponse(String message, HttpServletResponse response) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("message", message);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }

    static boolean ifAdmin(String role) {
        if (role != null)
            return role.equals("admin");
        return false;
    }

    static boolean ifManager(String role) {
        if (role != null)
            return role.equals("manager");
        return false;
    }

}
