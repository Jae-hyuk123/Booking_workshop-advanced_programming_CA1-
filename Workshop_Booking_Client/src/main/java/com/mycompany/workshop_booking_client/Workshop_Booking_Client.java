/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.workshop_booking_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

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
                
                // if user type "import; URL"
                if(message.toLowerCase().startsWith("import")) {
                    
                    // split user input by ";";
                    String[] parts = message.split(";");
                    
                    if(parts.length == 2) {
                        String url = parts[1].trim();
                        
                        // call importURL with url in out as arguments
                        importURL(url, in, out);
                    }
                    else {
                        System.out.println("invalid input! right format: import; url");
                    }
                }
                
                // if user does not type "import"
                // then consider it as normal booking commands
                else {
                    out.println(message);
                    response = in.readLine();
                    System.out.println(response);
                }
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
    
    public static void importURL(String url, BufferedReader in, PrintWriter out) {
        int success = 0;
        int fail = 0;
        
        try {
            // URL syntax
            URL link = new URL(url);
            
            // HTTP request + body stream
            BufferedReader web = new BufferedReader(new InputStreamReader(link.openStream()));
            
            // Get the first line first
            // and split it by "="
            String str;
            str = web.readLine();
            String[] parts = str.split("=");
            
            // if the length is not equal to 2, then close the connection
            if(parts.length != 2) {
                System.out.println("invalid file input");
                web.close();
                return;
            }
            
            String name = parts[1].trim();
            while((str = web.readLine()) != null) {
                // split each line by ";"
                // format: workshop name; date; time
                String[] partsForOneLine = str.split(";");
                
                // check the length and if it's three, then process
                if(partsForOneLine.length==3) {
                    out.println("book; " + name + "; "+ str);
                    System.out.println(in.readLine());
                    success++;
                }
                else {
                    System.out.println("Invalid line format (skipped): " + str);
                    fail++;
                }
            }
            
            System.out.println("Imported: " + success + "; Skipped: " + fail);
            web.close();
        }
        catch(MalformedURLException e1) {
            System.out.println("URL error");
            System.out.println(e1.getMessage());
        }
        catch(IOException e2) {
            System.out.println("IO EXception: host does not exist");
            System.out.println(e2.getMessage());
        }
        catch(Exception e) {
            System.out.println("Error (url): " + e.getMessage());
        }
    }
}
