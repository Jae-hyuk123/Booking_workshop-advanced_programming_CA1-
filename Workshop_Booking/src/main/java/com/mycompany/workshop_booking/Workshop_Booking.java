/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.workshop_booking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author ABC
 */
public class Workshop_Booking {

    private static ServerSocket servSock;
    private static final int PORT = 1234;
    
    public static void main(String[] args) {
        
        Socket link = null;
        int clientNumber = 0;
        
        try {
            // create server socket instance
            servSock = new ServerSocket(PORT);
            System.out.println("Server socket created sucessfully");
            System.out.println("Waiting for clinet");

            // create instance of bookingManage
            BookingManage bookManage = new BookingManage();
            
            while(true) {
                // wait for a connection to be estblished
                link = servSock.accept();
                
                // count the total number of client
                clientNumber++;
                System.out.println("Client connected, client number: " + clientNumber);
                
                // Thread
                // send link and bookMange as arguments
                ThreadForBookingManage tBookingManage = new ThreadForBookingManage(link, bookManage);
                
                Thread t = new Thread(tBookingManage);
                
                t.start(); 
            }
        }
        catch(Exception e) {
            System.out.println("Error (during connection): " + e.getMessage());
        }
        finally {
            try {                
                if(servSock != null) {
                    servSock.close();
                }
            }
            catch(Exception e) {
                System.out.println("Error (servSock close):" + e.getMessage());
            }
        }
        
    }
}
