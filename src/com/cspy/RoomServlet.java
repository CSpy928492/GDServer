package com.cspy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cspy.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class RoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/html;charset=utf-8");


        PrintWriter pw = new PrintWriter(resp.getOutputStream());




        String token = req.getParameter("token");
        if (isValidUser(token)) {
            String type = req.getParameter("type");
            List<Room> rooms = Main.getRooms();
            JSONObject jsonObject = new JSONObject();


            switch (type) {
                case "getRoom":
                    jsonObject.put("state", 0);
                    jsonObject.put("rooms", JSONArray.parseArray(JSONObject.toJSONString(rooms)));

                    pw.println(jsonObject.toString());
                    System.out.println(jsonObject.toString());
                    break;
                case "createRoom":
                    int roomNumber = getRoomNumber(rooms);
                    List<Player> players = new ArrayList<>();
                    Player owner = new Player(getUserByToken(token), token, PlayerState.UNPREPARED, "127.0.0.1");
                    players.add(owner);

                    Room newRoom = new Room(roomNumber, players, RoomState.NOT_READY);
                    rooms.add(newRoom);
                    jsonObject.put("state", 0);
                    jsonObject.put("roomNumber", roomNumber);
                    jsonObject.put("players", JSONArray.parseArray(JSONObject.toJSONString(players)));
                    pw.println(jsonObject.toString());
                    System.out.println("新加入房间 room :" + newRoom);

                    break;
                case "joinRoom":
                    String selectedRoomNumber = req.getParameter("roomNumber");
                    if (rooms.contains(new Room(Integer.parseInt(selectedRoomNumber),null,null))) {
                        Room room = rooms.get(rooms.indexOf(new Room(Integer.parseInt(selectedRoomNumber), null, null)));
                        jsonObject.put("state", 0);
                        jsonObject.put("roomMessage", room);
                        System.out.println("加入房间成功");
                    } else {
                        jsonObject.put("state", -1);
                        System.out.println("该房间不存在");
                    }
                    pw.println(jsonObject.toString());
                    break;
                case "autoJoinRoom":
                    int notFullRoomNumber = getNotFullRoomNumber(rooms);
                    if (notFullRoomNumber != -1) {
                        Room room = rooms.get(rooms.indexOf(new Room(notFullRoomNumber, null, null)));
                        jsonObject.put("state", 0);
                        jsonObject.put("roomMessage", room);
                        System.out.println("加入"+notFullRoomNumber+"号房间成功");
                    } else {
                        jsonObject.put("state", -1);
                        System.out.println("所有房间已满");
                    }
                    pw.println(jsonObject.toString());
                    break;
            }
        }
        pw.flush();

        pw.close();

    }

    private int getRoomNumber(List<Room> rooms) {
        rooms.sort(Room::compareTo);
        if (rooms.size() == 0) {
            return 0;
        }

        int i = rooms.get(0).getRoomNumber();
        while (rooms.contains(new Room(i,null,null))) {
            ++i;
        }
        return i;
    }

    private int getNotFullRoomNumber(List<Room> rooms) {
        for (Room room : rooms) {
            if (!room.isFull()) {
                return room.getRoomNumber();
            }
        }
        return -1;
    }

    private String getUserByToken(String token) {
        return "hello";
    }

    private boolean isValidUser(String token) {
        return true;
    }
}
