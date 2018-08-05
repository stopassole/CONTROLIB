package entity;

public class Livro {
	private String _id;
	private String nome;
	private String autor;
	private String genero;
	private String editora;
	private Integer quantidadeTotal;
	private Integer quantidadeDisponivel;
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
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}
	public Integer getQuantidadeTotal() {
		return quantidadeTotal;
	}
	public void setQuantidadeTotal(Integer quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}
	public Integer getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}
	public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
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
	
	public Livro(String _id, String nome, String autor, String genero, String editora, Integer quantidadeTotal,
			Integer quantidadeDisponivel, String dataCadastro, Boolean deletado) {
		this._id = _id;
		this.nome = nome;
		this.autor = autor;
		this.genero = genero;
		this.editora = editora;
		this.quantidadeTotal = quantidadeTotal;
		this.quantidadeDisponivel = quantidadeDisponivel;
		this.dataCadastro = dataCadastro;
		this.deletado = deletado;
	}
	
	@Override
	public String toString() {
		return "Livro [nome=" + nome + "]";
	}
}
