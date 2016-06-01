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
                
                byte[] bytes = new byte[16*1024];

                int count;
                while ((count = is.read(bytes)) > 0) {
//                    out.write(bytes, 0, count);
                    System.out.println(bytes);
                }
                
                String str = new String(bytes);
                System.out.println(str);
                
//                ois = new ObjectInputStream(is);
                
//                objeto = ois.readObject();
                System.out.println("Llegó objeto desde el cliente");
//                System.out.println("Objeto recibido!: "+objeto);
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
