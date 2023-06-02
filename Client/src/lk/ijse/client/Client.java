package lk.ijse.client;

import javafx.scene.layout.VBox;
import lk.ijse.client.controller.ChatAppController;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket remoteSocket=null;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket remoteSocket) {
        this.remoteSocket=remoteSocket;
        try {
            this.bufferedReader=new BufferedReader(new InputStreamReader(remoteSocket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(remoteSocket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error!!! Can't creat client");
            closeServer(remoteSocket,bufferedWriter,bufferedReader);
            throw new RuntimeException(e);
        }
    }

    public void closeServer(Socket remoteSocket,BufferedWriter bufferedWriter,BufferedReader bufferedReader){
        try {
            if (remoteSocket != null){
                remoteSocket.close();
            }
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (bufferedWriter!=null){
                bufferedWriter.close();
            }
        }catch (IOException e){
            System.out.println("Error!!! closing creat server");
            throw new RuntimeException(e);
        }
    }

    public void receiveMessage(VBox vbox_messages) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (remoteSocket.isConnected()){
                    try {
                        String  receiveMessage= bufferedReader.readLine();
                        ChatAppController.receiveMessage(receiveMessage,vbox_messages);
                    } catch (IOException e) {
                        System.out.println("Error!!! Can't read received message from client");
                        closeServer(remoteSocket,bufferedWriter,bufferedReader);
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }).start();
    }

    public void sentMessage(String messageToSent) {
        try {
            bufferedWriter.write(messageToSent);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("Error!!! Can't sent message to client");
            closeServer(remoteSocket,bufferedWriter,bufferedReader);
            throw new RuntimeException(e);
        }
    }
}
