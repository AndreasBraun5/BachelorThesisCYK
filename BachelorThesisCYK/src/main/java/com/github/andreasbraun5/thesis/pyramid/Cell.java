package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by AndreasBraun on 21.06.2017.
 */
public class Cell {

    // A CellElement can be of type Variable, VariableStart and VariableK
    private List<VariableK> cellElements = new ArrayList<>();
    private int i = 0;
    private int j = 0;
    private String centerName = "";

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
        centerName = "cells" + i + "" + j;
    }

    // A CellElement can be of type Variable, VariableStart and VariableK
    public Cell addVar(Set<VariableK> set) {
        this.cellElements.addAll(set);
        return this;
    }

    public List<VariableK> getCellElements() {
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
        for (CellElement ce : cellElements) {
            maxLen = Math.max(maxLen, ce.toString().length());
        }
        stringBuilder.append("[");
        for (CellElement ce : cellElements) {
            stringBuilder.append(Util.uniformStringMaker(ce.toString(), maxLen));
            stringBuilder.append("");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

}
