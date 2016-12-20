package com.company;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
class GrammarProperties {
    // Define absolute parameters here
    private String[] allPossibleVariables;
    private String[] allPossibleTerminals;
    private String[] possibleVariables;
    private String[] possibleTerminals;
    private int maxNumberOfVars; // limited by allPossibleVariables.length
    private int maxNumberOfVarsPerCell;
    private int maxSizeOfAlphabet; // limited by allPossibleTerminals.length
    private int maxSizeOfWord;
    private int sizeOfAlphabet; // if sizeOfAlphabet == maxSizeOfAlphabet then each terminal must
    // be used at least one time. Up till now only this case is supported.
    private int numberOfVars;
    private int sizeOfWord;

    public GrammarProperties(String[] allPossibleVariables, String[] allPossibleTerminals,
                             int maxNumberOfVars, int maxNumberOfVarsPerCell,
                             int maxSizeOfAlphabet, int maxSizeOfWord,
                             int sizeOfAlphabet,
                             int numberOfVars, int sizeOfWord){
        this.allPossibleVariables = allPossibleVariables;
        this.allPossibleTerminals = allPossibleTerminals;
        this.maxNumberOfVars = maxNumberOfVars;
        this.maxNumberOfVarsPerCell = maxNumberOfVarsPerCell;
        this.maxSizeOfAlphabet = maxSizeOfAlphabet;
        this.maxSizeOfWord = maxSizeOfWord;
        this.sizeOfAlphabet = sizeOfAlphabet;
        this.numberOfVars = numberOfVars;
        this.sizeOfWord = sizeOfWord;
        this.possibleVariables = new String[this.numberOfVars];
        System.arraycopy(this.allPossibleVariables, 0, this.possibleVariables,
                0, this.numberOfVars);
        this.possibleTerminals = new String[this.sizeOfAlphabet];
        System.arraycopy(this.allPossibleTerminals, 0, this.possibleTerminals,
                0, this.sizeOfAlphabet);

        testParameterConfiguration();
    }

    public void testParameterConfiguration() {
        assert maxNumberOfVars <= allPossibleVariables.length : "maxNumberOfVars is bigger than the allPossibleVariables.length";
        assert maxSizeOfAlphabet <=  allPossibleTerminals.length : "maxSizeOfAlphabet is bigger than the allPossibleTerminals.length";
        assert numberOfVars <= maxNumberOfVars : "numberOfVars is bigger than maxNumberOfVars";
        assert sizeOfWord <= maxSizeOfWord : "sizeOfWord is bigger than maxSizeOfWord";
        assert sizeOfAlphabet == maxSizeOfAlphabet : "sizeOfAlphabet is bigger than maxSizeOfAlphabet";
        assert numberOfVars <= sizeOfAlphabet && numberOfVars<= maxSizeOfAlphabet : "numberOfVars is bigger than sizeOfAlphabet";
        assert numberOfVars >= sizeOfAlphabet/2 && numberOfVars >= sizeOfAlphabet/2 : "numberOfVars is smaller than sizeOfAlphabet/2";

    }

    public String[] getPossibleTerminals() {
        return possibleTerminals;
    }

    public String[] getPossibleVariables() {
        return possibleVariables;
    }

    public int getNumberOfVars() {
        return numberOfVars;
    }

    public int getSizeOfAlphabet() {
        return sizeOfAlphabet;
    }

    public int getSizeOfWord() {
        return sizeOfWord;
    }

    public String[] getAllPossibleVariables() {
        return allPossibleVariables;
    }

    public String[] getAllPossibleTerminals() {
        return allPossibleTerminals;
    }

    public int getMaxNumberOfVars() {
        return maxNumberOfVars;
    }

    public int getMaxNumberOfVarsPerCell() {
        return maxNumberOfVarsPerCell;
    }

    public int getMaxSizeOfAlphabet() {
        return maxSizeOfAlphabet;
    }

    public int getMaxSizeOfWord() {
        return maxSizeOfWord;
    }
}
