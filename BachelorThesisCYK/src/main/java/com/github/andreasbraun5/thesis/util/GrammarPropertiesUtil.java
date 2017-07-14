package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by AndreasBraun on 13.07.2017.
 */
public final class GrammarPropertiesUtil {

    private GrammarPropertiesUtil() {
        //can't touch this!
    }

    public static GrammarProperties describeGrammar(Grammar grammar) {
        GrammarProperties grammarProperties = new GrammarProperties(grammar.getVariableStart());
        Set<Variable> variables =
                grammar.getProductionsAsList().stream().map(Production::getLeftHandSideElement).collect(Collectors.toSet());
        Set<Terminal> terminals = grammar.getProductionsAsList().stream().map(Production::getRightHandSideElement)
                .filter( rhse -> rhse instanceof Terminal ).map(rhse -> (Terminal) rhse).collect(Collectors.toSet());
        return grammarProperties.addTerminals(terminals).addVariables(variables);
    }

}
