package com.example.houseflipper;

public class Room {
    private int id;
    private String roomName;
    private double rollsCount;
    private double totalRollsCost;
    private int tilesCount;
    private int paintCansCount;
    private double paintCost;
    private double primerWeight;
    private double roomArea;

    public Room(int id, String roomName, double rollsCount, double totalRollsCost, int tilesCount,
                int paintCansCount, double paintCost, double primerWeight, double roomArea) {
        this.id = id;
        this.roomName = roomName;
        this.rollsCount = rollsCount;
        this.totalRollsCost = totalRollsCost;
        this.tilesCount = tilesCount;
        this.paintCansCount = paintCansCount;
        this.paintCost = paintCost;
        this.primerWeight = primerWeight;
        this.roomArea = roomArea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public double getRollsCount() {
        return rollsCount;
    }

    public void setRollsCount(double rollsCount) {
        this.rollsCount = rollsCount;
    }

    public double getTotalRollsCost() {
        return totalRollsCost;
    }

    public void setTotalRollsCost(double totalRollsCost) {
        this.totalRollsCost = totalRollsCost;
    }

    public int getTilesCount() {
        return tilesCount;
    }

    public void setTilesCount(int tilesCount) {
        this.tilesCount = tilesCount;
    }

    public int getPaintCansCount() {
        return paintCansCount;
    }

    public void setPaintCansCount(int paintCansCount) {
        this.paintCansCount = paintCansCount;
    }

    public double getPaintCost() {
        return paintCost;
    }

    public void setPaintCost(double paintCost) {
        this.paintCost = paintCost;
    }

    public double getPrimerWeight() {
        return primerWeight;
    }

    public void setPrimerWeight(double primerWeight) {
        this.primerWeight = primerWeight;
    }

    public double getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(double roomArea) {
        this.roomArea = roomArea;
    }
}

