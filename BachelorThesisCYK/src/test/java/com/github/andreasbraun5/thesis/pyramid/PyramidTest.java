package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.util.TestExercises;
import com.github.andreasbraun5.thesis.util.TiScriptExercise;
import com.github.andreasbraun5.thesis.util.Tuple;
import org.junit.Test;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.andreasbraun5.thesis.util.Tuple.of;
import static org.junit.Assert.assertEquals;

/**
 * Created by AndreasBraun on 02.07.2017.
 */
public class PyramidTest {

    @Test
    public void testPyramidConstructor(){
        // The printed output of the pyramid needs to be tested.
        assertEquals(true, false);
    }

    @Test
    public void testMatrixToPyramidCoordinates() {
        List<Tuple<Integer, Integer>> expected = Arrays.asList(
                of(0, 0), of(0, 1), of(0, 2), of(0, 3), of(0, 4),
                of(1, 0), of(1, 1), of(1, 2), of(1, 3),
                of(2, 0), of(2, 1), of(2, 2),
                of(3, 0), of(3, 1),
                of(4, 0)
        );

        List<Tuple<Integer, Integer>> arrayIndexes = Arrays.asList(
                of(0, 0), of(1, 1), of(2, 2), of(3, 3), of(4, 4),
                of(0, 1), of(1, 2), of(2, 3), of(3, 4),
                of(0, 2), of(1, 3), of(2, 4),
                of(0, 3), of(1, 4),
                of(0, 4)
        );

        for (int i = 0; i < arrayIndexes.size(); ++i) {
            assertEquals(expected.get(i), Pyramid.toPyramidCoordinates(arrayIndexes.get(i)));
        }
    }

    @Test
    public void testToString() {
        Pyramid pyramid = new Pyramid(TestExercises.getRandomVarKMatrix());
        System.out.println(pyramid.toString());
    }

}