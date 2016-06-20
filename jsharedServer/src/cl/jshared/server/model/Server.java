package cl.jshared.server.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread{
    
    private ServerSocket server;
    private Socket socketClienteNuevo;
    private Cliente clienteActual;
    private static Server _server;
    
    private final int puerto;
    private static LinkedList<Cliente> clientes;
    private static LinkedList<ClienteNuevoListener> listeners;
    
    
    private Server(int puerto){
        this.puerto = puerto;
        clientes = new LinkedList<>();
        listeners = new LinkedList<>();
    }
    
    public static Server getInstance(){
        if(_server == null)
            _server = new Server(2500);
        
        return _server;
    }
    
    public void addClienteNuevoListener(ClienteNuevoListener cnl){
        listeners.add(cnl);
    }

    public Cliente getCliente(String nick){
        
        Optional<Cliente> filter = clientes.stream().filter(
                (cli) -> cli.getNick().equalsIgnoreCase(nick)).findFirst();
    
        if (filter.isPresent()) return filter.get();
        
        else return null;
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
        
        // Remover un cliente si el id de este coincide con el id del parametro
        clientes.removeIf((cli) -> cli.getId() == socketId);
    }


    public synchronized LinkedList<Cliente> getClientes(){
        return Server.clientes;
    }
    
    public synchronized void addCliente(Cliente clienteNuevo) {
        clientes.add(clienteNuevo);

        listeners.stream().forEach((c) -> {
            c.actualizarListaGrafica();
        });
    }
    
    public synchronized boolean existeCliente(String nick) {
        return clientes.stream().anyMatch((cli) -> cli.getNick().equalsIgnoreCase(nick));
    }
            
}
