package gui;

import Domain.Player;
import Services.GameException;
import Services.IServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;

public class LogInController {
    @FXML
    private  TextField user;
    private IServices services;
    private GameController gameController;
    private Parent parent;

    public void setServices(IServices services) {
        this.services = services;
    }
    public void setParent(Parent parent) {
        this.parent = parent;
    }
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
    public void pressLogin(ActionEvent actionEvent) {
        if(!Objects.equals(user.getText(), "")){
            String nickname = user.getText();
            Player player= new Player();
            player.setNickname(nickname);
            player.setPrenume("");
            player.setNume("");
            try{
            services.login(player,gameController);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Logged in");
            alert.showAndWait();
                Stage stage=new Stage();
                stage.setTitle("Main window for " +player.getNickname());
                stage.setScene(new Scene(parent));

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.exit(0);
                    }
                });
                gameController.setPlayer(player);
                gameController.setServices(services);
                stage.show();

                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }
            catch(GameException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
