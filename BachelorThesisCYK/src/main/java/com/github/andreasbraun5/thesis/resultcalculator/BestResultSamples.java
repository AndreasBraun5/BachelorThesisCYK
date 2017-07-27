package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.exception.ScoreMatrixRuntimeException;
import com.github.andreasbraun5.thesis.main.ThesisDirectory;
import com.github.andreasbraun5.thesis.score.ScoringMatrix;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Word;
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
    public static final int COUNT_SAMPLES_TO_KEEP = 5;
    public final String name;
    private final List<Tuple<Double, ResultSample>> bestResultSamples = new ArrayList<>();

    /*private final TreeMap<Double, ResultSample> bestResultSamples =
            new TreeMap<>(Collections.reverseOrder());
    */
    public void write() {
        File test = new File(ThesisDirectory.BEST.path, name + ".txt");
        try (PrintWriter out = new PrintWriter(test)) {
            out.println(this.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Only valid samples will be added. If the score of a sample is better than at least the worst score in
     * bestResultSamples, then it replaces one of the COUNT_SAMPLES_TO_KEEP samples.
     */
    public void tryAdd(Map<Word, List<ResultSample>> chunkResults) {
        Set<ResultSample> samples = new HashSet<>();
        chunkResults.forEach((word, resultSamples) -> samples.addAll(resultSamples));
        samples.forEach(sample -> {
            if (sample.isValid()) {
                double tempScore = ScoringMatrix.scoreResultSample(sample);
                if (bestResultSamples.size() < COUNT_SAMPLES_TO_KEEP) {
                    bestResultSamples.add(new Tuple<>(tempScore, sample));
                    bestResultSamples.sort((o1, o2) -> {
                        if (o1.x > o2.x) {
                            return -1;
                        } else if (o1.x < o2.x) {
                            return 1;
                        } else {
                            return 0;
                        }
                    });
                } else if (bestResultSamples.size() == COUNT_SAMPLES_TO_KEEP) {
                    // get key with lowest value
                    int indexWorst = bestResultSamples.size() - 1;
                    double minScoreNeeded = bestResultSamples.get(indexWorst).x;
                    if (tempScore > minScoreNeeded) {
                        bestResultSamples.remove(indexWorst);
                        bestResultSamples.add(new Tuple<>(tempScore, sample));
                        bestResultSamples.sort((o1, o2) -> {
                            if (o1.x > o2.x) {
                                return -1;
                            } else if (o1.x < o2.x) {
                                return 1;
                            } else {
                                return 0;
                            }
                        });
                    }
                } else {
                    throw new ScoreMatrixRuntimeException("COUNT_SAMPLES_TO_KEEP criteria isn't met.");
                }
            }
        });
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("BestResultSamples{");
        bestResultSamples.forEach(resultSampleTuple -> {
                    stringBuilder.
                            append("\\n\\n############################################################\\n\" +\n" +
                                    "\"############################################################\")\n")
                            .append("SCORE OF SAMPLE:").append(resultSampleTuple.x).append("\n")
                            .append(resultSampleTuple.y.toString());
                }
        );
        return stringBuilder.toString();
    }
}
