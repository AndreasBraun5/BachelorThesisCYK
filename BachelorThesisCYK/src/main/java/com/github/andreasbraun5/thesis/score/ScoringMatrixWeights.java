package com.github.andreasbraun5.thesis.score;

import com.github.andreasbraun5.thesis.pyramid.CellSimple;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by AndreasBraun on 16.07.2017.
 * Each one of the properties of the ResultSample will be scored.
 */
@Getter
@Setter
@Builder
public class ScoringMatrixWeights {
    public static final int MAX_SUM_SCORE_AVAILABLE = 50;
    public static final int OUT_OF_RANGE_SCORE = -100;
}
