package com.github.andreasbraun5.thesis.antlr;

import com.github.andreasbraun5.thesis.grammar.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by AndreasBraun on 20.07.2017.
 */
public class GrammarTreeListener extends GrammarBaseListener {

    @Getter
    private VariableStart varStart;
    @Getter
    private Set<Production> productions = new HashSet<>();

    @Override
    public void exitVarStart(GrammarParser.VarStartContext ctx) {
        String variableName = ctx.nonTerminal().getText();
        varStart = new VariableStart(variableName);
    }

    @Override
    public void exitSingleRule(GrammarParser.SingleRuleContext ctx) {
        String leftSideStr = ctx.nonTerminal(0).getText();
        Variable leftSide;
        if (leftSideStr.equals(varStart.getName())) {
            leftSide = varStart;
        } else {
            leftSide = Variable.of(leftSideStr);
        }
        if (ctx.terminal() != null && !ctx.terminal().getText().equals("")) {
            productions.add(Production.of(leftSide, Terminal.of(ctx.terminal().getText())));
        } else {
            Variable fst = Variable.of(ctx.nonTerminal(1).getText());
            Variable snd = Variable.of(ctx.nonTerminal(2).getText());
            productions.add(Production.of(leftSide, VariableCompound.of(fst, snd)));
        }
    }

}
