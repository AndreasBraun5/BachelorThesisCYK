package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by AndreasBraun on 21.06.2017.
 */
public class Pyramid {

    // cells is also an upper triangular matrix internally
    private CellK[][] cells;
    private final Word word;

    public Pyramid(Word word){
        this.word = word;
    }

    public Pyramid(Set<VariableK>[][] origMatrix, Word word) {
        this.setCells(origMatrix);
        this.word = word;
    }

    // Initialising the pyramid with the given indexes.
    public Pyramid(SetVarKMatrix setVarKMatrix) {
        this(setVarKMatrix.getSetV(), setVarKMatrix.getWord());
    }

    public void setCells(Set<VariableK>[][] origMatrix){
        int length = origMatrix[0].length;
        // pyramid has now length times many rows
        this.cells = new CellK[length][];
        { // Now the zero row has length times many cells, the 1st row has length-1 times many cells, ...
            int j = length;
            for (int i = 0; i < length; i++) {
                this.cells[i] = new CellK[j];
                j--;
            }
        }
        {
            for (int i = 0; i < origMatrix.length; ++i) {
                for (int j = i; j < origMatrix[i].length; ++j) {
                    Tuple<Integer, Integer> pyramidCoordinates = toPyramidCoordinates(i, j);
                    CellK cell = new CellK(pyramidCoordinates.x, pyramidCoordinates.y);
                    origMatrix[i][j].forEach(cell::addVar);
                    cells[pyramidCoordinates.x][pyramidCoordinates.y] = cell;
                }
            }
        }
    }

    public static Tuple<Integer, Integer> toPyramidCoordinates(int i, int j) {
        return Tuple.of(j - i, i);
    }

    public static Tuple<Integer, Integer> toPyramidCoordinates(Tuple<Integer, Integer> tuple) {
        return toPyramidCoordinates(tuple.x, tuple.y);
    }

    /**
     * @return There are no duplicates. A1, A2 becomes only A.
     */
    public CellSimple[][] getCellsSimple() {
        int length = cells.length;
        CellSimple[][] setVVariable = new CellSimple[length][];
        { // Now the zero row has length times many cells, the 1st row has length-1 times many cells, ...
            int j = length;
            for (int i = 0; i < length; i++) {
                setVVariable[i] = new CellSimple[j];
                j--;
            }
        }
        {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    setVVariable[i][j] = new CellSimple(i, j);
                    setVVariable[i][j].addVars(cells[i][j].getCellSimple().getCellElements());
                }
            }
        }
        return setVVariable;
    }

    @Override
    public String toString() {
        return printPyramid(this.cells);
    }

    public static String printPyramid(Cell[][] cells) {
        StringBuilder stringBuilder = new StringBuilder();
        int pyramidSize = cells.length;
        int maxLen = 0;
        {
            for (int i = 0; i < pyramidSize; i++) {
                for (int j = 0; j < pyramidSize - i; j++) {
                    maxLen = Math.max(maxLen, cells[i][j].toString().length());
                }
            }
        }
        {
            if (maxLen % 2 == 1) {
                maxLen = maxLen + 1;
            }
        }
        {
            for (int i = 0; i < pyramidSize; i++) {
                int emptySpace = (int) (i / 2.0) * maxLen;
                for (int x = 0; x < emptySpace; ++x) {
                    stringBuilder.append(" ");
                }
                for (int j = 0; j < pyramidSize - i; j++) {
                    stringBuilder.append(Util.uniformStringMaker(cells[i][j].toString(), maxLen));
                }
                for (int x = 0; x < emptySpace; ++x) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public Word  getWord() {
        return this.word;
    }

    public int getSize() {
        return this.cells.length;
    }

    public CellK getCellK(int i, int j) {
        return this.cells[i][j];
    }

    public CellK[][] getCellsK() {
        return cells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pyramid pyramid = (Pyramid) o;

        if (!Arrays.deepEquals(cells, pyramid.cells)) return false;
        return word != null ? word.equals(pyramid.word) : pyramid.word == null;
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(cells);
        result = 31 * result + (word != null ? word.hashCode() : 0);
        return result;
    }


}
