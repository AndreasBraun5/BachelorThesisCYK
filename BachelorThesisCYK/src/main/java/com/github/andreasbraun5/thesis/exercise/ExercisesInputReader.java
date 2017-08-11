package com.github.andreasbraun5.thesis.exercise;

import com.github.andreasbraun5.thesis.antlr.ExerciseStringConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


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
