package cliente;

import java.awt.image.BufferedImage;

public class EstacaoMicroClima {
	private String identificador = null;
	private BufferedImage imagemLampada = null;

	private int linha;
	private int coluna;
	private String precipitacao = "";
	private String umidade = "";

	public EstacaoMicroClima() {
	}

	public EstacaoMicroClima( int lin, int col) {
		this.linha = lin;
		this.coluna = col;
	}

	public EstacaoMicroClima( int lin, int col, String identif) {
		this.linha = lin;
		this.coluna = col;
		this.identificador = identif;
	}
	

	//Gets & Sets
	public BufferedImage getImagemLampada() {
		return imagemLampada;
	}
	public void setImagemLampada(BufferedImage imagemLampada) {
		this.imagemLampada = imagemLampada;
	}

	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public int getLinha() {
		return linha;
	}
	public void setLinha(int linha) {
		this.linha = linha;
	}
	
	public int getColuna() {
		return coluna;
	}
	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public String getPrecipitacao() {
		return precipitacao;
	}

	public void setPrecipitacao(String precipitacao) {
		this.precipitacao = precipitacao;
	}

	public String getUmidade() {
		return umidade;
	}

	public void setUmidade(String umidade) {
		this.umidade = umidade;
	}
}
