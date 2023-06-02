package lk.ijse.client.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.client.Client;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatAppController implements Initializable {
    public AnchorPane app_main;
    public JFXButton btnSent;
    public TextField tf_message;
    public ScrollPane sp_main;
    public VBox vbox_messages;
    public Label lbl_name;
    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.client=new Client(new Socket("localhost",4000));
            System.out.println("Client connected to server!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double)newValue);
            }
        });

        client.receiveMessage(vbox_messages);
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

            client.sentMessage(messageToSent);
            tf_message.clear();
        }
    }

    public void tf_messageOnAction(ActionEvent actionEvent) {
        btnSentOnAction(actionEvent);
    }

    public static void receiveMessage(String receivedMessage,VBox vbox){
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text=new Text(receivedMessage);
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

}
