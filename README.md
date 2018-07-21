# GoTwo Lang

Linguagem feita para o primeiro trabalho da disciplina de compiladores 2018.
Esta linguagem foi inspirada na linguagem de programação Go, criada pelo
Google. Go é uma linguagem procedural orientada à objetos, inspirada em C
com algumas modificações (var para declaração de variáveis e coletor de lixo 
como exemplos) e um forte foco em programação concorrente.  
A linguagem GoTwo é uma simplificação da linguagem Go, contando com uma
sintaxe parecida, porém com a falta de algumas características da linguagem
completa.

Por: André Lopes, Bruno Brezolin e Melissa Wong

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
<program> = 'package', <IDENT>, ';', 
          ['import', <importDecl>, ';'],
          <functionList>, EOF;

<importDecl>  (<importSpec>)+ | '(' <importSpec> ')'

<importSpec> = [ ( "." | <IDENT> ) ] , <STRING_LITERAL>

<funcList> = (<funcDecl>, [<funcList>]) ;

<funcDecl> = 'func', <IDENT>, <funcBody> ;

<funcBody> = '(', (<paramList>)*, ')', 
            [ <funcReturnBody> ] <statement> ;

<funcReturnBody> =  ('(', (<returnList>)* ')' | <type>) ;

<returnList> = [ <IDENT> ] <type> (',' [ <IDENT> ] <type>)* ;

<paramList> = <IDENT>, <paramLType> ;

<paramLType> = <type>, (',', <IDENT>, <type>)*
|   (',', <IDENT>)* <type> ;

<varDecl> = 'var', <IDENT>, (',', <IDENT>)*, [<type>] ;

<constDecl> = 'const', <IDENT>, [<type>] ;

<statList> = [<statement>, <statList>] ;

<atribStat> = <lvalue>, [('=' | ':='), <expression>] ;

<returnStat> = 'return', [<expression>, (',', <expression>)*] ;

<ifStat> = 'if', '(', <expression>, ')', <statement>, ['else', <statement>] ;

<forStat> = 'for', [ '(', [<atribStat>], ';',
                      [<expression>], ';',
                      [<atribStat>], ')' ],
                      <statement> ;
            
<lvalue> = <identifier>, ['(', <argList>], ')'], ('[', <expression>, ']' |
          '.', <identifier>, ['(', <argList>, ')']) ;

<expression> = <numexpr>, [(<comparison>) <numexpr>] ;

<numexpr> = <term>, (('+' | '-'), <term>)* ;

<term> = <unaryexpr>, (( '*' | '/' | '%'), <unaryexpr>)* ;

<unaryexpr> = [('+' | '-')], <factor> ;

<factor> = <INTEGER_LITERAL> | <STRING_LITERAL> | 
           <NULL_LITERAL> | <FLOATING_POINT_LITERAL> | <lvalue> | 
           '(', <expression>, ')' ;

<argList> = [<expression> (',', <expression>)*] ;

<comparison> = '>' | '<' | '==' | '<=' | '>=' | '!=' ;

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

## Tratamento de Erros

Os erros sintáticos são tratados pelo método de pânico, com algumas melhorias, foram usados tokens de sincronização e os conjuntos de Primeiros, assim como analise de quais os melhores tokens de sincronização para cada caso expecifico. São mostradas mensagens para que o usuário possa identificar quais erros está cometendo durante o processo de construção de seu código.
Os erros léxicos são mostrados para o usuário, tanto como tokens que não fazem parte de nenhuma ER ou constantes literais com tokens não aceitos no meio.
Para ambos é mostrado a quantidade de erros no final da execução do parser.

## Funcionamento do Programa

O programa utiliza das ferramentas do Javacc (Gerador de Parser e Analisador Léxico) para fazer as analises léxica e sintática, no programa é definido um método principal que faz o requerimento das entradas e das tags de debug, assim como chama o analisador léxico e sintático, a entrada é analisada por meio de métodos recursivos, usando uma perspectiva de analise top-down e uma gramática marjoritariamente LL(1).

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
    - [x] Descrição da estratégia de tratamento de erros e tipos de erros
- [x] Exemplo de código fonte correto
- [x] Exemplo de código fonte incorreto
