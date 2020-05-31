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
import java.util.HashSet;


public class UserServlet extends HttpServlet {

    private WriterIO writerIO;

    public UserServlet() throws IOException {
        this.writerIO = new WriterIO("data.txt");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String delete = request.getParameter("data");
        writerIO.deleteUsers(delete);

        request.getRequestDispatcher("user.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        HashSet<String> users = writerIO.readUsers();

        drawTable(writer, users);

        request.getRequestDispatcher("user.jsp").forward(request, response);
    }


    private void drawTable(PrintWriter writer, HashSet<String> users){
        writer.println("<a href=\"index.jsp\">" +
                "<button>" +
                "Create another one user" +
                "</button>" +
                "</a><br><br>");
        writer.println("<table>" +
                "<tr>" +
                "<th>" + "Username" + "</th>" +
                "<th>" + "Password" + "</th>" +
                "<th>" + "Gender" + "</th>" +
                "<th>" + "Music" + "</th>" +
                "<th>" + "Films" + "</th>" +
                "<th>" + "Games" + "</th>" +
                "</tr>");
        for (String st:
                users) {
            String[] user = st.split(",");
            writer.print("<tr>");
            for (String u:
                    user) {
                writer.print("<td>" + u + "</td>");
            }
            writer.println("</tr>");
        }
        writer.print("</table>");
        writer.println("<form method=\"POST\">\n" +
                "    DELETE DATA: <input name=\"data\" placeholder=\" Type any string value to delete \"/>\n" +
                "    <br><br>\n" +
                "    <input type=\"submit\" value=\"Submit\" />");
        writer.close();
    }
}
