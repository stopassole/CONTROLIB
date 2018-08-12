package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import dao.DAO;
import javafx.concurrent.Task;
import javafx.scene.control.Button;

public class CreateDatabase extends Task<Void> {

	public static final String END_POINT = "CreateDatabase";

	private static final Logger log = Logger.getLogger(CreateDatabase.END_POINT);

	DAO dao = new DAO();
	Button button;

	public CreateDatabase(Button button) {
		this.button = button;
	}

	public void createTableCadastro() {
		try {

			log.info(END_POINT + "/criartabelacadastro -> Inicio");

			Connection conexao = dao.conexaoUsuario();
			String sql = "CREATE TABLE cadastro(_id serial NOT NULL,\r\n"
					+ "					email character varying(255) not null,\r\n"
					+ "					empresa character varying(255) not null,\r\n"
					+ "					cpf character varying(255) not null,\r\n"
					+ "					senha character varying(255) not null,\r\n"
					+ "					lembrarSenha boolean default false,\r\n"
					+ "					dataCadastro date default now(),\r\n"
					+ "					CONSTRAINT cadastro_pkey PRIMARY KEY (_id)) ";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.executeQuery();

			log.info(END_POINT + "/criartabelacadastro -> Fim");

		} catch (Exception e) {

		} finally {
			this.updateProgress(25, 100);
			createTableUsuario();
		}
	}

	public void createTableUsuario() {
		try {

			log.info(END_POINT + "/criartabelausuario -> Inicio");

			Connection conexao = dao.conexaoUsuario();
			String sql = "CREATE TABLE usuario(_id serial NOT NULL,\r\n"
					+ "					nome character varying(255) not null,\r\n"
					+ "					sobrenome character varying(255) not null,\r\n"
					+ "					endereco character varying(255),\r\n"
					+ "					email character varying(255) not null,\r\n"
					+ "					telefone character varying(255) not null,\r\n"
					+ "					cpf character varying(255),\r\n"
					+ "					dataNascimento date not null,\r\n"
					+ "					tipo character varying(255) not null,\r\n"
					+ "					dataCadastro date default now(),\r\n"
					+ "					deletado boolean default false,\r\n"
					+ "					CONSTRAINT usuario_pkey PRIMARY KEY (_id)) ";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.executeQuery();

			log.info(END_POINT + "/criartabelausuario -> Fim");

		} catch (Exception e) {

		} finally {
			this.updateProgress(50, 100);
			createTableLivro();
		}
	}

	private void createTableLivro() {
		try {

			log.info(END_POINT + "/criartabelalivro -> Inicio");

			Connection conexao = dao.conexaoUsuario();
			String sql = "CREATE TABLE livro(_id serial NOT NULL,\r\n"
					+ "					nome character varying(255) not null,\r\n"
					+ "					codigo character varying(255),\r\n"	
					+ "					autor character varying(255) not null,\r\n"
					+ "					genero character varying(255),\r\n"
					+ "					editora character varying(255),\r\n"
					+ "					publicacao date,\r\n"
					+ "					quantidadetotal integer not null,\r\n"
					+ "					quantidadedisponivel integer not null,\r\n"
					+ "					dataCadastro date default now(),\r\n"
					+ "					deletado boolean default false,\r\n"
					+ "					CONSTRAINT livro_pkey PRIMARY KEY (_id)) ";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.executeQuery();

			log.info(END_POINT + "/criartabelalivro -> Fim");

		} catch (Exception e) {

		} finally {
			this.updateProgress(100, 100);
		}
	}

	@Override
	protected Void call() throws Exception {
		try {

			log.info(END_POINT + "/criardatabase -> Inicio");

			this.updateProgress(10, 100);
			String url = "jdbc:postgresql://localhost:5432/";
			String usuario = "postgres";
			String senha = "postgres";

			Class.forName("org.postgresql.Driver");
			Connection conexao = DriverManager.getConnection(url, usuario, senha);
			PreparedStatement stmt = conexao.prepareStatement("CREATE DATABASE controlib");
			stmt.execute();
			this.updateProgress(20, 100);

			log.info(END_POINT + "/criardatabase -> Fim");

		} catch (Exception e) {

		} finally {
			this.updateProgress(10, 100);
			createTableCadastro();
			button.setVisible(true);
		}

		return null;
	}
}
