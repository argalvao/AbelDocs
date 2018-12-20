package client;

import client.controllers.ScreenController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.channels.AlreadyBoundException;
import java.rmi.NotBoundException;


public class Main extends Application {
    private static void shutdown(Stage mainWindow) throws NotBoundException, IOException, AlreadyBoundException, ClassNotFoundException {
        //Método a ser invocado quando o usuário quiser fechar a tela
        Alert alert = new Alert(Alert.AlertType.NONE, "Você realmente gostaria de sair?", ButtonType.YES, ButtonType.NO); // Alerta de confirmação
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            Platform.exit(); //Sai da tela
            System.exit(0); //Sai do sistema
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent base = FXMLLoader.load(Main.class.getResource("Main.fxml"));
            base.setId("pane");
            Scene scene = new Scene(base);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Docs do Sertão");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            ScreenController.exibirJanela(AlertType.ERROR, "Docs do Sertão", "Erro!",
                    "Erro ao carregar a página solicitada!");
            e.printStackTrace();

        }


        primaryStage.setOnCloseRequest(evt -> { //Evento que é chamado quando uma solicitação de fechar a tela é realizada
            try {
                shutdown(primaryStage); // Chama o método shutdown
            } catch (NotBoundException | IOException | AlreadyBoundException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
