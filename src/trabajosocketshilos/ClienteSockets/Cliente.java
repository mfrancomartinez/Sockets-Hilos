/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajosocketshilos.ClienteSockets;

import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
      try{
        System.out.println("Creando socket del cliente");
        Socket s = new Socket();
        System.out.println("Estableciendo la conexión");
        
        InetSocketAddress addr = new InetSocketAddress("localhost", 9999);
        s.connect(addr);
        
        Scanner sc = new Scanner(System.in);
        
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        
        while(true){
            System.out.println(dis.readUTF());
            String respuesta = sc.nextLine();
            dos.writeUTF(respuesta);
            
            if(respuesta.equals("Salir")){
                System.out.println("Cerrando esta conexión, gracias por elegirnos");
                s.close();
                System.out.println("Conexión cerrada");
                break;
            }
            
            String msjrecibido = dis.readUTF();
            System.out.println(msjrecibido);
                
        }
        sc.close();
        dis.close();
        dos.close();
        
      }catch(Exception e){
          e.printStackTrace();
      }
    }
    
}
