# Ultima.School

## Projeto final do curso Desenvolvedor Java - CRM - API REST

### Descrição do projeto

O intuito desse projeto é mostrar a criação e funcionamento de uma API REST completa. Ou seja,
o aluno terá capacidade de executar a aplicação, gravar dados, apagar dados e alterar dados no 
banco de dado usando a interface REST provida por esta API.

Dado o tempo de curso, nem todas as boas práticas ou conceitos possíveis para uma API REST foram
seguidos ou utilizados. Como sinalizado abaixo, foram usados algumas soluções diferentes para resolver
os mesmos problemas de forma a ilustrar alumas das possíveis soluções providas pelo Spring Boot.

### Pré requisitos

#### Banco de Dados

O projeto espera que esteja configurado um banco de dados MySQL sendo executado na máquina local (`localhost:3306`) e com um banco de dados chamado `projetocrm` e usuário `root` (sem senha) conforme especificado no arquivo `application.properties` do projeto.

Na pasta `resources/data` do projeto, existe o arquivo `cria_bd.sql` que tem o script necessário para criar a estrutura necessária no BD para uso com o projeto.

#### Versão Java

Para o curso e o projeto, a versão mínima da JDK é a **Java 8**.



### Ao Instrutor

Observações sobre o projeto aqui exemplificado:
1. Classe `ClienteController`: foram usados os 4 métodos mais conhecidos:
   1. `GET` para a leitura dos dados do `Cliente`
   2. `DELETE` para a exclusão física do registro do banco de dados
   3. `PUT` para a alteração dos dados do cliente - vale observar que todo o objeto `Cliente` deve ser enviado para que não acorram erros (simplificação por questões do tempo de curso)
   4. `POST` para a criação de um novo cliente - vale a observação do item acima
2. A classe `Cliente` representa um cliente do CRM com 6 campos. Cada campo possui um tipo diferente para ilustrar a forma como o REST faz a conversão automática e como o aluno deve pensar na hora de fazer o uso dos métodos adequados nos `PreparedStatement` e `ResultSet`:
   1. `nome` (tipo: `String`)
   2. `endereco`  (tipo: `String`)
   3. `telefone` (tipo: `String`)
   4. `salario` (tipo: `double`)
   5. `nr_filhos` (tipo: `int`)
   6. `data_cadastro` (tipo: `Date`)
3. A classe `ClienteService` existe para representar a camada de serviços da aplicação. É importante frisar esse ponto para que os alunos já comecem a ter o conhecimento de *Separation of Concerns*, organização do código e lógica de negócios. Ainda nesse tema, existe uma `Exception` customizada (`ClienteNaoEncontradoException`) criada para tratamento de erro de cliente não encontrado no banco de dados. Serve para ilustrar o tratamento de exceções na camada de serviços.
4. A classe `ClienteRepository` possui alguns tratamentos diferenciados na forma de lidar com os parâmetros de entrada. Também serve para ilustrar as diferentes formas de tratamento que são possíveis pelo Spring Boot:
   1. Nos métodos `leClientePorId` e `apagaClientePorId` é usado a forma não convencional de *pegar* uma conexão com o banco de dados.  `connection = jdbcTemplate.getDataSource().getConnection();` -  Comparar com o método utilizado durante o curso no Módulo 5. Ilustra o uso puro do `PreparedStatement` e `ResultSet`.
   2. Nos métodos `alteraClientePorId` e `escreveCliente` é usada a forma Spring Boot de passagem de parâmetros nomeados `NamedParameterJdbcTemplate`. Serve para mostrar como isso dá legibilidade ao código e como o código fica mais enxuto.

### Exemplo de JSON do objeto `Cliente`:

```
{
   "id": 1,
   "nome": "<nome do cliente>",
   "endereco": "<endereco do cliente>",
   "telefone": "<telefone do cliente>",
   "salario": 12345.00,
   "nrFilhos": 1,
   "dataCadastro": "2022-11-02"
}
```
*O campo `<id>` só deve ser informado para o método `PUT`.*

