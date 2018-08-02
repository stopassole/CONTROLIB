package entity;

public class Usuario {
	private String _id;
	private String nome;
	private String sobrenome;
	private String endereco;
	private String email;
	private String telefone;
	private String CPF;
	private String dataNascimento;
	private String tipo;
	private String dataCadastro;
	private Boolean deletado;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String cPF) {
		CPF = cPF;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Boolean getDeletado() {
		return deletado;
	}
	public void setDeletado(Boolean deletado) {
		this.deletado = deletado;
	}
	
	public Usuario(String _id, String nome, String sobrenome, String endereco, String email, String telefone,String cPF, String dataNascimento, String tipo, String dataCadastro, Boolean deletado) {
		this._id = _id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.endereco = endereco;
		this.email = email;
		this.telefone = telefone;
		this.CPF = cPF;
		this.dataNascimento = dataNascimento;
		this.tipo = tipo;
		this.dataCadastro = dataCadastro;
		this.deletado = deletado;
	}
	
	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", sobrenome=" + sobrenome + "]";
	}
}
