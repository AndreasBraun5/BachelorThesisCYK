package com.github.andreasbraun5.thesis.exercise;

import com.github.andreasbraun5.thesis.antlr.ExerciseStringConverter;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.main.ThesisDirectory;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.Word;

import java.io.*;


/**
 * Created by AndreasBraun on 20.07.2017.
 */
public class ExercisesInputReader {

    public static Exercise read(String fileName) {
        StringBuilder str = new StringBuilder();
        try (FileReader fr = new FileReader(new File(fileName));
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                str.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ExerciseStringConverter exerciseStringConverter = new ExerciseStringConverter();
        return exerciseStringConverter.fromString(str.toString());
    }




}
