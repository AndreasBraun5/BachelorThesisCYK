grammar Exercise;

exerciseDefinition: grammarDefinition NEWLINE
                    wordDefinition NEWLINE?;

grammarDefinition: NEWLINE* varStart NEWLINE
                   rules;

varStart: 'start:' WHITE_SPACE* nonTerminal SEMICOLON;

rules: 'rules:' WHITE_SPACE* OPEN_BRACE_CURLY NEWLINE
                    (singleRule NEWLINE)+
                 CLOSE_BRACE_CURLY SEMICOLON;

singleRule: WHITE_SPACE* nonTerminal // A
      WHITE_SPACE* '->' WHITE_SPACE* // ->
      terminal WHITE_SPACE* // a
    |
      WHITE_SPACE* nonTerminal // A
      WHITE_SPACE* '->' WHITE_SPACE* // ->
      nonTerminal WHITE_SPACE+ nonTerminal WHITE_SPACE*;

wordDefinition: 'word:' WHITE_SPACE* terminals WHITE_SPACE* SEMICOLON;

terminals: terminal
         |
           terminal WHITE_SPACE terminals;

nonTerminal: UPPERCASE_OR_NUM+ '\''?;
terminal: LOWER_CASE+;

UPPERCASE_OR_NUM: ('A'..'Z');

LOWER_CASE: ('a'..'z' | '0'..'9');

OPEN_BRACE: '(';
CLOSE_BRACE: ')';

SEMICOLON : ';';

OPEN_BRACE_CURLY: '{';
CLOSE_BRACE_CURLY: '}';

WHITE_SPACE: ' ' | '\t';

NEWLINE: '\n';