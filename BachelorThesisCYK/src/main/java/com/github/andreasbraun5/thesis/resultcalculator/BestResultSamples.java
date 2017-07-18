package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.main.ThesisDirectory;
import com.github.andreasbraun5.thesis.score.ScoringMatrix;
import com.github.andreasbraun5.thesis.util.Word;
import com.sun.deploy.util.OrderedHashSet;
import lombok.Builder;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by AndreasBraun on 18.07.2017.
 */
@Getter
@Builder
public class BestResultSamples {

    public final String name;
    private final List<ResultSample> bestResultSamples = new ArrayList<>();

    public void write() {
        File test = new File(ThesisDirectory.BEST.path, name + ".txt");
        PrintWriter out = null;
        try {
            out = new PrintWriter(test);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.println(this.toString());
        out.close();
    }

    public void tryAdd(Map<Word, List<ResultSample>> chunkResults) {
        Set<ResultSample> samples = new HashSet<>();
        Map<Double, ResultSample> bestSamples = new TreeMap<>();
        chunkResults.forEach((word, resultSamples) -> samples.addAll(resultSamples));
        double minScoreNeeded = 0.0;
        if (bestSamples.size() < 5) { //TODO ahh fuck...
            double tempScore = ScoringMatrix.scoreResultSample(sample);
            bestSamples.put(tempScore, sample)
        } else {
            samples.forEach(sample -> {
                double tempScore = ScoringMatrix.scoreResultSample(sample);
                if (tempScore > minScoreNeeded) {

                }
            });
        }
        // score each sample
        // decide if score is positive
        // save score of lowest best sample which is used to decide if adding is possible

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\n\n\nBestResultSamples{");
        for (ResultSample resultSample : this.bestResultSamples) {
            stringBuilder.append("\n\n############################################################\n" +
                    "############################################################")
                    .append(resultSample.toString());
        }
        return stringBuilder.toString();
    }
}
