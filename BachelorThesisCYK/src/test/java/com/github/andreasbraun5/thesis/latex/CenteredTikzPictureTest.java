package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.SS13Exercise;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import org.junit.Test;

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
        System.out.print(SetVarKMatrix.getStringToPrintAsLowerTriangularMatrix(setVarKMatrix.getSetV()));
        Pyramid pyramid = setVarKMatrix.getAsPyramid();
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        System.out.println(CenteredTikzPicture.beginToString());
        System.out.print(pyramidLatex.pyramidToTex());
        System.out.println(CenteredTikzPicture.endToString());
    }


}