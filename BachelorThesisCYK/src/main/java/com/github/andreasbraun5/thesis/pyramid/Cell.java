package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.exception.CellRuntimeException;
import com.github.andreasbraun5.thesis.latex.CellLatex;
import com.github.andreasbraun5.thesis.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by AndreasBraun on 21.06.2017.
 */
public class Cell {

    // A CellElement can be of type Variable, VariableStart and VariableK
    private List<CellElement> vars = new ArrayList<>();
    private int i = 0;
    private int j = 0;
    private String centerName = "";

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
        centerName = "cell" + i + "" + j;
    }

    // A CellElement can be of type Variable, VariableStart and VariableK
    public <T extends CellElement> Cell addVar(Set<T> set) {
        this.vars.addAll(set);
        return this;
    }

    public List<CellElement> getCellElement() {
        return vars;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int maxLen = 0;
        for (CellElement ce : vars) {
            maxLen = Math.max(maxLen, ce.toString().length());
        }
        stringBuilder.append("[");
        for (CellElement ce : vars) {
            stringBuilder.append(Util.uniformStringMaker(ce.toString(), maxLen));
            stringBuilder.append("");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

}
