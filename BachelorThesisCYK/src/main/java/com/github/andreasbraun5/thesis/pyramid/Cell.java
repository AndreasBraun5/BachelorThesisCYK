package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.util.Util;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by AndreasBraun on 21.06.2017.
 */
public abstract class Cell <T extends CellElement> {

    private List<T> cellElements = new ArrayList<>();
    private int i = 0;
    private int j = 0;

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public Cell addVar(T var) {
        this.cellElements.add(var);
        return this;
    }

    // A CellElement can be of type Variable, VariableStart and VariableK
    public Cell addVars(Collection<T> set) {
        this.cellElements.addAll(set);
        return this;
    }

    public Cell addVars(List<T> list){
        this.cellElements.addAll(list);
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
        int comma = 1;
        for (T cell : cellElements) {
            stringBuilder.append(Util.padWithSpaces(cell.toString(), maxLen));
            if (comma < cellElements.size()) {
                stringBuilder.append(",");
                comma++;
            }
            stringBuilder.append("");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell<?> cell = (Cell<?>) o;

        if (i != cell.i) return false;
        if (j != cell.j) return false;
        return cellElements != null ? cellElements.equals(cell.cellElements) : cell.cellElements == null;
    }

    @Override
    public int hashCode() {
        int result = cellElements != null ? cellElements.hashCode() : 0;
        result = 31 * result + i;
        result = 31 * result + j;
        return result;
    }

}
