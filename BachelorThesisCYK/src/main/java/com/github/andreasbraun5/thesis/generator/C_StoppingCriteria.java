package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;

/**
 * Created by AndreasBraun on 12.07.2017.
 */
public class C_StoppingCriteria {

    public static boolean stoppingCriteriaMet(GrammarPyramidWrapper grammarPyramidWrapper) {
        boolean isMet;
        int countCells = 0;
        int coundCellsNotEmpty = 0;
        CellK[][] cells = grammarPyramidWrapper.getPyramid().getCellsK();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                countCells++;
                if (cells[i][j].getCellElements().size() > 0) {
                    coundCellsNotEmpty++;
                }
            }
        }
        isMet = ((double) coundCellsNotEmpty / countCells) >= 0.5;
        return isMet;
    }

}
