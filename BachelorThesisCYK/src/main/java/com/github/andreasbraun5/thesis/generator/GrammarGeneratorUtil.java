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
                grammar.addProductions(new Production(var, tempRhse));
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
    static GrammarPyramidWrapper deleteUnnecessaryProductions(
            GrammarPyramidWrapper grammarPyramidWrapper) {
        Grammar grammar = grammarPyramidWrapper.getGrammar();
        Pyramid pyramid = grammarPyramidWrapper.getPyramid();
        CellK[][] cells = pyramid.getCellsK();
        Map<Variable, Set<Production>> productions = new HashMap<>(grammar.getProductionsMap());
        // Calculate usefulProductions and replace the productions contained in grammar at the end.
        Set<Production> usefulProductions = new HashSet<>();
        {   // check for epsilon rule, that equals the empty word.
            List<Production> prodsList = new ArrayList<>();
            for (Variable var : productions.keySet()) {
                prodsList.addAll(productions.get(var));
            }
            for (Production prod : prodsList) {
                if (prod.getRightHandSideElement().getClass() == Epsilon.class) {
                    usefulProductions.add(prod);
                }
            }
        }
        {   // check row = 0 for contributing productions
            for (int j = 0; j < cells[0].length; j++) {
                for (VariableK vark : cells[0][j].getCellElements()) {
                    usefulProductions.addAll(
                            contributingProductionsPerCellForTerminals(pyramid.getWord(), productions.get(vark.getLhse()))
                    );
                }
            }
        }
        {   // for each cell in the pyramid starting in the  row = 1
            for (int i = 1; i < cells[0].length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    // calculatePossibleCellPairs
                    Set<Tuple<CellK, CellK>> cellPairs = new HashSet<>(Util.calculatePossibleCellPairs(i, j, pyramid));
                    Set<VariableCompound> variableCompounds = new HashSet<>();
                    {   // for each cellPair calculate its VariablesCompound
                        for (Tuple<CellK, CellK> cellPair : cellPairs) {
                            variableCompounds.addAll(Util.calculateVariablesCompoundForCellPair(cellPair));
                        }
                    }
                    // for each of the productions of the variable in the cell check if it contains one of the variableCompounds
                    // Now only relevant productions are checked. You could give every production too.
                    for (VariableK varK : cells[i][j].getCellElements()) {
                        usefulProductions.addAll(
                                contributingProductionsPerCellForVarComp(variableCompounds, productions.get(varK.getLhse()))
                        );
                    }
                }
            }
        }
        // replace all productions that have been in the grammar up till now.
        grammar.removeAllProductions();
        grammar.addProductions(usefulProductions);
        grammarPyramidWrapper.setGrammar(grammar);
        return grammarPyramidWrapper;
    }

    /**
     * Regarding all possible variableCompounds of one cell in the pyramid, the list of productions is checked whether
     * one of its elements is useful for or not. Usefulness of an production iff the rhse of the production is element
     * of the set of variablesCompounds.
     */
    static Set<Production> contributingProductionsPerCellForVarComp(
            Set<VariableCompound> variableCompounds, Set<Production> prods) {
        Set<Production> useful = new HashSet<>();
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
    static Set<Production> contributingProductionsPerCellForTerminals(
            Word word, Set<Production> prods) {
        Set<Terminal> uniqueTerminals = new HashSet<>(word.getTerminals());
        Set<Production> useful = new HashSet<>();
        for (Production prod : prods) {
            if (uniqueTerminals.contains(prod.getRightHandSideElement())) {
                useful.add(prod);
            }
        }
        return useful;
    }


    public static Set<VariableCompound> calculateAllVariablesCompoundForGrammar(Set<Variable> vars) {
        Set<VariableCompound> varComp = new HashSet<>();
        for (Variable var1 : vars) {
            for (Variable var2 : vars) {
                varComp.add(new VariableCompound(var1, var2));
            }
        }
        return varComp;
    }

    public static Set<VariableCompound> calculateSubSetForCell(CellK cellK, Pyramid pyramid) {
        Set<VariableCompound> varComp = new HashSet<>();
        for (Tuple<CellK, CellK> tuple : Util.calculatePossibleCellPairs(cellK.getI(), cellK.getJ(), pyramid)) {
            varComp.addAll(Util.calculateVariablesCompoundForCellPair(tuple));
        }
        return varComp;
    }

    /**
     * Merges all productions with the same rhse's.
     */
    public static GrammarPyramidWrapper postprocessing(GrammarPyramidWrapper grammarPyramidWrapper) {
        Grammar grammar = grammarPyramidWrapper.getGrammar();
        List<Production> prodList = grammar.getProductionsAsList();
        Set<VariableCompound> varCompsOfRhs = new HashSet<>();
        // Get all possible rhses
        prodList.forEach(production -> {
            if (production.getRightHandSideElement() instanceof VariableCompound) {
                varCompsOfRhs.add((VariableCompound) production.getRightHandSideElement());
            }
        });
        varCompsOfRhs.forEach(variableCompound -> {
            // Identify the productions with the same rhse
            List<Production> prodsWithSameVarComp = new ArrayList<>();
            prodList.forEach(production -> {
                if (production.getRightHandSideElement().equals(variableCompound)) {
                    prodsWithSameVarComp.add(production);
                }
            });
            Collections.shuffle(prodsWithSameVarComp);
            // Delete productions with the same rhse as long there is only one left
            while (prodsWithSameVarComp.size() > 1) {
                Production prod = prodsWithSameVarComp.get(0);
                prodsWithSameVarComp.remove(0);
                grammar.removeProduction(prod);
            }
        });
        return grammarPyramidWrapper;
    }

}
