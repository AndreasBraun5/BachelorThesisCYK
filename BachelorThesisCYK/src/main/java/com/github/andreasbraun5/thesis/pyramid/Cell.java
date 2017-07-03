package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.grammar.LeftHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by AndreasBraun on 21.06.2017.
 */
public abstract class Cell <T extends CellElement> {

    private List<T> cellElements = new ArrayList<>();
    private int i = 0;
    private int j = 0;
    private String centerName = "";

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
        centerName = "cells" + i + "" + j;
    }

    public Cell addVar(T var) {
        this.cellElements.add(var);
        return this;
    }

    // A CellElement can be of type Variable, VariableStart and VariableK
    public Cell addVars(Set<T> set) {
        this.cellElements.addAll(set);
        return this;
    }

    public List<T> getCellElements() {
        return cellElements;
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
        for (T cell : cellElements) {
            maxLen = Math.max(maxLen, cell.toString().length());
        }
        stringBuilder.append("[");
        for (T cell : cellElements) {
            stringBuilder.append(Util.uniformStringMaker(cell.toString(), maxLen));
            stringBuilder.append("");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
