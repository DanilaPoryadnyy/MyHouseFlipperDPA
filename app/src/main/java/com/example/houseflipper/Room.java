package com.example.houseflipper;

public class Room {
    private String roomName;
    private double surfaceArea;
    private double rollsCount;
    private double rollsCost;
    private double tileCount;
    private double paintVolume;
    private double paintCost;
    private double primerConsumption;

    public Room(String roomName, double surfaceArea, double rollsCount, double rollsCost,
                double tileCount, double paintVolume, double paintCost, double primerConsumption) {
        this.roomName = roomName;
        this.surfaceArea = surfaceArea;
        this.rollsCount = rollsCount;
        this.rollsCost = rollsCost;
        this.tileCount = tileCount;
        this.paintVolume = paintVolume;
        this.paintCost = paintCost;
        this.primerConsumption = primerConsumption;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public double getRollsCount() {
        return rollsCount;
    }

    public void setRollsCount(double rollsCount) {
        this.rollsCount = rollsCount;
    }

    public double getRollsCost() {
        return rollsCost;
    }

    public void setRollsCost(double rollsCost) {
        this.rollsCost = rollsCost;
    }

    public double getTileCount() {
        return tileCount;
    }

    public void setTileCount(double tileCount) {
        this.tileCount = tileCount;
    }

    public double getPaintVolume() {
        return paintVolume;
    }

    public void setPaintVolume(double paintVolume) {
        this.paintVolume = paintVolume;
    }

    public double getPaintCost() {
        return paintCost;
    }

    public void setPaintCost(double paintCost) {
        this.paintCost = paintCost;
    }

    public double getPrimerConsumption() {
        return primerConsumption;
    }

    public void setPrimerConsumption(double primerConsumption) {
        this.primerConsumption = primerConsumption;
    }
}
