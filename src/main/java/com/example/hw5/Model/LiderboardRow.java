package com.example.hw5.Model;

public class LiderboardRow {
    public String name;
    public long moves;
    public long seconds;

    public LiderboardRow(String name, long moves, long seconds) {
        this.name = name;
        this.moves = moves;
        this.seconds = seconds;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMoves() {
        return moves;
    }

    public void setMoves(long moves) {
        this.moves = moves;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

}
