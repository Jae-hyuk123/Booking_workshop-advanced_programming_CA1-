/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.workshop_booking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author ABC
 */
public class Workshop_Booking {

    private static ServerSocket servSock;
    private static final int PORT = 1234;
    
    public static void main(String[] args) {
        
        Socket link = null;
        
        try {
            // create server socket instance
            servSock = new ServerSocket(PORT);
            System.out.println("Server socket created sucessfully");
            System.out.println("Waiting for clinet");
            
            // waiting for a connection to be established
             
            link = servSock.accept();   // it will wait until client arrives
            System.out.println("Client connected");
            
            // use the input and output stream to send and receive information
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);
                        
            // read a message
            // keep the connection until client sends stop
            String response;
                        
            BookingManage bookManage = new BookingManage();
            
            while((response = in.readLine()) != null) {
                
                if(response.equalsIgnoreCase("stop")) {
                    out.println("TERMINATE");   // send TERMINATE to client
                    System.out.println("Connection finished");
                    break;
                }
                
                
                String parts[] = response.split(";");
                
                String action = parts[0].trim();
                
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
                    
                }
                
            }
            
        }
        catch(Exception e) {
            System.out.println("Error (during connection): " + e.getMessage());
        }
        finally {
            try {
                if(link != null) {
                    link.close();
                }
                
                if(servSock != null) {
                    servSock.close();
                }
            }
            catch(Exception e) {
                System.out.println("Error (during close):" + e.getMessage());
            }
        }
        
    }
}
