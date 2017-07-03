package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.pyramid.VariableK;

import java.util.Set;

/**
 * Created by AndreasBraun on 02.07.2017.
 */
public final class SS12Exercise {

    private SS12Exercise() {
    }

    public static final Grammar SS12_GRAMMAR;
    public static final Word SS12_EXAMPLE_WORD = new Word("cbbaaccb");
    public static final SetVarKMatrix SS12_SET_VARK;

    static {
        {
            Grammar grammar = new Grammar(new VariableStart("S"));
            Production productions[] = new Production[9];
            productions[0] = new Production(new VariableStart("S"), new VariableCompound(new Variable("A"), new Variable("B")));
            productions[1] = new Production(new VariableStart("S"), new VariableCompound(new Variable("B"), new Variable("C")));
            productions[2] = new Production(new Variable("A"), new VariableCompound(new Variable("B"), new Variable("A")));
            productions[3] = new Production(new Variable("A"), new Terminal("a"));
            productions[4] = new Production(new Variable("A"), new Terminal("c"));
            productions[5] = new Production(new Variable("B"), new VariableCompound(new Variable("C"), new Variable("B")));
            productions[6] = new Production(new Variable("B"), new Terminal("b"));
            productions[7] = new Production(new Variable("C"), new VariableCompound(new Variable("A"), new Variable("C")));
            productions[8] = new Production(new Variable("C"), new Terminal("c"));
            grammar.addProduction(productions);
            SS12_GRAMMAR = grammar;
        }
        {
            int wordLength = SS12_EXAMPLE_WORD.getWordLength();
            Set<VariableK>[][] setVTemp = Util.getInitialisedHashSetArray(wordLength, VariableK.class);

            // reconstructing example matrix from SCRIPT_SET_VARK
            setVTemp[0][0].add(new VariableK(new Variable("A"), 1));
            setVTemp[0][0].add(new VariableK(new Variable("C"), 1));
            setVTemp[0][1].add(new VariableK(new VariableStart("S"), 1));
            setVTemp[0][1].add(new VariableK(new Variable("B"), 1));
            setVTemp[0][3].add(new VariableK(new Variable("A"), 2));
            setVTemp[0][5].add(new VariableK(new Variable("C"), 1));
            setVTemp[0][5].add(new VariableK(new Variable("C"), 4));
            setVTemp[0][5].add(new VariableK(new VariableStart("S"), 2));
            setVTemp[0][6].add(new VariableK(new Variable("C"), 1));
            setVTemp[0][6].add(new VariableK(new Variable("C"), 4));
            setVTemp[0][6].add(new VariableK(new VariableStart("S"), 2));
            setVTemp[0][7].add(new VariableK(new VariableStart("S"), 1));
            setVTemp[0][7].add(new VariableK(new VariableStart("S"), 4));
            setVTemp[0][7].add(new VariableK(new Variable("B"), 1));
            setVTemp[0][7].add(new VariableK(new Variable("B"), 6));
            setVTemp[0][7].add(new VariableK(new Variable("B"), 7));

            setVTemp[1][1].add(new VariableK(new Variable("B"), 2));
            setVTemp[1][3].add(new VariableK(new Variable("A"), 2));
            setVTemp[1][5].add(new VariableK(new Variable("C"), 4));
            setVTemp[1][5].add(new VariableK(new VariableStart("S"), 2));
            setVTemp[1][6].add(new VariableK(new Variable("C"), 4));
            setVTemp[1][6].add(new VariableK(new VariableStart("S"), 2));
            setVTemp[1][7].add(new VariableK(new Variable("B"), 6));
            setVTemp[1][7].add(new VariableK(new Variable("B"), 7));
            setVTemp[1][7].add(new VariableK(new VariableStart("S"), 4));

            setVTemp[2][2].add(new VariableK(new Variable("B"), 3));
            setVTemp[2][3].add(new VariableK(new Variable("A"), 3));
            setVTemp[2][5].add(new VariableK(new Variable("C"), 4));
            setVTemp[2][5].add(new VariableK(new VariableStart("S"), 3));
            setVTemp[2][6].add(new VariableK(new Variable("C"), 4));
            setVTemp[2][6].add(new VariableK(new VariableStart("S"), 3));
            setVTemp[2][7].add(new VariableK(new VariableStart("S"), 4));
            setVTemp[2][7].add(new VariableK(new Variable("B"), 6));
            setVTemp[2][7].add(new VariableK(new Variable("B"), 7));

            setVTemp[3][3].add(new VariableK(new Variable("A"), 4));
            setVTemp[3][5].add(new VariableK(new Variable("C"), 4));
            setVTemp[3][6].add(new VariableK(new Variable("C"), 4));
            setVTemp[3][7].add(new VariableK(new VariableStart("S"), 4));
            setVTemp[3][7].add(new VariableK(new Variable("B"), 6));
            setVTemp[3][7].add(new VariableK(new Variable("B"), 7));

            setVTemp[4][4].add(new VariableK(new Variable("A"), 5));
            setVTemp[4][5].add(new VariableK(new Variable("C"), 5));
            setVTemp[4][6].add(new VariableK(new Variable("C"), 5));
            setVTemp[4][7].add(new VariableK(new VariableStart("S"), 5));
            setVTemp[4][7].add(new VariableK(new Variable("B"), 6));
            setVTemp[4][7].add(new VariableK(new Variable("B"), 7));

            setVTemp[5][5].add(new VariableK(new Variable("A"), 6));
            setVTemp[5][5].add(new VariableK(new Variable("C"), 6));
            setVTemp[5][6].add(new VariableK(new Variable("C"), 6));
            setVTemp[5][7].add(new VariableK(new VariableStart("S"), 6));
            setVTemp[5][7].add(new VariableK(new Variable("B"), 6));
            setVTemp[5][7].add(new VariableK(new Variable("B"), 7));

            setVTemp[6][6].add(new VariableK(new Variable("A"), 7));
            setVTemp[6][6].add(new VariableK(new Variable("C"), 7));
            setVTemp[6][7].add(new VariableK(new Variable("B"), 7));
            setVTemp[6][7].add(new VariableK(new VariableStart("S"), 7));

            setVTemp[7][7].add(new VariableK(new Variable("B"), 8));

            SS12_SET_VARK = new SetVarKMatrix(wordLength, SS12_EXAMPLE_WORD).setSetV(setVTemp);
        }
    }
}
