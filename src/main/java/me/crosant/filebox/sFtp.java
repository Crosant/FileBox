/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.crosant.filebox;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Florian Reichmuth
 */
public class sFtp {
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp sftpChannel = null;
    public sFtp (String user, String password, String server){
        

        java.util.Properties config = new java.util.Properties(); 
        config.put("StrictHostKeyChecking", "no");
        try {
            session = jsch.getSession(user, server, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;

        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
    }
    
    private boolean get(String remoteFile, String localFile){
        try {
            sftpChannel.get(remoteFile, localFile);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(sFtp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean put(String localFile, String remoteFile){
        try {
            sftpChannel.put(localFile, "./" + remoteFile);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(sFtp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
