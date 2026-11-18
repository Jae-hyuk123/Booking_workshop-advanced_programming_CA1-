/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.workshop_booking;

import java.util.ArrayList;

/**
 *
 * @author ABC
 */
public class BookingManage {
    
    private ArrayList<Booking> bookings = new ArrayList<>();
    
    public String book(String name, String workshopName, String date, String time) throws InvalidCommandException {
        String allInfo = name + ", " + workshopName + ", " + date + ", " + time;
        
        for(int i=0; i<bookings.size(); i++) {
            if(allInfo.equalsIgnoreCase(bookings.get(i).getAllInfo())) {
                throw new InvalidCommandException(name + " already has this booking");
            }
        }
        
        bookings.add(new Booking(name, workshopName, date, time));
        return list(name);
    }
    
    public String list(String name) {
        int count = 0;
        String list = "";
        
        for(int i=0; i<bookings.size(); i++) {
            if(name.equalsIgnoreCase(bookings.get(i).getName())) {
                list += bookings.get(i).getWorkshopName() + " on " + bookings.get(i).getDate() + " at " + bookings.get(i).getTime() + "; ";
                count++;
            }
        }
        
        if(count == 0) {
            return "There is no booking for " + name;
        }
        else {
            return name + " currently has " + count + " booking(s): " + list;
        }
    }
    
    public String cancel(String name, String workshopName, String date, String time) throws InvalidCommandException {
        
        String allInfo = name + ", " + workshopName + ", " + date + ", " + time;
        
        for(int i=0; i<bookings.size(); i++) {
            if(allInfo.equalsIgnoreCase(bookings.get(i).getAllInfo())) {
                bookings.remove(i);
                return "Cancelled. " + list(name);
            }
        }
        
        throw new InvalidCommandException(name + " does not have this booking");
    }
    
}
