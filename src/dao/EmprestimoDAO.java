package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import entity.Emprestimo;
import entity.EmprestimoDTO;

public class EmprestimoDAO {

	public static final String END_POINT = "EmprestimoDAO";

	private static final Logger log = Logger.getLogger(EmprestimoDAO.END_POINT);

	DAO dao = new DAO();

	public void salvarEmprestimo(Emprestimo emprestimo) throws Exception {
		log.info(END_POINT + "/salvaremprestimo -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement(
				"INSERT INTO emprestimo(idUsuario,idLivro,dataEmprestimo,dataVencimento) VALUES (?, ?, ?, ?)");
		stmt.setInt(1, Integer.valueOf(emprestimo.getIdUsuario()));
		stmt.setInt(2, Integer.valueOf(emprestimo.getIdLivro()));

		SimpleDateFormat dataOriginal = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date date = dataOriginal.parse(emprestimo.getDataEmprestimo());
		SimpleDateFormat novaData = new SimpleDateFormat("yyyy-MM-dd");
		stmt.setDate(3, Date.valueOf(novaData.format(date)));

		java.util.Date dateVencimento = dataOriginal.parse(emprestimo.getDataVencimento());
		stmt.setDate(4, Date.valueOf(novaData.format(dateVencimento)));

		stmt.executeUpdate();

		conexao.close();

		log.info(END_POINT + "/salvaremprestimo -> Fim");

	}

	public List<EmprestimoDTO> buscarEmprestimos() throws Exception {
		log.info(END_POINT + "/buscaremprestimos -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("SELECT * from allEmprestimos");

		ResultSet rs = stmt.executeQuery();

		List<EmprestimoDTO> list = new ArrayList<>();

		while (rs.next()) {
			EmprestimoDTO dto = new EmprestimoDTO(null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

			dto.set_id(rs.getString("_id"));
			dto.setIdUsuario(rs.getString("idUsuario"));
			dto.setIdLivro(rs.getString("idLivro"));
			dto.setDataEmprestimo(String.valueOf(rs.getDate("dataEmprestimo")));
			dto.setDataVencimento(String.valueOf(rs.getDate("dataVencimento")));
			dto.setDataCadastroEmprestimo(String.valueOf(rs.getDate("dataCadastroEmprestimo")));
			dto.setEmprestimoDeletado(rs.getBoolean("emprestimoDeletado"));
			dto.setNomeLivro(rs.getString("nomeLivro"));
			dto.setCodigoLivro(rs.getString("codigoLivro"));
			dto.setAutorLivro(rs.getString("autorLivro"));
			dto.setGeneroLivro(rs.getString("generoLivro"));
			dto.setEditoraLivro(rs.getString("editoraLivro"));
			dto.setQuantidadeTotalLivro(rs.getInt("quantidadeTotalLivro"));
			dto.setQuantidadeDisponivelLivro(rs.getInt("quantidadeDisponivelLivro"));
			dto.setDataCadastroLivro(String.valueOf(rs.getDate("dataCadastroLivro")));
			dto.setPublicacaoLivro(String.valueOf(rs.getDate("publicacaoLivro")));
			dto.setLivroDeletado(rs.getBoolean("livroDeletado"));
			dto.setNomeUsuario(rs.getString("nomeUsuario"));
			dto.setSobrenomeUsuario(rs.getString("sobrenomeUsuario"));
			dto.setEnderecoUsuario(rs.getString("enderecoUsuario"));
			dto.setEmailUsuario(rs.getString("emailUsuario"));
			dto.setTelefoneUsuario(rs.getString("telefoneUsuario"));
			dto.setCPFUsuario(rs.getString("CPFUsuario"));
			dto.setDataNascimentoUsuario(String.valueOf(rs.getDate("dataNascimentoUsuario")));
			dto.setTipoUsuario(rs.getString("tipoUsuario"));
			dto.setDataCadastroUsuario(String.valueOf(rs.getDate("dataCadastroUsuario")));
			dto.setUsuarioDeletado(rs.getBoolean("usuarioDeletado"));

			list.add(dto);
		}

		conexao.close();

		log.info(END_POINT + "/buscaremprestimos -> Fim");

		return list;
	}

	public EmprestimoDTO getByIdEmprestimo(String idEmprestimo) throws Exception {
		log.info(END_POINT + "/buscaremprestimobyid -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("SELECT * from allEmprestimos where _id = " + idEmprestimo + ";");

		ResultSet rs = stmt.executeQuery();

		EmprestimoDTO dto = new EmprestimoDTO(null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

		if (rs.next()) {
			dto.set_id(rs.getString("_id"));
			dto.setIdUsuario(rs.getString("idUsuario"));
			dto.setIdLivro(rs.getString("idLivro"));
			dto.setDataEmprestimo(String.valueOf(rs.getDate("dataEmprestimo")));
			dto.setDataVencimento(String.valueOf(rs.getDate("dataVencimento")));
			dto.setDataCadastroEmprestimo(String.valueOf(rs.getDate("dataCadastroEmprestimo")));
			dto.setEmprestimoDeletado(rs.getBoolean("emprestimoDeletado"));
			dto.setNomeLivro(rs.getString("nomeLivro"));
			dto.setCodigoLivro(rs.getString("codigoLivro"));
			dto.setAutorLivro(rs.getString("autorLivro"));
			dto.setGeneroLivro(rs.getString("generoLivro"));
			dto.setEditoraLivro(rs.getString("editoraLivro"));
			dto.setQuantidadeTotalLivro(rs.getInt("quantidadeTotalLivro"));
			dto.setQuantidadeDisponivelLivro(rs.getInt("quantidadeDisponivelLivro"));
			dto.setDataCadastroLivro(String.valueOf(rs.getDate("dataCadastroLivro")));
			dto.setPublicacaoLivro(String.valueOf(rs.getDate("publicacaoLivro")));
			dto.setLivroDeletado(rs.getBoolean("livroDeletado"));
			dto.setNomeUsuario(rs.getString("nomeUsuario"));
			dto.setSobrenomeUsuario(rs.getString("sobrenomeUsuario"));
			dto.setEnderecoUsuario(rs.getString("enderecoUsuario"));
			dto.setEmailUsuario(rs.getString("emailUsuario"));
			dto.setTelefoneUsuario(rs.getString("telefoneUsuario"));
			dto.setCPFUsuario(rs.getString("CPFUsuario"));
			dto.setDataNascimentoUsuario(String.valueOf(rs.getDate("dataNascimentoUsuario")));
			dto.setTipoUsuario(rs.getString("tipoUsuario"));
			dto.setDataCadastroUsuario(String.valueOf(rs.getDate("dataCadastroUsuario")));
			dto.setUsuarioDeletado(rs.getBoolean("usuarioDeletado"));

		}

		conexao.close();

		log.info(END_POINT + "/buscaremprestimobyid -> Fim");

		return dto;
	}

}
