package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.util.SS12Exercise;
import org.junit.Test;

/**
 * Created by AndreasBraun on 18.07.2017.
 */
public class TreeLatexTest {
    @Test
    public void constructTree() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("CellLatexTest: Generating LaTeX code for the cells.");
        TreeLatex tree = SS12Exercise.SS12_TREE;
        System.out.println(tree.toString());

    }

}