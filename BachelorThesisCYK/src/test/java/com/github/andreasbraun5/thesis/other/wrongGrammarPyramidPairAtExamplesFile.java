package com.github.andreasbraun5.thesis.other;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorDiceRollOnly;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.resultcalculator.ResultSample;
import com.github.andreasbraun5.thesis.util.GrammarPropertiesUtil;
import com.github.andreasbraun5.thesis.util.Word;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by AndreasBraun on 13.07.2017.
 */
public class wrongGrammarPyramidPairAtExamplesFile {

    private Grammar grammar;
    private GrammarPyramidWrapper pyramidWrapper;
    private GrammarProperties grammarProperties;

    @Before
    public void setup() {
        pyramidWrapper =
                GrammarPyramidWrapper.builder().grammar(grammar = Grammar.empty(VariableStart.of("S"))
                        .addProductions(Production.of(VariableStart.of("S"), VariableCompound.of(Variable.of("B"), Variable.of("C"))),
                                Production.of(VariableStart.of("S"), Terminal.of("a")),
                                Production.of(Variable.of("C"), VariableCompound.of(Variable.of("B"), Variable.of("B"))),
                                Production.of(Variable.of("B"), Terminal.of("b")),
                                Production.of(Variable.of("B"), VariableCompound.of(VariableStart.of("S"), VariableStart.of("S"))),
                                Production.of(Variable.of("B"), VariableCompound.of(Variable.of("C"), Variable.of("C"))))
                ).pyramid(Pyramid.empty(Word.fromStringCharwise("bbaabaabba"))).build();

        pyramidWrapper = CYK.calculateSetVAdvanced(pyramidWrapper);
        grammarProperties = GrammarPropertiesUtil.describeGrammar(grammar);
    }

    @Test
    public void test() {
        ResultSample resultSample = new ResultSample(pyramidWrapper, new GrammarGeneratorSettings( grammarProperties, "<>"  ));
        System.out.println(resultSample);
    }

    @Test
    public void testGrammarGeneratorDiceRollOnly() {
        GrammarGeneratorSettings grammarGeneratorSettings = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorDiceRollOnlyWithLog.txt");
        grammarGeneratorSettings.setMaxValueTerminalsAreAddedTo(1);
        grammarGeneratorSettings.setMinValueTerminalsAreAddedTo(1);
        grammarGeneratorSettings.setMaxValueCompoundVariablesAreAddedTo(2);

        GrammarGeneratorDiceRollOnly grammarGeneratorDiceRollOnly = new GrammarGeneratorDiceRollOnly(grammarGeneratorSettings);
        grammarGeneratorDiceRollOnly.generateGrammarPyramidWrapper(pyramidWrapper, System.out::println);
    }

}
