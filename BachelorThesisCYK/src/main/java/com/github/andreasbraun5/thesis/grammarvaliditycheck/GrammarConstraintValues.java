package com.github.andreasbraun5.thesis.grammarvaliditycheck;

/**
 * Created by AndreasBraun on 02.07.2017.
 */
// TODO: Delete
public class GrammarConstraintValues {

    private Integer minRightCellCombinationsForced = 1; // optional, default = 1
    private Integer maxSumOfProductions = 10; // optional, default = 10
    private Integer maxSumOfVarsInPyramid = 100; // optional, default = 50

    private Integer sizeOfWord = 10; // optional, default = 3
    private Integer maxNumberOfVarsPerCell = 3; // optional, default = 3

    public Integer getMaxSumOfProductions() {
        return maxSumOfProductions;
    }

    public void setMaxSumOfProductions(Integer maxSumOfProductions) {
        this.maxSumOfProductions = maxSumOfProductions;
    }

    public Integer getMaxSumOfVarsInPyramid() {
        return maxSumOfVarsInPyramid;
    }

    public void setMaxSumOfVarsInPyramid(Integer maxSumOfVarsInPyramid) {
        this.maxSumOfVarsInPyramid = maxSumOfVarsInPyramid;
    }

    public Integer getSizeOfWord() {
        return sizeOfWord;
    }

    public void setSizeOfWord(Integer sizeOfWord) {
        this.sizeOfWord = sizeOfWord;
    }

    public Integer getMaxNumberOfVarsPerCell() {
        return maxNumberOfVarsPerCell;
    }

    public void setMaxNumberOfVarsPerCell(Integer maxNumberOfVarsPerCell) {
        this.maxNumberOfVarsPerCell = maxNumberOfVarsPerCell;
    }

    public Integer getMinRightCellCombinationsForced() {

        return minRightCellCombinationsForced;
    }

    public void setMinRightCellCombinationsForced(Integer minRightCellCombinationsForced) {
        this.minRightCellCombinationsForced = minRightCellCombinationsForced;
    }
}
