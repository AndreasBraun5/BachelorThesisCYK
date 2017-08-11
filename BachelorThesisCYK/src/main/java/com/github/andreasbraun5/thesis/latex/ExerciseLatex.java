package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;

/**
 * Created by AndreasBraun on 18.07.2017.
 */
public class ExerciseLatex {

    private GrammarLatex grammar;
    private PyramidLatex pyramid;
    private TreeLatex tree;
    private static final String preamble = "" +
            "\\documentclass[a4paper,12pt,headsepline]{scrartcl}\n" +
            "\\usepackage{tikz}\n" +
            "\\usepackage{tikz-qtree}\n" +
            "\\usepackage{fix-cm}\n" +
            "\\begin{document}\n";
    private static final String postamble = "\\end{document}\n";

    public ExerciseLatex(Grammar grammar, Pyramid pyramid) {
        this.grammar = new GrammarLatex(grammar);
        this.pyramid = new PyramidLatex(pyramid);
        this.tree = TreeLatex.generateRandomTree(pyramid, pyramid.getCellK(pyramid.getCellsK().length - 1, 0));
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(preamble)
                .append(grammar)
                .append(pyramid.toStringTex())
                .append(tree)
                .append(postamble);
        return str.toString();
    }
}
