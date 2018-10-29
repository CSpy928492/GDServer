package com.cspy.util;

import java.util.ArrayList;
import java.util.List;

public class Main {
    volatile static List<Player> players;
    volatile static List<Room> rooms;

    static {
        rooms = new ArrayList<>();
        players = new ArrayList<>();


        players.add(new Player("1","12",PlayerState.OFFLINE,"localhost"));
        players.add(new Player("1","123",PlayerState.OFFLINE,"127.0.0.1"));
        players.add(new Player("1","1",PlayerState.GAMING,"192.168.0.1"));


        rooms.add(new Room(0,players,RoomState.GAMING));
        rooms.add(new Room(1,players,RoomState.NOT_READY));
        rooms.add(new Room(2,players,RoomState.GAMING));
        rooms.add(new Room(3,players,RoomState.GAMING));
        rooms.add(new Room(4,players,RoomState.NOT_READY));
        rooms.add(new Room(5,players,RoomState.GAMING));
        rooms.add(new Room(6,players,RoomState.GAMING));
        rooms.add(new Room(7,players,RoomState.NOT_READY));
        rooms.add(new Room(8,players,RoomState.GAMING));
        rooms.add(new Room(9,players,RoomState.GAMING));
        rooms.add(new Room(10,players,RoomState.NOT_READY));
        rooms.add(new Room(11,players,RoomState.GAMING));


    }

    public static List<Room> getRooms() {
//        rooms = rooms.subList(0, rooms.size() - 2);
        return rooms;
    }



}
