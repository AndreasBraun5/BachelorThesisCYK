package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.util.SS13Exercise;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;
import org.junit.Test;

import java.util.Set;

/**
 * Created by Andreas Braun on 08.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class CenteredTikzPictureTest {

    @Test
    public void toStringTest() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("CenteredTikzPictureTest: Generating LaTeX code for TikzPicture.");
        SetVarKMatrix setVarKMatrix = SS13Exercise.SS13_SET_VARK;
        System.out.print(setVarKMatrix.getStringToPrintAsLowerTriangularMatrix());
        Pyramid pyramid = setVarKMatrix.getAsPyramid();
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        CenteredTikzPicture tikz = new CenteredTikzPicture();
        System.out.println(tikz.beginToString());
        System.out.print(pyramidLatex.pyramidToTex());
        System.out.println(tikz.endToString());
    }


}