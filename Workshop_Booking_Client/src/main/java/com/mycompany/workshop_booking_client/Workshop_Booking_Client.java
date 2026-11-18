/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.workshop_booking_client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author ABC
 */
public class Workshop_Booking_Client {
    
    private static InetAddress host;
    private static final int PORT = 1234;
    
    public static void main(String[] args) {
        
        Socket link = null;
        
        try {
            host = InetAddress.getLocalHost();
            
            link = new Socket(host, PORT);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(),true);
            
            BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
            
            String message = null;
            String response= null;
            
            System.out.println("Enter message to be sent to server: ");
            
            do {
                message = userEntry.readLine();
                out.println(message);
                response = in.readLine();
                System.out.println(response);
            } while(!message.equalsIgnoreCase("stop"));
        }
        catch(Exception e) {
            System.out.println("Error (during connection): " + e.getMessage());
        }
        finally {
                try {
                    if(link != null) {
                        link.close();
                    }
                }
                catch (Exception e) {
                    System.out.println("Error (during close): " + e.getMessage());
                }
        }
    }
}
