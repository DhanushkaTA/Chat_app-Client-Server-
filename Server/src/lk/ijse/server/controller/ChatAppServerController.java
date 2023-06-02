package lk.ijse.server.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lk.ijse.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatAppServerController implements Initializable {
    public AnchorPane app_main;
    public com.jfoenix.controls.JFXButton btnSent;
    public TextField tf_message;
    public ScrollPane sp_main;
    public VBox vbox_messages;
    public Label lbl_name;

    private Server server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            server=new Server(new ServerSocket(4000));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        /** to change vbox height*/
        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }
        });

        server.receiveMessage(vbox_messages);
    }



    public void btnSentOnAction(ActionEvent actionEvent) {
        String messageToSent=tf_message.getText();

        /** ensure to text field not empty*/
        if(!messageToSent.isEmpty()){

            HBox hBox=new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));

            Text text=new Text(messageToSent);
            TextFlow textFlow=new TextFlow(text);

            textFlow.setStyle("-fx-color: rgb(239,242,255);"+
                    "-fx-background-color: rgb(15,125,242);" +
                    "-fx-background-radius: 20px;");

            textFlow.setPadding(new Insets(5,10,5,10));
            text.setFill(Color.color(0.934,0.945,0.996));

            hBox.getChildren().add(textFlow);
            vbox_messages.getChildren().add(hBox);

            server.sentMessage(messageToSent);
            tf_message.clear();
        }
    }

    public static void addReceiveMessage(String receiveMessage,VBox vbox){
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text=new Text(receiveMessage);
        TextFlow textFlow=new TextFlow(text);

        textFlow.setStyle("-fx-background-color: rgb(233,233,235);" +
                "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5,10,5,10));
        //text.setFill(Color.color(0.934,0.945,0.996));

        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);
            }
        });
    }

    public void tf_messageOnAction(ActionEvent actionEvent) {
        btnSentOnAction(actionEvent);
    }


}
