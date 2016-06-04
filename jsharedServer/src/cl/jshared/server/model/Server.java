package cl.jshared.server.model;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread{
    
    private ServerSocket server;
    private Socket socketClienteNuevo;
    private Cliente clienteActual;
    private static Server _server;
    
    private final int puerto;
    private static List<Cliente> clientes;
    private static List<ClienteNuevoListener> listeners;
    
    
    private Server(int puerto){
        this.puerto = puerto;
        clientes = new ArrayList<>();
        listeners = new ArrayList<>();
    }
    
    public static Server getInstance(){
        if(_server == null)
            _server = new Server(2500);
        
        return _server;
    }
    
    public void addClienteNuevoListener(ClienteNuevoListener cnl){
        listeners.add(cnl);
    }
    
    @Override
    public void run(){
        try {
            server = new ServerSocket(puerto);
            System.out.println("Servidor arriba en puerto: "+puerto);
            while(true){
                System.out.println("Esperando cliente...");
                socketClienteNuevo = server.accept();
                System.out.println("Llegó cliente nuevo: "+socketClienteNuevo);
                
                
//                mostrarInfo(socketClienteNuevo);
                clienteActual = new Cliente(socketClienteNuevo);
//                Server.clientes.add(clienteActual);
                clienteActual.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized void eliminarCliente(int socketId){
//        for(Cliente c : clientes){
//            if(){
//            
//            }
//        }
    }


    public synchronized List<Cliente> getClientes(){
        return Server.clientes;
    }
    
    public synchronized void addCliente(Cliente clienteNuevo) {
        clientes.add(clienteNuevo);
        
        for(ClienteNuevoListener c : listeners){
            c.actualizarListaGrafica();
        }
    }
   
    
    public synchronized boolean existeCliente(String nick) {
        for(Cliente c: clientes){
            if(c.getNick().equalsIgnoreCase(nick)){
                return true;
            }
        }
        return false;
    }
            
}
