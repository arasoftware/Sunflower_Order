package com.ara.sunflowerorder.models;

//Represents the Unit of Measurement
public class UOM {
    int id;
    String name;

    public UOM() {
    }

    public UOM(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
