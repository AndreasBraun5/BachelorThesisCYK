package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.VariableK;

import java.util.Set;

/**
 * Created by AndreasBraun on 03.07.2017.
 */
public final class SS13Exercise {

    private SS13Exercise() {
    }

    public static final Grammar SS13_GRAMMAR;
    public static final Word SS13_EXAMPLE_WORD = Word.fromStringCharwise("bbacbc");
    public static final SetVarKMatrix SS13_SET_VARK;
    public static final GrammarPyramidWrapper SS13_GRAMMAR_PYRAMID_WRAPPER = GrammarPyramidWrapper.builder().build();


    static {
        {
            Grammar grammar = new Grammar(new VariableStart("S"));
            Production productions[] = new Production[10];
            productions[0] = new Production(new VariableStart("S"), new VariableCompound(new Variable("A"), new Variable("A")));
            productions[1] = new Production(new VariableStart("S"), new VariableCompound(new Variable("A"), new Variable("C")));
            productions[2] = new Production(new VariableStart("S"), new VariableCompound(new Variable("C"), new Variable("B")));
            productions[3] = new Production(new Variable("A"), new VariableCompound(new Variable("B"), new Variable("C")));
            productions[4] = new Production(new Variable("A"), new Terminal("a"));
            productions[5] = new Production(new Variable("A"), new Terminal("b"));
            productions[6] = new Production(new Variable("B"), new VariableCompound(new Variable("B"), new Variable("B")));
            productions[7] = new Production(new Variable("B"), new Terminal("b"));
            productions[8] = new Production(new Variable("C"), new VariableCompound(new Variable("A"), new Variable("C")));
            productions[9] = new Production(new Variable("C"), new Terminal("c"));
            grammar.addProductions(productions);
            SS13_GRAMMAR = grammar;
            SS13_GRAMMAR_PYRAMID_WRAPPER.setGrammar(grammar);
        }
        {
            int wordLength = SS13_EXAMPLE_WORD.getWordLength();
            Set<VariableK>[][] setVTemp = Util.getInitialisedHashSetArray(wordLength, VariableK.class);

            // reconstructing example matrix from SCRIPT_SET_VARK
            setVTemp[0][0].add(new VariableK(new Variable("A"), 1));
            setVTemp[0][0].add(new VariableK(new Variable("B"), 1));
            setVTemp[0][1].add(new VariableK(new VariableStart("S"), 1));
            setVTemp[0][1].add(new VariableK(new Variable("B"), 1));
            setVTemp[0][3].add(new VariableK(new VariableStart("S"), 1));
            setVTemp[0][3].add(new VariableK(new Variable("C"), 1));
            setVTemp[0][3].add(new VariableK(new Variable("A"), 1));
            setVTemp[0][3].add(new VariableK(new Variable("A"), 2));
            setVTemp[0][4].add(new VariableK(new VariableStart("S"), 4));
            setVTemp[0][5].add(new VariableK(new VariableStart("S"), 1));
            setVTemp[0][5].add(new VariableK(new VariableStart("S"), 4));
            setVTemp[0][5].add(new VariableK(new Variable("C"), 1));
            setVTemp[0][5].add(new VariableK(new Variable("C"), 4));
            setVTemp[0][5].add(new VariableK(new Variable("A"), 1));

            setVTemp[1][1].add(new VariableK(new Variable("A"), 2));
            setVTemp[1][1].add(new VariableK(new Variable("B"), 2));
            setVTemp[1][2].add(new VariableK(new VariableStart("S"), 2));
            setVTemp[1][3].add(new VariableK(new VariableStart("S"), 2));
            setVTemp[1][3].add(new VariableK(new Variable("C"), 2));
            setVTemp[1][3].add(new VariableK(new Variable("A"), 2));
            setVTemp[1][4].add(new VariableK(new VariableStart("S"), 4));
            setVTemp[1][5].add(new VariableK(new Variable("C"), 4));
            setVTemp[1][5].add(new VariableK(new VariableStart("S"), 4));

            setVTemp[2][2].add(new VariableK(new Variable("A"), 3));
            setVTemp[2][3].add(new VariableK(new Variable("C"), 3));
            setVTemp[2][3].add(new VariableK(new VariableStart("S"), 3));
            setVTemp[2][4].add(new VariableK(new VariableStart("S"), 4));

            setVTemp[3][3].add(new VariableK(new Variable("C"), 4));
            setVTemp[3][4].add(new VariableK(new VariableStart("S"), 4));

            setVTemp[4][4].add(new VariableK(new Variable("A"), 5));
            setVTemp[4][4].add(new VariableK(new Variable("B"), 5));
            setVTemp[4][5].add(new VariableK(new Variable("C"), 5));
            setVTemp[4][5].add(new VariableK(new VariableStart("S"), 5));
            setVTemp[4][5].add(new VariableK(new Variable("A"), 5));

            setVTemp[5][5].add(new VariableK(new Variable("C"), 6));

            SS13_SET_VARK = new SetVarKMatrix(wordLength, SS13_EXAMPLE_WORD).setSetV(setVTemp);
            SS13_GRAMMAR_PYRAMID_WRAPPER.setPyramid(SS13_SET_VARK.getAsPyramid());
        }
    }
}
