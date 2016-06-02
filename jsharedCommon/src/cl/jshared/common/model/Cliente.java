package cl.jshared.common.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread implements Serializable {

    private Socket socket;
    private String nick;
    private InputStream is;
    private OutputStream os;

    public Cliente(Socket socket) {
        this.socket = socket;
        this.nick = null;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public void run() {
        System.out.println("Hilo Cliente iniciado! --> ID: " + this.getId());

        try {
            
//                is = socket.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1]; 
            
            int count = socket.getInputStream().read(bytes);
            System.out.println("Llegó objeto desde el cliente");
            while (count != -1) {
                baos.write(bytes, 0, count);
                
                count = socket.getInputStream().read(bytes);
            }
            
            Object o = Serial.deserialize(baos.toByteArray());
            
            if(o instanceof String){
                String str = (String)o;
                System.out.println(str);
            }else if(o instanceof X){
                X x = (X)o;
                System.out.println(x.getId());
                System.out.println(x.getString());
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Hilo Cliente finalizado--> ID: " + this.getId());
    }
}
