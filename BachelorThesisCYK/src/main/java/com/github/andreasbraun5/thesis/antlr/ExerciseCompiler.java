package com.github.andreasbraun5.thesis.antlr;

import com.github.andreasbraun5.thesis.exercise.Exercise;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.util.Word;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * Created by AndreasBraun on 20.07.2017.
 */
public class ExerciseCompiler {


    public static Exercise parseExercise(String string) {
        StringBuilder additionalMessage = new StringBuilder();
        ExerciseLexer lexer = new ExerciseLexer(new ANTLRInputStream(string));
        ExerciseParser parser = new ExerciseParser(new CommonTokenStream(lexer));

        parser.getErrorListeners().clear();
        parser.addErrorListener(
                new BaseErrorListener() {
                    @Override
                    public void syntaxError(
                            Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line,
                            int charPositionInLine,
                            String msg,
                            RecognitionException e) {
                        additionalMessage.append("SyntaxEception in GrammarDefinition: \"")
                                .append(string)
                                .append("\": ")
                                .append(msg);
                        if (offendingSymbol instanceof CommonToken) {
                            CommonToken token = (CommonToken) offendingSymbol;
                            if (token.getText().equals("*") || token.getText().equals("+") || token.getText()
                                    .equals("?")) {
                                additionalMessage.append(", dangling metacharacter: '")
                                        .append(((CommonToken) offendingSymbol).getText())
                                        .append("' at line ")
                                        .append(token.getLine())
                                        .append(", pos ")
                                        .append(token.getCharPositionInLine());
                            }
                        }
                    }
                }
        );
        parser.setBuildParseTree(true);
        ExerciseParser.ExerciseDefinitionContext definition = parser.exerciseDefinition();
        if (parser.getNumberOfSyntaxErrors() > 0) {
            throw new IllegalArgumentException("malformed GrammarDefinition found : " + string + "\n" + additionalMessage.toString());
        }
        ParseTreeWalker walker = new ParseTreeWalker();
        ExerciseTreeListener grammarTreeListener = new ExerciseTreeListener();
        walker.walk(grammarTreeListener, definition);
        Grammar grammar = Grammar.empty(grammarTreeListener.getVarStart());
        grammar.addProductions(grammarTreeListener.getProductions());
        Word word = grammarTreeListener.getWord();
        // check for each terminal in word if it is element of at least one rhse in the grammar
        return Exercise.builder()
                .grammar(grammar)
                .word(word).build();
    }
}
