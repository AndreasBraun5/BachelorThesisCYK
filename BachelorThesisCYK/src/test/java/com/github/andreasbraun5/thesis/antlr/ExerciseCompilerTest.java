package com.github.andreasbraun5.thesis.antlr;

import com.github.andreasbraun5.thesis.exercise.Exercise;
import com.github.andreasbraun5.thesis.util.TiScriptExercise;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by AndreasBraun on 20.07.2017.
 */
public class ExerciseCompilerTest {

    @Test
    public void test() {
        Exercise exercise = Exercise.builder().grammar(TiScriptExercise.SCRIPT_GRAMMAR)
                .word(TiScriptExercise.SCRIPT_EXAMPLE_WORD).build();
        ExerciseStringConverter exerciseStringConverter = new ExerciseStringConverter();
        String exerciseString = exerciseStringConverter.exerciseToString(exercise);
        System.out.println(exerciseString);
        Exercise exerciseParsed = ExerciseCompiler.parseExercise(exerciseString);
        Assert.assertEquals(exercise, exerciseParsed);
    }

}
