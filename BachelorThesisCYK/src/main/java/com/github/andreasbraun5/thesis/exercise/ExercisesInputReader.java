package com.github.andreasbraun5.thesis.exercise;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.Word;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by AndreasBraun on 20.07.2017.
 */
public class ExercisesInputReader {

    public static Exercise read() {

        List<String> lines = new ArrayList<>();

        String fileName = "C:\\Users\\AndreasBraun\\Documents\\BachelorThesis\\BachelorThesisCYK\\exercise\\exerciseInput.txt";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(lines::add);

        } catch (IOException e) {
            e.printStackTrace();
        }

        VariableStart varStart;
        {
            int varStartLineIndex = 0;
            String varStartLine = lines.get(0);
            lines.remove(varStartLineIndex);
            // Get the String with ) at the end
            String varStartString = varStartLine.split(":\\(")[1];
            varStart = new VariableStart(varStartString.substring(0, varStartString.length() - 1));
        }

        Word word;
        {
            int wordLineIndex = lines.size() - 1;
            String wordLine = lines.get(wordLineIndex);
            lines.remove(wordLineIndex);
            List<Terminal> terminals = new ArrayList<>();
            // search for ( then again until ) and use what is in between as terminal of the word
            while (wordLine.contains("\u0028")) {
                Map<String, String> ret = findAndDeleteSubstringFromLine(wordLine, "\u0028", "\u0029");
                terminals.add(new Terminal(ret.get("phrase")));
                wordLine = ret.get("wordLine");
            }
            word = new Word(terminals);
        }

        Grammar grammar = new Grammar(varStart);
        {
            // remove the empty lines
            lines.remove(0);
            lines.remove(lines.size() - 1);
            // [{(B)-->(b)}, {(B)-->(C,B)}]
            while (lines.size() > 0) {
                String lineStr = lines.get(0);
                while (lineStr.contains("{")) {
                    Map<String, String> ret = findAndDeleteSubstringFromLine(lineStr, "{", "}");
                    String prod = ret.get("phrase");
                    lineStr = ret.get("wordLine");
                    grammar.addProductions(parseProduction(prod, varStart));
                }
                lines.remove(0);
            }
        }
        GrammarPyramidWrapper grammarPyramidWrapper = GrammarPyramidWrapper.builder()
                .grammar(grammar)
                .pyramid(new Pyramid(word)).build();
        Pyramid pyramid = CYK.calculateSetVAdvanced(grammarPyramidWrapper).getPyramid();
        return Exercise.builder().grammar(grammar).word(word).pyramid(pyramid).build();
    }

    private static Map<String, String> findAndDeleteSubstringFromLine(String wordLine, String startSymbol, String endSymbol) {
        int startTerminal = wordLine.indexOf(startSymbol) + 1;
        int endTerminal = wordLine.indexOf(endSymbol);
        Map<String, String> ret = new HashMap<>();
        ret.put("phrase", wordLine.substring(startTerminal, endTerminal));
        wordLine = wordLine.substring(endTerminal + 1);
        ret.put("wordLine", wordLine);
        return ret;
    }

    private static Production parseProduction(String prod, VariableStart variableStart) {
        // {(B)-->(b)} or {(B)-->(C,B)}
        while (prod.contains("(")) {
            Map<String, String> ret = findAndDeleteSubstringFromLine(prod, "(", ")");
            Variable var;
            String lhseStr = ret.get("phrase");
            if(lhseStr.equals(variableStart.toString())){
                var = variableStart;
            } else {
                var = Variable.of(ret.get("phrase"));
            }
            String varCompOrTerm = ret.get("wordLine");
            prod = ret.get("wordLine");
            if (varCompOrTerm.contains(",")) {
                Map<String, String> ret2 = findAndDeleteSubstringFromLine(prod, "(", ")");
                // -->(C,B)
                String[] varComps = ret2.get("phrase").split(",");
                String var1 = varComps[0].substring(varComps[0].length()-1);
                String var2 = varComps[1].substring(0,1);
                return Production.of(var, VariableCompound.of(Variable.of(var1), Variable.of(var2)));
            } else {
                Map<String, String> ret3 = findAndDeleteSubstringFromLine(prod, "(", ")");
                String terminal = ret3.get("phrase");
                return Production.of(var, Terminal.of(terminal));
            }
        }
        return null;
    }


}
