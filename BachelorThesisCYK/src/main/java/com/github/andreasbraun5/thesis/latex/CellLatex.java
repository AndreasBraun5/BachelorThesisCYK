package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.exception.CellRuntimeException;
import com.github.andreasbraun5.thesis.pyramid.CellElement;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Andreas Braun on 04.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class CellLatex {
    // No more than 5 variables in one CellLatex are allowed.
    private List<CellElement> vars = new ArrayList<>();
    // center(name,y) coordinates will later on be used to position the variables in the cells using left, above, right,
    // below and center
    public double centerX = 0.0;
    public double centerY = 0.0;
    public double i = 0;
    public double j = 0;
    private String centerName = "";

    public CellLatex(int i, int j) {
        this.i = i;
        this.j = j;
        centerName = "center" + i + "" + j;
    }

    // A CellElement can be of type Variable, VariableStart and VariableK
    public <T extends CellElement> CellLatex addVar(Set<T> set) {
        this.vars.addAll(set);
        return this;
    }

    private String drawTheVars() {
        StringBuilder str = new StringBuilder();
        if (vars.size() > 5) {
            throw new CellRuntimeException("There are more than 5 Variables in the pyramid cells, coordinates: " +
                    i + ", " + j);
        }
        if (vars.get(0) instanceof VariableK) {
            ArrayList<VariableK> varK = new ArrayList<>(Util.filter(vars, VariableK.class));
            int k = 0;
            str.append("\\node [] at (" + centerName + ") {\\myfontvars{");
            for (int i = k; i < 3 && i < varK.size(); i++) {
                str.append(varK.get(i).getLhse() + "$_{" + varK.get(i).getK() + "}$");
            }
            str.append("}};\n");
            k = 3;
            if (vars.size() > k) {
                str.append("\\node [above] at (" + centerName + ") {\\myfontvars{" + varK.get(k)
                        .getLhse() + "$_{" + varK.get(k).getK() + "}$}};\n");
            }
            k = 4;
            if (vars.size() > k) {
                str.append("\\node [below] at (" + centerName + ") {\\myfontvars{" + varK.get(k)
                        .getLhse() + "$_{" + varK.get(k).getK() + "}$}};\n");
            }

        } else {
            int k = 0;
            str.append("\\node [] at (" + centerName + ") {\\myfontvars{~");
            for (int i = k; i < 3 && i < vars.size(); i++) {
                str.append("$" + vars.get(i).toString() + "$~");
            }
            str.append("}};\n");
            k = 3;
            if (vars.size() > k) {
                str.append("\\node [above] at (" + centerName + ") {\\myfontvars{" + vars.get(k)
                        + "$_{" + vars.get(k) + "}$}};\n");
            }
            k = 4;
            if (vars.size() > k) {
                str.append("\\node [below] at (" + centerName + ") {\\myfontvars{" + vars.get(k)
                        + "$_{" + vars.get(k) + "}$}};\n");
            }

        }
        return str.toString();
    }

    public String cellToTex() {
        StringBuilder str = new StringBuilder();
        if (vars.size() > 0) {
            str.append("%cells" + (int) i + "" + (int) j + "\n");
            str.append("\\coordinate (" + centerName + ") at (" + centerX + "," + centerY + ");\n");
            str.append("\\node [below=0.18cm] at (" + centerName + ") {\\myfontnumbering{$(" + (int) i + "" + (int) j + ")$}};\n");
            str.append(drawTheVars());
        } else {
            str.append("%cells" + (int) i + "" + (int) j + "\n");
            str.append("\\coordinate (" + centerName + ") at (" + centerX + "," + centerY + ");\n");
            str.append("\\node [below=0.18cm] at (" + centerName + ") {\\myfontnumbering{$(" + (int) i + "" + (int) j + ")$}};\n");
            str.append("");
        }
        return str.toString();
    }

    public List<CellElement> getCellElement() {
        return vars;
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
            stringBuilder.append(Util.padWithSpaces(ce.toString(), maxLen));
            stringBuilder.append("");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

