package controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import dao.EmprestimoDAO;
import entity.EmprestimoDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import resource.Inicio;
import util.DateUtil;

public class ListEmprestimosController extends DashboardController implements Initializable {
	@FXML
	private TextFlow idTextFlow;
	@FXML
	private Button idSair;
	@FXML
	private Button btnEmprestimos;
	@FXML
	private Button btnLivros;
	@FXML
	private Button btnUsuarios;
	@FXML
	private TextField idPesquisar;
	@FXML
	private TextField idDataInicio;
	@FXML
	private TextField idDataFim;
	@FXML
	private Button btnPesquisar;
	@FXML
	private Button btnNovoEmprestimo;
	@FXML
	private TableView<EmprestimoDTO> tbEmprestimos;
	@FXML
	private TableColumn<EmprestimoDTO, String> columUsuario;
	@FXML
	private TableColumn<EmprestimoDTO, String> columnLivro;
	@FXML
	private TableColumn<EmprestimoDTO, String> columnEmprestimo;
	@FXML
	private TableColumn<EmprestimoDTO, String> columnVencimento;
	@FXML
	private TableColumn<EmprestimoDTO, ImageView> columnImage;

	private List<EmprestimoDTO> emprestimos = new ArrayList<>();

	public static String idEmprestimo = null;

	DateUtil date = new DateUtil();

