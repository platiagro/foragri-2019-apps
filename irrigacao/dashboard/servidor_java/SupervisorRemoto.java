//Web service endpoint 

package servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

@WebServiceProvider
@ServiceMode(value = javax.xml.ws.Service.Mode.MESSAGE)
@BindingType(value = HTTPBinding.HTTP_BINDING)
public class SupervisorRemoto implements Provider<Source> {
	private int contadorResposta = 0;
	private int contadorRespostaMax = 0;
	private List<String> listaResultado = null;
	
	@Resource
	private WebServiceContext wsContext;


	@Override
	public Source invoke(Source request) {
		if (wsContext == null) {
			System.out.println( "Falha na injecao de dependencia do wsContext" );
			throw new RuntimeException("Falha na injecao de dependencia do wsContext");
		}
		MessageContext msgContext = wsContext.getMessageContext();
		
		String msg = (String) msgContext.get(MessageContext.HTTP_REQUEST_METHOD); 
		
		switch (msg ) {
		case "DELETE":
			return doDelete(msgContext);
		case "GET":
			return doGet(msgContext);
		case "POST": //INSERE
			return doPost(msgContext, request);
		case "PUT": //ATUALIZA
			return doPut(msgContext, request);
		default:
			throw new HTTPException(405);
		}
	}

	private Source doDelete(MessageContext msgContext) {
		String qs = (String) msgContext.get(MessageContext.QUERY_STRING);
		//System.out.println( qs );
		
		try {
			StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
			xml.append("<response>DELETE ainda nao implementado</response>");
			return new StreamSource(new StringReader(xml.toString()));
		} 
		catch (Exception e) {
			throw new HTTPException(500);
		}
	}

	private Source doGet(MessageContext msgContext) {
		String qs = (String) msgContext.get(MessageContext.QUERY_STRING);
		//System.out.println( qs );
		
		
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
		xml.append("<response>GET ainda nao implementado</response>");
		return new StreamSource(new StringReader(xml.toString()));
	}

	private Source doPost(MessageContext msgContext, Source source) {
		String qs = (String) msgContext.get(MessageContext.QUERY_STRING);
		//System.out.println( qs );
		
		String resposta = null;
		List<String> listaResp = null;
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
		
		try {
			//DOMResult dom = new DOMResult();
			//Transformer t = TransformerFactory.newInstance().newTransformer();
			//t.transform(source, dom);
			//XPathFactory xpf = XPathFactory.newInstance();
			//XPath xp = xpf.newXPath();
			
			//System.out.println( "[Servidor] POST" );
			//System.out.println( xp.evaluate("/identif", dom.getNode(), XPathConstants.NODESET) );
						
		} 
		catch ( Exception e ) {
			System.out.println( e.getMessage() );
			throw new HTTPException(500);
		}
		
		try {
			if( this.getListaResultado() == null || (contadorResposta >= contadorRespostaMax) ) {
				contadorResposta = 0;
				contadorRespostaMax = 0;
				listaResp = this.carregaRespostas();
				this.setListaResultado( listaResp );
			}
			else {//Tenta carregar arquivo
				listaResp = this.getListaResultado();
			}	

			if( listaResp != null ) {
				this.setListaResultado( listaResp );
				resposta = this.getListaResultado().get( contadorResposta  );
				contadorResposta++;
			}
			else {
				resposta = "#";
			}
		}
		catch (Exception e) {
			System.out.println("Erro ao processar a resposta!");
			xml.append("<response>#</response>");
			return new StreamSource(new StringReader(xml.toString()));
		}
		
		
		xml.append("<response>" + resposta + "</response>");
		return new StreamSource(new StringReader(xml.toString()));
	}

	private Source doPut(MessageContext msgContext, Source source) {
		String qs = (String) msgContext.get(MessageContext.QUERY_STRING);
		//System.out.println( qs );
		
		try {
			DOMResult dom = new DOMResult();
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.transform(source, dom);
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xp = xpf.newXPath();
//			NodeList sensores = (NodeList) xp.evaluate("/sensor", dom.getNode(), XPathConstants.NODESET);
//			
//			String umidade = xp.evaluate("@umidade", sensores.item(0)).trim();
//			String temperatura = xp.evaluate("@title", sensores.item(0)).trim();
//			System.out.println("Umidde= " + umidade + " e temperatura = " + temperatura );

		} 
		catch (Exception e) {
			System.out.println( e.getMessage() );
			throw new HTTPException(500);
		}
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
		xml.append("<response>PUT ainda nao implementado</response>");
		return new StreamSource(new StringReader(xml.toString()));
	}

	private List<String> carregaRespostas() {
		String nomeArqResposta = "irrig_implant_resposta.csv";
		String linhaEntrada = null;
		String chaveStr = null;

		BufferedReader arqEntrada = null;
		
		List<String> resultado = new LinkedList<String>();

		try {
			arqEntrada = new BufferedReader( new FileReader( "etc" + File.separatorChar + nomeArqResposta ));
			contadorRespostaMax = 0;//Reseta tamanho do conjunto
		}
		catch (Exception e) {
			System.out.println("Erro ao tentar abrir o arquivos com os dados de resposta!");
			return null;
		}
		
		try {
			linhaEntrada = arqEntrada.readLine();
			
			while (linhaEntrada != null) {
				if( ! linhaEntrada.startsWith("#")) {
					chaveStr = linhaEntrada + "";
					resultado.add( chaveStr );
					contadorRespostaMax++;;
				}
				linhaEntrada = arqEntrada.readLine();
			}
		}
		catch (Exception e) {
			System.out.println("Erro ao tentar ler o arquivos com os dados de resposta!");
			return null;
		}
		
		try {
			arqEntrada.close();
		}
		catch (Exception e) {
			System.out.println("Erro ao tentar fechar o arquivos com os dados de resposta!");
		}
		
		return resultado;
	}
	
	
	//Gets & Set
	public List<String> getListaResultado() {
		return listaResultado;
	}

	public void setListaResultado( List<String> listaResult) {
		this.listaResultado = listaResult;
	}

	
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:5000/sensores", new SupervisorRemoto());
	}
}