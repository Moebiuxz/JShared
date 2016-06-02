package cl.jshared.common.model;

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
                System.out.println(baos.toString());
                count = socket.getInputStream().read(bytes);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Hilo Cliente finalizado--> ID: " + this.getId());
    }
}
