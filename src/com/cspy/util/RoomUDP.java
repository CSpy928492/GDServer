package com.cspy.util;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class RoomUDP {

    volatile List<Player> playerList;
    volatile List<Player> winners;



    static final int UDP_PORT = 9900;

    DatagramSocket datagramSocket;
    Room room;

    public RoomUDP(Room room) {
        this.room = room;
        this.playerList = room.getPlayers();
        winners = new ArrayList<>();
        try{
            datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        byte[] data = message.getBytes();
        DatagramPacket packet;
        for (Player player : playerList) {
            try {
                packet = new DatagramPacket(data, data.length, InetAddress.getByName(player.getIp()), UDP_PORT + room.getRoomNumber());
                datagramSocket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean addPlayer(Player player) {
        if (playerList.size() > 4) {
            return false;
        } else {
            sendMessage("玩家:" + player.getUsername() + "加入游戏");
            return this.playerList.add(player);

        }
    }

    public boolean removePlayer(Player player) {
        if (playerList.contains(player)) {
            sendMessage("玩家:" + player.getUsername() + "退出游戏");
            return playerList.remove(player);
        } else {
            return false;
        }
    }


//    @Override
//    public void run() {
//        while (true) {
//
//        }
//    }
}
