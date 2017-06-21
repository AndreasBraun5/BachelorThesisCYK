package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.exception.CellRuntimeException;
import com.github.andreasbraun5.thesis.util.Util;

import java.util.ArrayList;

/**
 * Created by AndreasBraun on 21.06.2017.
 */
public class Pyramid {

    public final ArrayList<ArrayList<Cell>> cell;
    private String[] word;

    // Initialising the pyramid with the given indexes.
    public Pyramid(int pyramidSize) {
        // Initialise the pyramid structure
        this.cell = new ArrayList<>(pyramidSize);
        {
            for (int i = 0; i < pyramidSize; i++) {
                this.cell.add(new ArrayList<>(pyramidSize - i));
            }
        }
        // Initialise the pyramid cells with its indexes.
        for (int i = 0; i < pyramidSize; i++) {
            for (int j = 0; j < pyramidSize - i; j++) {
                this.cell.get(i).add(new Cell(i, j));
            }
        }
    }

    public Cell getUpperRight(Cell cell) {
        int cellUpperRightI = cell.getI() - 1;
        int cellUpperRightJ = cell.getJ() + 1;
        int pyramidSize = word.length;
        if (cellUpperRightI < 0 || cellUpperRightI > pyramidSize - 1) {
            throw new CellRuntimeException("Index i out of bounds.");
        }
        if (cellUpperRightJ < 0 || cellUpperRightJ > pyramidSize - cell.getI()) {
            throw new CellRuntimeException("Index j out of bounds.");
        }
        return this.cell.get(cellUpperRightI).get(cellUpperRightJ);
    }

    public Cell getUpperLeft(Cell cell) {
        int cellUpperLeftI = cell.getI() - 1;
        int cellUpperLeftJ = cell.getJ();
        int pyramidSize = word.length;
        if (cellUpperLeftI < 0 || cellUpperLeftI > pyramidSize - 1) {
            throw new CellRuntimeException("Index i out of bounds.");
        }
        if (cellUpperLeftJ < 0 || cellUpperLeftJ > pyramidSize - cell.getI()) {
            throw new CellRuntimeException("Index j out of bounds.");
        }
        return this.cell.get(cellUpperLeftI).get(cellUpperLeftJ);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int pyramidSize = cell.size();
        int maxLen = 0;
        for (int i = 0; i < pyramidSize; i++) {
            for (int j = 0; j < pyramidSize - i; j++) {
                maxLen = Math.max(maxLen, cell.get(i).get(j).toString().length());
            }
        }
        for (int i = 0; i < pyramidSize; i++) {
            for (int j = 0; j < pyramidSize - i; j++) {
                stringBuilder.append(Util.uniformStringMaker(cell.get(i).get(j).toString(), maxLen));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String[] getWord() {
        return word;
    }

    public ArrayList<ArrayList<Cell>> getCell() {
        return cell;
    }

    public void setWord(String[] word) {
        this.word = word;
    }

    public void setWord(String word) {
        StringBuilder[] stringBuilder = new StringBuilder[word.length()];
        String[] str = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            stringBuilder[i] = new StringBuilder();
            stringBuilder[i].append(word.charAt(i));
        }
        for (int i = 0; i < word.length(); i++) {
            str[i] = stringBuilder[i].toString();
        }
        this.word = str;
    }

}
