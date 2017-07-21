package com.github.andreasbraun5.thesis.antlr;

import com.github.andreasbraun5.thesis.exercise.Exercise;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.List;

/**
 * Created by AndreasBraun on 21.07.2017.
 */
public class ExerciseStringConverter {

    public Exercise fromString(String string) {
        return ExerciseCompiler.parseExercise(string);
    }


    public String exerciseToString(Exercise exercise) {
        Grammar grammar = exercise.getGrammar();
        StringBuilder str = new StringBuilder();
        str.append("start:" + grammar.getVariableStart() + ";").append("\n");
        str.append("rules:{").append("\n");
        List<Production> prodList = grammar.getProductionsAsList();
        prodList.forEach(production -> str.append(
                production.getLeftHandSideElement())
                .append(" -> ")
                .append(production.getRightHandSideElement())
                .append("\n"));
        str.append("};\n");
        Word word = exercise.getWord();
        str.append("word:");
        word.getTerminals().forEach(terminal -> str.append(" ").append(terminal));
        str.append(";");
        return str.toString();
    }

    /*
    start:S;
rules:{
    A -> A A
    A -> a
};
word: a a a a a;
     */

}
