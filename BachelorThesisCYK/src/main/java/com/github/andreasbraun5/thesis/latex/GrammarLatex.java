package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.Variable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by AndreasBraun on 18.07.2017.
 */
public class GrammarLatex {

    private final Map<Variable, Set<Production>> productionsMap;

    public GrammarLatex(Grammar grammar) {
        this.productionsMap = grammar.getProductionsMap();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\\begin{center} \n\\begin{tabular}{l}");
        productionsMap.forEach((variable, productions) -> {
            str.append("$").append(variable.getLhse()).append("\\rightarrow ");
            productionsMap.get(variable).forEach(production -> {
                str.append(production.getRightHandSideElement()).append("~|~");
            });
            str.deleteCharAt(str.length()-2);
            str.append("$\\\\ \n");
        });
        str.append("\\end{tabular} \n\\end{center}\n");
        return str.toString();
    }
}
