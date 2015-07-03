Aplicação para criar, processar e consultar uma malha logística.

O recurso logistics-network expõe o método para upload o arquivo no formato: <br/>
A B 10 <br/>
B D 15 <br/>
A C 20 <br/>
C D 30 <br/>
B E 50 <br/>
D E 30 <br/>

Representando origem, destino e distancia entre os pontos.

Para enviar um arquivo utilize o seguinte recurso: <br/>
curl -v -F file=@test-network.txt http://localhost:8080/trial-ws/api/logistics-network/{network-name}

onde {network-name} é o nome da malha logística.

Após o envio as informações do arquivo serão persistidas. <br/>
Um processo executa continuamente buscando malhas ainda não processadas, as rotas possíveis serão geradas e estarão disponíveis em:

http://localhost:8080/trial-ws/api/path/{network-name}/{source}/{target}?autonomy={autonomy}&liter-price={liter-price}

Os parâmetros no 'Path' são obrigatórios.

Os parâmetros na 'Query string' são opcionais, mas se forem informados a aplicação poderá calcular a quantidade de combustível necessária e o custo total.

As principais tecnologias utilizadas foram:
- MySQL como base de dados
- Jersey para a criação dos recursos RESTful
- Grizzly como servidor HTTP
- JPA com Eclipselink para persistência
- Spring para injeção de dependências

Para criar a base de dados utilize o script: sql/ddl.sql

<p>Para empacotar a apicação execute:</p>
mvn clean package no diretório raiz. <br/>
O arquivo logistics-network-1.0-bin.zip será gerado dentro da pasta target. <br/>
Deszipe o arquivo logistics-network-1.0-bin.zip <br/>
Acesse a pasta gerada (logistics-network) <br/>
Execute o comando: java -jar logistics-network-1.0.jar <br/>
O grizzly será iniciado e a aplicação responderá por padrão no endereço: localhost:8080

*** PONTOS DE ATENÇÃO ***
<p>Os seguintes pontos podem necessitar alteração:</p>
Caminho do log: src/main/resources/logback.xml<br/>
Diretório base: pom.xml<br/>
Binds do servidor HTTP: Main class<br/>
Configuração do datasource: PersistenceConfiguration class<br/>




