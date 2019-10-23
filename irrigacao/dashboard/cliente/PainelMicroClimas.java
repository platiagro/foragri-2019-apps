package cliente;

import java.awt.image.BufferedImage;

public class PainelMicroClimas {
	private BufferedImage imagemPainel = null;
	private int linha;
	private int coluna;
	String valorMC1 = null;
	String valorMC2 = null;
	String valorMC3 = null;
	String valorMC4 = null;

	public PainelMicroClimas() {
	}

	public PainelMicroClimas( int lin, int col) {
		this.linha = lin;
		this.coluna = col;
	}

	public PainelMicroClimas( int lin, int col, BufferedImage img) {
		this.linha = lin;
		this.coluna = col;
		this.imagemPainel = img;
	}
	
	public void resetMicroClimas() {
		valorMC1 = null;
		valorMC2 = null;
		valorMC3 = null;
		valorMC4 = null;
	}
	
	//Gets & Sets
	public BufferedImage getImagemPainel() {
		return imagemPainel;
	}
	public void setImagemPainel(BufferedImage imagem) {
		this.imagemPainel = imagem;
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

	public String getValorMC1() {
		return valorMC1;
	}
	public void setValorMC1(String valorMC1) {
		this.valorMC1 = valorMC1;
	}

	public String getValorMC2() {
		return valorMC2;
	}
	public void setValorMC2(String valorMC2) {
		this.valorMC2 = valorMC2;
	}

	public String getValorMC3() {
		return valorMC3;
	}
	public void setValorMC3(String valorMC3) {
		this.valorMC3 = valorMC3;
	}

	public String getValorMC4() {
		return valorMC4;
	}
	public void setValorMC4(String valorMC4) {
		this.valorMC4 = valorMC4;
	}
}
