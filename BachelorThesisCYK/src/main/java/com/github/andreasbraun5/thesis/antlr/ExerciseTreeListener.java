package com.github.andreasbraun5.thesis.antlr;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.util.Word;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by AndreasBraun on 20.07.2017.
 */
public class ExerciseTreeListener extends ExerciseBaseListener {

    @Getter
    private VariableStart varStart;
    @Getter
    private Set<Production> productions = new HashSet<>();
    @Getter
    private Word word;

    @Override
    public void exitWordDefinition(ExerciseParser.WordDefinitionContext ctx) {
        List<Terminal> terminalList = new ArrayList<>();

        ExerciseParser.TerminalsContext terminals = ctx.terminals();
        do {
            ExerciseParser.TerminalContext terminal = terminals.terminal();
            terminalList.add(Terminal.of(terminal.getText()));
        } while((terminals = terminals.terminals()) != null);

        this.word = new Word(terminalList);
    }

    @Override
    public void exitVarStart(ExerciseParser.VarStartContext ctx) {
        String variableName = ctx.nonTerminal().getText();
        varStart = new VariableStart(variableName);
    }

    @Override
    public void exitSingleRule(ExerciseParser.SingleRuleContext ctx) {
        String leftSideStr = ctx.nonTerminal(0).getText();
        Variable leftSide;
        if (leftSideStr.equals(varStart.getName())) {
            leftSide = varStart;
        } else {
            leftSide = Variable.of(leftSideStr);
        }
        if (ctx.terminal() != null && !ctx.terminal().getText().equals("")) {
            RightHandSideElement rhse;
            if(ctx.terminal().getText().equals("eps")) {
                rhse = new Epsilon();
            } else {
                rhse = Terminal.of(ctx.terminal().getText());
            }
            productions.add(Production.of(leftSide, rhse));
        } else {
            Variable fst = Variable.of(ctx.nonTerminal(1).getText());
            Variable snd = Variable.of(ctx.nonTerminal(2).getText());
            productions.add(Production.of(leftSide, VariableCompound.of(fst, snd)));
        }
    }

}
