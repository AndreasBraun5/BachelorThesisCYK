package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.generator._GrammarGeneratorDiceRollOnly;
import com.github.andreasbraun5.thesis.generator._GrammarGeneratorDiceRollTopDown;
import com.github.andreasbraun5.thesis.generator._GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.resultcalculator.Result;
import com.github.andreasbraun5.thesis.resultcalculator.ResultCalculator;
import com.github.andreasbraun5.thesis.util.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! SetV is in reality an upper triangular matrix !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // TODO Note: The favouritism can be set for the starting variable specifically.
    // TODO Note: Maybe use the fraction of CountVars and countTerminals.
    // TODO Note: Maybe incorporate in rightCellCombinations forced, that more than * different vars are forcing.
    // TODO Note: all checks are done on the simpleSetV
    // TODO Note: Tree package Latex: http://tex.stackexchange.com/questions/5447/how-can-i-draw-simple-trees-in-latex
    // TODO Note: C:\GitHub\BachelorThesis\BachelorThesisCYK>mvn clean install    --> .jar
    //
    // TODO Implement: CYK tree in Combination with latex picture creation.
    // TODO: Algorithm Duda Prepare, but maybe this isn't wanted
    // TODO: Implement pyramid vs methods: getRight, getLeft, getUpperRight, getUpperLeft, ... methods in SetVMatrix. Possible to check indices. Legacy code still works.
    // TODO: Outsource calculateSubsetForCell from CYK algorithm. Implicit testing possible and reuse.
    // TODO: Comparator needed for the stored samples in the .txt file.
    // TODO: More than 5 cells in TikzPicture Cell
    //
    // TODO DONE?: Implement Wim's algorithm from meeting 6 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // TODO: Wim's algorithm output rethink because of too many times not adding a production.
    //
    // TODO: See [Duda, Pattern Classification] chapter 8.5 Recognition with strings. In more Detail:
    //			8.5.2 Edit Distance(p. 418): Definition of similarity or difference between two strings. Deletion,
    //				insertion and exchange operations with different weighting possible. In general this is used for
    //				string matching with errors. So to say more error robust classification or recognition of spoken
    // 				words and and hand written words.
    // TODO: See [Duda, Pattern Classification] chapter 8.6 Grammatical Methods. In more Detail:
    //			8.6.2 Recognition Using Grammars (p. 426): Bottom-Up Parsing = CKY, starting from the leaves;
    // 				Top-Down, starting from root node, "with some specified criteria that guide the choice of which
    //				rule to apply. Such criteria could include beginning the parse ...";
    // TODO: See [Duda, Pattern Classification] chapter 8.7 Grammatical Inference. In more Detail:
    //			8.7 "In many applications, the grammar is designed by hand. ... It is important to learn a grammar from
    //				example sentences [ for our case this would only be the wanted or validity==true samples ]
    // 				{ Guess of myself: So to find some kind of meta grammar that describes grammars we seek }
    //				https://en.wikipedia.org/wiki/Grammar_induction
    //				See algorithm 5: Grammatical Inference (Overview).
    //				https://en.wikipedia.org/wiki/Grammar_induction
    public static void main(String[] args) throws IOException {

        // TODO: insert init Method that checks that all subdirectories like logs,temp,... are created
        ThesisDirectory.initPaths();

        /**
         * 	Generating the settings for the generatorTest.
         * 	GrammarProperties = general settings, the same for all settings of one run.
         * 	GrammarGeneratorSettingsDiceRoll = generator specific settings.
         */
        /**
         * 	N = countOfGrammarsToGenerate.
         * 	Select the testing method.
         * 	Comparability of the TestResults is given via using the same N and the same GrammarProperties.
         */
        // It is recommended to use a high countDifferentWords. Word independent results are achieved.
        int countGeneratedGrammarsPerWord = 100;
        int countDifferentWords = 100;

        ResultCalculator resultCalculator = ResultCalculator.buildResultCalculator().
                setCountDifferentWords(countDifferentWords).
                setCountOfGrammarsToGeneratePerWord(countGeneratedGrammarsPerWord);
        GrammarProperties grammarProperties = generateGrammarPropertiesForTesting();


        _GrammarGeneratorSettings settings6 = new _GrammarGeneratorSettings(grammarProperties, "result6.txt");
        settings6.setMaxValueTerminalsAreAddedTo(1);
        settings6.setMinValueTerminalsAreAddedTo(1);
        settings6.setMaxValueCompoundVariablesAreAddedTo(2);
        Result result6 = resultCalculator.buildResultWithGenerator(
                new _GrammarGeneratorDiceRollOnly(settings6),
                // TODO: // if null here nothing is logged
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.file(settings6.name)))
        );
        // TODO: should be done always!? How best done? Via destructor?
        Util.writeResultToTxtFile(result6);


        _GrammarGeneratorSettings settings7 = new _GrammarGeneratorSettings(grammarProperties, "result7.txt");
        settings7.setMaxValueTerminalsAreAddedTo(1);
        settings7.setMinValueTerminalsAreAddedTo(1);
        settings7.setMaxValueCompoundVariablesAreAddedTo(2);
        Result result7 = resultCalculator.buildResultWithGenerator(
                new _GrammarGeneratorDiceRollTopDown(settings7),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.file(settings7.name)))
        );
        Util.writeResultToTxtFile(result7);


        _GrammarGeneratorSettings settings8 = new _GrammarGeneratorSettings(grammarProperties, "result8.txt");
        settings8.setMaxValueTerminalsAreAddedTo(2);
        settings8.setMinValueTerminalsAreAddedTo(1);
        settings8.setMaxValueCompoundVariablesAreAddedTo(2);
        Result result8 = resultCalculator.buildResultWithGenerator(
                new _GrammarGeneratorDiceRollOnly(settings8),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.file(settings8.name)))
        );
        Util.writeResultToTxtFile(result8);


        _GrammarGeneratorSettings settings9 = new _GrammarGeneratorSettings(grammarProperties, "result9.txt");
        settings9.setMaxValueTerminalsAreAddedTo(2);
        settings9.setMinValueTerminalsAreAddedTo(1);
        settings9.setMaxValueCompoundVariablesAreAddedTo(2);
        Result result9 = resultCalculator.buildResultWithGenerator(
                new _GrammarGeneratorDiceRollTopDown(settings9),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.file(settings9.name)))
        );
        Util.writeResultToTxtFile(result9);


        _GrammarGeneratorSettings settings10 = new _GrammarGeneratorSettings(grammarProperties, "result10.txt");
        settings10.setMaxValueTerminalsAreAddedTo(2);
        settings10.setMinValueTerminalsAreAddedTo(1);
        settings10.setMaxValueCompoundVariablesAreAddedTo(2);
        settings10.grammarProperties.grammarPropertiesExamConstraints.setMinRightCellCombinationsForced(5);
        Result result10 = resultCalculator.buildResultWithGenerator(
                new _GrammarGeneratorDiceRollOnly(settings10),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.file(settings10.name)))
        );
        Util.writeResultToTxtFile(result10);


        _GrammarGeneratorSettings settings11 = new _GrammarGeneratorSettings(grammarProperties, "result11.txt");
        settings11.setMaxValueTerminalsAreAddedTo(2);
        settings11.setMinValueTerminalsAreAddedTo(1);
        settings11.setMaxValueCompoundVariablesAreAddedTo(2);
        settings11.grammarProperties.grammarPropertiesExamConstraints.setMinRightCellCombinationsForced(5);
        Result result11 = resultCalculator.buildResultWithGenerator(
                new _GrammarGeneratorDiceRollTopDown(settings11),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.file(settings11.name)))
        );
        Util.writeResultToTxtFile(result11);
    }

    public static GrammarProperties generateGrammarPropertiesForTesting() {
        Set<Variable> variables = new HashSet<>();
        variables.add(new Variable("A"));
        variables.add(new Variable("B"));
        variables.add(new Variable("C"));
        Set<Terminal> terminals = new HashSet<>();
        terminals.add(new Terminal("a"));
        terminals.add(new Terminal("b"));
        //terminals.add( new Terminal( "c" ) );
        //terminals.add( new Terminal( "d" ) );
        //terminals.add( new Terminal( "e" ) );
        //terminals.add( new Terminal( "f" ) );

        /**
         * Some settings for the beginning:
         * variables.size() should be around 4, considering exam exercises.
         * terminals.size() should be 2.
         * Used default setting:
         * grammarPropertiesGrammarRestrictions: sizeOfWord = 10 // All TestResults will be based on words of this size
         * grammarPropertiesGrammarRestrictions: maxNumberOfVarsPerCell = 3 // The CYK simple pyramid must contain less than 4 vars in cone cell
         * grammarPropertiesExamConstraints: minRightCellCombinationsForced = 1
         * grammarPropertiesExamConstraints: countSumOfProductions = 10; // approximate maximum value taken from the exam exercises
         * grammarPropertiesExamConstraints: minRightCellCombinationsForced = 1 countSumOfVarsInPyramid = 50; // approximate maximum value taken from the exam exercises
         */
        GrammarProperties grammarProperties = new GrammarProperties(new VariableStart("S"), variables, terminals);
        grammarProperties.grammarPropertiesGrammarRestrictions.setMaxNumberOfVarsPerCell(3); //Set like this
        return grammarProperties;
    }
}