/**
 * Esta classe eh responsavel por representar um cliente que envia dados para um servidor e mostra os resultados em uma tela
 * 
 * Author Oclair Prado em 16/out/2019
 * 
 */

/**
 * Historico de alteracoes
 * 
 * Controle 0.0.1 ==> Versao inicial desta classe
 * 
 */


package cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class SupervisorCliente {
	static String controle = "0.0.1";
	//static String endPoint = "http://localhost:5000/sensores";
	
	//Inf fornecida pela DOJOT
	//static String endPoint = "http://10.50.11.180:31380/seldon/kubeflow/irrigacao-regression-270ad552/api/v0.1/predictions";
	static String endPoint = null;
	
	
	static DecimalFormat comDecimais2Casas = new DecimalFormat("#0.00");
	static DecimalFormat semDecimais2Casas = new DecimalFormat("00");
	
	static int contadorResposta = -1;
	static int contadorRespostaMax = 0;
	static List<String> listaDadosEntrada = null;
	static String precipitacao = null;
	static String probPrecipitacao = null;


	//Apoio para tela
	static PainelDeDesenho pDes = null;
	
	static void doDelete(String identifSensor) throws Exception {
		URL url = new URL(endPoint + ((identifSensor != null) ? "?sensor=" + identifSensor : ""));
		HttpURLConnection httpurlc = (HttpURLConnection) url.openConnection();
		httpurlc.setRequestMethod("DELETE");
		httpurlc.setDoInput(true);
		InputStreamReader isr;
		isr = new InputStreamReader(httpurlc.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		StringBuilder xml = new StringBuilder();

		String line;
		while ((line = br.readLine()) != null) {
			xml.append(line);
		}
		System.out.println("[Cliente] DELETE");
		System.out.println(xml);
		System.out.println();
	}

	static void doGet(String identifSensor) throws Exception {
		URL url = new URL(endPoint + ((identifSensor != null) ? "?sensor=" + identifSensor : ""));
		HttpURLConnection httpurlc = (HttpURLConnection) url.openConnection();
		httpurlc.setRequestMethod("GET");
		httpurlc.setDoInput(true);
		InputStreamReader isr;
		isr = new InputStreamReader(httpurlc.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		StringBuilder xml = new StringBuilder();

		String line;
		while ((line = br.readLine()) != null) {
			xml.append(line);
		}
		System.out.println("[Cliente] GET");
		System.out.println(xml);
		System.out.println();
	}

	static void doPost(String msg) throws Exception {
		URL url = new URL(endPoint);
		String resposta = null;
		
		//System.out.println("[Cliente] POST");
		
		HttpURLConnection httpurlc = (HttpURLConnection) url.openConnection();
		httpurlc.setRequestMethod("POST");
		httpurlc.setDoOutput(true);
		httpurlc.setDoInput(true);
		
		httpurlc.setRequestProperty("Content-Type", "application/json");		
		
		OutputStream os = httpurlc.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
		osw.write( msg );
		osw.close();

		
		if (httpurlc.getResponseCode() == 200) {
			InputStreamReader isr;
			isr = new InputStreamReader(httpurlc.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			System.out.println( sb.toString() );
			
			resposta = sb.toString();
			processaResposta( resposta );
			
			
		} else {
			System.err.println("Erro ao tentar isnerir informacao: " + httpurlc.getResponseCode());
		}
		System.out.println();
	}

	static void doPut(String xml) throws Exception {
		URL url = new URL(endPoint);
		HttpURLConnection httpurlc = (HttpURLConnection) url.openConnection();
		httpurlc.setRequestMethod("PUT");
		httpurlc.setDoOutput(true);
		httpurlc.setDoInput(true);
		httpurlc.setRequestProperty("Content-Type", "text/json");
		OutputStream os = httpurlc.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
		osw.write(xml);
		osw.close();

		if (httpurlc.getResponseCode() == 200) {
			InputStreamReader isr;
			isr = new InputStreamReader(httpurlc.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			System.out.println(sb.toString());
		} else {
			System.err.println("Erro ao tentar atualizar informacao: " + httpurlc.getResponseCode());
		}
		System.out.println();
	}
	
	
	static void processaResposta( String resp ) {
		PainelDeDesenho pd = SupervisorCliente.getpDes( );
		int posLamp;
		String legenda = null;
		Lampada lamp = null;
		LegendaCores legendaCor = pd.getLegendaCores();
				
		//Pega posicao relativa da lampada
		posLamp = contadorResposta % 18;//Total de lampadas = 18
		lamp = selecionaLampada( posLamp );
				
		//Ajusta texto da lampada
		
		if( lamp != null ) {
			legenda = extraiLegenda( resp );
			if( legenda != null ) {
				lamp.setMensagem( legenda );
			}
			else {
				lamp.setMensagem("");
			}
		}
		
		//Ajusta cor da lampada
		ajustaCorLampada( lamp, legendaCor );
		
		//Ajusta painel micro climas
		ajustarPainelMicroClima( pd.painelMC );
		
		//Ajusta prob de precipitacao
		ajustarProbPrecipicacao( pd.probPrec );
		
		pd.paintComponent( pd.ga );
	}
	
	
	static void ajustaCorLampada( Lampada lamp, LegendaCores leg ) {
		String dadoStr = lamp.getMensagem();
		float dado = 0;
		
		try {
			//Apenas testa se recebeu dado numerico
			dado = Float.parseFloat( dadoStr.replace(",", ".") );
			if( dado <0 ) {
				dado = 0;
			}
		}
		catch (Exception e) {
			dado = 0;
		}
		
		lamp.setValorRega( dado );
		if( dado < leg.getLimiteVerde() ) {
			//Apaga
			lamp.setImagemLampada( pDes.imagemLampadaApagada );
		}
		else if( dado >= leg.getLimiteVerde() && dado < leg.getLimiteAzul() ) {
			//Acende verde
			lamp.setImagemLampada( pDes.imagemLampadaVerde );
		}
		else if( dado >= leg.getLimiteAzul() && dado < leg.getLimiteAmarelo() ) {
			//Acende azul
			lamp.setImagemLampada( pDes.imagemLampadaAzul );
		}
		else if( dado >= leg.getLimiteAmarelo() && dado < leg.getLimiteVermelho() ) {
			//Acende amarelo
			lamp.setImagemLampada( pDes.imagemLampadaAmarela );
		}
		else {
			//Acende vermelho
			lamp.setImagemLampada( pDes.imagemLampadaVermelha );
		}
	}
	
	
	static void ajustarPainelMicroClima( PainelMicroClimas painelMC ) {
		int posic = 0;
		String msg = null;
		
		if( precipitacao == null ) {
			return;
		}
		
		msg = precipitacao;
		posic = contadorResposta % 18;
		
		if( posic == 0 ) {//Aciona MC1 (0,0)
			painelMC.setValorMC1( msg );
		}
		if( posic == 3 ) {//Aciona MC2 (0,1)
			painelMC.setValorMC2( msg );
		}
		if( posic == 12 ) {//Aciona MC3 (1,0)
			painelMC.setValorMC3( msg );
		}
		if( posic == 9 ) {//Aciona MC4 (1,1)
			painelMC.setValorMC4( msg );
		}
	}
	
	static void ajustarProbPrecipicacao( ProbPrecipitacao probPrec ) {
		int posic = 0;
		String msg = null;
		
		if( probPrecipitacao == null ) {
			return; 
		}
		
		msg = probPrecipitacao;
		posic = contadorResposta % 18;
		
		if( posic == 0 ) {//Mostra probabilidade de precipitacao
			probPrec.setMensagem( msg );
		}
	}
	
	static String extraiLegenda( String resp ) {
		int posInic = 0;
		int posFim = 0;
		float resultado = 0;
		String result = null;
		String msgStr = null;
		String dadosStr = null;
		
		//Resposta esperada
		/*
		 { "meta": { 
                     "puid": "dvc3v4laabhm54fh6jt7bj7a1p",
                     "tags": {},
                     "routing": {},
                     "requestPath": { "regression": "platiagro/regression-deployment:0.0.1" },
                     "metrics": []
                   },
           "data": {
                     "names": [],
                     "ndarray": [ 6.492940647406746 ]
                   }
         }
        */

		//Nao quero importar projeto extra nenhum então vou apenas limpar o objeto json
		msgStr = resp.replace("{", "");
		msgStr = msgStr.replace("}", "");
		msgStr = msgStr.replace("\"", "");
		msgStr = msgStr.replaceAll(" ", "");
		msgStr = msgStr.replaceAll("\\[", "");
		msgStr = msgStr.replaceAll("\\]", "");
        posInic = msgStr.indexOf("ndarray:");
        posFim = msgStr.indexOf(",", posInic);
        if( posFim < 0) {
        	dadosStr = msgStr.substring(posInic + 8);
        }
        else {
        	dadosStr = msgStr.substring(posInic + 8, posFim);
        }
        
		try {
			//Apenas testa se recebeu dado numerico
			resultado = Float.parseFloat( dadosStr );
			//Se nao deu erro entao pode usar o dado recebido		
			if( resultado < 0 ) {
				resultado = 0;
			}
			//result = dadosStr;
			
			//Frmata para ter apenas duas casas decimais
			result = comDecimais2Casas.format( resultado );
		}
		catch (Exception e) {
			result = null; 
		}
		
		return result;
	}
	
	static Lampada selecionaLampada( int posic ) {
		if( posic == 0 ) {
			return pDes.lampada00;
		}
		else if( posic == 1 ) {
			return pDes.lampada01;
		}
		else if( posic == 2 ) {
			return pDes.lampada02;
		}
		else if( posic == 3 ) {
			return pDes.lampada03;
		}
		else if( posic == 4 ) {
			return pDes.lampada04;
		}
		else if( posic == 5 ) {
			return pDes.lampada05;
		}
		else if( posic == 6 ) {
			return pDes.lampada10;
		}
		else if( posic == 7 ) {
			return pDes.lampada11;
		}
		else if( posic == 8 ) {
			return pDes.lampada12;
		}
		else if( posic == 9 ) {
			return pDes.lampada13;
		}
		else if( posic == 10 ) {
			return pDes.lampada14;
		}
		else if( posic == 11 ) {
			return pDes.lampada15;
		}
		else if( posic == 12 ) {
			return pDes.lampada20;
		}
		else if( posic == 13 ) {
			return pDes.lampada21;
		}
		else if( posic == 14 ) {
			return pDes.lampada22;
		}
		else if( posic == 15 ) {
			return pDes.lampada23;
		}
		else if( posic == 16 ) {
			return pDes.lampada24;
		}
		else if( posic == 17 ) {
			return pDes.lampada25;
		}
		return null;
	}
	
	
	static void carregaDadosEntrada() {
		String nomeArqEntrada = "irrig_implant_dado_entrada.csv";
		String linhaEntrada = null;
		String chaveStr = null;

		BufferedReader arqEntrada = null;
		
		List<String> resultado = new LinkedList<String>();

		try {
			arqEntrada = new BufferedReader( new FileReader( "etc" + File.separatorChar + nomeArqEntrada ));
			contadorRespostaMax = 0;//Reseta tamanho do conjunto
		}
		catch (Exception e) {
			System.out.println("Erro ao tentar abrir o arquivos com os dados de resposta!");
			return;
		}
		
		try {
			linhaEntrada = arqEntrada.readLine();
			
			while (linhaEntrada != null) {
				if( ! linhaEntrada.startsWith("#") && ! linhaEntrada.startsWith("D") && ! linhaEntrada.startsWith("A") ) {
					chaveStr = linhaEntrada + "";
					resultado.add( chaveStr );
				}
				linhaEntrada = arqEntrada.readLine();
			}
			SupervisorCliente.setListaDadosEntrada( resultado );
		}
		catch (Exception e) {
			System.out.println("Erro ao tentar ler o arquivos com os dados de resposta!");
			return;
		}
		
		try {
			arqEntrada.close();
		}
		catch (Exception e) {
			System.out.println("Erro ao tentar fechar o arquivos com os dados de resposta!");
		}
		return;
	}
	
	

	//Gets & Sets
	public static String getControle() {
		return controle;
	}
	private static void setControle(String contr) {
		controle = contr;
	}
	
	static PainelDeDesenho getpDes() {
		return pDes;
	}
	static void setpDes( PainelDeDesenho p ) {
		pDes = p;
	}

	public static List<String> getListaDadosEntrada() {
		return listaDadosEntrada;
	}
	public static void setListaDadosEntrada(List<String> listaDadosEntr) {
		SupervisorCliente.listaDadosEntrada = listaDadosEntr;
	}

	

	public static void main(String[] args) throws Exception {
		boolean flagHelp = false;
		float valor = 0;
		String msg = null;
		String resposta = null;
		String campos[] = null;
		StringBuilder jsonStr = new StringBuilder();
		SubscriberMQTT subsMQTT = null;
		String identif = null;
		
		
		if (args.length != 1 ) {
			flagHelp = true;
		}
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("/?") || args[0].equalsIgnoreCase("?")
					|| args[0].equalsIgnoreCase("h")
					|| args[0].equalsIgnoreCase("/h")) {
				flagHelp = true;
			}
		}
		if (flagHelp) {
			System.err.println("\n###########################################################################");
			System.err.println("#                                                                         #");
			System.err.println("#            SupervisorCliente       Controle [" + SupervisorCliente.getControle() + "]                     #");
			System.err.println("#                                                                         #");
			System.err.println("# (c) Copyright Fundacao CPqD 201.  Todos os direitos reservados.         #");
			System.err.println("# http://www.cpqd.com.br                                                  #");
			System.err.println("#                                                                         #");
			System.err.println("###########################################################################");

			System.out.println("\nUse java SupervisorCliente identificador");
			System.out.println("Onde:");
			System.out.println("     identificador = codigo fornecido pela DOJOT para comunicacao via MQTT");
			
			System.exit(0);
		}
		
		
	
		//doDelete("35");
		
		PainelDeDesenho pd = new PainelDeDesenho();
		PainelAlarmes pa = new PainelAlarmes(pd);
		SupervisorCliente.setpDes( pd );

		pa.setVisible(true);
		
		//Trecho para aguardar a URL do modelo = endPont
		try {
			identif = args[0];
			subsMQTT = new SubscriberMQTT( identif );
	        
	        while( subsMQTT.getMsgRecebida() == null ) {
	        	System.out.println("esperando URL do modelo...");
	      
	        	try {
					Thread.sleep(3000);
				} 
				catch (Exception e) {
					System.out.println("Falha ao tentar suspender processo principal");
				}  
	        	
	        	//Verifica se o endPoint foi preenchido
	        	if( subsMQTT.getMsgRecebida() != null ) {//url ja foi recebida da DOJOT
	        		endPoint = subsMQTT.getMsgRecebida();
	        	}
	        }	
		}
		catch (Exception e) {
			System.out.println("Falha ao tentar obter a URL do modelo!");
			System.exit(2);
		}
		
		while( true ) {
			
			//Verifica se ocorreu alteracao de endPoint
			if( ! endPoint.contentEquals( subsMQTT.getMsgRecebida() )){
				endPoint = subsMQTT.getMsgRecebida();
							
				//Reinicia painel micro climas
				pd.painelMC.resetMicroClimas();
				
				//Reinicia probabilidade de precipitacao
				pd.probPrec.resetProbabilidadePrecipitacao();
				
				//Reinicia painel de lampadas
				pd.resetLampadas();
				
				//Reinicia contador de dados de entrada
				contadorResposta = -1; //Reset contator de dados de entrada
			}
			
			
			if( listaDadosEntrada == null ) {
				SupervisorCliente.carregaDadosEntrada();
				
				if( listaDadosEntrada == null ) {
					System.out.println("Problemas com os dados de entrada!");
					System.exit(1);
				}
				contadorRespostaMax = listaDadosEntrada.size();
				contadorResposta = -1; //Reset contator de dados de entrada
			}
			
			contadorResposta++;
			if(contadorResposta >= contadorRespostaMax) {
				contadorResposta = 0; //Reset contador de dados de entrada
			}
			resposta = listaDadosEntrada.get( contadorResposta );
						
			campos = resposta.split(";");
				
			/*
			 * JSON esperado pelo servidor:
			 '{"data":{"ndarray":[["2018-01-08",1,1.1,0.0,94.0,0.8,1.8,1.3,1.0,0.7,26.7,24.3,20.6,23.1]],"names":["DATA","Mes","Precipitacao","Insolacao","Umidade Relativa Media","probabilidade de precipitação","Umidade 1 (12:00)","Umidade 2 (18:00)","Umidade 3 (00:00)","Umidade 4 (06:00)","Temperatura 1 (12:00)","Temperatura 2 (18:00","Temperatura 3 (00:00)","Temperatura 4 (06:00)"]}}'
			 */
			//Sao 14 no arq contando com a data
			jsonStr.setLength( 0 );
			jsonStr.append( "{\"data\":{\"ndarray\":[[\"" );
			jsonStr.append( campos[0] + "\"");
			for (int i = 1; i < campos.length; i++) {//comeca no 1 para pular a data 
				jsonStr.append( "," + campos[i]);
			}
			jsonStr.append( "]],\"names\":[\"Data\",\"Mês\",\"Precipitação\",\"Insolacao\",\"Umidade relativa do ar média\",\"Probabilidade de precipitação\",\"Umidade 1 (12:00)\",\"Umidade 2 (18:00)\",\"Umidade 3 (00:00)\",\"Umidade 4 (06:00)\",\"Temperatura 1 (12:00)\",\"Temperatura 2 (18:00)\",\"Temperatura 3 (00:00)\",\"Temperatura 4 (06:00)\",\"Quantidade de água\"]}}" );

			//Ajusta variaceis de apoio para precipitacao
			try {
				valor = Float.parseFloat( campos[2] );
				precipitacao = comDecimais2Casas.format( valor );
			}
			catch (Exception e) {
				precipitacao = null; 
			}

			//Ajusta variaceis de apoio para probabilidade de precipitacao
			try {
				valor = Float.parseFloat( campos[5] );
				probPrecipitacao = comDecimais2Casas.format( valor );
			}
			catch (Exception e) {
				probPrecipitacao = null; 
			}
			
		
			/*
			 * Dados lidos do arquivo CSV
			 DATA;Mês;Precipitacao;Insolacao;Umidade Relativa do ar Media;probabilidade de precipitação;Umidade 1 (12:00);Umidade 2 (18:00);Umidade 3 (00:00);Umidade 4 (06:00);Temperatura 1 (12:00);Temperatura 2 (18:00;Temperatura 3 (00:00);Temperatura 4 (06:00)
             31/01/2018;1;0.2;2.3;79;0.7;1.5;1.4;1.4;1.4;33.5;29.6;23.8;27.7
             31/01/2018;1;2.6;0.4;85;0.9;3;2.3;1.9;1.5;29.2;25.2;19.3;23.2
			 */
			
			//Novo
			//Data;Mês;Precipitação;Insolacao;Umidade relativa do ar média;Probabilidade de precipitação;Umidade 1 (12:00);Umidade 2 (18:00);Umidade 3 (00:00);Umidade 4 (06:00);Temperatura 1 (12:00);Temperatura 2 (18:00);Temperatura 3 (00:00);Temperatura 4 (06:00);Quantidade de água
			//2018-01-16;1;0.4;11.5;72;0.3;3.2;3.2;3.2;2.3;35;29.7;21.7;27.1;14.8

			
			msg = jsonStr.toString();
			
			System.out.println( msg );
			
			doPost(msg);
			
			try {
				Thread.sleep(3000);
			} 
			catch (Exception e) {
				System.out.println("Falha ao tentar suspender processo principal");
			}
		}
		
		//System.exit(0);

	}
}
