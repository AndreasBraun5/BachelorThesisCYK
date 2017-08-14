package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.grammar.Epsilon;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AndreasBraun on 18.07.2017.
 */
public class GrammarLatex {

    private final Map<Variable, List<Production>> productionsMap;

    public GrammarLatex(Grammar grammar) {
        Map<Variable, List<Production>> productionsMap1;
        Map<Variable, List<Production>> listMap = new HashMap<>();
        grammar.getProductionsMap().forEach((variable, productions) -> listMap.put(variable, new ArrayList<>(productions)));
        productionsMap1 = listMap;
        productionsMap = productionsMap1;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\\begin{center} \n\\begin{tabular}{l}\n");
        productionsMap.forEach((variable, productions) -> {
            str.append("$").append(variable.getLhse()).append("\\rightarrow ");
            productionsMap.get(variable).forEach(production -> {
                if (production.getRightHandSideElement() instanceof Epsilon) {
                    str.append("\\epsilon").append("~|~");
                } else {
                    str.append(production.getRightHandSideElement()).append("~|~");
                }
            });
            str.deleteCharAt(str.length() - 2);
            str.append("$\\\\ \n");
        });
        str.append("\\end{tabular} \n\\end{center}\n");
        return str.toString();
    }
}
