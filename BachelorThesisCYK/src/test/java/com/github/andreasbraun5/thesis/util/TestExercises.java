package com.github.andreasbraun5.thesis.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.github.andreasbraun5.thesis.util.SS12Exercise.SS12_SET_VARK;
import static com.github.andreasbraun5.thesis.util.SS13Exercise.SS13_SET_VARK;
import static com.github.andreasbraun5.thesis.util.TiScriptExercise.SCRIPT_SET_VARK;

/**
 * Created by AndreasBraun on 02.07.2017.
 */
public final class TestExercises {

    private TestExercises() {

    }

    public static SetVarKMatrix getRandomVarKMatrix() {
        List<SetVarKMatrix> setVarKMatrices = new ArrayList<>();
        setVarKMatrices.add(SS12_SET_VARK);
        setVarKMatrices.add(SS13_SET_VARK);
        setVarKMatrices.add(SCRIPT_SET_VARK);
        Random random = new Random();
        int i = random.nextInt(setVarKMatrices.size());
        return setVarKMatrices.get(i);
    }

}
