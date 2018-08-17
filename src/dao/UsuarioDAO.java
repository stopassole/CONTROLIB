package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import entity.Usuario;

public class UsuarioDAO {

	public static final String END_POINT = "UsuarioDAO";

	private static final Logger log = Logger.getLogger(UsuarioDAO.END_POINT);

	DAO dao = new DAO();

	public void salvarUsuario(Usuario usuario) throws Exception {
		log.info(END_POINT + "/salvarusuario -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement(
				"INSERT INTO usuario(nome,sobrenome,endereco,email,telefone,cpf,dataNascimento, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		stmt.setString(1, usuario.getNome());
		stmt.setString(2, usuario.getSobrenome());
		stmt.setString(3, usuario.getEndereco());
		stmt.setString(4, usuario.getEmail());
		stmt.setString(5, usuario.getTelefone());
		stmt.setString(6, usuario.getCPF());
		SimpleDateFormat dataOriginal = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date date = dataOriginal.parse(usuario.getDataNascimento());
		SimpleDateFormat novaData = new SimpleDateFormat("yyyy-MM-dd");
		stmt.setDate(7, Date.valueOf(novaData.format(date)));
		stmt.setString(8, usuario.getTipo());

		stmt.executeUpdate();

		conexao.close();

		log.info(END_POINT + "/salvarusuario -> Fim");
	}

	public List<Usuario> buscarUsuarios() throws Exception {

		log.info(END_POINT + "/buscarusuarios -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM usuario WHERE deletado = false;");
		ResultSet rs = stmt.executeQuery();

		List<Usuario> list = new ArrayList<>();

		while (rs.next()) {
			Usuario u = new Usuario(null, null, null, null, null, null, null, null, null, null, null);

			u.set_id(rs.getString("_id"));
			u.setNome(rs.getString("nome"));
			u.setSobrenome(rs.getString("sobrenome"));
			u.setEndereco(rs.getString("endereco"));
			u.setEmail(rs.getString("email"));
			u.setTelefone(rs.getString("telefone"));
			u.setCPF(rs.getString("cpf"));
			u.setDataNascimento(String.valueOf(rs.getDate("datanascimento")));
			u.setTipo(rs.getString("tipo"));
			u.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
			u.setDeletado(rs.getBoolean("deletado"));

			list.add(u);
		}

		conexao.close();

		log.info(END_POINT + "/buscarusuarios -> Fim");

		return list;
	}

	public Usuario getByIdUsuario(String idUsuario) throws Exception {

		log.info(END_POINT + "/buscarusuariobyid -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("SELECT * FROM usuario WHERE deletado = false and _id = \'" + idUsuario + "\';");
		ResultSet rs = stmt.executeQuery();

		Usuario u = new Usuario(null, null, null, null, null, null, null, null, null, null, null);

		if (rs.next()) {

			u.set_id(rs.getString("_id"));
			u.setNome(rs.getString("nome"));
			u.setSobrenome(rs.getString("sobrenome"));
			u.setEndereco(rs.getString("endereco"));
			u.setEmail(rs.getString("email"));
			u.setTelefone(rs.getString("telefone"));
			u.setCPF(rs.getString("cpf"));
			u.setDataNascimento(String.valueOf(rs.getDate("datanascimento")));
			u.setTipo(rs.getString("tipo"));
			u.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
			u.setDeletado(rs.getBoolean("deletado"));
		}
		conexao.close();

		log.info(END_POINT + "/buscarusuariobyid -> Fim");

		return u;
	}

	public void editarUsuario(String idUsuarioEditar, Usuario usuario) throws Exception {

		log.info(END_POINT + "/editarusuario -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement(
				"UPDATE usuario SET nome = ?, sobrenome = ?, endereco = ?, email = ?, telefone = ?, cpf = ?, datanascimento = ?, tipo = ? WHERE _id = \'"
						+ idUsuarioEditar + "\';");
		stmt.setString(1, usuario.getNome());
		stmt.setString(2, usuario.getSobrenome());
		stmt.setString(3, usuario.getEndereco());
		stmt.setString(4, usuario.getEmail());
		stmt.setString(5, usuario.getTelefone());
		stmt.setString(6, usuario.getCPF());
		SimpleDateFormat dataOriginal = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date date = dataOriginal.parse(usuario.getDataNascimento());
		SimpleDateFormat novaData = new SimpleDateFormat("yyyy-MM-dd");
		stmt.setDate(7, Date.valueOf(novaData.format(date)));
		stmt.setString(8, usuario.getTipo());

		stmt.executeUpdate();

		conexao.close();

		log.info(END_POINT + "/editarusuario -> Fim");
	}

	public List<Usuario> buscarUsuariosFiltro(String text) throws Exception {

		log.info(END_POINT + "/buscarusuariosfiltro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("SELECT * FROM usuario WHERE deletado = false and usuario.nome LIKE '%" + text
						+ "%' or usuario.sobrenome LIKE '%" + text + "%' or usuario.email LIKE  '%" + text
						+ "%' or usuario.tipo LIKE  '%" + text + "%';");
		ResultSet rs = stmt.executeQuery();

		List<Usuario> list = new ArrayList<>();

		while (rs.next()) {
			Usuario u = new Usuario(null, null, null, null, null, null, null, null, null, null, null);

			u.set_id(rs.getString("_id"));
			u.setNome(rs.getString("nome"));
			u.setSobrenome(rs.getString("sobrenome"));
			u.setEndereco(rs.getString("endereco"));
			u.setEmail(rs.getString("email"));
			u.setTelefone(rs.getString("telefone"));
			u.setCPF(rs.getString("cpf"));
			u.setDataNascimento(String.valueOf(rs.getDate("datanascimento")));
			u.setTipo(rs.getString("tipo"));
			u.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
			u.setDeletado(rs.getBoolean("deletado"));

			list.add(u);
		}

		conexao.close();

		log.info(END_POINT + "/buscarusuariosfiltro -> Fim");

		return list;
	}

	public List<Usuario> validaUsuario(Usuario usuario) throws Exception {

		log.info(END_POINT + "/validausuario -> Inicio");

		SimpleDateFormat dataOriginal = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date date = dataOriginal.parse(usuario.getDataNascimento());
		SimpleDateFormat novaData = new SimpleDateFormat("yyyy-MM-dd");

		Connection conexao = dao.conexaoUsuario();
		String sql = "SELECT * FROM usuario WHERE usuario.nome= \'" + usuario.getNome()
				+ "\'AND usuario.sobrenome =\'" + usuario.getSobrenome() + "\'AND usuario.email =\'"
				+ usuario.getEmail() + "\'AND usuario.telefone =\'" + usuario.getTelefone()
				+ "\'AND usuario.datanascimento =\'" + Date.valueOf(novaData.format(date)) + "\'AND usuario.tipo =\'"
				+ usuario.getTipo() + "\'AND usuario.deletado =\'" + false + "\';";
		PreparedStatement stmt = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery();
		
		List<Usuario> list = new ArrayList<>();

		while (rs.next()) {
			Usuario u = new Usuario(null, null, null, null, null, null, null, null, null, null, null);

			u.set_id(rs.getString("_id"));
			u.setNome(rs.getString("nome"));
			u.setSobrenome(rs.getString("sobrenome"));
			u.setEndereco(rs.getString("endereco"));
			u.setEmail(rs.getString("email"));
			u.setTelefone(rs.getString("telefone"));
			u.setCPF(rs.getString("cpf"));
			u.setDataNascimento(String.valueOf(rs.getDate("datanascimento")));
			u.setTipo(rs.getString("tipo"));
			u.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
			u.setDeletado(rs.getBoolean("deletado"));

			list.add(u);
		}

		conexao.close();

		log.info(END_POINT + "/validausuario -> Fim");

		return list;
	}

	public void excluirUsuario(String idUsuario) throws Exception {
		log.info(END_POINT + "/excluirusuario -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("UPDATE usuario SET deletado = true WHERE _id = \'" + idUsuario + "\';");

		stmt.executeUpdate();

		conexao.close();

		log.info(END_POINT + "/excluirusuario -> Fim");
	}
}