package lk.ijse.client.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ChatAppController {
    public AnchorPane app_main;
    public JFXButton btnSent;
    public TextField tf_message;
    public ScrollPane sp_main;
    public VBox vbox_messages;
    public Label lbl_name;

    public void btnSentOnAction(ActionEvent actionEvent) {

    }

    public void tf_messageOnAction(ActionEvent actionEvent) {
        btnSentOnAction(actionEvent);
    }
}
