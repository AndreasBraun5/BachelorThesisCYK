package com.github.andreasbraun5.thesis.json;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by AndreasBraun on 13.07.2017.
 */
public class GrammarJSONConverter {

    private static final Type grammarType = new TypeToken<Grammar>(){}.getType();

    private Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    public String toString(Grammar grammar) {
        Gson gson = gson();
        return gson.toJson(grammar, grammarType);
    }

    public Grammar fromString(String string) {
        Gson gson = gson();
        return gson.fromJson(string, grammarType);
    }

}
