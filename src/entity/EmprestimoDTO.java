package entity;

public class EmprestimoDTO {
	private String _id;
	private String idUsuario;
	private String idLivro;
	private String dataEmprestimo;
	private String dataVencimento;
	private String dataCadastroEmprestimo;
	private Boolean emprestimoDeletado;
	private String nomeLivro;
	private String codigoLivro;
	private String autorLivro;
	private String generoLivro;
	private String editoraLivro;
	private Boolean livroDisponivel;
	private String dataCadastroLivro;
	private String publicacaoLivro;
	private Boolean livroDeletado;
	private String nomeUsuario;
	private String sobrenomeUsuario;
	private String enderecoUsuario;
	private String emailUsuario;
	private String telefoneUsuario;
	private String CPFUsuario;
	private String dataNascimentoUsuario;
	private String tipoUsuario;
	private String dataCadastroUsuario;
	private Boolean usuarioDeletado;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(String idLivro) {
		this.idLivro = idLivro;
	}

	public String getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(String dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getDataCadastroEmprestimo() {
		return dataCadastroEmprestimo;
	}

	public void setDataCadastroEmprestimo(String dataCadastroEmprestimo) {
		this.dataCadastroEmprestimo = dataCadastroEmprestimo;
	}

	public Boolean getEmprestimoDeletado() {
		return emprestimoDeletado;
	}

	public void setEmprestimoDeletado(Boolean emprestimoDeletado) {
		this.emprestimoDeletado = emprestimoDeletado;
	}

	public String getNomeLivro() {
		return nomeLivro;
	}

	public void setNomeLivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}

	public String getCodigoLivro() {
		return codigoLivro;
	}

	public void setCodigoLivro(String codigoLivro) {
		this.codigoLivro = codigoLivro;
	}

	public String getAutorLivro() {
		return autorLivro;
	}

	public void setAutorLivro(String autorLivro) {
		this.autorLivro = autorLivro;
	}

	public String getGeneroLivro() {
		return generoLivro;
	}

	public void setGeneroLivro(String generoLivro) {
		this.generoLivro = generoLivro;
	}

	public String getEditoraLivro() {
		return editoraLivro;
	}

	public void setEditoraLivro(String editoraLivro) {
		this.editoraLivro = editoraLivro;
	}

	public Boolean getLivroDisponivel() {
		return livroDisponivel;
	}

	public void setLivroDisponivel(Boolean livroDisponivel) {
		this.livroDisponivel = livroDisponivel;
	}

	public String getDataCadastroLivro() {
		return dataCadastroLivro;
	}

	public void setDataCadastroLivro(String dataCadastroLivro) {
		this.dataCadastroLivro = dataCadastroLivro;
	}

	public String getPublicacaoLivro() {
		return publicacaoLivro;
	}

	public void setPublicacaoLivro(String publicacaoLivro) {
		this.publicacaoLivro = publicacaoLivro;
	}

	public Boolean getLivroDeletado() {
		return livroDeletado;
	}

	public void setLivroDeletado(Boolean livroDeletado) {
		this.livroDeletado = livroDeletado;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSobrenomeUsuario() {
		return sobrenomeUsuario;
	}

	public void setSobrenomeUsuario(String sobrenomeUsuario) {
		this.sobrenomeUsuario = sobrenomeUsuario;
	}

	public String getEnderecoUsuario() {
		return enderecoUsuario;
	}

	public void setEnderecoUsuario(String enderecoUsuario) {
		this.enderecoUsuario = enderecoUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getTelefoneUsuario() {
		return telefoneUsuario;
	}

	public void setTelefoneUsuario(String telefoneUsuario) {
		this.telefoneUsuario = telefoneUsuario;
	}

	public String getCPFUsuario() {
		return CPFUsuario;
	}

	public void setCPFUsuario(String cPFUsuario) {
		CPFUsuario = cPFUsuario;
	}

	public String getDataNascimentoUsuario() {
		return dataNascimentoUsuario;
	}

	public void setDataNascimentoUsuario(String dataNascimentoUsuario) {
		this.dataNascimentoUsuario = dataNascimentoUsuario;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getDataCadastroUsuario() {
		return dataCadastroUsuario;
	}

	public void setDataCadastroUsuario(String dataCadastroUsuario) {
		this.dataCadastroUsuario = dataCadastroUsuario;
	}

	public Boolean getUsuarioDeletado() {
		return usuarioDeletado;
	}

	public void setUsuarioDeletado(Boolean usuarioDeletado) {
		this.usuarioDeletado = usuarioDeletado;
	}

	public EmprestimoDTO(String _id, String idUsuario, String idLivro, String dataEmprestimo, String dataVencimento,
			String dataCadastroEmprestimo, Boolean emprestimoDeletado, String nomeLivro, String codigoLivro,
			String autorLivro, String generoLivro, String editoraLivro, Boolean livroDisponivel, String dataCadastroLivro, String publicacaoLivro, Boolean livroDeletado,
			String nomeUsuario, String sobrenomeUsuario, String enderecoUsuario, String emailUsuario,
			String telefoneUsuario, String CPFUsuario, String dataNascimentoUsuario, String tipoUsuario,
			String dataCadastroUsuario, Boolean usuarioDeletado) {
		this._id = _id;
		this.idUsuario = idUsuario;
		this.idLivro = idLivro;
		this.dataEmprestimo = dataEmprestimo;
		this.dataVencimento = dataVencimento;
		this.dataCadastroEmprestimo = dataCadastroEmprestimo;
		this.emprestimoDeletado = emprestimoDeletado;
		this.nomeLivro = nomeLivro;
		this.codigoLivro = codigoLivro;
		this.autorLivro = autorLivro;
		this.generoLivro = generoLivro;
		this.editoraLivro = editoraLivro;
		this.livroDisponivel = livroDisponivel;
		this.dataCadastroLivro = dataCadastroLivro;
		this.publicacaoLivro = publicacaoLivro;
		this.livroDeletado = livroDeletado;
		this.nomeUsuario = nomeUsuario;
		this.sobrenomeUsuario = sobrenomeUsuario;
		this.enderecoUsuario = enderecoUsuario;
		this.emailUsuario = emailUsuario;
		this.telefoneUsuario = telefoneUsuario;
		this.CPFUsuario = CPFUsuario;
		this.dataNascimentoUsuario = dataNascimentoUsuario;
		this.tipoUsuario = tipoUsuario;
		this.dataCadastroUsuario = dataCadastroUsuario;
		this.usuarioDeletado = usuarioDeletado;
	}
}
