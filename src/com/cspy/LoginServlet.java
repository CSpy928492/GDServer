package com.cspy;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;


public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setHeader("Access-Control-Allow-Origin", "*");
        System.out.println("connected");

        Enumeration<String> paramName = req.getParameterNames();
        HashMap<String, String> map = new HashMap<>();
        while (paramName.hasMoreElements()) {
            String pName = paramName.nextElement();
            map.put(pName, req.getParameter(pName));
        }

        resp.setContentType("text/html;charset=utf-8");

        PrintWriter pw = resp.getWriter();
        JSONObject result = new JSONObject();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println("username=" + username);
        System.out.println("password=" + password);

        String token = loginValid(username, password);

        if (token.equals("invalid")) {
            result.put("state",-1);
        } else {
            result.put("state",0);
            result.put("token",token);
        }
        pw.println(result.toString());
        pw.close();
    }

    private String loginValid(String username, String password) {
        if (validString(username) && validString(password)) {
            return "token:" + username + password;
        } else {
            return "invalid";
        }
    }

    private boolean validString(String str) {
        return str != null && str.length() != 0;
    }

}
