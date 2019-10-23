package cliente;

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class LegendaCores {
	static DecimalFormat comDecimais2Casas = new DecimalFormat("#0.00");
	static DecimalFormat semDecimais2Casas = new DecimalFormat("00");
	
	private BufferedImage imagemLegenda = null;
	private int linha;
	private int coluna;
	private int largura;
	private int altura;

	private float limiteVerde = 5;
	private float limiteAzul = 7;
	private float limiteAmarelo = 10;
	private float limiteVermelho = 14;
	
	private String mensagem = "";


	public LegendaCores() {
	}

	public LegendaCores( int lin, int col, int larg, int alt ) {
		this.linha = lin;
		this.coluna = col;
		this.largura = larg;
		this.altura = alt;
	}

	public LegendaCores( int lin, int col, int larg, int alt, BufferedImage img ) {
		this.linha = lin;
		this.coluna = col;
		this.largura = larg;
		this.altura = alt;
		this.imagemLegenda = img;
	}
	
	
	//Gets & Sets
	public BufferedImage getImagemLegenda() {
		return imagemLegenda;
	}
	public void setImagemLegenda(BufferedImage imagemLeg) {
		this.imagemLegenda = imagemLeg;
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
	
	public int getLargura() {
		return largura;
	}
	public void setLargura(int largura) {
		this.largura = largura;
	}

	public int getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}


	public String getMensagem() {
		//"00     05         07        10        14";
		StringBuffer msg = new StringBuffer();
		msg.append( "00        " + semDecimais2Casas.format( limiteVerde ));
		msg.append( "          " + semDecimais2Casas.format( limiteAzul ));
		msg.append( "           " + semDecimais2Casas.format( limiteAmarelo ));
		msg.append( "          " + semDecimais2Casas.format( limiteVermelho ));
		
		return msg.toString();
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public float getLimiteVerde() {
		return limiteVerde;
	}
	public void setLimiteVerde(float limiteVerde) {
		this.limiteVerde = limiteVerde;
	}

	public float getLimiteAzul() {
		return limiteAzul;
	}
	public void setLimiteAzul(float limiteAzul) {
		this.limiteAzul = limiteAzul;
	}

	public float getLimiteAmarelo() {
		return limiteAmarelo;
	}
	public void setLimiteAmarelo(float limiteAmarelo) {
		this.limiteAmarelo = limiteAmarelo;
	}

	public float getLimiteVermelho() {
		return limiteVermelho;
	}
	public void setLimiteVermelho(float limiteVermelho) {
		this.limiteVermelho = limiteVermelho;
	}

}
