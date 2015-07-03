#logistics-network

Aplicação para criar, processar e consultar uma malha logística.

O recurso logistics-network expõe o método para upload o arquivo no formato: <br/>
```
A B 10
B D 15
A C 20
C D 30
B E 50
D E 30
```

Representando origem, destino e distancia entre os pontos.

Para enviar um arquivo utilize o seguinte recurso: <br/>
`curl -v -F file=@test-network.txt http://localhost:8080/trial-ws/api/logistics-network/{network-name}`

onde `{network-name}` é o nome da malha logística.

Após o envio as informações do arquivo serão persistidas. <br/>
Um processo executa continuamente buscando malhas ainda não processadas, as rotas possíveis serão geradas e estarão disponíveis em:

`http://localhost:8080/trial-ws/api/path/{network-name}/{source}/{target}?autonomy={autonomy}&liter-price={liter-price}`

Os parâmetros no 'Path' são obrigatórios.

Os parâmetros na 'Query string' são opcionais, mas se forem informados a aplicação poderá calcular a quantidade de combustível necessária e o custo total.

As principais tecnologias utilizadas foram:
- MySQL como base de dados
- Jersey para a criação dos recursos RESTful
- Grizzly como servidor HTTP
- JPA com Eclipselink para persistência
- Spring para injeção de dependências

Para criar a base de dados utilize o script: 
- `sql/ddl.sql`

*Talvez seja necessário ajustar o tamanho das colunas `varchar` ou a precisão das colunas `decimal` dependendo da massa de teste*

<p>Para empacotar a apicação execute:</p>
1. `mvn clean package` no diretório raiz. O arquivo `logistics-network-1.0-bin.zip` será gerado dentro da pasta target.
2. Deszipe o arquivo `logistics-network-1.0-bin.zip`
3. Acesse a pasta gerada `(logistics-network)` <br/>
4. Execute o comando: `java -jar logistics-network-1.0.jar`

O grizzly será iniciado e a aplicação responderá por padrão no endereço: `http://localhost:8080`

*** **PONTOS DE ATENÇÃO** ***
<p>Os seguintes pontos necessitam alteração:</p>
Caminho do log: 
- Altere o caminho no arquivo `src/main/resources/logback.xml`

Diretório base: 
- Altere a tag `base.dir` no `pom.xml` e configure um diretório com permissão de escrita existente na máquina<br/>

Binds do servidor HTTP: 
- Altere a classe `trial.logisticsnetwork.core.Main`

Configuração do datasource: 
- Altere a classe `trial.logisticsnetwork.core.PersistenceConfiguration`


Melhorias:
- Colocar as variáveis em arquivos properties ao invés de escrevê-las no código.
- Padronizar os objetos `Response` isolando atributos comuns na super classe.
- Retornar os códigos HTTP corretos de acordo com a resposta, atualmente todos os métodos retornam 200
