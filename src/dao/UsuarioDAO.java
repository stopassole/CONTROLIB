package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import entity.Usuario;

public class UsuarioDAO {

	public static final String END_POINT = "UsuarioDAO";

	private static final Logger log = Logger.getLogger(UsuarioDAO.END_POINT);

	DAO dao = new DAO();

	public int contUsuario() throws Exception {

		log.info(END_POINT + "/contusuario -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		String sql = "SELECT count(*) FROM usuario";
		PreparedStatement stmt = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet = stmt.executeQuery();
		int cont = 0;
		if (resultSet.next()) {
			cont = resultSet.getInt(1);
		}
		log.info(END_POINT + "/contusuario -> Fim");

		return cont;
	}

	public void salvarUsuario(Usuario usuario) throws Exception {

		log.info(END_POINT + "/salvarusuario -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("INSERT INTO Usuario(email,empresa,cpf,senha) VALUES (?, ?, ?, ?)");
		stmt.setString(1, usuario.getEmail());
		stmt.setString(2, usuario.getEmpresa());
		stmt.setString(3, usuario.getCpf());
		stmt.setString(4, usuario.getSenha());

		stmt.executeUpdate();

		log.info(END_POINT + "/salvarusuario -> Fim");
	}

	public void alterarSenha(String email, String cpf, String senhaCriptografada) throws Exception {
		log.info(END_POINT + "/alterarsenha -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("UPDATE usuario SET senha = ? WHERE cpf = ? AND email = ?");

		stmt.setString(1, senhaCriptografada);
		stmt.setString(2, cpf);
		stmt.setString(3, email);

		stmt.executeUpdate();

		log.info(END_POINT + "/alterarsenha -> Fim");
	}

	public String getValida(String email, String cpf) throws Exception {
		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("SELECT _id FROM usuario WHERE usuario.email= ? AND usuario.cpf = ?;");
		stmt.setString(1, email);
		stmt.setString(2, cpf);

		ResultSet rs = stmt.executeQuery();

		String valor = null;
		if (rs.next()) {
			valor = rs.getString("_id");
		}
		return valor;
	}

	public String getValidaUsuario(String email, String senha) throws Exception {
		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("SELECT _id FROM usuario WHERE usuario.email= ? AND usuario.senha = ?;");
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
		PreparedStatement stmt = conexao.prepareStatement("UPDATE usuario SET lembrarSenha = ? WHERE senha = ? AND email = ?");

		stmt.setBoolean(1, lembrarSenha);
		stmt.setString(2, senha);
		stmt.setString(3, email);

		stmt.executeUpdate();

		log.info(END_POINT + "/alterarlembrarsenha -> Fim");
	}

	public Usuario buscarUsuario() throws Exception {
		log.info(END_POINT + "/buscarusuario -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM usuario");

		ResultSet rs = stmt.executeQuery();

		Usuario usuario = new Usuario(null, null, null, null, null, null);

		if (rs.next()) {
			usuario.set_id(rs.getString("_id"));
			usuario.setCpf(rs.getString("cpf"));
			usuario.setEmail(rs.getString("email"));
			usuario.setEmpresa(rs.getString("empresa"));
			usuario.setLembraSenha(rs.getBoolean("lembrarSenha"));
			usuario.setSenha(rs.getString("senha"));
		}

		log.info(END_POINT + "/buscarusuario -> Fim");

		return usuario;
	}

}
