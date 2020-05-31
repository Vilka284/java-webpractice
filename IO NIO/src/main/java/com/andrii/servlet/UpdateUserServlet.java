package com.andrii.servlet;

import com.andrii.writer.WriterIO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

@WebServlet(name = "UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {

    private WriterIO writerIO;

    public UpdateUserServlet() throws IOException {
        this.writerIO = new WriterIO("data.txt");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userData = request.getParameter("data");
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String[] preferences = request.getParameterValues("preferences");

        String[] user = {
                name,
                password,
                gender,
                Arrays.toString(preferences).replaceAll("[\\[\\](){}]", "")
        };

        writerIO.updateUsers(userData, user);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("update.jsp").forward(request, response);
    }
}
