package com.andrii.servlet;

import com.andrii.dao.CruderIO;
import com.andrii.module.user.User;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Collectors;


@WebServlet(name = "MainServlet")
public class MainServlet extends HttpServlet {

    private CruderIO cruderIO;
    private JsonObject json;

    public MainServlet() throws IOException {
        this.cruderIO = new CruderIO("users.db");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        // json to String
        String name = data.getString("name");
        String pswd = data.getString("pswd");
        String gender = data.getString("gender");
        String pref = data.getString("pref");
        User u = new User(
                name,
                pswd,
                gender,
                pref
        );

        boolean state = cruderIO.writeUser(u);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        json = new JsonObject();
        json.addProperty("message", state ? "ok" : "error writing in file");
        out.print(json.toString());
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cruderIO.deleteUser(cruderIO.findUserByName("Android"));
        Map<String, String> idUserMap = cruderIO.readUsers().stream()
                .collect(Collectors.toMap(User::getUid, User::getName));
        json = new JsonObject();
        idUserMap.forEach((id, name) -> json.addProperty(("id_" + id), name));
        json.addProperty("message", "ok");

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        String nameToBeUpdated = data.getString("nameToBeUpdated");
        String name = data.getString("name");
        String pswd = data.getString("pswd");
        String gender = data.getString("gender");
        String pref = data.getString("pref");
        User u = new User(
                name,
                pswd,
                gender,
                pref
        );

        boolean state = cruderIO.updateUser(cruderIO.findUserByName(nameToBeUpdated), u);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        json = new JsonObject();
        json.addProperty("message", state ? "ok" : "error writing in file");
        out.print(json.toString());
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject(request.getParameter("myData"));
        String name = data.getString("nameToDelete");

        boolean state = cruderIO.deleteUser(cruderIO.findUserByName(name));

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        json = new JsonObject();
        json.addProperty("message", state ? "ok" : "error writing in file");
        out.print(json.toString());
        out.flush();
    }
}
