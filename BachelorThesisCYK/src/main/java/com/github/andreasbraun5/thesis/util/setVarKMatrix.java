package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.pyramid.CellElement;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andreas Braun on 14.02.2017.
 * https://github.com/AndreasBraun5/
 */
@Getter
public class SetVarKMatrix {

    private Set<VariableK>[][] setV;
    private String name = "setVarK";
    private Word word;

    /**
     * The compiler is responsible for guaranteeing type safety. At runtime all generics are of type object (type
     * erasure). The static generic parameter S is different to the class generic parameter T.
     * Because of the static context of this method an object of type object is generated. At runtime there is no
     * chance to know of what actual type the static S is. An compiler hint "clazz" is needed to specify the type of
     * the static S. With this the compiler is able to guarantee the type safety between S and T.
     */

    public SetVarKMatrix(int wordLength, Word word) {
        @SuppressWarnings("unchecked")
        Set<CellElement>[][] setVTemp = new Set[wordLength][wordLength];
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                setVTemp[i][j] = new HashSet<>();
            }
        }
        this.word = word;
    }

    /**
     * Method to get the setV as a String for printing purposes.
     * The setV pyramid points downwards (reflection on the diagonal).
     */
    public static String getStringToPrintAsLowerTriangularMatrix(Set<VariableK>[][] setV) {
        StringBuilder stringBuilder = new StringBuilder().append("\n");
        int wordLength = setV.length;
        int maxLen = 0;
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                maxLen = Math.max(maxLen, setV[j][i].toString().length());
            }
        }
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                stringBuilder.append(Util.uniformStringMaker(setV[j][i].toString(), maxLen));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();

    }

    /**
     * Method to get the setV as a String for printing purposes.
     * The setV pyramid points downwards (reflection on the diagonal).
     */
    public String getStringToPrintAsLowerTriangularMatrixSimple(Set<VariableK>[][] setV) {
        StringBuilder stringBuilder = new StringBuilder().append("\n");
        int wordLength = setV.length;
        Set<Variable>[][] setVVariable = Util.getSimpleSetDoubleArray(setV);
        int maxLen = 0;
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                maxLen = Math.max(maxLen, setVVariable[j][i].toString().length());
            }
        }
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                stringBuilder.append(Util.uniformStringMaker(setVVariable[j][i].toString(), maxLen));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public Pyramid getAsPyramid() {
        return SetVarKMatrix.matrixToPyramid(setV, word);
    }

    public static Pyramid matrixToPyramid(Set<VariableK>[][] setV, Word word) {
        return new Pyramid(setV, word);
    }

    /**
     * Method to get the setV as a String for printing purposes.
     */
    public static String getStringToPrintAsUpperTriangularMatrix(Set<VariableK>[][] setV) {
        StringBuilder stringBuilder = new StringBuilder().append("\n");
        int wordLength = setV.length;
        int maxLen = 0;
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                maxLen = Math.max(maxLen, setV[i][j].toString().length());
            }
        }
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                stringBuilder.append(Util.uniformStringMaker(setV[i][j].toString(), maxLen));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Method to get the setV as a String for printing purposes.
     */
    public static String getStringToPrintAsUpperTriangularMatrixSimple(Set<VariableK>[][] setV) {
        StringBuilder stringBuilder = new StringBuilder().append("\n");
        int wordLength = setV.length;
        Set<Variable>[][] setVVariable = Util.getSimpleSetDoubleArray(setV);
        int maxLen = 0;
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                maxLen = Math.max(maxLen, setVVariable[i][j].toString().length());
            }
        }
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                stringBuilder.append(Util.uniformStringMaker(setVVariable[i][j].toString(), maxLen));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public SetVarKMatrix setSetV(Set<VariableK>[][] setV) {
        this.setV = setV;
        return this;
    }


}
