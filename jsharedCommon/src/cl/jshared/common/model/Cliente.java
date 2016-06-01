package cl.jshared.common.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread implements Serializable{
    private Socket socket;
    private String nick;
    private InputStream is;
    private OutputStream os;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Object objeto;
    
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
    public void run(){
        System.out.println("Hilo Cliente iniciado! --> ID: "+this.getId());
        while(true){
            try {
                System.out.println("Esperando stream del cliente...");
                is = socket.getInputStream();
                System.out.println("Llegó objeto desde el cliente");
                ois = new ObjectInputStream(is);
                
                objeto = ois.readObject();
                System.out.println("Objeto recibido!: "+objeto);
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
