/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.workshop_booking;

/**
 *
 * @author ABC
 */
public class Booking {
    private String name, workshopName, date, time;
    
    public Booking(String name, String workshopName, String date, String time) {
        this.name = name;
        this.workshopName = workshopName;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    
    public String getAllInfo() {
        return name + ", " + workshopName + ", " + date + ", " + time;
    }
}
