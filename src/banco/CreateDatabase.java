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
			this.updateProgress(30, 100);
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
			this.updateProgress(40, 100);
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
					+ "					publicacao character varying(255),\r\n"
					+ "					disponivel boolean default true,\r\n"
					+ "					dataCadastro date default now(),\r\n"
					+ "					deletado boolean default false,\r\n"
					+ "					CONSTRAINT livro_pkey PRIMARY KEY (_id)) ";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.executeQuery();

			log.info(END_POINT + "/criartabelalivro -> Fim");

		} catch (Exception e) {

		} finally {
			this.updateProgress(60, 100);
			createTableEmprestimo();
		}
	}

	public void createTableEmprestimo() {
		try {

			log.info(END_POINT + "/criartabelaemprestimo -> Inicio");

			Connection conexao = dao.conexaoUsuario();
			String sql = "CREATE TABLE emprestimo(_id serial NOT NULL,\r\n"
					+ "					idUsuario integer not null,\r\n"
					+ "					idLivro integer not null,\r\n"
					+ "					dataEmprestimo date not null,\r\n"
					+ "					dataVencimento date not null,\r\n"
					+ "					dataCadastro date default now(),\r\n"
					+ "					deletado boolean default false,\r\n"
					+ "   				CONSTRAINT fk_livro FOREIGN KEY (idLivro)\r\n"
					+ "     		    REFERENCES livro (_id) MATCH SIMPLE\r\n"
					+ "                 ON UPDATE NO ACTION\r\n" + "                 ON DELETE SET NULL,\r\n"
					+ "					CONSTRAINT fk_usuario FOREIGN KEY (idUsuario)\r\n"
					+ "       		    REFERENCES usuario (_id) MATCH SIMPLE\r\n"
					+ "					ON UPDATE NO ACTION\r\n" + "  		        ON DELETE SET NULL,"
					+ "					CONSTRAINT emprestimo_pkey PRIMARY KEY (_id)) ";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.executeQuery();

			log.info(END_POINT + "/criartabelaemprestimo -> Fim");

		} catch (Exception e) {

		} finally {
			this.updateProgress(80, 100);
			createViewAllEmprestimos();
		}
	}

	private void createViewAllEmprestimos() {
		try {
			log.info(END_POINT + "/criarviewallemprestimos-> Inicio");

			Connection conexao = dao.conexaoUsuario();
			String sql = "create view allEmprestimos as\r\n" + "select\r\n" + "emprestimo._id,\r\n"
					+ "emprestimo.idUsuario,\r\n" + "emprestimo.idLivro,\r\n" + "emprestimo.dataVencimento, \r\n"
					+ "emprestimo.dataEmprestimo,\r\n" + "emprestimo.dataCadastro as dataCadastroEmprestimo,\r\n"
					+ "emprestimo.deletado as emprestimoDeletado,\r\n" + "livro.nome as nomeLivro,\r\n"
					+ "livro.codigo as codigoLivro,\r\n" + "livro.autor as autorLivro,\r\n"
					+ "livro.genero as generoLivro,\r\n" + "livro.editora as editoraLivro,\r\n"
					+ "livro.disponivel as livroDisponivel,\r\n" + "livro.dataCadastro as dataCadastroLivro,\r\n"
					+ "livro.publicacao as publicacaoLivro,\r\n" + "livro.deletado as livroDeletado,\r\n"
					+ "usuario.nome as nomeUsuario,\r\n" + "usuario.sobrenome as sobrenomeUsuario,\r\n"
					+ "usuario.endereco as enderecoUsuario,\r\n" + "usuario.email as emailUsuario,\r\n"
					+ "usuario.telefone as telefoneUsuario,\r\n" + "usuario.cpf as CPFUsuario,\r\n"
					+ "usuario.dataNascimento as dataNascimentoUsuario,\r\n" + "usuario.tipo as tipoUsuario,\r\n"
					+ "usuario.dataCadastro as dataCadastroUsuario,\r\n" + "usuario.deletado as usuarioDeletado\r\n"
					+ "from livro\r\n" + "join emprestimo on livro._id = emprestimo.idLivro\r\n"
					+ "join usuario on  usuario._id = emprestimo.idUsuario\r\n" + "and usuario.deletado = false\r\n"
					+ "and livro.deletado = false\r\n" + "and emprestimo.deletado = false\r\n" + "\r\n" + "";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.executeQuery();

			log.info(END_POINT + "/criarviewallemprestimos -> Fim");

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
			createTableCadastro();
			button.setVisible(true);
		}

		return null;
	}
}
