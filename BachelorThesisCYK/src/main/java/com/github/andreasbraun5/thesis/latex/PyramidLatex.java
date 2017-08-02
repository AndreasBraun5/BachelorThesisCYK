package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.exception.CellRuntimeException;
import com.github.andreasbraun5.thesis.pyramid.Cell;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.ArrayList;

/**
 * Created by Andreas Braun on 04.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class PyramidLatex {
    private ArrayList<ArrayList<CellLatex>> cells;
    private Word word;

    // Initialising the pyramid with the given indexes.
    public PyramidLatex(Pyramid pyramid) {
        if (!checkCountElementsPerCell(pyramid.getCellsK())) {
            throw new CellRuntimeException("There are less than 5 Vars,\n" +
                    "but more than 5 VarKs\nin one Cell.\nPlease choose another one\nor modify it.");
        }
        int pyramidSize = pyramid.getSize();
        this.word = pyramid.getWord();
        this.cells = new ArrayList<>(pyramidSize);
        {
            for (int i = 0; i < pyramidSize; i++) {
                this.cells.add(new ArrayList<>(pyramidSize - i));
            }
        }
        {// Cell00
            this.cells.get(0).add(new CellLatex(0, 0));
            this.cells.get(0).get(0).getCellElement().addAll(pyramid.getCellK(0, 0).getCellElements());
            this.cells.get(0).get(0).centerX = 0.5; // starting shift of cell_00
            this.cells.get(0).get(0).centerY = 0;
        }

        double tempCenterY = 0.0;
        for (int i = 0; i < pyramidSize; i++) {
            for (int j = 0; j < pyramidSize - i; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                this.cells.get(i).add(new CellLatex(i, j));
                if (j == 0) {
                    this.cells.get(i).get(j).centerX = this.cells.get(i - 1).get(j).centerX + 0.5;
                    this.cells.get(i).get(j).centerY = tempCenterY;
                    this.cells.get(i).get(j).getCellElement().addAll(pyramid.getCellK(i, j).getCellElements());
                    continue;
                }
                this.cells.get(i).get(j).centerX = this.cells.get(i).get(j - 1).centerX + 1.0;
                this.cells.get(i).get(j).centerY = tempCenterY;
                this.cells.get(i).get(j).getCellElement().addAll(pyramid.getCellK(i, j).getCellElements());
            }
            tempCenterY = tempCenterY - 0.5;
        }
    }

    public boolean checkCountElementsPerCell(Cell[][] cells) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].getCellElements().size() > 5) {
                    return false;
                }
            }
        }
        return true;
    }

    private String drawPyramidStructure() {
        StringBuilder str = new StringBuilder();
        int n = this.cells.size();
        str.append("\\newcommand{\\myfontvars}[1]{\n" +
                "\\fontsize{4.9}{12}\\selectfont{#1}\n" +
                "}");
        str.append("\\newcommand{\\myfontnumbering}[1]{\n" +
                "\\fontsize{2.5}{12}\\selectfont{#1}\n" +
                "}");
        // \coordinate (top) at (3,-3);
        str.append("%Outer hull\n%Tip of the pyramid\n\\coordinate (tip) at (" + n / 2 + "," + -n / 2 + ");\n");
        //	\foreach \i in {0,...,6} {
        //		\coordinate (\i) at (\i,0);
        //	}
        str.append("\\foreach \\i in {0,...," + n + "} {\n \t\\coordinate (\\i) at (\\i,0);\n}\n");
        //%Draw the left and right line of the pyramid pointing downwards
        // \draw (0) -- (tip) -- (6);
        str.append("%Draw the left and right line of the pyramid pointing downwards\n\\draw (0) -- (tip) -- (" + n + ");\n");
        // %Grid lines direction top-right to down-left
        // 	\coordinate (dli) at (i*0.5, -i*0.5);
        // 	\draw (dli) -- (i,0);
        str.append("%Grid lines direction down-left to top-right\n");
        {
            for (int i = 1; i < n; i++) {
                str.append("\\coordinate (dl" + i + ") at (" + (i * 0.5) + "," + (-i * 0.5) + ");\n");
            }
        }
        {
            for (int i = 1; i < n; i++) {
                str.append("\\draw (dl" + i + ") -- (" + i + ",0);\n");
            }
        }
        // %Grid lines direction down-right to top-left
        // 	\coordinate (dri) at (i*0.5,-i*0.5);
        // 	\draw (dri) -- (i,0);
        str.append("%Grid lines direction down-right to top-left\n");
        {
            for (int i = 1; i < n; i++) {
                str.append("\\coordinate (dr" + i + ") at (" + ((n / 2) + i * 0.5) + "," + (-n / 2 + i * 0.5) + ");\n");
            }
        }
        {
            for (int i = 1; i < n; i++) {
                str.append("\\draw (dr" + i + ") -- (" + i + ",0);\n");
            }
        }
        //	% Small lines at the top
        //	\draw (\i) -- (\i+());
        str.append("%Small lines at the top\n");
        {
            for (int i = 0; i <= n; i++) {
                str.append("\\coordinate (top" + i + ") at (" + (-0.5 + 0.5 + i) + ",0.0);\n");
            }
        }
        {
            for (int i = 0; i <= n; i++) {
                str.append("\\coordinate (topUpper" + i + ") at (" + (-0.5 + 0.5 + i) + ",0.6);\n");
            }
        }
        {
            for (int i = 0; i <= n; i++) {
                str.append("\\draw (top" + i + ") -- (topUpper" + i + ");\n");
            }
        }
        //	% The string
        // 	\coordinate (wi) at (0.5 + i, 1);
        // 	\node [center] at (wi) {word[i]};
        str.append("%The string\n");
        {
            for (int i = 0; i < n; i++) {
                str.append("\\coordinate (w" + i + ") at (" + (0.5 + i) + ",0.6);\n");
            }
        }
        {
            for (int i = 0; i < n; i++) {
                str.append("\\node [] at (w" + i + ") {" + word.getTerminals().get(i).getTerminalName() + "};\n");
            }
        }
        return str.toString();
    }

    public String toTex() {
        StringBuilder str = new StringBuilder();
        str.append(drawPyramidStructure());
        str.append("% Variables in the cells\n");
        for (int i = 0; i < cells.size(); i++) {
            for (int j = 0; j < cells.get(i).size(); j++) {
                str.append(cells.get(i).get(j).cellToTex());
            }
        }
        return str.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int pyramidSize = cells.size();
        int maxLen = 0;
        for (int i = 0; i < pyramidSize; i++) {
            for (int j = 0; j < pyramidSize - i; j++) {
                maxLen = Math.max(maxLen, cells.get(i).get(j).toString().length());
            }
        }
        for (int i = 0; i < pyramidSize; i++) {
            for (int j = 0; j < pyramidSize - i; j++) {
                stringBuilder.append(Util.padWithSpaces(cells.get(i).get(j).toString(), maxLen));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static String begin() {
        StringBuilder str = new StringBuilder("");
        str.append("\\begin{center}\n")
                .append("\\resizebox{\\linewidth}{!}{\n")
                .append("\\begin{tikzpicture}[baseline]\n");
        return str.toString();
    }

    public static String end() {
        StringBuilder str = new StringBuilder("");
        str.append("\\end{tikzpicture}\n")
                .append("}\n")
                .append("\\end{center}\n");
        return str.toString();
    }

    public String toStringTex() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(begin());
        stringBuilder.append(toTex());
        stringBuilder.append(end());
        return stringBuilder.toString();
    }
}
