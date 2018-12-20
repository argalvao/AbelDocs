package client.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ScreenController {

    @FXML
    Button loginButton;

    @FXML
    Button newDocument;

    @FXML
    Hyperlink cadastroTrue;

    @FXML
    Hyperlink cadastrar;

    @FXML
    TextField usernameInput;

    @FXML
    ComboBox<String> selectDocuments;

    @FXML
    TextArea textAreaDocument;

    // Abre uma nova tela com o nome passado como par�metro
    private void loadScreen(ActionEvent event, String screenName, String title) {
        if (event != null) { // tem alguma tela para fechar antes
            Node currentScreen = (Node) event.getSource();
            currentScreen.getScene().getWindow().hide(); // fecha a tela atual antes de abrir a pr�xima
        }
        try {
            FXMLLoader screenLoader = new FXMLLoader(ScreenController.class.getResource(screenName));
            Parent base = screenLoader.load();
            Scene scene = new Scene(base);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            exibirJanela(AlertType.ERROR, "Alerta de Erro", "Erro!", "Não foi possível carregar a página solicitada!");
        }
    }

    // Exibe um alerta padrão de confirmaçãoo/erro na tela atual
    public static void exibirJanela(AlertType tipoAlerta, String titulo, String cabecalho, String conteudo) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(conteudo);
        alerta.showAndWait();
    }

    @FXML
    public void loginEvent(ActionEvent event) {
        ClientController.getInstance().setUserName(this.usernameInput.getText());
        ObservableList<String> documents = ClientController.getInstance().getUserDocuments();
        if (documents != null) {
            //this.selectDocuments.setItems(documents);
            this.loadScreen(event, "/client/Page.fxml", "Docs do Sertão");
        }
        ScreenController.exibirJanela(AlertType.ERROR, "Docs do Sertão", "Erro!",
                "Você não possui cadastro no Sistema");
    }

    @FXML
    public void registerEvent(ActionEvent event) {
        this.loadScreen(event, "/client/Cadastro.fxml", "Docs do Sertão");
    }

    @FXML
    public void terminalEvent(ActionEvent event) {
        String documentName = ClientController.getInstance().createDocument();
        if (documentName != null) {
            String content = ClientController.getInstance().openDocument(documentName);
            System.out.println(this.textAreaDocument);
            //this.textAreaDocument.setText(content != null ? content : "");
            this.loadScreen(event, "/client/Terminal.fxml", "Docs do Sertão");
        }
    }

    @FXML
    public void homeEvent(ActionEvent event) {
        this.loadScreen(event, "/client/Main.fxml", "Docs do Sertão");
    }

}
