package cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PainelDeDesenho extends JPanel {

	private static final long serialVersionUID = 1L;
	static DecimalFormat comDecimais2Casas = new DecimalFormat("#0.00");
	static DecimalFormat semDecimais2Casas = new DecimalFormat("00");
	
	public Graphics2D ga = null;
	
	Lampada lampada00 = new Lampada(0,0);
	Lampada lampada01 = new Lampada(0,1);
	Lampada lampada02 = new Lampada(0,2);
	Lampada lampada03 = new Lampada(0,3);
	Lampada lampada04 = new Lampada(0,4);
	Lampada lampada05 = new Lampada(0,5);
	
	Lampada lampada10 = new Lampada(1,0);
	Lampada lampada11 = new Lampada(1,1);
	Lampada lampada12 = new Lampada(1,2);
	Lampada lampada13 = new Lampada(1,3);
	Lampada lampada14 = new Lampada(1,4);
	Lampada lampada15 = new Lampada(1,5);
	
	Lampada lampada20 = new Lampada(2,0);
	Lampada lampada21 = new Lampada(2,1);
	Lampada lampada22 = new Lampada(2,2);
	Lampada lampada23 = new Lampada(2,3);
	Lampada lampada24 = new Lampada(2,4);
	Lampada lampada25 = new Lampada(2,5);
	
	EstacaoMicroClima estacaoMicroClima00 = new EstacaoMicroClima(0,0);
	EstacaoMicroClima estacaoMicroClima01 = new EstacaoMicroClima(0,1);
	EstacaoMicroClima estacaoMicroClima10 = new EstacaoMicroClima(1,0);
	EstacaoMicroClima estacaoMicroClima11 = new EstacaoMicroClima(1,1);
	
	PainelMicroClimas painelMC = new PainelMicroClimas(1005, 110);
	
	LegendaCores legendaCores = new LegendaCores( 100, 620, 300, 50 );//Posicao fica na tela
	
	ProbPrecipitacao probPrec = new ProbPrecipitacao(1000, 570);
	
	BufferedImage imagemMoldura = null;

	BufferedImage imagemLampadaApagada = null;
	BufferedImage imagemLampadaVerde = null;
	BufferedImage imagemLampadaAzul = null;
	BufferedImage imagemLampadaAmarela = null;
	BufferedImage imagemLampadaVermelha = null;
	
	BufferedImage imagemLampadaLaranja = null;
	
	BufferedImage imagemLegendaCores = null;
	BufferedImage imagemPainelMicroClimas = null;
	
	BufferedImage imagemProbPrecipitacao = null;
	
	JSlider sldr_verde = new JSlider(JSlider.HORIZONTAL, 0, 5, 5);
	
	
	public PainelDeDesenho() {
		super();
		
		this.add(sldr_verde);
		
		String nomeArqMoldura = "etc" + File.separatorChar + "moldura_6_verde.png";
		
		String nomeArqApagado = "etc" + File.separatorChar + "lamp_apagada_verde.png";

		String nomeArqVerde = "etc" + File.separatorChar + "lamp_verde_verde.png";	
		String nomeArqAzul = "etc" + File.separatorChar + "lamp_azul_verde.png";
		String nomeArqAmarelo = "etc" + File.separatorChar + "lamp_amarela_verde.png";
		String nomeArqVermelho = "etc" + File.separatorChar + "lamp_vermelha_verde.png";
		
		String nomeArqLaranja = "etc" + File.separatorChar + "lamp_laranja_verde.png";
		String nomeArqLegendaCores = "etc" + File.separatorChar + "legenda_verde.png";
		
		String nomeArqPainelMicroClimas = "etc" + File.separatorChar + "painel_micro_climas.png";
		
		String nomeArqProbPrecipitacao = "etc" + File.separatorChar + "prob_precipitacao.png";
		
		try {
			imagemMoldura = ImageIO.read(new File( nomeArqMoldura ));
			
			imagemLampadaApagada = ImageIO.read(new File( nomeArqApagado ));
			
			imagemLampadaVerde = ImageIO.read(new File( nomeArqVerde ));
			imagemLampadaAmarela = ImageIO.read(new File( nomeArqAmarelo ));
			imagemLampadaAzul = ImageIO.read(new File( nomeArqAzul ));
			imagemLampadaVermelha = ImageIO.read(new File( nomeArqVermelho ));
			
			imagemLampadaLaranja = ImageIO.read(new File( nomeArqLaranja ));
			imagemLegendaCores = ImageIO.read(new File( nomeArqLegendaCores ));		
			
			imagemPainelMicroClimas = ImageIO.read(new File( nomeArqPainelMicroClimas ));
			
			imagemProbPrecipitacao = ImageIO.read(new File( nomeArqProbPrecipitacao ));
			
			lampada00.setImagemLampada(imagemLampadaApagada);
			lampada01.setImagemLampada(imagemLampadaApagada);
			lampada02.setImagemLampada(imagemLampadaApagada);
			lampada03.setImagemLampada(imagemLampadaApagada);
			lampada04.setImagemLampada(imagemLampadaApagada);
			lampada05.setImagemLampada(imagemLampadaApagada);
			
			lampada10.setImagemLampada(imagemLampadaApagada);
			lampada11.setImagemLampada(imagemLampadaApagada);
			lampada12.setImagemLampada(imagemLampadaApagada);
			lampada13.setImagemLampada(imagemLampadaApagada);
			lampada14.setImagemLampada(imagemLampadaApagada);
			lampada15.setImagemLampada(imagemLampadaApagada);
			
			lampada20.setImagemLampada(imagemLampadaApagada);
			lampada21.setImagemLampada(imagemLampadaApagada);
			lampada22.setImagemLampada(imagemLampadaApagada);
			lampada23.setImagemLampada(imagemLampadaApagada);
			lampada24.setImagemLampada(imagemLampadaApagada);
			lampada25.setImagemLampada(imagemLampadaApagada);
			
			estacaoMicroClima00.setImagemLampada(imagemLampadaLaranja );
			estacaoMicroClima01.setImagemLampada(imagemLampadaLaranja );
			estacaoMicroClima10.setImagemLampada(imagemLampadaLaranja );
			estacaoMicroClima11.setImagemLampada(imagemLampadaLaranja );
			
			painelMC.setImagemPainel( imagemPainelMicroClimas );
			
			legendaCores.setImagemLegenda(imagemLegendaCores);
			legendaCores.setLimiteVerde( 5 );
			legendaCores.setLimiteAzul( 7 );
			legendaCores.setLimiteAmarelo( 10 );
			legendaCores.setLimiteVermelho( 14 );
			
			probPrec.setImagem( imagemProbPrecipitacao );
			
		}
		catch (Exception e) {
			System.out.println("Imagem nao encontrada!");
		}		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBackground(Color.white);
		ga = (Graphics2D) g;
				
		//Mostra fundo base
		this.apresentaFundo();
		
		this.acionaLampada( lampada00 );
		this.acionaLampada( lampada01 );
		this.acionaLampada( lampada02 );
		this.acionaLampada( lampada03 );
		this.acionaLampada( lampada04 );
		this.acionaLampada( lampada05 );
		
		this.acionaLampada( lampada10 );
		this.acionaLampada( lampada11 );
		this.acionaLampada( lampada12 );
		this.acionaLampada( lampada13 );
		this.acionaLampada( lampada14 );
		this.acionaLampada( lampada15 );
		
		this.acionaLampada( lampada20 );
		this.acionaLampada( lampada21 );
		this.acionaLampada( lampada22 );
		this.acionaLampada( lampada23 );
		this.acionaLampada( lampada24 );
		this.acionaLampada( lampada25 );
			
		this.acionaMicroClima( estacaoMicroClima00 );
		this.acionaMicroClima( estacaoMicroClima01 );
		this.acionaMicroClima( estacaoMicroClima10 );
		this.acionaMicroClima( estacaoMicroClima11 );
		
		//Legenda cores e texto
		this.apresentaLegendaCores( legendaCores );		
		
		//Mostra painel de micro climas
		this.apresentaPainelMicroClimas( painelMC );
		
		//Mostra probabilidade de precipitacao
		this.apresentaProbabilidadeDePrecipitacao( probPrec );
		
		this.repaint();

	}

	
	public void resetLampadas() {
		lampada00.reset();		
		lampada01.reset();
		lampada02.reset();
		lampada03.reset();
		lampada04.reset();
		lampada05.reset();
		
		lampada10.reset();
		lampada11.reset();
		lampada12.reset();
		lampada13.reset();
		lampada14.reset();
		lampada15.reset();
		
		lampada20.reset();
		lampada21.reset();
		lampada22.reset();
		lampada23.reset();
		lampada24.reset();
		lampada25.reset();
		
		lampada00.setImagemLampada(imagemLampadaApagada);
		lampada01.setImagemLampada(imagemLampadaApagada);
		lampada02.setImagemLampada(imagemLampadaApagada);
		lampada03.setImagemLampada(imagemLampadaApagada);
		lampada04.setImagemLampada(imagemLampadaApagada);
		lampada05.setImagemLampada(imagemLampadaApagada);
		
		lampada10.setImagemLampada(imagemLampadaApagada);
		lampada11.setImagemLampada(imagemLampadaApagada);
		lampada12.setImagemLampada(imagemLampadaApagada);
		lampada13.setImagemLampada(imagemLampadaApagada);
		lampada14.setImagemLampada(imagemLampadaApagada);
		lampada15.setImagemLampada(imagemLampadaApagada);
		
		lampada20.setImagemLampada(imagemLampadaApagada);
		lampada21.setImagemLampada(imagemLampadaApagada);
		lampada22.setImagemLampada(imagemLampadaApagada);
		lampada23.setImagemLampada(imagemLampadaApagada);
		lampada24.setImagemLampada(imagemLampadaApagada);
		lampada25.setImagemLampada(imagemLampadaApagada);
	}

	private void apresentaFundo() {
		String nomeArqFundoBase = "etc" + File.separatorChar + "fundo_base.png";
		
		try {
			BufferedImage imagemMolduraBase = ImageIO.read(new File( nomeArqFundoBase ));
			ga.drawImage( imagemMolduraBase, 0, 0, null);
			
			// Bordas
			ga.drawImage(imagemMoldura, 55, 110, 900, 470, null);

		}
		catch (Exception e) {
			System.out.println("Imagem nao encontrada!");
		}
	}
	
	private void apresentaProbabilidadeDePrecipitacao( ProbPrecipitacao probPrec ) {
		
		try {
			// Bordas
			ga.drawImage( probPrec.getImagem(), probPrec.getLinha(), probPrec.getColuna(), null); //img, x, y
			ga.setColor( Color.BLACK );
			
			Font font = new Font("Courrier", Font.CENTER_BASELINE, 16);
			ga.setFont( font );	
			
			if( probPrec.getMensagem() != null ) {
				ga.drawString( probPrec.getMensagem(), 1050, 635);//texto, x,y 
			}
			
			//Titulo da legenda de probabilidade de precipitacao
			ga.drawString( "Probabilidade de precipitação", 1005, 590);//texto, x,y 
			
		}
		catch (Exception e) {
			System.out.println("Imagem da probabilidade de precipitacao nao encontrada!");
		}
	}
	
	private void apresentaPainelMicroClimas( PainelMicroClimas painelMC ) {
		
		try {
			// Bordas
			ga.drawImage( painelMC.getImagemPainel(), painelMC.getLinha(), painelMC.getColuna(), null); //img, x, y
			ga.setColor( Color.BLACK );
			
			//Titulo dos micro climas
			ga.drawString( "Precipitação real", 1060, 150);//texto, x,y 
			
			Font font = new Font("Courrier", Font.CENTER_BASELINE, 16);
			ga.setFont( font );	
			
			if( painelMC.getValorMC1() != null ) {
				ga.drawString( painelMC.getValorMC1(), 1050, 205);//texto, x,y 
			}
			
			if( painelMC.getValorMC2() != null ) {
				ga.drawString( painelMC.getValorMC2(), 1170, 205);//texto, x,y 
			}
			
			if( painelMC.getValorMC3() != null ) {
				ga.drawString( painelMC.getValorMC3(), 1050, 325);//texto, x,y 
			}
			
			if( painelMC.getValorMC4() != null ) {
				ga.drawString( painelMC.getValorMC4(), 1170, 325);//texto, x,y 
			}
		}
		catch (Exception e) {
			System.out.println("Imagem do painel micro clima nao encontrada!");
		}
	}

	
	private void apresentaLegendaCores( LegendaCores leg ) {
		String nomeArqFundoBase = "etc" + File.separatorChar + "legenda_verde.png";
		
		try {
			BufferedImage imagemLegendaCores = ImageIO.read(new File( nomeArqFundoBase ));
			//ga.drawImage( imagemLegendaCores, 0, 0, null);
			
			// Bordas
			ga.drawImage(imagemLegendaCores, leg.getLinha(), leg.getColuna(), leg.getLargura(), leg.getAltura(), null); //img, x, y, larg, altura
			ga.setColor( Color.BLACK );
			
			Font font = new Font("Courrier", Font.CENTER_BASELINE, 16);
			ga.setFont( font );
			//Numeros da leganda
			ga.drawString( "00", 100, 685);//texto, x,y
			ga.drawString( semDecimais2Casas.format( leg.getLimiteVerde() ), 150, 685);//texto, x,y
			ga.drawString( semDecimais2Casas.format( leg.getLimiteAzul() ), 210, 685);//texto, x,y
			ga.drawString( semDecimais2Casas.format( leg.getLimiteAmarelo() ), 270, 685);//texto, x,y
			ga.drawString( semDecimais2Casas.format( leg.getLimiteVermelho() ), 330, 685);//texto, x,y
			//ga.drawString( leg.getMensagem(), 100, 685);//texto, x,y 
			
			//Titulo da legenda de cores
			ga.drawString( "Volume de água", 100, 610);//texto, x,y 
		
			
			sldr_verde.setOpaque(true);
			sldr_verde.setPaintTrack(true); 
	        sldr_verde.setPaintTicks(true); 
	        sldr_verde.setPaintLabels(true); 
	        sldr_verde.setMajorTickSpacing(2); 
	        sldr_verde.setMinorTickSpacing(1); 
	        sldr_verde.setForeground(Color.black);
	        sldr_verde.setBackground(new Color ( 0, 139, 65) );
	        //sldr_verde.setBackground( Color.green );
			sldr_verde.setLocation(600, 620);
			
			Border blackline = BorderFactory.createLineBorder(Color.black);
			sldr_verde.setBorder(blackline);
			
			sldr_verde.addChangeListener(new ChangeListener() {
			      public void stateChanged(ChangeEvent event) {
			        int valor = sldr_verde.getValue();
			        
			        leg.setLimiteVerde( valor );
			       
			      }
			});
			
			ga.drawString( "Qtd mínima para acionar o regador", 600, 610);//texto, x,y 
			
		}
		catch (Exception e) {
			System.out.println("Imagem da lampada nao encontrada!");
		}
	}
	
	public void acionaLampada( Lampada lamp ) {
		if ( lamp.getLinha() < 4 && lamp.getColuna() < 6) {		
			try {                                                               
		        ga.drawImage( lamp.getImagemLampada(), (lamp.getColuna() * 120) + 175, (lamp.getLinha() * 120) + 200, 40, 40, null);
		        
		        if( ! lamp.getMensagem().trim().equalsIgnoreCase("")) {
					Font font = new Font("Courrier", Font.CENTER_BASELINE, 16);
					ga.setColor( Color.BLACK );
					ga.setFont( font );                                        
					ga.drawString( lamp.getMensagem(),(lamp.getColuna() * 120) + 175, (lamp.getLinha() * 120) + 190);//x,y 
		        }
			}
			catch (Exception e) {
				System.out.println("Imagem nao encontrada!");
			}
		}
	}		
	
	
	public void acionaMicroClima( EstacaoMicroClima mClima ) {
		try {                                      //375  260
	        ga.drawImage( mClima.getImagemLampada(), 360, 260, 40, 40, null); //0 0
	        ga.drawImage( mClima.getImagemLampada(), 360, 380, 40, 40, null); //0 1
	        ga.drawImage( mClima.getImagemLampada(), 590, 260, 40, 40, null); //1 0
	        ga.drawImage( mClima.getImagemLampada(), 590, 380, 40, 40, null); //1 1
		}
		catch (Exception e) {
			System.out.println("Imagem nao encontrada!");
		}		
	}
	
	//Gets & Sets
	public LegendaCores getLegendaCores() {
		return legendaCores;
	}
	public void setLegendaCores(LegendaCores legendaCores) {
		this.legendaCores = legendaCores;
	}

}
