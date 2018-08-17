package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import entity.Livro;

public class LivroDAO {

	public static final String END_POINT = "LivroDAO";

	private static final Logger log = Logger.getLogger(LivroDAO.END_POINT);

	DAO dao = new DAO();

	public void salvarLivro(Livro livro) throws Exception {
		log.info(END_POINT + "/salvarlivro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement(
				"INSERT INTO livro(nome,codigo,autor,genero,editora, publicacao) VALUES (?, ?, ?, ?, ?, ?)");
		stmt.setString(1, livro.getNome());
		stmt.setString(2, livro.getCodigo());
		stmt.setString(3, livro.getAutor());
		stmt.setString(4, livro.getGenero());
		stmt.setString(5, livro.getEditora());
		if (livro.getPublicacao() != null && !livro.getPublicacao().equals("")) {
			SimpleDateFormat dataOriginal = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date date = dataOriginal.parse(livro.getPublicacao());
			SimpleDateFormat novaData = new SimpleDateFormat("yyyy-MM-dd");
			stmt.setDate(6, Date.valueOf(novaData.format(date)));
		} else {
			stmt.setDate(6, null);
		}

		stmt.executeUpdate();

		conexao.close();

		log.info(END_POINT + "/salvarlivro -> Fim");

	}

	public void editarLivro(String idLivroEditar, Livro livro) throws Exception {
		log.info(END_POINT + "/editarlivro -> Inicio");

		Livro livroById = getByIdLivro(idLivroEditar);

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement(
				"UPDATE livro SET nome = ?, codigo = ?, autor = ?,  genero = ?, editora = ? , publicacao = ?,  disponivel = ?  WHERE _id = \'"
						+ idLivroEditar + "\';");

		stmt.setString(1, livro.getNome());
		stmt.setString(2, livro.getCodigo());
		stmt.setString(3, livro.getAutor());
		stmt.setString(4, livro.getGenero());
		stmt.setString(5, livro.getEditora());
		if (livro.getPublicacao() != null && !livro.getPublicacao().equals("")) {
			SimpleDateFormat dataOriginal = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date date = dataOriginal.parse(livro.getPublicacao());
			SimpleDateFormat novaData = new SimpleDateFormat("yyyy-MM-dd");
			stmt.setDate(6, Date.valueOf(novaData.format(date)));
		} else {
			stmt.setDate(6, null);
		}
		stmt.setBoolean(7, livroById.getDisponivel());

		stmt.executeUpdate();

		conexao.close();

		log.info(END_POINT + "/editarlivro -> Fim");
	}

