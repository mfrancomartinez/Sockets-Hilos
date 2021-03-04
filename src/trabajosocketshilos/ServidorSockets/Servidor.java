/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajosocketshilos.ServidorSockets;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Creando socket servidor");

        ServerSocket sS = new ServerSocket();

        System.out.println("Realizando el bind");

        InetSocketAddress addr = new InetSocketAddress("localhost", 9999);
        sS.bind(addr);

        System.out.println("Aceptando conexiones");

        while (true) {

            Socket s = null;

            try {
                s = sS.accept();

                System.out.println("Se ha conectado un nuevo cliente: " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Asignando hilo...");

                Thread t = new Hilos(dis, dos, s);

                t.start();

            } catch (Exception e) {
                s.close();
                e.printStackTrace();
            }
        }

    }
}

class Hilos extends Thread {

    public double precio1 = 25.0;
    public double precio2 = 50.0;
    public double precio3 = 100.0;

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    public Hilos(DataInputStream dis, DataOutputStream dos, Socket s) {
        this.dis = dis;
        this.dos = dos;
        this.s = s;
    }   
    

    public void run() {
        Double preciofinal = 0.0;
        String mensajeRecibido;
        String mensajeAenviar;
        while (true) {
            try {
                dos.writeUTF("Bienvendido a nuestro restaurante, que desea tomar? (Puede escoger entre Menu1, Menu2 o Menu3) \n"
                        + "Teclea Salir para cerrar conexion");

                mensajeRecibido = dis.readUTF();

                if (mensajeRecibido.equals("Salir")) {
                    System.out.println("El cliente " + this.s + "se va...");
                    System.out.println("Cerrando la conexion del cliente");
                    this.s.close();
                    System.out.println("Conexion cerrada");
                    break;
                }

                switch (mensajeRecibido) {
                    case "Menu1":
                        preciofinal = preciofinal + precio1;
                        mensajeAenviar = "Su pedido se ha procesado, precio a pagar: " + preciofinal;
                        dos.writeUTF(mensajeAenviar);
                        break;

                    case "Menu2":
                        preciofinal = preciofinal + precio2;
                        mensajeAenviar = "Su pedido se ha procesado, precio a pagar: " + preciofinal;
                        dos.writeUTF(mensajeAenviar);
                        break;

                    case "Menu3":
                        preciofinal = preciofinal + precio3;
                        mensajeAenviar = "Su pedido se ha procesado, precio a pagar: " + preciofinal;
                        dos.writeUTF(mensajeAenviar);
                        break;

                    default:
                        dos.writeUTF("Su pedido no consta en nuestra lista de menús");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
