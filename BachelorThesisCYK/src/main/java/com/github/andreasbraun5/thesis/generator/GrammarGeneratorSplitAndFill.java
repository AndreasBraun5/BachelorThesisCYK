package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.util.Tuple;

import java.util.*;

/**
 * Created by AndreasBraun on 25.07.2017.
 */
public class GrammarGeneratorSplitAndFill extends GrammarGenerator {

    private static final Random random = new Random();


    public GrammarGeneratorSplitAndFill(GrammarGeneratorSettings grammarGeneratorSettings) {
        super("SPLITANDFILL", grammarGeneratorSettings);
    }

    @Override
    public GrammarPyramidWrapper generateGrammarPyramidWrapper(GrammarPyramidWrapper grammarPyramidWrapper, WorkLog workLog) {
        {   // Start of logging
            workLog.log("#########################################################################################################");
            workLog.log("START of Logging of GrammarGeneratorSplitThenFill.");
            // Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
            grammarPyramidWrapper.setGrammar(new Grammar(grammarGeneratorSettings.grammarProperties.variableStart));
            workLog.log("Used word:");
            workLog.log(grammarPyramidWrapper.getPyramid().getWord().toString());
        }
        // Line 3: Define the Sol tuple
        Tuple<GrammarPyramidWrapper, Variable> sol;
        {   // Line 4: recursive call of SplitThenFill
            int iMax = grammarPyramidWrapper.getPyramid().getWord().getWordLength() - 1;
            sol = splitAndFill(grammarPyramidWrapper, iMax, 0, workLog);
        }

        grammarPyramidWrapper = CYK.calculateSetVAdvanced(sol.x);
        /*
        workLog.log("Grammar before postprocessing: ");
        workLog.log(grammarPyramidWrapper.getGrammar().toString());
        grammarPyramidWrapper = GrammarGeneratorUtil.postprocessing(grammarPyramidWrapper);
        workLog.log("Grammar after postprocessing: ");
        workLog.log(grammarPyramidWrapper.getGrammar().toString());*/
        return grammarPyramidWrapper;
    }

    private Tuple<GrammarPyramidWrapper, Variable> splitAndFill(
            GrammarPyramidWrapper grammarPyramidWrapper,
            int i,
            int j,
            WorkLog workLog) {
        Set<Variable> variables = grammarGeneratorSettings.grammarProperties.variables;
        {   // Line 1 to 6:
            if (i == 0) {
                // Line 2 to 4:
                Terminal wj = grammarPyramidWrapper.getPyramid().getWord().getTerminals().get(j);
                List<Production> prodsWithWj = searchWj(grammarPyramidWrapper.getGrammar().getProductionsAsList(), wj);
                if (prodsWithWj.size() == 0) {
                    // Line 3:
                    workLog.log("Terminal " + wj + " not yet added:");
                    workLog.log("Grammar before adding: ");
                    workLog.log(grammarPyramidWrapper.getGrammar().toString());
                    List<Terminal> wjList = new ArrayList<>();
                    wjList.add(wj);
                    grammarPyramidWrapper = A_DistributeTerminals.distributeTerminals(
                            wjList,
                            grammarPyramidWrapper,
                            grammarGeneratorSettings,
                            new ArrayList<>(variables)
                    );
                    workLog.log("Grammar after adding: ");
                    workLog.log(grammarPyramidWrapper.getGrammar().toString());
                }
                // Line 5:
                prodsWithWj = searchWj(grammarPyramidWrapper.getGrammar().getProductionsAsList(), wj);
                return new Tuple<>(grammarPyramidWrapper, prodsWithWj.get(0).getLeftHandSideElement());
            }
        }
        // Line 7:
        int m = random.nextInt(i) + (j + 1); // ((j + i) + 1     - (j - 1)) || + (j + 1)
        // Line 8 + 9:
        Tuple<GrammarPyramidWrapper, Variable> left = splitAndFill(grammarPyramidWrapper, (m - j - 1), j, workLog);
        Tuple<GrammarPyramidWrapper, Variable> right = splitAndFill(grammarPyramidWrapper, (j + i - m), m, workLog);
        {   // Line 10 to 13:
            int iMax = grammarPyramidWrapper.getPyramid().getWord().getWordLength() - 1;
            if (i == iMax) {
                // Line 11:
                workLog.log("Start symbol not added.");
                workLog.log("Grammar before adding: ");
                workLog.log(grammarPyramidWrapper.getGrammar().toString());
                grammarPyramidWrapper.getGrammar().addProductions(Production.of(
                        grammarPyramidWrapper.getGrammar().getVariableStart(),
                        VariableCompound.of(left.y, right.y))
                );
                workLog.log("Grammar after adding: ");
                workLog.log(grammarPyramidWrapper.getGrammar().toString());
                // Line 12:
                return new Tuple<>(grammarPyramidWrapper, randomV(variables));
            }
        }
        // Line 14:
        Variable randomVar = randomV(variables);
        workLog.log("Adding varComp " + left.y + right.y + " to var " + randomVar);
        workLog.log("Grammar before adding: ");
        workLog.log(grammarPyramidWrapper.getGrammar().toString());
        grammarPyramidWrapper.getGrammar().addProductions(Production.of(
                randomVar,
                VariableCompound.of(left.y, right.y))
        );
        workLog.log("Grammar after adding: ");
        workLog.log(grammarPyramidWrapper.getGrammar().toString());
        // Line 15:
        return new Tuple<>(grammarPyramidWrapper, randomVar);
    }

    private Variable randomV(Set<Variable> vars) {
        List<Variable> tempList = new LinkedList<>(vars);
        Collections.shuffle(tempList, random);
        return tempList.get(0);
    }

    private List<Production> searchWj(List<Production> prodsList, Terminal wj) {
        List<Production> tempProds = new ArrayList<>();
        // find all productions that have wj as a rhse
        prodsList.forEach(production -> {
            if (production.getRightHandSideElement() instanceof Terminal &&
                    production.getRightHandSideElement().equals(wj)) {
                tempProds.add(production);
            }
        });
        return tempProds;
    }
}
