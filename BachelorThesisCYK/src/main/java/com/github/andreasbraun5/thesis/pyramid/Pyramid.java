package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.Set;

/**
 * Created by AndreasBraun on 21.06.2017.
 */
public class Pyramid {

    private final CellK[][] cells;
    private final Word word;

    public Pyramid(Set<VariableK>[][] orig, Word word) {
        this.cells = new CellK[orig.length][orig[0].length];
        for (int i = 0; i < orig.length; ++i) {
            for (int j = i; j < orig[i].length; ++j) {
                Tuple<Integer, Integer> pyramidCoordinates = toPyramidCoordinates(i, j);
                CellK cell = new CellK(pyramidCoordinates.x, pyramidCoordinates.y);
                orig[i][j].forEach(cell::addVar);
                cells[pyramidCoordinates.x][pyramidCoordinates.y] = cell;
            }
        }
        this.word = word;
    }

    // Initialising the pyramid with the given indexes.
    public Pyramid(SetVarKMatrix setVarKMatrix) {
        this(setVarKMatrix.getSetV(), setVarKMatrix.getWord());
    }

    public static Tuple<Integer, Integer> toPyramidCoordinates(int i, int j) {
        return Tuple.of(j - i, i);
    }

    public static Tuple<Integer, Integer> toPyramidCoordinates(Tuple<Integer, Integer> tuple) {
        return toPyramidCoordinates(tuple.x, tuple.y);
    }

    // TODO KILL THIS!
    public SetVarKMatrix getAsVarKMatrix() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return remember that there are no duplicates. A1, A2 becomes only A
     */
    public CellSimple[][] getCellsSimple() {
        int length = cells.length;
        CellSimple[][] setVVariable = new CellSimple[length][];
        for (int i = 0; i < length; ++i) {
            setVVariable = new CellSimple[i][cells[i].length];
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                setVVariable[i][j] = cells[i][j].getCellSimple();
            }
        }
        return setVVariable;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int pyramidSize = this.cells.length;
        int maxLen = 0;
        for (int i = 0; i < pyramidSize; i++) {
            for (int j = 0; j < pyramidSize - i; j++) {
                maxLen = Math.max(maxLen, this.getCellK(i, j).toString().length());
            }
        }
        if (maxLen % 2 == 1) {
            maxLen = maxLen + 1;
        }
        for (int i = 0; i < pyramidSize; i++) {
            int emptySpace = (int) (i / 2.0) * maxLen;
            for (int x = 0; x < emptySpace; ++x) {
                stringBuilder.append(" ");
            }
            for (int j = 0; j < pyramidSize - i; j++) {
                stringBuilder.append(Util.uniformStringMaker(this.getCellK(i, j).toString(), maxLen));
            }
            for (int x = 0; x < emptySpace; ++x) {
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public Word getWord() {
        return this.word;
    }

    public int getSize() {
        return this.cells.length;
    }

    public Cell getCellK(int i, int j) {
        return this.cells[i][j];
    }

    public CellK[][] getCellsK() {
        return cells;
    }
}
