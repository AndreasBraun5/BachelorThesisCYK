package com.github.andreasbraun5.thesis.antlr;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * Created by AndreasBraun on 20.07.2017.
 */
public class GrammarCompiler {


    public static Grammar parseGrammar(String string) {
        GrammarLexer lexer = new GrammarLexer( new ANTLRInputStream( string ) );
        GrammarParser parser = new GrammarParser( new CommonTokenStream( lexer ) );
        parser.setBuildParseTree( true );
        GrammarParser.GrammarDefinitionContext definition = parser.grammarDefinition();

        ParseTreeWalker walker = new ParseTreeWalker();
        GrammarTreeListener grammarTreeListener = new GrammarTreeListener();
        walker.walk( grammarTreeListener, definition );

        Grammar grammar = Grammar.empty(grammarTreeListener.getVarStart());
        grammar.addProductions(grammarTreeListener.getProductions());
        return grammar;
    }

}
