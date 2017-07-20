package com.github.andreasbraun5.thesis.json;

import com.github.andreasbraun5.thesis.util.TiScriptExercise;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by AndreasBraun on 20.07.2017.
 */
public class GrammarJSONConverterTest {
    @Test
    public void toStringTest() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("TexFileTestExercise: TexFileTestExercise");
        GrammarJSONConverter converter = new GrammarJSONConverter();
        System.out.println(converter.toString(TiScriptExercise.SCRIPT_GRAMMAR));
    }

    @Test
    public void fromString() throws Exception {
    }

}