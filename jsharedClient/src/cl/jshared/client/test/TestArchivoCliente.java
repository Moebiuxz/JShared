package cl.jshared.client.test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestArchivoCliente {

    public final static int SOCKET_PORT = 13267;      // puerto
    public final static String SERVER = "127.0.0.1";  // localhost

    public final static String FILE_TO_RECEIVED = "downloaded.txt";  // ruta del archivo a recibir

    public final static int FILE_SIZE = 6022386; // file size temporary hard coded
    // should bigger than the file to be downloaded --- no entendí una mierda.

    public static void main(String[] args) {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Conectando...");

            // receive file
            byte[] mybytearray = new byte[FILE_SIZE];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;

            do {
                bytesRead
                        = is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) {
                    current += bytesRead;
                }
            } while (bytesRead > -1);

            bos.write(mybytearray, 0, current);
            bos.flush();
            System.out.println("Archivo " + FILE_TO_RECEIVED
                    + " descargado (" + current + " bytes leidos)");
        } catch (IOException ex) {
            Logger.getLogger(TestArchivoCliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (sock != null) {
                    sock.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(TestArchivoCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
