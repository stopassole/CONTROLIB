package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ComboBox;

import javafx.scene.image.ImageView;

import javafx.scene.layout.Pane;
import resource.Inicio;

public class CadastroUsuarioController extends DashboardController {
	@FXML
	private Pane idDataNasc;
	@FXML
	private TextField idNome;
	@FXML
	private TextField idSobrenome;
	@FXML
	private TextField idEndereco;
	@FXML
	private TextField idEmail;
	@FXML
	private TextField idTelefone;
	@FXML
	private TextField idCPF;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnSalvar;
	@FXML
	private ImageView bntCancelar;
	@FXML
	private ComboBox idTipo;

	@FXML
	public void fechar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListUsuario.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}
	
	@FXML
	public void salvar() throws Exception {
		fechar();
	}
}
