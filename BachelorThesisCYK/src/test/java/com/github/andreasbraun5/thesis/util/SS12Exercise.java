package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.latex.TreeLatex;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.VariableK;

import java.util.Set;

/**
 * Created by AndreasBraun on 02.07.2017.
 */
public final class SS12Exercise {

    private SS12Exercise() {
    }

    public static final Grammar SS12_GRAMMAR;
    public static final Word SS12_EXAMPLE_WORD = Word.fromStringCharwise("cbbaaccb");
    public static final SetVarKMatrix SS12_SET_VARK;
    public static final GrammarPyramidWrapper SS12_GRAMMAR_PYRAMID_WRAPPER = GrammarPyramidWrapper.builder().build();
    public static final TreeLatex SS12_TREE;

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
            grammar.addProductions(productions);
            SS12_GRAMMAR = grammar;
            SS12_GRAMMAR_PYRAMID_WRAPPER.setGrammar(grammar);
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
            SS12_GRAMMAR_PYRAMID_WRAPPER.setPyramid(SS12_SET_VARK.getAsPyramid());
        }

        {
            TreeLatex w1 = new TreeLatex("c");
            TreeLatex w2 = new TreeLatex("b");
            TreeLatex w3 = new TreeLatex("b");
            TreeLatex w4 = new TreeLatex("a");
            TreeLatex w5 = new TreeLatex("a");
            TreeLatex w6 = new TreeLatex("c");
            TreeLatex w7 = new TreeLatex("c");
            TreeLatex w8 = new TreeLatex("b");

            TreeLatex A1_1 = new TreeLatex("A1", w1);
            TreeLatex B2_1 = new TreeLatex("B2", w2);
            TreeLatex B3_1 = new TreeLatex("B3", w3);
            TreeLatex A4_1 = new TreeLatex("A4", w4);
            TreeLatex A5_1 = new TreeLatex("A5", w5);
            TreeLatex C6_1 = new TreeLatex("C6", w6);
            TreeLatex C7_1 = new TreeLatex("C7", w7);
            TreeLatex B8_1 = new TreeLatex("B8", w8);

            TreeLatex A3_2 = new TreeLatex("A3", B3_1, A4_1);
            TreeLatex C5_2 = new TreeLatex("C5", A5_1, C6_1);
            TreeLatex B7_2 = new TreeLatex("B7", C7_1, B8_1);

            TreeLatex A2_3 = new TreeLatex("A2", B2_1, A3_2);
            TreeLatex C4_4 = new TreeLatex("C4", A2_3, C5_2);
            TreeLatex B6_5 = new TreeLatex("B6", C4_4, B7_2);
            TreeLatex S1_6 = new TreeLatex("S1", A1_1, B6_5);
            SS12_TREE = S1_6;
        }
    }
}
