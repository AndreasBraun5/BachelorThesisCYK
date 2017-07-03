package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.grammar.Variable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by AndreasBraun on 03.07.2017.
 */
public class CellK extends Cell<VariableK> {

    public CellK(int i, int j) {
        super(i, j);
    }

    /**
     * @return If there is A1, A2 it returns A only. There are no duplicates.
     */
    public CellSimple getCellSimple() {
        CellSimple cellSimple = new CellSimple(this.getI(), this.getJ());
        Set<Variable> variableSet = new HashSet<>();
        for (CellElement ce : getCellElements()) {
            variableSet.add(ce.getVariable());
        }
        cellSimple.addVars(variableSet);
        return cellSimple;
    }

}
