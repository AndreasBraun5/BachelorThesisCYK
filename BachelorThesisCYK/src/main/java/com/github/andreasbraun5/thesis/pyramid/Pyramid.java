package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.exception.CellRuntimeException;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by AndreasBraun on 21.06.2017.
 */
public class Pyramid {

    public final ArrayList<ArrayList<Cell>> cells;
    private Word word;

    // Initialising the pyramid with the given indexes.
    public Pyramid(int pyramidSize, Word word) {
        // Initialise the pyramid structure
        this.cells = new ArrayList<>(pyramidSize);
        {
            for (int i = 0; i < pyramidSize; i++) {
                this.cells.add(new ArrayList<>(pyramidSize - i));
            }
        }
        // Initialise the pyramid cells with its indexes.
        for (int i = 0; i < pyramidSize; i++) {
            for (int j = 0; j < pyramidSize - i; j++) {
                this.cells.get(i).add(new Cell(i, j));
            }
        }
        this.word = word;
    }

    // TODO Test this
    public Cell getUpperRight(Cell cell) {
        int cellUpperRightI = cell.getI() - 1;
        int cellUpperRightJ = cell.getJ() + 1;
        int pyramidSize = word.getWordLength();
        if (cellUpperRightI < 0 || cellUpperRightI > pyramidSize - 1) {
            throw new CellRuntimeException("Index i out of bounds.");
        }
        if (cellUpperRightJ < 0 || cellUpperRightJ > pyramidSize - cell.getI()) {
            throw new CellRuntimeException("Index j out of bounds.");
        }
        return this.cells.get(cellUpperRightI).get(cellUpperRightJ);
    }

    // TODO Test this
    public Cell getUpperLeft(Cell cell) {
        int cellUpperLeftI = cell.getI() - 1;
        int cellUpperLeftJ = cell.getJ();
        int pyramidSize = word.getWordLength();
        if (cellUpperLeftI < 0 || cellUpperLeftI > pyramidSize - 1) {
            throw new CellRuntimeException("Index i out of bounds.");
        }
        if (cellUpperLeftJ < 0 || cellUpperLeftJ > pyramidSize - cell.getI()) {
            throw new CellRuntimeException("Index j out of bounds.");
        }
        return this.cells.get(cellUpperLeftI).get(cellUpperLeftJ);
    }

    // TODO Test this
    public SetVarKMatrix getAsVarKMatrix() {
        int wordLength = getWord().getWordLength();
        SetVarKMatrix setVarKMatrix = new SetVarKMatrix(wordLength, word);
        Set<VariableK>[][] setV = Util.getInitialisedHashSetArray(wordLength, VariableK.class);
        int m = 0; // column index j of the pyramid
        for (int i = 0; i < wordLength; i++) { // index i of the upper triangular matrix from top to down
            int k = 0; // row index i of the pyramid
            for (int j = i; j < wordLength - i; j++) { // index j of the upper triangular matrix from left to right
                setV[i][j].addAll(this.getCell(k , m).getCellElements());
                k++;
            }
            m++;
        }
        setVarKMatrix.setSetV(setV);
        return setVarKMatrix;
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
                stringBuilder.append(Util.uniformStringMaker(cells.get(i).get(j).toString(), maxLen));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public Word getWord() {
        return word;
    }

    public ArrayList<ArrayList<Cell>> getCells() {
        return cells;
    }

    public Cell getCell(int i, int j) {
        return cells.get(i).get(j);
    }

}
