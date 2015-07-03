Aplicação para criar, processar e consultar uma malha logística.

O recurso logistics-network expõe o método para upload o arquivo no formato:
A B 10
B D 15
A C 20
C D 30
B E 50
D E 30

Representando origem, destino e distancia entre os pontos.

Para enviar um arquivo utilize o seguinte recurso:
curl -v -F file=@test-network.txt http://localhost:8080/trial-ws/api/logistics-network/{network-name}

onde {network-name} é o nome da malha logística.

Após o envio as informações do arquivo serão persistidas.
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

Para criar a base de dados utilize o script:
- sql/ddl.sql

Para empacotar a apicação execute:
mvn clean package no diretório raiz. O arquivo logistics-network-1.0-bin.zip será gerado dentro da pasta target.
Deszipe o arquivo logistics-network-1.0-bin.zip
Acesse a pasta gerada (logistics-network)
Execute o comando: java -jar logistics-network-1.0.jar
O grizzly será iniciado e a aplicação responderá por padrão no endereço: localhost:8080

*** PONTOS DE ATENÇÃO ***

Os seguintes pontos podem necessitar alteração:

Caminho do log
- src/main/resources/logback.xml
Diretório base
- pom.xml
Binds do servidor HTTP
- Main class
Configuração do datasource
- PersistenceConfiguration class




