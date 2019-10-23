//Cliente da DOJOT para enviar URL do modelo

package cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClienteDojot {
	static String endPointDaDojotAutrizacao = "http://10.50.11.147:8000/auth";
	static String endPointDaDojotURL = "http://10.50.11.147:8000/device/72269c/actuate";
	
	
	static String doGetAutorizacao(String msg) throws Exception {
		URL url = new URL(endPointDaDojotAutrizacao);
		String resposta = null;
		
		HttpURLConnection httpurlc = (HttpURLConnection) url.openConnection();
		httpurlc.setRequestMethod("GET");
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
			
			resposta = sb.toString();
			return resposta;
			
			
		} else {
			System.err.println("Erro ao tentar isnerir informacao: " + httpurlc.getResponseCode());
		}
		return null;
	}

	
	
	static void doGetComToken(String msg, String token) throws Exception {
		
		URL url = new URL(endPointDaDojotURL);
		String resposta = null;
		
		HttpURLConnection httpurlc = (HttpURLConnection) url.openConnection();
		httpurlc.setRequestMethod("GET");
		httpurlc.setDoOutput(true);
		httpurlc.setDoInput(true);
		
		httpurlc.setRequestProperty( "Content-Type", "application/json");
		
		//Insere token recebido
		httpurlc.setRequestProperty( "Authorization", token );
		
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
			
		} else {
			System.err.println("Erro ao tentar isnerir informacao: " + httpurlc.getResponseCode());
		}
	}

	
	public static void main(String[] args) throws Exception {
		String msg = null;
		String token = null;
		int posInic = 0;
		
		try {
			msg = "{\"username\":\"admin\",\"passwd\": \"admin\"}";
			token = doGetAutorizacao( msg );
			posInic = token.indexOf(":");
			token = token.substring( posInic + 1 ).trim();
			token = token.replace("{", "");
			token = token.replace("}", "");
			token = token.replace("\"", "");
			token = "Bearer " + token;
		}
		catch (Exception e) {
			System.out.println("Falha ao tentar obter o token da Dojot!");
			System.exit(1);
		}
				
		try {
			//Prepaara mensagem com url a ser divulgada
			msg = "{\"attrs\":{\"url\":\"http://10.50.11.180:31380/seldon/kubeflow/irrigacao-regression-270ad552/api/v0.1/predictions\"}}";
			
			System.out.println( "Envianndo para DOJOT: \n" + msg + "\n" + token + "\n" );
			
			doGetComToken( msg, token );
		}
		catch (Exception e) {
			System.out.println("Falha ao tentar obter a URL do modelo!");
			System.exit(3);
		}
	}
}
