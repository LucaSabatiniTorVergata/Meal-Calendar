package com.example.mealcalendar.fillfridge;

import java.util.*;


public class Frigorifero {


    private Map<String, Integer> inventario;


    public Frigorifero() {
        this.inventario  =  new HashMap<>();
    }

    public Map<String, Integer> getInventario() {
        return inventario;
    }
    public void setInventario(Map<String, Integer> inventario) {
        this.inventario = inventario;
    }
}
