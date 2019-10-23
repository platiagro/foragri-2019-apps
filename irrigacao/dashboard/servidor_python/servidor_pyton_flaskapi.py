#servidor_python_flaskapi.py
#Exemplo de servidor WebService Python
#endpoint http://127.0.0.1:5000/sensores
#Operando em modo Rest

#Precisa de:
#pip install flask
#pip install requests

from flask import Flask, json, request
import csv

#Carrega arquivo de dados
with open('etc/irrig_implant_resposta.csv', 'r') as arqEntrada:
  reader = csv.reader(arqEntrada)
  listaRespostas = list(reader)

#Cria contadores de respostas (devem ser globais) 
contadorResposta = 1
contadorRespostaMax = len(listaRespostas)

  
api = Flask(__name__)

#Metodos
#=================================================
@api.route('/sensores', methods=['GET'])
def get_companies():
   return json.dumps({})
   #return "nada"
   
#=================================================
@api.route('/sensores', methods=['POST'])
def post_companies():
   #return json.dumps({"success": True}), 201
   global contadorResposta
   global contadorRespostaMax
   global listaRespostas
   
   #Confere dados recebidos
   print(request.get_json())
   
   #Verifica se chegou no limite
   if contadorResposta >= contadorRespostaMax: 
      contadorResposta = 1 #Reset do contador
   
   retorno = listaRespostas[contadorResposta]
   contadorResposta = contadorResposta + 1
   
   #return json.dumps({"litros": retorno[0]}), 200
   
   return json.dumps({"meta":{"puid": "dvc3v4laabhm54fh6jt7bj7a1p","tags":{},"routing":{},"requestPath":{"regression": "platiagro/regression-deployment:0.0.1" },"metrics":[]},"data":{"names":[],"ndarray": retorno[0] }}), 200
   
#=================================================

if __name__ == '__main__':
    api.run()
