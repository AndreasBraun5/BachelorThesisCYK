package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;

/**
 * Created by AndreasBraun on 18.07.2017.
 */
public class ExerciseLatex {

    public GrammarLatex grammar;
    public PyramidLatex pyramid;
    public static final String preamble = "" +
            "\\documentclass[a4paper,12pt,headsepline]{scrartcl}\n" +
            "\\usepackage{tikz}\n" +
            "\\usepackage{fix-cm}\n" +
            "\\begin{document}\n";
    public static final String postamble = "\\end{document}\n";

    public ExerciseLatex(Grammar grammar, Pyramid pyramid) {
        this.grammar = new GrammarLatex(grammar);
        this.pyramid = new PyramidLatex(pyramid);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(preamble)
                .append(grammar.toString())
                .append(pyramid.asTikz())
                .append(postamble);
        return str.toString();
    }
}
