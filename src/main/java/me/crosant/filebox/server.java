/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.crosant.filebox;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Florian Reichmuth
 */
public class server {

  private static int port=4444, maxConnections=10;
  // Listen for incoming connections and handle them
  public server() {
    int i=0;

    try{
      ServerSocket listener = new ServerSocket(port);
      Socket server;

      while((i++ < maxConnections) || (maxConnections == 0)){
        doComms connection;

        server = listener.accept();
        doComms conn_c= new doComms(server);
        Thread t = new Thread(conn_c);
        t.start();
      }
    } catch (IOException ioe) {
      System.out.println("IOException on socket listen: " + ioe);
      ioe.printStackTrace();
    }
  }

}

class doComms implements Runnable {
    private Socket server;
    private String line,input;

    doComms(Socket server) {
      this.server=server;
    }

    public void run () {

      input="";

      try {
        // Get input from the client
        BufferedReader in = new BufferedReader(new InputStreamReader (server.getInputStream()));
        PrintStream out = new PrintStream(server.getOutputStream());

        while((line = in.readLine()) != null) {
         input=input + line;
         System.out.println(line);
         FileBox.irc.send(line);
        }

      } catch (IOException ioe) {
        System.out.println("IOException on socket listen: " + ioe);
        ioe.printStackTrace();
      }
    }
}

