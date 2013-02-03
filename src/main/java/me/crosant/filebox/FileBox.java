package me.crosant.filebox;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

        
public class FileBox {
    static sFtp sftp;
    static IRC irc;
    public static void main(String args[]) throws Exception {
        
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("server")){
            irc = new IRC("crosant.skyirc.net", "FileBox", "#minecraft");
            irc.start();
            server server = new server();

        }
        else{
            DropBox.main(args);
   
        }
    }
    else{
            DropBox.main(args);
  
        }
    }
    
    static void upload(String localfile, String remotefile){
        try {
            Socket Socket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            Socket = new Socket("crosant.skyirc.net", 4444);
            out = new PrintWriter(Socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                            Socket.getInputStream()));
            sftp.put(localfile, "filebox/" + remotefile);
            out.write(remotefile + " was uploaded by " +  FileBox.user);
            out.flush();
            System.out.println(localfile);
            System.out.println(remotefile);

            Socket.close();
            out.close();
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(FileBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void setUser(String user){
        FileBox.user=user;
    }
    
    public static void connect(String user, String pass, String server){
        sftp = new sFtp(user, pass, server);

    }
    
    private static String user;
    
}