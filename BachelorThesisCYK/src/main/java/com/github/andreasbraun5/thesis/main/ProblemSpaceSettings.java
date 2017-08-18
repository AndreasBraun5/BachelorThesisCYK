package com.github.andreasbraun5.thesis.main;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProblemSpaceSettings {

    private int countVariables;
    private int countTerminals;
    private int sizeOfWord;

    private int maxSumOfProductions;
    private int minRightCellCombinationsForced;
    private int maxVarsPerCell;
    private int varsInPyramid;

    private int minCompoundAddTo;
    private int maxCompoundAddTo;
    private int minTerminalAddTo;
    private int maxTerminalAddTo;

    // True then use root not empty criteria, false then use more than half of cells in pyramid not empty.
    private boolean useRootNotEmpty;

}
