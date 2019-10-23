package cliente;

import java.awt.image.BufferedImage;

public class ProbPrecipitacao {
	private BufferedImage imagem = null;

	private int linha;
	private int coluna;
	private String mensagem = "";

	public ProbPrecipitacao() {
	}

	public ProbPrecipitacao( int lin, int col) {
		this.linha = lin;
		this.coluna = col;
	}
	
	public void resetProbabilidadePrecipitacao() {
		this.mensagem = null;
	}


	//Gets & Sets
	public BufferedImage getImagem() {
		return imagem;
	}
	public void setImagem(BufferedImage img) {
		this.imagem = img;
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

	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
