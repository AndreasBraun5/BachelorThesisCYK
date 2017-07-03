package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.grammarproperties.GrammarWordMatrixWrapper;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;
import org.junit.Test;

import java.util.Set;

/**
 * Created by Andreas Braun on 14.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class WriteToTexFileTest {

    @Test
    public void TexFileTest() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("WriteToTexFileTest: Generating latex code into the directory structure.");

        int wordLength = 6;

        Set<VariableK>[][] setVTemp = Util.getInitialisedHashSetArray(wordLength, VariableK.class);
        // reconstructing example matrix
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
        setVTemp[1][3].add(new VariableK(new Variable("C"), 2));
        setVTemp[1][3].add(new VariableK(new VariableStart("S"), 2));
        setVTemp[1][3].add(new VariableK(new Variable("A"), 2));
        setVTemp[1][4].add(new VariableK(new VariableStart("S"), 4));
        setVTemp[1][5].add(new VariableK(new Variable("C"), 4));
        setVTemp[1][5].add(new VariableK(new VariableStart("S"), 4));

        setVTemp[2][2].add(new VariableK(new Variable("A"), 3));
        setVTemp[2][3].add(new VariableK(new VariableStart("S"), 3));
        setVTemp[2][3].add(new VariableK(new Variable("C"), 3));
        setVTemp[2][4].add(new VariableK(new VariableStart("S"), 4));

        setVTemp[3][3].add(new VariableK(new Variable("C"), 4));
        setVTemp[3][4].add(new VariableK(new VariableStart("S"), 4));

        setVTemp[4][4].add(new VariableK(new Variable("A"), 5));
        setVTemp[4][4].add(new VariableK(new Variable("B"), 5));
        setVTemp[4][5].add(new VariableK(new Variable("C"), 5));
        setVTemp[4][5].add(new VariableK(new VariableStart("S"), 5));
        setVTemp[4][5].add(new VariableK(new Variable("A"), 5));

        setVTemp[5][5].add(new VariableK(new Variable("C"), 6));

        Word word = new Word("bbacbc");
        SetVarKMatrix setVarKMatrixSolution = new SetVarKMatrix(wordLength, word).setSetV(setVTemp);
        Pyramid pyramid = setVarKMatrixSolution.getAsPyramid();
        CenteredTikzPicture tikz = new CenteredTikzPicture();
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        WriteToTexFile.writeToMainTexFile("pyramidLatex");
        WriteToTexFile.writeToTexFile("pyramidLatex", tikz.beginToString() + pyramidLatex.pyramidToTex() + tikz.endToString());

    }

    @Test
    public void TexFileTest2() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("WriteToTexFileTest2: Generating latex code directory structure.");

        int wordLength = 8;

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

        Word word = new Word("cbcaaccb");
        SetVarKMatrix setVarKMatrixSolution = new SetVarKMatrix(wordLength, word).setSetV(setVTemp);
        Pyramid pyramid = setVarKMatrixSolution.getAsPyramid();
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        CenteredTikzPicture tikz = new CenteredTikzPicture();
        WriteToTexFile.writeToMainTexFile("pyramidLatex");
        WriteToTexFile.writeToTexFile("pyramidLatex", tikz.beginToString() + pyramidLatex.pyramidToTex() + tikz.endToString());

    }

    @Test
    public void TexFileTest3() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator CYK: algorithmAdvanced with input Grammar from the TI1 script");
        // @formatter:off
        Grammar grammar = new Grammar(new VariableStart("S"));
        Production productions[] = new Production[15];
        productions[0] = new Production(new VariableStart("S"), new VariableCompound(new Variable("N"), new Variable("B")));
        productions[1] = new Production(new VariableStart("S"), new VariableCompound(new Variable("E"), new Variable("A")));
        productions[2] = new Production(new VariableStart("S"), new Terminal(""));
        productions[3] = new Production(new Variable("S'"), new VariableCompound(new Variable("N"), new Variable("B")));
        productions[4] = new Production(new Variable("S'"), new VariableCompound(new Variable("E"), new Variable("A")));
        productions[5] = new Production(new Variable("N"), new Terminal("0"));
        productions[6] = new Production(new Variable("E"), new Terminal("1"));
        productions[7] = new Production(new Variable("A"), new Terminal("0"));
        productions[8] = new Production(new Variable("A"), new VariableCompound(new Variable("N"), new Variable("S'")));
        productions[9] = new Production(new Variable("A"), new VariableCompound(new Variable("E"), new Variable("C")));
        productions[10] = new Production(new Variable("B"), new Terminal("1"));
        productions[11] = new Production(new Variable("B"), new VariableCompound(new Variable("E"), new Variable("S'")));
        productions[12] = new Production(new Variable("B"), new VariableCompound(new Variable("N"), new Variable("D")));
        productions[13] = new Production(new Variable("C"), new VariableCompound(new Variable("A"), new Variable("A")));
        productions[14] = new Production(new Variable("D"), new VariableCompound(new Variable("B"), new Variable("B")));
        grammar.addProduction(productions);
        Word word = new Word("01110100");
        GrammarWordMatrixWrapper grammarWordMatrixWrapper = new GrammarWordMatrixWrapper().setGrammar(grammar);
        grammarWordMatrixWrapper.setWord(word);
        // @formatter:on
        SetVarKMatrix setVarKMatrixCalculated = CYK.calculateSetVAdvanced(grammarWordMatrixWrapper);
        System.out.println(setVarKMatrixCalculated.getStringToPrintAsLowerTriangularMatrix());
        int wordLength = word.getWordLength();
        Set<VariableK>[][] setVTemp = Util.getInitialisedHashSetArray(wordLength, VariableK.class);

        //reconstructing example matrix from scriptTI1
        setVTemp[0][0].add(new VariableK(new Variable("A"), 1));
        setVTemp[0][0].add(new VariableK(new Variable("N"), 1));
        setVTemp[0][1].add(new VariableK(new VariableStart("S"), 1));
        setVTemp[0][1].add(new VariableK(new Variable("S'"), 1));
        setVTemp[0][2].add(new VariableK(new Variable("B"), 1));
        setVTemp[0][3].add(new VariableK(new Variable("D"), 3));
        setVTemp[0][4].add(new VariableK(new Variable("B"), 1));
        setVTemp[0][5].add(new VariableK(new Variable("D"), 3));
        setVTemp[0][5].add(new VariableK(new Variable("D"), 5));
        setVTemp[0][6].add(new VariableK(new Variable("B"), 1));
        setVTemp[0][7].add(new VariableK(new VariableStart("S"), 1));
        setVTemp[0][7].add(new VariableK(new Variable("S'"), 1));

        setVTemp[1][1].add(new VariableK(new Variable("E"), 2));
        setVTemp[1][1].add(new VariableK(new Variable("B"), 2));
        setVTemp[1][2].add(new VariableK(new Variable("D"), 2));
        setVTemp[1][4].add(new VariableK(new Variable("D"), 2));
        setVTemp[1][6].add(new VariableK(new Variable("D"), 2));
        setVTemp[1][7].add(new VariableK(new Variable("B"), 2));

        setVTemp[2][2].add(new VariableK(new Variable("E"), 3));
        setVTemp[2][2].add(new VariableK(new Variable("B"), 3));
        setVTemp[2][3].add(new VariableK(new Variable("D"), 3));
        setVTemp[2][4].add(new VariableK(new Variable("B"), 3));
        setVTemp[2][5].add(new VariableK(new Variable("D"), 3));
        setVTemp[2][5].add(new VariableK(new Variable("D"), 5));
        setVTemp[2][6].add(new VariableK(new Variable("B"), 3));
        setVTemp[2][7].add(new VariableK(new VariableStart("S"), 3));
        setVTemp[2][7].add(new VariableK(new Variable("S'"), 3));

        setVTemp[3][3].add(new VariableK(new Variable("E"), 4));
        setVTemp[3][3].add(new VariableK(new Variable("B"), 4));
        setVTemp[3][4].add(new VariableK(new VariableStart("S"), 4));
        setVTemp[3][4].add(new VariableK(new Variable("S'"), 4));
        setVTemp[3][5].add(new VariableK(new Variable("B"), 4));
        setVTemp[3][6].add(new VariableK(new VariableStart("S"), 4));
        setVTemp[3][6].add(new VariableK(new Variable("S'"), 4));
        setVTemp[3][7].add(new VariableK(new Variable("A"), 4));

        setVTemp[4][4].add(new VariableK(new Variable("A"), 5));
        setVTemp[4][4].add(new VariableK(new Variable("N"), 5));
        setVTemp[4][5].add(new VariableK(new VariableStart("S"), 5));
        setVTemp[4][5].add(new VariableK(new Variable("S'"), 5));
        setVTemp[4][6].add(new VariableK(new Variable("A"), 5));
        setVTemp[4][7].add(new VariableK(new Variable("C"), 5));
        setVTemp[4][7].add(new VariableK(new Variable("C"), 7));

        setVTemp[5][5].add(new VariableK(new Variable("E"), 6));
        setVTemp[5][5].add(new VariableK(new Variable("B"), 6));
        setVTemp[5][6].add(new VariableK(new VariableStart("S"), 6));
        setVTemp[5][6].add(new VariableK(new Variable("S'"), 6));
        setVTemp[5][7].add(new VariableK(new Variable("A"), 6));

        setVTemp[6][6].add(new VariableK(new Variable("A"), 7));
        setVTemp[6][6].add(new VariableK(new Variable("N"), 7));
        setVTemp[6][7].add(new VariableK(new Variable("C"), 7));

        setVTemp[7][7].add(new VariableK(new Variable("A"), 8));
        setVTemp[7][7].add(new VariableK(new Variable("N"), 8));

        SetVarKMatrix setVarKMatrixSolution = new SetVarKMatrix(wordLength, word).setSetV(setVTemp);
        System.out.println(setVarKMatrixSolution.getStringToPrintAsUpperTriangularMatrix());
        System.out.println(setVarKMatrixSolution.getStringToPrintAsLowerTriangularMatrix());

        Pyramid pyramid = setVarKMatrixSolution.getAsPyramid();
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        System.out.println(pyramidLatex.toString());
        CenteredTikzPicture tikz = new CenteredTikzPicture();
        WriteToTexFile.writeToMainTexFile("scriptPyramid");
        WriteToTexFile.writeToTexFile("scriptPyramid", tikz.beginToString() + pyramidLatex.pyramidToTex() + tikz.endToString());

    }

}