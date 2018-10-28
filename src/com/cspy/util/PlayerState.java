package com.cspy.util;

public enum PlayerState {
    OFFLINE("离线",0),
    ONLINE("在线",1),
    GAMING("游戏中",2),
    GAME_OFFLINE("掉线",3);


    private String stateName;
    private int number;

    private PlayerState(String stateName, int number) {
        this.stateName =stateName;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getStateName() {
        return stateName;
    }
}
