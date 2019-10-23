package cliente;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class PainelAlarmes extends JFrame {
	static public PainelDeDesenho pDes = new PainelDeDesenho();

	private static final long serialVersionUID = 1L;


	public PainelAlarmes(PainelDeDesenho painel) {
		pDes = painel;
		this.setSize(1350, 700);
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        
        this.setUndecorated(true);
		
		this.getContentPane().add( pDes );
		
	}
	
	
	
	
	public static void main(String[] args) {

		PainelDeDesenho pd = new PainelDeDesenho();
		PainelAlarmes pa = new PainelAlarmes( pd );
		
		pd.probPrec.setMensagem( "22,32");
		
		
		pa.setVisible(true);

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		pd.lampada11.setImagemLampada(pd.imagemLampadaVerde);
		pd.lampada11.setMensagem( "12,21");
		pd.paintComponent( pd.ga );
		
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		pd.lampada22.setImagemLampada(pd.imagemLampadaAmarela);
		pd.lampada22.setMensagem( "2,21");
		pd.paintComponent( pd.ga );


		
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		pd.lampada23.setImagemLampada(pd.imagemLampadaVermelha);
		pd.lampada23.setMensagem( "11,21");
		pd.paintComponent( pd.ga );
		
	}
}