	public void editarDisponivel(String idLivroEditar, Boolean disponivel) throws Exception {
		log.info(END_POINT + "/editardisponivel -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("UPDATE livro SET disponivel = ? WHERE _id = \'" + idLivroEditar + "\';");
		stmt.setBoolean(1, disponivel);

		stmt.executeUpdate();

		conexao.close();

		log.info(END_POINT + "/editardisponivel -> Fim");
	}

	public List<Livro> validaLivro(Livro livro1) throws Exception {
		log.info(END_POINT + "/validalivro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		String sql = "SELECT * FROM livro WHERE livro.codigo= \'" + livro1.getCodigo() + "\'AND livro.deletado =\'"
				+ false + "\';";
		PreparedStatement stmt = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery();

		List<Livro> list = new ArrayList<>();
		
		while (rs.next()) {
			Livro livro = new Livro(null, null, null, null, null, null, null, null, null, null);
			livro.set_id(rs.getString("_id"));
			livro.setNome(rs.getString("nome"));
			livro.setCodigo(rs.getString("codigo"));
			livro.setAutor(rs.getString("autor"));
			livro.setGenero(rs.getString("genero"));
			livro.setEditora(rs.getString("editora"));
			livro.setPublicacao(String.valueOf(rs.getDate("publicacao")));
			livro.setDisponivel(rs.getBoolean("disponivel"));
			livro.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
			livro.setDeletado(rs.getBoolean("deletado"));
			
			list.add(livro);
		}

		conexao.close();

		log.info(END_POINT + "/validalivro -> Fim");

		return list;
	}

	public List<Livro> buscarLivros() throws Exception {
		log.info(END_POINT + "/buscarlivros -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM livro WHERE deletado = false;");
		ResultSet rs = stmt.executeQuery();

		List<Livro> list = new ArrayList<>();

		while (rs.next()) {
			Livro livro = new Livro(null, null, null, null, null, null, null, null, null, null);

			livro.set_id(rs.getString("_id"));
			livro.setNome(rs.getString("nome"));
			livro.setCodigo(rs.getString("codigo"));
			livro.setAutor(rs.getString("autor"));
			livro.setGenero(rs.getString("genero"));
			livro.setEditora(rs.getString("editora"));
			livro.setPublicacao(String.valueOf(rs.getDate("publicacao")));
			livro.setDisponivel(rs.getBoolean("disponivel"));
			livro.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
			livro.setDeletado(rs.getBoolean("deletado"));

			list.add(livro);
		}

		conexao.close();

		log.info(END_POINT + "/buscarlivros -> Fim");

		return list;
	}

	public List<Livro> buscarLivrosDisponiveis() throws Exception {
		log.info(END_POINT + "/buscarlivrosdisponiveis -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("SELECT * FROM livro WHERE deletado = false AND livro.disponivel = true;");
		ResultSet rs = stmt.executeQuery();

		List<Livro> list = new ArrayList<>();

		while (rs.next()) {
			Livro livro = new Livro(null, null, null, null, null, null, null, null, null, null);

			livro.set_id(rs.getString("_id"));
			livro.setNome(rs.getString("nome"));
			livro.setCodigo(rs.getString("codigo"));
			livro.setAutor(rs.getString("autor"));
			livro.setGenero(rs.getString("genero"));
			livro.setEditora(rs.getString("editora"));
			livro.setPublicacao(String.valueOf(rs.getDate("publicacao")));
			livro.setDisponivel(rs.getBoolean("disponivel"));
			livro.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
			livro.setDeletado(rs.getBoolean("deletado"));

			list.add(livro);
		}

		conexao.close();

		log.info(END_POINT + "/buscarlivrosdisponiveis -> Fim");

		return list;
	}

	public List<Livro> buscarLivrosFiltro(String text) throws Exception {
		log.info(END_POINT + "/buscarlivrosfiltro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement(
				"SELECT * FROM livro WHERE deletado = false and livro.nome LIKE '%" + text + "%' or livro.autor LIKE '%"
						+ text + "%' or livro.genero LIKE '%" + text + "%' or livro.codigo LIKE '%" + text + "%';");
		ResultSet rs = stmt.executeQuery();

		List<Livro> list = new ArrayList<>();

		while (rs.next()) {
			Livro livro = new Livro(null, null, null, null, null, null, null, null, null, null);

			livro.set_id(rs.getString("_id"));
			livro.setNome(rs.getString("nome"));
			livro.setCodigo(rs.getString("codigo"));
			livro.setAutor(rs.getString("autor"));
			livro.setGenero(rs.getString("genero"));
			livro.setEditora(rs.getString("editora"));
			livro.setPublicacao(String.valueOf(rs.getDate("publicacao")));
			livro.setDisponivel(rs.getBoolean("disponivel"));
			livro.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
			livro.setDeletado(rs.getBoolean("deletado"));

			list.add(livro);
		}

		conexao.close();

		log.info(END_POINT + "/buscarlivrosfiltro -> Fim");

		return list;
	}

	public void excluirLivro(String idLivro) throws Exception {
		log.info(END_POINT + "/excluirlivro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("UPDATE livro SET deletado = true WHERE _id = \'" + idLivro + "\';");

		stmt.executeUpdate();

		conexao.close();

		log.info(END_POINT + "/excluirlivro -> Fim");
	}

	public Livro getByIdLivro(String idLivro) throws Exception {
		log.info(END_POINT + "/buscarlivrobyid -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("SELECT * FROM livro WHERE deletado = false and _id = \'" + idLivro + "\';");
		ResultSet rs = stmt.executeQuery();

		Livro livro = new Livro(null, null, null, null, null, null, null, null, null, null);

		if (rs.next()) {

			livro.set_id(rs.getString("_id"));
			livro.setNome(rs.getString("nome"));
			livro.setCodigo(rs.getString("codigo"));
			livro.setAutor(rs.getString("autor"));
			livro.setGenero(rs.getString("genero"));
			livro.setEditora(rs.getString("editora"));
			livro.setPublicacao(String.valueOf(rs.getDate("publicacao")));
			livro.setDisponivel(rs.getBoolean("disponivel"));
			livro.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
			livro.setDeletado(rs.getBoolean("deletado"));
		}

		conexao.close();

		log.info(END_POINT + "/buscarlivrobyid  -> Fim");

		return livro;
	}

}
