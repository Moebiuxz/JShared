package cl.jshared.server.model;

import cl.jshared.common.model.NuevoUsuario;
import cl.jshared.common.model.Serial;
import cl.jshared.common.model.UsuarioYaExiste;
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
    private String nick; // es el identificador
    private InputStream is;
    private OutputStream os;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Cliente(Socket socket) {
        this.socket = socket;
//        mostrarInfo(socket);
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
            while (true) {

                is = socket.getInputStream();
                ois = new ObjectInputStream(is);
                
                Object o = ois.readObject();
                
                if(o instanceof String){
                    String str = (String) o;
                    System.out.println(str);
                } else if (o instanceof NuevoUsuario) {
                    NuevoUsuario nu = (NuevoUsuario) o;

                    System.out.println("Llegó un nuevo usuario");

                    this.setNick(nu.getNick()); // error acá, esta como null ver bien porque estoy dentro de un hilo
                    if (!Server.getInstance().existeCliente(nu.getNick())) {
                        System.out.println("No existe el usuario! por ende se agrega");
                        Server.getInstance().addCliente(this);
                    } else {
                        System.out.println("Existe el usuario, enviando un objeto al cliente");
                        enviarObjeto(new UsuarioYaExiste());
                        break;
                    }
                }
            }
        } catch(java.net.SocketException se){
            Server.getInstance().eliminarCliente(this.getNick());
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
//        try {
//            
////                is = socket.getInputStream();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            byte[] bytes = new byte[1024]; 
//            
//            int count = socket.getInputStream().read(bytes);
//            System.out.println("Llegó objeto desde el cliente");
//            while (count != -1) {
//                baos.write(bytes, 0, count);
//                count = socket.getInputStream().read(bytes);
//            }
//            
//            Object o = Serial.deserialize(baos.toByteArray());
//            
//            if(o instanceof String){
//                String str = (String)o;
//                System.out.println(str);
//            }else if(o instanceof NuevoUsuario){
//                NuevoUsuario nu = (NuevoUsuario)o;
//                
//                System.out.println("Llegó un nuevo usuario");
//                
//                this.setNick(nu.getNick()); // error acá, esta como null ver bien porque estoy dentro de un hilo
//                if(!Server.getInstance().existeCliente(nu.getNick())){
//                    System.out.println("No existe el usuario! por ende se agrega");
//                    Server.getInstance().addCliente(this);
//                }else{
//                    System.out.println("Existe el usuario, enviando un objeto al cliente");
//                    //acá tengo que enviar un objeto al cliente
//                }
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println("Hilo Cliente finalizado--> ID: " + this.getId());
    }

    private void enviarObjeto(Object o) {
            try {
                os = socket.getOutputStream();
                oos = new ObjectOutputStream(os);

                oos.writeObject(o);
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//     private void mostrarInfo(Socket s) {
//        try {
//            System.out.println("--------------------------");
//            System.out.println("Información");
//            System.out.println("--------------------------");
//            System.out.println("getChannel()");
//            System.out.println("--------------------------");
//            if(s.getChannel() != null){
//                System.out.println("Channel localAddress():"+s.getChannel().getLocalAddress());
//                System.out.println("Channel remoteAddress():"+s.getChannel().getRemoteAddress());
//            }
//            
//            System.out.println("--------------------------");
//            System.out.println("getInetAddress()");
//            System.out.println("--------------------------");
//            
//            System.out.println("canonicalHostName():"+s.getInetAddress().getCanonicalHostName());
//            System.out.println("hostAddress()"+s.getInetAddress().getHostAddress());
//            System.out.println("hostName():"+s.getInetAddress().getHostName());
//            
//            System.out.println("--------------------------");
//            System.out.println("getLocalAddress()");
//            System.out.println("--------------------------");
//            
//            System.out.println("canonicalHostName():"+s.getLocalAddress().getCanonicalHostName());
//            System.out.println("hostAddress()"+s.getLocalAddress().getHostAddress());
//            System.out.println("hostName():"+s.getLocalAddress().getHostName());
//            System.out.println("--------------------------");
//        } catch (IOException ex) {
//            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @Override
    public String toString() {
        return this.nick;
    }
}
