package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;

/**
 * Created by AndreasBraun on 12.07.2017.
 */
public class C_StoppingCriteria {

    /*
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
    }*/

    public static boolean stoppingCriteriaMet(GrammarPyramidWrapper grammarPyramidWrapper) {
        return stoppingCriteriaMetSplitThenFill(grammarPyramidWrapper);
    }

    /**
     * True if the root of the pyramid is not emtpy.
     */
    public static boolean stoppingCriteriaMetSplitThenFill(GrammarPyramidWrapper grammarPyramidWrapper) {
        Pyramid pyramid = grammarPyramidWrapper.getPyramid();
        int size = pyramid.getSize();
        return grammarPyramidWrapper.getPyramid().getCellK(size - 1, 0).getCellElements().size() != 0;
    }

}
