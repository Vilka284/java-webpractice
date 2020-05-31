package com.andrii.servlet;

import com.andrii.writer.WriterIO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;


public class RegisterUserServlet extends HttpServlet {

    private WriterIO writerIO;

    public RegisterUserServlet() throws IOException {
        this.writerIO = new WriterIO("data.txt");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
        writerIO.writeUsers(user);

        request.getRequestDispatcher("user.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}


