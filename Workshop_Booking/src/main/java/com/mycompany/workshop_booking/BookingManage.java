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
    
    public synchronized String book(String name, String workshopName, String date, String time) throws InvalidCommandException {
        
        // create variable that has user name, workshopname, date, and time
        String allInfo = name + ", " + workshopName + ", " + date + ", " + time;
        
        for(int i=0; i<bookings.size(); i++) {
            // check if a user already hsa the same booking
            if(allInfo.equalsIgnoreCase(bookings.get(i).getAllInfo())) {
                // throw exception if a user has the same booking
                throw new InvalidCommandException(name + " already has this booking");
            }
        }
        
        // add a booking
        bookings.add(new Booking(name, workshopName, date, time));
        return list(name);
    }
    
    public synchronized String list(String name) {
        int count = 0;
        String list = "";
        
        for(int i=0; i<bookings.size(); i++) {
            // check if a user has the booking by name
            if(name.equalsIgnoreCase(bookings.get(i).getName())) {
                // if a user has the booking, then add the booking to list string in a speicifed from 
                list += bookings.get(i).getWorkshopName() + " on " + bookings.get(i).getDate() + " at " + bookings.get(i).getTime() + "; ";
                count++;
            }
        }
        
        // if a user has no booking
        if(count == 0) {
            return "There is no booking for " + name;
        }
        
        // if a user has bookings 
        else {
            return name + " currently has " + count + " booking(s): " + list;
        }
    }
    
    public synchronized String cancel(String name, String workshopName, String date, String time) throws InvalidCommandException {
        
        // create variable that has user name, workshopname, date, and time
        String allInfo = name + ", " + workshopName + ", " + date + ", " + time;
        
        for(int i=0; i<bookings.size(); i++) {
            // check if a user has the booking
            if(allInfo.equalsIgnoreCase(bookings.get(i).getAllInfo())) {
                // remove the booking
                bookings.remove(i);
                return "Cancelled. " + list(name);
            }
        }
        
        // throw exception if the user does not have that booking
        throw new InvalidCommandException(name + " does not have this booking");
    }
    
}
