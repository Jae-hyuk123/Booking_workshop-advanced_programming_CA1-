/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.workshop_booking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author ABC
 */
public class ThreadForBookingManage implements Runnable {
    
    private Socket link;
    private BookingManage bookManage;
    
    public ThreadForBookingManage(Socket link, BookingManage bookManage) {
        this.link = link;
        this.bookManage = bookManage;
    }
    
    
    public void run() {
        
        String response = "";
        
        try {
            
        // use the input and output stream to send and receive information
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);

            while((response = in.readLine()) != null) {
                if(response.equalsIgnoreCase("stop")) {
                    out.println("TERMINATE");   // send TERMINATE to client
                    System.out.println("Connection finished");
                    break;
                }
                
                String parts[] = response.split(";");
                
                String action = parts[0].trim();
                
                try {
                    if(action.equalsIgnoreCase("book")) {
                        out.println(bookManage.book(parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim()));
                    }
                    else if(action.equalsIgnoreCase("list")) {
                        out.println(bookManage.list(parts[1].trim()));
                    }
                    else if(action.equalsIgnoreCase("cancel")) {
                        out.println(bookManage.cancel(parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim()));
                    }
                    else {
                        throw new InvalidCommandException("action must be book/cancel/list");
                    }
                }
                catch(InvalidCommandException e) {
                    out.println("InvalidCommandException: " + e.getMessage());
                    System.out.println("InvalidCommandException: " + e.getMessage());
                }
            }
        }
        catch(Exception e) {
            System.out.println("Error (during thread): " + e.getMessage());
        }
        finally {
            if(link != null) {
                try {
                    link.close();
                }
                catch(Exception e) {
                    System.out.println("Error (link close): " + e.getMessage());
                }
            }
        }
    }
}
