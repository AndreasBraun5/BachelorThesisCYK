package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.*;

/**
 * Created by AndreasBraun on 01.06.2017.
 */
public class GrammarGeneratorUtil {

    private static final Random random = new Random();

    /**
     * Dice roll for every rhse how often it is added and to which vars in the grammar it is added.
     * Equals the Distribute standard method.
     * variablesWeighted: allows favouritism of specific variables.
     */
    static GrammarPyramidWrapper distributeDiceRollRightHandSideElements(
            GrammarPyramidWrapper grammarPyramidWrapper,
            List<? extends RightHandSideElement> rightHandSideElements,
            int minCountElementDistributedTo,
            int maxCountElementDistributedTo,
            List<Variable> variablesWeighted) {
        Grammar grammar = grammarPyramidWrapper.getGrammar();
        for (RightHandSideElement tempRhse : rightHandSideElements) {
            // countOfLeftSideRhseWillBeAdded is element of the interval [minCountElementDistributedTo, maxCountElementDistributedTo]
            int countOfLeftSideRhseWillBeAdded = random.nextInt(maxCountElementDistributedTo) + minCountElementDistributedTo;
            //Removing Variables from variablesWeighted until countOfVarsTerminalWillBeAdded vars are left.
            List<Variable> tempVar = new ArrayList<>(variablesWeighted);
            for (int i = tempVar.size(); i > countOfLeftSideRhseWillBeAdded; i--) {
                tempVar.remove(random.nextInt(tempVar.size()));
            }
            //Adding the element to the leftover variables
            for (Variable var : tempVar) {
                grammar.addProduction(new Production(var, tempRhse));
            }
        }
        grammarPyramidWrapper.setGrammar(grammar);
        return grammarPyramidWrapper;
    }

    /**
     * It is expected that a valid grammar, pyramid and word are given within the grammarPyramidWrapper.
     * A production is contributing if it is possible to fill at least one cell of the pyramid.
     * Additionally the epsilon rule will be kept.
     */
    static GrammarPyramidWrapper onlyKeepContributingProductions(
            GrammarPyramidWrapper grammarPyramidWrapper) {
        Grammar grammar = grammarPyramidWrapper.getGrammar();
        Pyramid pyramid = grammarPyramidWrapper.getPyramid();
        CellK[][] cells = pyramid.getCellsK();
        Map<Variable, List<Production>> productions = new HashMap<>(grammar.getProductionsMap());
        // Calculate usefulProductions and replace the productions contained in grammar at the end.
        Set<Production> usefulProductions = new HashSet<>();
        {   // check for epsilon rule, that equals the empty word.
            List<Production> prodsList = new ArrayList<>();
            for (Variable var : productions.keySet()) {
                prodsList.addAll(productions.get(var));
            }
            for (Production prod : prodsList) {
                if (prod.getRightHandSideElement().getClass() == VariableEpsilon.class ){
                    usefulProductions.add(prod);
                }
            }
        }
        {   // check row = 0 for useful productions
            for (int j = 0; j < cells[0].length; j++) {
                for (VariableK vark : cells[0][j].getCellElements()) {
                    usefulProductions.addAll(
                            contributingProductionsPerCellForTerminals(pyramid.getWord(), productions.get(vark.getVariable()))
                    );
                }
            }
        }
        {   // for each cell in the pyramid starting in the  row = 1
            for (int i = 1; i < cells[0].length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    // calculatePossibleCellPairs
                    Set<Tuple<CellK, CellK>> cellPairs = new HashSet<>(calculatePossibleCellPairs(cells[i][j], pyramid));
                    Set<VariableCompound> variableCompounds = new HashSet<>();
                    {   // for each cellPair calculate its VariablesCompound
                        for (Tuple<CellK, CellK> cellPair : cellPairs) {
                            variableCompounds.addAll(Util.calculateVariablesCompound(cellPair));
                        }
                    }
                    // for each of the productions of the variable in the cell check if it contains one of the variableCompounds
                    // Now only relevant productions are checked. You could give every production too.
                    for (VariableK varK : cells[i][j].getCellElements()) {
                        usefulProductions.addAll(
                                contributingProductionsPerCellForVarComp(variableCompounds, productions.get(varK.getVariable()))
                        );
                    }
                }
            }
        }
        // replace all productions that have been in the grammar up till now.
        grammar.removeAllProductions();
        grammar.addProduction(usefulProductions);
        grammarPyramidWrapper.setGrammar(grammar);
        return grammarPyramidWrapper;
    }

    /**
     * Regarding all possible variableCompounds of one cell, the list of productions is checked whether one of its
     * elements is useful for or not. Usefulness of an production iff the rhse of the production is element of the set
     * of variablesCompounds.
     */
    static List<Production> contributingProductionsPerCellForVarComp(
            Set<VariableCompound> variableCompounds, List<Production> prods) {
        List<Production> useful = new ArrayList<>();
        for (Production prod : prods) {
            if (variableCompounds.contains(prod.getRightHandSideElement())) {
                useful.add(prod);
            }
        }
        return useful;
    }

    /**
     * This is used to check row = 0 for useful productions.
     */
    static List<Production> contributingProductionsPerCellForTerminals(
            Word word, List<Production> prods) {
        Set<Terminal> uniqueTerminals = new HashSet<>(word.getTerminals());
        List<Production> useful = new ArrayList<>();
        for (Production prod : prods) {
            if (uniqueTerminals.contains(prod.getRightHandSideElement())) {
                useful.add(prod);
            }
        }
        return useful;
    }

    static Set<Tuple<CellK, CellK>> calculatePossibleCellPairs(CellK cell, Pyramid pyramid) {
        Set<Tuple<CellK, CellK>> cellTuples = new HashSet<>();
        CellK[][] cells = pyramid.getCellsK();
        int i = cell.getI();
        int j = cell.getJ();
        int iLeft;      // [i-1,0]
        int jLeft;      // equals j
        int iRight;     // [0,i-1]
        int jRight;     // [i+j,j+1]
        {
            jLeft = j;
            iRight = 0;
            jRight = i + j;
            for (iLeft = i - 1; iLeft >= 0; iLeft--) {
                CellK cellLeft = cells[iLeft][jLeft];
                CellK cellRight = cells[iRight][jRight];
                //noinspection SuspiciousNameCombination
                cellTuples.add(new Tuple<>(cellLeft, cellRight));
                iRight++;
                jRight--;
            }
        }
        return cellTuples;
    }

}
