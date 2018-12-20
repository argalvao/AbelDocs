package client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class TelaController {

	@FXML
	Button loginButton;

	@FXML
	Button newDocument;

	@FXML
	Hyperlink cadastroTrue;

	@FXML
	Hyperlink cadastrar;

	@FXML
	public void loginEvent(ActionEvent event) {
		TelaController.carregarTela(event, "/client/Page.fxml", "Docs do Sertão");
	}

	@FXML
	public void registerEvent(ActionEvent event) {
		TelaController.carregarTela(event, "/client/Cadastro.fxml", "Docs do Sertão");
	}

	@FXML
	public void terminalEvent(ActionEvent event) {
		TelaController.carregarTela(event, "/client/Terminal.fxml", "Docs do Sertão");
	}

	@FXML
	public void homeEvent(ActionEvent event) {
		TelaController.carregarTela(event, "/client/Main.fxml", "Docs do Sertão");
	}
	
	@FXML
	public void text() {
		
	}

	// Abre uma nova tela com o nome passado como par�metro
	public static void carregarTela(ActionEvent evento, String nomeTela, String titulo) {
		if (evento != null) { // tem alguma tela para fechar antes
			Node telaAtual = (Node) evento.getSource();
			telaAtual.getScene().getWindow().hide(); // fecha a tela atual antes de abrir a pr�xima
		}
		try {
			FXMLLoader carregadorTela = new FXMLLoader(TelaController.class.getResource(nomeTela));
			Parent base = (Parent) carregadorTela.load();
			Scene scene = new Scene(base);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle(titulo);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
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

}