	@FXML
	public void novoEmprestimo() throws Exception {
		InfoEmprestimoController.idEmprestimo = null;
		Parent root = FXMLLoader.load(getClass().getResource("/view/CadastroEmprestimo.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idSair.setVisible(false);
		populaTabela();
		confTabela();
	}

	@SuppressWarnings("static-access")
	public void confTabela() {

		columUsuario.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getNomeUsuario()));
		columnLivro.setCellValueFactory(celula -> {
			idEmprestimo = celula.getValue().get_id();
			return new SimpleStringProperty(celula.getValue().getNomeLivro());
		});
		columnEmprestimo.setCellValueFactory(celula -> {
			try {
				return new SimpleStringProperty(date.dataFormatoYYYYMMDD(celula.getValue().getDataEmprestimo()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		});

		columnVencimento.setCellValueFactory(celula -> {
			try {
				return new SimpleStringProperty(date.dataFormatoYYYYMMDD(celula.getValue().getDataVencimento()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		});

		columnImage.setCellFactory(
				new Callback<TableColumn<EmprestimoDTO, ImageView>, TableCell<EmprestimoDTO, ImageView>>() {
					@Override
					public TableCell<EmprestimoDTO, ImageView> call(TableColumn<EmprestimoDTO, ImageView> columnImage) {
						return new TableCell<EmprestimoDTO, ImageView>() {
							ImageView img = new ImageView("/images/Group 131.png");
							Button button = new Button();
							{
								button.setGraphic(img);
								button.setMaxSize(50, 50);
								button.setStyle("-fx-background-color:transparent; -fx-cursor:hand; -fx-text-fill: #FFFFFF; ");
								button.setOnMouseClicked(visualizar(button));
							}

							public void updateItem(ImageView img, boolean empty) {
								super.updateItem(img, empty);
								if (!empty) {
									button.setId(idEmprestimo);
									setGraphic(button);
									setAlignment(Pos.CENTER);
								}
							}
						};
					}
				});

		SimpleDateFormat formatHoje = new SimpleDateFormat("dd/MM/yyyy");
		String hoje = formatHoje.format(new Date(System.currentTimeMillis()));

		tbEmprestimos.setRowFactory(row -> {
			return new TableRow<EmprestimoDTO>() {
				@Override
				public void updateItem(EmprestimoDTO item, boolean empty) {
					super.updateItem(item, empty);
					try {
						if (!empty && date.getMaiorData(hoje, item.getDataVencimento())) {
							columnVencimento.setStyle("-fx-text-fill: #FF0000;");
						}else {
							columnVencimento.setStyle("-fx-text-fill: green;");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
		});
	}

	private EventHandler<? super MouseEvent> visualizar(Button button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Parent root = null;
				try {
					idEmprestimo = button.getId();
					root = FXMLLoader.load(getClass().getResource("/view/InfoEmprestimo.fxml"));
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				Scene scene = new Scene(root);
				Inicio.myStage.setScene(scene);
			}
		});
		return null;
	}

	public void populaTabela() {
		EmprestimoDAO dao = new EmprestimoDAO();
		try {
			emprestimos = dao.buscarEmprestimos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (emprestimos != null && !emprestimos.isEmpty()) {
			tbEmprestimos.getItems().addAll(emprestimos);
		}
	}

	@SuppressWarnings("static-access")
	public void pesquisar() throws Exception {
		EmprestimoDAO dao = new EmprestimoDAO();

		if (!idPesquisar.getText().isEmpty() && !idDataInicio.getText().isEmpty() && !idDataFim.getText().isEmpty()) {
			if (date.isValidDate(idDataInicio.getText()) && date.isValidDate(idDataFim.getText())) {
				if (date.verificaMaiorData(idDataInicio.getText(), idDataFim.getText())) {
					emprestimos = dao.buscarEmprestimosFiltro(idPesquisar.getText(), idDataInicio.getText(),
							idDataFim.getText());
				} else {
					AlertFalha falha = new AlertFalha();
					falha.text = "A data de Vencimento deve ser maior que a data de Emprestimo";
					falha.clicado = idTextFlow;
					falha.start(new Stage());
				}
			} else {
				AlertFalha falha = new AlertFalha();
				falha.text = "Informe datas válidas";
				falha.clicado = idTextFlow;
				falha.start(new Stage());
			}
		} else if (!idPesquisar.getText().isEmpty() && idDataInicio.getText().isEmpty()
				&& idDataFim.getText().isEmpty()) {
			emprestimos = dao.buscarEmprestimosFiltro(idPesquisar.getText());
		} else if (idPesquisar.getText().isEmpty() && !idDataInicio.getText().isEmpty()
				&& !idDataFim.getText().isEmpty()) {
			if (date.isValidDate(idDataInicio.getText()) && date.isValidDate(idDataFim.getText())) {
				if (date.verificaMaiorData(idDataInicio.getText(), idDataFim.getText())) {
					emprestimos = dao.buscarEmprestimosFiltro(idDataInicio.getText(), idDataFim.getText());
				} else {
					AlertFalha falha = new AlertFalha();
					falha.text = "A data de Fim deve ser maior que a data de Inicio";
					falha.clicado = idTextFlow;
					falha.start(new Stage());
				}
			} else {
				AlertFalha falha = new AlertFalha();
				falha.text = "Informe datas válidas";
				falha.clicado = idTextFlow;
				falha.start(new Stage());
			}
		} else if (idPesquisar.getText().isEmpty() && !idDataInicio.getText().isEmpty() && idDataFim.getText().isEmpty()
				|| idPesquisar.getText().isEmpty() && idDataInicio.getText().isEmpty()
						&& !idDataFim.getText().isEmpty()) {
			AlertFalha falha = new AlertFalha();
			falha.text = "Preencha os dois campos de datas para filtrar por data.";
			falha.clicado = idTextFlow;
			falha.start(new Stage());
		} else if (!idPesquisar.getText().isEmpty() && !idDataInicio.getText().isEmpty()
				&& idDataFim.getText().isEmpty()
				|| !idPesquisar.getText().isEmpty() && idDataInicio.getText().isEmpty()
						&& !idDataFim.getText().isEmpty()) {
			AlertFalha falha = new AlertFalha();
			falha.text = "Preencha os dois campos de datas para filtrar por data.";
			falha.clicado = idTextFlow;
			falha.start(new Stage());
		} else {
			emprestimos = dao.buscarEmprestimos();
		}
		tbEmprestimos.getItems().setAll(emprestimos);
		confTabela();
	}

	@SuppressWarnings("static-access")
	@FXML
	public void tbDataInicio(KeyEvent event) {
		date.mascaraData(event, idDataInicio);
	}

	@SuppressWarnings("static-access")
	@FXML
	public void tbDataFim(KeyEvent event) {
		date.mascaraData(event, idDataFim);
	}

	public void enterPressedNovoEmprestimo(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			novoEmprestimo();
		}
	}

	public void enterPressedPesquisar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			pesquisar();
		}
	}

	@FXML
	public void change() {
		if (idSair.isVisible()) {
			idSair.setVisible(false);
		} else {
			idSair.setVisible(true);
		}

	}
}
