# GoTwo Lang

Linguagem feita para o primeiro trabalho da disciplina de compiladores 2018.
Esta linguagem foi inspirada na linguagem de programação Go, criada pelo
Google. Go é uma linguagem procedural orientada à objetos, inspirada em C
com algumas modificações (var para declaração de variáveis e coletor de lixo 
como exemplos) e um forte foco em programação concorrente.  
A linguagem GoTwo é uma simplificação da linguagem Go, contando com uma
sintaxe parecida, porém com a falta de algumas características da linguagem
completa.

Por: André Victor, Bruno Brezolin e Melissa Wong

## Expressões Regulares

```xml
<!--Aceita uma cadeia para representar um inteiro de um ou mais dígitos-->
<INTEGER_LITERAL>: (<DIGIT>)+

<!--Aceita uma cadeia que comece e termine com ", porém a cadeia entre o início e o final não deve conter ", \n ou \r-->
<STRING_LITERAL>: '\"' ( ~['\"','\n','\r'])* '\"'

<!--Nulo-->
<NULL_LITERAL>: 'nil'

<!--Aceita diversas representações de ponto flutuante, incluindo com expoente-->
<FLOATING_POINT_LITERAL>:
    (<DIGIT>)+ '.' (<DIGIT>)* (<EXPONENT>)? (['f','F','d','D'])?  
    |   '.' (<DIGIT>)+ (<EXPONENT>)? (['f','F','d','D'])?  
    |   (<DIGIT>)+ <EXPONENT> (['f','F','d','D'])?  
    |   (<DIGIT>)+ (<EXPONENT>)? ['f','F','d','D'] 

<!--Aceita um expoente positivo ou negativo de um ou mais dígitos-->
<EXPONENT>: ['e','E'] (['+','-'])? (<DIGIT>)+

<!--Aceita uma cadeia para representar uma variável, deve ser iniciada com uma letra e então seguida por uma ou mais letras, dígitos ou '_'-->
<IDENT>: (<LETTER>) (<LETTER>|<DIGIT>|'_')*

<!--Aceita uma letra minúscula ou maiúscula-->
<LETTER>: ( <LOWERLETTER> | <UPPERLETTER> ) >

<!--Aceita uma letra minúscula entre 'a' e 'z'-->
<LOWERLETTER>: ['a'-'z']

<!--Aceita uma letra maiúscula entre 'A' e 'Z'-->
<UPPERLETTER>: ['A'-'Z']

<!--Aceita um dígito entre '0' e '9'-->
<DIGIT>: ['0'-'9']
```

## Gramática

```xml

<!--Simbolo inicial da linguagem, com o package, a lista de imports (opcional) e a lista de funções, respectivamente-->
<program> = 'package', <IDENT>, ';', 
          ['import', <importDecl>, ';'],
          <functionList>, EOF;

<!--Declaração de imports-->
<importDecl> = (importSpec)+ 
|  "(", importSpec, ")" ;

<!--Especificação de um import-->
<importSpec> = [ ( "." | <IDENT> ) ], <STRING_LITERAL>

<!--Lista de funções-->
<funcList> = (<funcDecl>, [<funcList>]) ;

<!--Declaração de funções-->
<funcDecl> = 'func', <IDENT>, <funcBody> ;

<!--Corpo de uma função-->
<funcBody> = '(', (<paramList>)*, ')', 
            [ <funcReturnBody> ] <statement> ;

<!--Especificação do retorno de uma função-->
<funcReturnBody> =  ('(', (<returnList>)* ')' 
|   <type>) ;

<!--Lista de retorno-->
<returnList> = [ <IDENT> ] <type> (',' [ <IDENT> ] <type>)* ;

<!--Lista de parâmetros-->
<paramList> = <IDENT>, <paramLType> ;

<!--Lista de tipos de parâmetros-->
<paramLType> = <type>, (',', <IDENT>, <type>)*
|   (',', <IDENT>)* <type> ;

<!--Declaração de variáveis-->
<varDecl> = 'var', <IDENT>, (',', <IDENT>)*, [<type>] ;

<!--Declaração de constantes-->
<constDecl> = 'const', <IDENT>, [<type>] ;

<!--Lista de statements-->
<statList> = [<statement>, <statList>] ;

<!--Atribuição-->
<atribStat> = <lvalue>, [('=' | ':='), <expression>] ;

<!--Return-->
<returnStat> = 'return', [<expression>, (',', <expression>)*] ;

<!--If statement-->
<ifStat> = 'if', '(', <expression>, ')', <statement>, ['else', <statement>] ;

<!--For statement-->
<forStat> = 'for', [ '(', [<atribStat>], ';',
                      [<expression>], ';',
                      [<atribStat>], ')' ],
                      <statement> ;

<!--Especificação de variável que recebe uma atribuição-->            
<lvalue> = <IDENT>, ['(', <argList>], ')'], 
          ('[', <expression>, ']' 
|         '.', <IDENT>, ['(', <argList>, ')']) ;

<!--Expressão-->
<expression> = <numexpr>, [(<comparison>) <numexpr>] ;

<!--Expressão numérica-->
<numexpr> = <term>, (('+' | '-'), <term>)* ;

<!--Termo de uma expressão numérica-->
<term> = <unaryexpr>, (( '*' | '/' | '%'), <unaryexpr>)* ;

<!--Expressão unária-->
<unaryexpr> = [('+' | '-')], <factor> ;

<!--Fatores-->
<factor> = <INTEGER_LITERAL> | <STRING_LITERAL> | 
           <NULL_LITERAL> | <FLOATING_POINT_LITERAL> | <lvalue> | 
           '(', <expression>, ')' ;

<!--Lista de argumentos-->
<argList> = [<expression> (',', <expression>)*] ;

<!--Símbolos de comparação-->
<comparison> = '>' | '<' | '==' | '<=' | '>=' | '!=' ;

<!--Statements-->
<statement> = <funcDecl>
  | <varDecl>, ';'
  | <constDecl>, ';'
  | <atribStat>, ';'
  | <returnStat>, ';'
  | <ifStat>
  | <forStat>
  | '{' <statList> '}'
  | 'break', ';'
  | ';' ;

<!--Tipos-->
<type> = 'int' | <STRING_LITERAL> | 'bool' | 'float' | 'byte' | 'rune' ;
```

## Requisitos

Os requisitos e versões destes mostrados aqui são os utilizados no 
desenvolvimento deste projeto. Pode ocorrer o caso de que uma versão mais nova
ou antiga funcione, mas recomenda-se utilizar as versões especificadas aqui

| Requisito | Versão |
|-----------|--------|
| Java - JRE| 1.8    |
| Java - JDK| 1.8    |
| javacc    | 6.0    |

## A fazer

- [x] Gramática da linguagem em EBNF
- [x] Código comentado javacc para o compilador da linguagem
    - [x] Gera árvore sintática e mostra no terminal
    - [x] Tratamento de erros léxicos e sintáticos
- [ ] Arquivo de informações da linguagem
    - [x] Nome do software
    - [x] Nome dos integrantes do grupo
    - [x] Descrição da linguagem
    - [x] Descrições das expressões para reconhecimento de tokens
    - [x] Descrição da gramática
    - [ ] Descrição do programa que faz análise léxica e sintática
    - [ ] Descrição da estratégia de tratamento de erros e tipos de erros
- [ ] Exemplo de código fonte correto
- [ ] Exemplo de código fonte incorreto
