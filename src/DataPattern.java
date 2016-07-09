
public class DataPattern {

	String nome;
	String cargo;
	String municipio;
	String partido;
	String votos;
	String receita;
	String receita_de_comites;
	String custo_do_voto;
	String resultado;
	
	public DataPattern(){
		
	}
	
	public DataPattern(String nome, String cargo, String municipio, String partido, String votos, String receita,
			String receita_de_comites, String custo_do_voto, String resultado) {
		this.nome = nome;
		this.cargo = cargo;
		this.municipio = municipio;
		this.partido = partido;
		this.votos = votos;
		this.receita = receita;
		this.receita_de_comites = receita_de_comites;
		this.custo_do_voto = custo_do_voto;
		this.resultado = resultado;
	}



	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getPartido() {
		return partido;
	}
	public void setPartido(String partido) {
		this.partido = partido;
	}
	public String getVotos() {
		return votos;
	}
	public void setVotos(String votos) {
		this.votos = votos;
	}
	public String getReceita() {
		return receita;
	}
	public void setReceita(String receita) {
		this.receita = receita;
	}
	public String getReceita_de_comites() {
		return receita_de_comites;
	}
	public void setReceita_de_comites(String receita_de_comites) {
		this.receita_de_comites = receita_de_comites;
	}
	public String getCusto_do_voto() {
		return custo_do_voto;
	}
	public void setCusto_do_voto(String custo_do_voto) {
		this.custo_do_voto = custo_do_voto;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	
}
