/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.crosant.filebox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class IRC implements Runnable {
   private String server;
   
   protected void server(String server) {
      this.server = server;
   }
   
   protected String server() {
      return this.server;
   }
   
   private int port;
   
   protected void port(int port) {
      this.port = port;
   }
   
   protected int port() {
      return this.port;
   }
   
   private String nick, user, name;
   
   protected void nick(String nick) {
      this.nick = nick;
   }
   
   protected String nick() {
      return this.nick;
   }
   
   protected void user(String user) {
      this.user = user;
   }
   
   protected String user() {
      return this.user;
   }
   
   protected void name(String name) {
      this.name = name;
   }
   
   protected String name() {
      return this.name;
   }
   
   private boolean isActive;
   
   protected void isActive(boolean bool) {
      this.isActive = bool;
   }
   
   protected boolean isActive() {
      return this.isActive;
   }
   
   public IRC(String server, String name, String channel) {
      System.out.println("Initializing.");
      this.server(server);
      this.port(6667);
      this.nick(name);
      this.user(name);
      this.name(name);
   }
   
   private Socket socket;
   
   private BufferedReader in;
   
   private BufferedWriter out;
   
   protected void start() throws java.io.IOException, InterruptedException {
      this.socket = new Socket(this.server(), this.port());
      this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      if (socket.isConnected()) {
         out.write("NICK " + this.nick() + "\r\n");
         out.write("USER " + this.user() + " \"\" \"\" :" + this.name() + "\r\n");
         out.flush();
         this.isActive(true);
         System.out.println("Starting thread.");
         new Thread(this).start();
         Thread.sleep(1000);
         out.write("JOIN #minecraft \r\n");
         out.flush();
      }
   }
   
   public void run() {
      String buffer;
      while (this.isActive()) {
         try {
            while ((buffer = in.readLine()) != null) {
               if (buffer.startsWith("PING")) {
                  out.write("PONG " + buffer.substring(5) + "\r\n");
                  out.flush();
               }
               System.out.println(buffer);
            }
         } catch (java.io.IOException e) {
         }
      }
   }
   
   public void send(String msg) throws IOException{
       out.write("PRIVMSG #minecraft :" + msg + "\r\n");
       out.flush();
   }
   
}