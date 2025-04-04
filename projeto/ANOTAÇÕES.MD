localhost:8080/user/novoUser
{ 
    "nome": "Victor",
    "username": "TRINIKZ", 
    "senha": "newnacional",
    "telefone": "11930324059",
    "email": "trinikz@proton.me",
    "endereco":"08636250"
}

localhost:8080/user/register
{
"Username": "TRINIKZ",
"Password": "12345"
}
@Autowired indica ao Spring Framework onde aplicar a injeção automática de dependências. Ela pode ser usada em atributos, construtores e métodos. 

anotação @PostMapping do Java é uma das anotações que mapeia URLs para métodos específicos em um controlador. Ela funciona como um atalho para simplificar o mapeamento dos métodos HTTP. 
Como funciona 
A anotação @PostMapping faz parte de um grupo de anotações que internamente usam @RequestMapping.
Essas anotações funcionam como atalhos que simplificam o mapeamento dos métodos HTTP.
Elas também expressam de forma mais concisa os métodos de manipulação.
A vantagem das anotações compostas é que o código fica mais limpo e de fácil leitura.
O que é um mapper? 
Um mapper é um padrão de projeto que mapeia objetos de um domínio para outro.
Ele atua como uma ponte entre duas representações distintas de dados.
Facilita a comunicação e a transformação entre elas.
Anotações do Spring Boot 
As anotações @GetMapping, @PostMapping e @PutMapping são usadas em uma aplicação Spring Boot.
Elas mapeiam URLs para métodos específicos em um controlador.

No Java, a anotação @GetMapping mapeia URLs para métodos específicos em um controlador, direcionando requisições HTTP do tipo GET para os métodos corretos. 

No Java, a anotação @PutMapping mapeia URLs para métodos que atualizam recursos, como produtos, em uma aplicação Spring Boot. 
