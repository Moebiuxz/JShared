package cl.jshared.server.model;

import cl.jshared.common.model.Cliente;
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
    
    private final int puerto;
    private static List<Cliente> clientes;
    
    
    public Server(int puerto){
        this.puerto = puerto;
        clientes = new ArrayList<>();
    }
    
    @Override
    public void run(){
        try {
            System.out.println("Servidor arriba en puerto: "+puerto);
            while(true){
                server = new ServerSocket(puerto);
                socketClienteNuevo = server.accept();
                System.out.println("Llegó cliente nuevo: "+socketClienteNuevo);
                clienteActual = new Cliente(socketClienteNuevo);
                Server.clientes.add(clienteActual);
                clienteActual.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
