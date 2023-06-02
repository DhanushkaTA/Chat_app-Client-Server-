package lk.ijse.server;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket localSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(ServerSocket serverSocket){
        this.serverSocket=serverSocket;

        try {
            this.localSocket=serverSocket.accept();
            this.bufferedReader=new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(localSocket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error!!! Can't creat server");
            closeServer(localSocket,bufferedWriter,bufferedReader);
            throw new RuntimeException(e);
        }
    }

    public void closeServer(Socket localSocket,BufferedWriter bufferedWriter,BufferedReader bufferedReader){
        try {
            if (localSocket != null){
                localSocket.close();
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

    public void sentMessage(String messageToSent){
        try {
            bufferedWriter.write(messageToSent);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("Error!!! Can't sent message to client");
            closeServer(localSocket,bufferedWriter,bufferedReader);
            throw new RuntimeException(e);
        }
    }

    public void receiveMessage(VBox vbox_messages){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (localSocket.isConnected()){
                    try {
                        String  receiveMessage= bufferedReader.readLine();

                    } catch (IOException e) {
                        System.out.println("Error!!! Can't read received message from client");
                        closeServer(localSocket,bufferedWriter,bufferedReader);
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }).start();
    }
}
