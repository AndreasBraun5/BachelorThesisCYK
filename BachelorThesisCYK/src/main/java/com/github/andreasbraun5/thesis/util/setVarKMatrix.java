package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.latex.PyramidLatex;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andreas Braun on 14.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class SetVarKMatrix {

    private Set<VariableK>[][] setV;
    private String name = "setV";

    /**
     * The compiler is responsible for guaranteeing type safety. At runtime all generics are of type object (type
     * erasure). The static generic parameter S is different to the class generic parameter T.
     * Because of the static context of this method an object of type object is generated. At runtime there is no
     * chance to know of what actual type the static S is. An compiler hint "clazz" is needed to specify the type of
     * the static S. With this the compiler is able to guarantee the type safety between S and T.
     */
    public static SetVarKMatrix buildEmptySetVMatrixWrapper(int wordLength) {
        @SuppressWarnings("unchecked")
        Set<VariableK>[][] setVTemp = new Set[wordLength][wordLength];
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                setVTemp[i][j] = new HashSet<>();
            }
        }
        return new SetVarKMatrix().setSetV(setVTemp);
    }

    /**
     * Method to get the setV as a String for printing purposes.
     * The setV pyramid points downwards (reflection on the diagonal).
     */
    public String getStringToPrintAsLowerTriangularMatrix() {
        StringBuilder stringBuilder = new StringBuilder(name).append("\n");
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
    public String getStringToPrintAsLowerTriangularMatrixSimple() {
        StringBuilder stringBuilder = new StringBuilder(name).append("\n");
        int wordLength = setV.length;
        Set<Variable>[][] setVVariable = getSimpleSetDoubleArray();
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

    // TODO: think about again!! Test needed.
    public Pyramid getPyramid() {
        int wordLength = setV[1].length;
        Pyramid pyramid = new Pyramid(wordLength);
        int k = 0; // row index i of matrix
        int m = 0; // column index j of matrix
        // dif between index i of matrix and index j of matrix for the first row with index 0 of the pyramid is 0,
        // in the second row with index 1 of pyramid the dif is 1. [Only renaming, you could use index i of the pyramid]
        int dif = 0;
        for (int i = 0; i < wordLength; i++) { // index i of the pyramid, row wise from top to down
            for (int j = 0; j < wordLength - i; j++) { // index j of the pyramid, cell wise from left to right
                m = k + dif;
                pyramid.getCell().get(i).get(j).addVar(setV[k][m]);
                k++;
            }
            dif++; // increment the dif for each row
            k++;
        }
        // TODO: no word is set in the pyramid.
        return pyramid;
    }

    /**
     * Method to get the setV as a String for printing purposes.
     */
    public String getStringToPrintAsUpperTriangularMatrix() {
        StringBuilder stringBuilder = new StringBuilder(name).append("\n");
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
    public String getStringToPrintAsUpperTriangularMatrixSimple() {
        StringBuilder stringBuilder = new StringBuilder(name).append("\n");
        int wordLength = setV.length;
        Set<Variable>[][] setVVariable = getSimpleSetDoubleArray();
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

    public Set<VariableK>[][] getSetV() {
        return setV;
    }

    public SetVarKMatrix setSetV(Set<VariableK>[][] setV) {
        this.setV = setV;
        return this;
    }

    /**
     * Set<VariableK>[][] --> Set<Variable>[][]
     */
    public Set<Variable>[][] getSimpleSetDoubleArray() {
        int length = setV.length;
        Set<Variable>[][] setVVariable = Util.getInitialisedHashSetArray(length, Variable.class);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                setVVariable[i][j] = Util.varKSetToVarSet(setV[i][j]);
            }
        }
        return setVVariable;
    }


}
