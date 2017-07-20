grammar Grammar;

grammarDefinition: varStart NEWLINE
                   rules NEWLINE?;

varStart: 'start:' WHITE_SPACE* nonTerminal ';';

rules: 'rules:' WHITE_SPACE* OPEN_BRACE_CURLY NEWLINE
                    (singleRule NEWLINE)+
                 CLOSE_BRACE_CURLY ';';
       
singleRule: WHITE_SPACE* nonTerminal // A
      WHITE_SPACE* '->' WHITE_SPACE* // ->
      terminal WHITE_SPACE* // a
    |
      WHITE_SPACE* nonTerminal // A
      WHITE_SPACE* '->' WHITE_SPACE* // ->
      nonTerminal WHITE_SPACE+ nonTerminal WHITE_SPACE*;

nonTerminal: UPPERCASE_OR_NUM+;
terminal: LOWER_CASE+;
               
UPPERCASE_OR_NUM: ('A'..'Z' | '0'..'9');

LOWER_CASE: ('a'..'z');

OPEN_BRACE: '(';
CLOSE_BRACE: ')';

OPEN_BRACE_CURLY: '{';
CLOSE_BRACE_CURLY: '}';

WHITE_SPACE: ' ' | '\t';

NEWLINE: '\n';