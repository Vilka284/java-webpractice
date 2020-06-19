package com.andrii.servlet;

import com.andrii.dao.RoleDAO;
import com.andrii.dao.UserDAO;
import com.andrii.entity.User;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.andrii.servlet.MainPageServlet.returnJsonResponse;

@WebServlet(name = "ManagementServlet")
public class ManagementServlet extends HttpServlet {

    private static UserDAO userDAO = UserDAO.getInstance();
    private static RoleDAO roleDAO = RoleDAO.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        User user = (User) (session.getAttribute("currentSessionUser"));
        String role = (String) session.getAttribute("userRole");
        String message = "unknown action";
        boolean admin = role.equals(roleDAO.getRoleByName("admin").toString());

        if (admin) {
            userDAO.setRole(
                    data.getInt("userId"),
                    2
            );
            message = "ok";
        }

        returnJsonResponse(message, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MainPageServlet.redirect(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        HttpSession session = request.getSession();
        User user = (User) (session.getAttribute("currentSessionUser"));
        String role = (String) session.getAttribute("userRole");
        String message = "unknown action";
        boolean admin = role.equals(roleDAO.getRoleByName("admin").toString());

        if (admin) {
            userDAO.setRole(
                    data.getInt("userId"),
                    1
            );
            message = "ok";
        }

        returnJsonResponse(message, response);
    }
}
