package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import entity.Cadastro;

public class CadastroDAO {

	public static final String END_POINT = "CadastroDAO";

	private static final Logger log = Logger.getLogger(CadastroDAO.END_POINT);

	DAO dao = new DAO();

	public int contCadastro() throws Exception {

		log.info(END_POINT + "/contcadastro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		String sql = "SELECT count(*) FROM cadastro";
		PreparedStatement stmt = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet = stmt.executeQuery();
		int cont = 0;
		if (resultSet.next()) {
			cont = resultSet.getInt(1);
		}
		log.info(END_POINT + "/contcadastro -> Fim");

		return cont;
	}

	public void salvarCadastro(Cadastro cadastro) throws Exception {

		log.info(END_POINT + "/salvarcadastro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("INSERT INTO cadastro(email,empresa,cpf,senha) VALUES (?, ?, ?, ?)");
		stmt.setString(1, cadastro.getEmail());
		stmt.setString(2, cadastro.getEmpresa());
		stmt.setString(3, cadastro.getCpf());
		stmt.setString(4, cadastro.getSenha());

		stmt.executeUpdate();

		log.info(END_POINT + "/salvarcadastro-> Fim");
	}

	public void alterarSenha(String email, String cpf, String senhaCriptografada) throws Exception {
		log.info(END_POINT + "/alterarsenha -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("UPDATE cadastro SET senha = ? WHERE cpf = ? AND email = ?");

		stmt.setString(1, senhaCriptografada);
		stmt.setString(2, cpf);
		stmt.setString(3, email);

		stmt.executeUpdate();

		log.info(END_POINT + "/alterarsenha -> Fim");
	}

	public String getValida(String email, String cpf) throws Exception {
		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("SELECT _id FROM cadastro WHERE cadastro.email= ? AND cadastro.cpf = ?;");
		stmt.setString(1, email);
		stmt.setString(2, cpf);

		ResultSet rs = stmt.executeQuery();

		String valor = null;
		if (rs.next()) {
			valor = rs.getString("_id");
		}
		return valor;
	}

	public String getValidaCadastro(String email, String senha) throws Exception {
		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("SELECT _id FROM cadastro WHERE cadastro.email= ? AND cadastro.senha = ?;");
		stmt.setString(1, email);
		stmt.setString(2, senha);

		ResultSet rs = stmt.executeQuery();

		String valor = null;
		if (rs.next()) {
			valor = rs.getString("_id");
		}
		return valor;
	}

	public void alterarLembrarSenha(String email, String senha, Boolean lembrarSenha) throws Exception {
		log.info(END_POINT + "/alterarlembrarsenha -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("UPDATE cadastro SET lembrarSenha = ? WHERE senha = ? AND email = ?");

		stmt.setBoolean(1, lembrarSenha);
		stmt.setString(2, senha);
		stmt.setString(3, email);

		stmt.executeUpdate();

		log.info(END_POINT + "/alterarlembrarsenha -> Fim");
	}

	public Cadastro buscarCadastro() throws Exception {
		log.info(END_POINT + "/buscarcadastro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM cadastro");

		ResultSet rs = stmt.executeQuery();

		Cadastro cadastro = new Cadastro(null, null, null, null, null, null, null);

		if (rs.next()) {
			cadastro.set_id(rs.getString("_id"));
			cadastro.setCpf(rs.getString("cpf"));
			cadastro.setEmail(rs.getString("email"));
			cadastro.setEmpresa(rs.getString("empresa"));
			cadastro.setLembraSenha(rs.getBoolean("lembrarSenha"));
			cadastro.setSenha(rs.getString("senha"));
			cadastro.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
		}

		log.info(END_POINT + "/buscarcadastro -> Fim");

		return cadastro;
	}

}
