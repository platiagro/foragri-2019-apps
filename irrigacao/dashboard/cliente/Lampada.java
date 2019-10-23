package cliente;

import java.awt.image.BufferedImage;

public class Lampada {
	private BufferedImage imagemLampada = null;
	private int linha;
	private int coluna;
	private float valorRega = 0;
	String mensagem = "";


	public Lampada() {
	}

	public Lampada( int lin, int col) {
		this.linha = lin;
		this.coluna = col;
	}

	public Lampada( int lin, int col, BufferedImage img) {
		this.linha = lin;
		this.coluna = col;
	}
	
	public void reset() {
		mensagem = "";
	}
	
	//Gets & Sets
	public BufferedImage getImagemLampada() {
		return imagemLampada;
	}
	public void setImagemLampada(BufferedImage imagemLampada) {
		this.imagemLampada = imagemLampada;
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

	public float getValorRega() {
		return valorRega;
	}
	public void setValorRega(float valorRega) {
		this.valorRega = valorRega;
	}
}
