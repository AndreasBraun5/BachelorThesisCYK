package com.github.andreasbraun5.thesis.exercise;

import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by AndreasBraun on 21.07.2017.
 */
@Getter
@Setter
@Builder
public class ExerciseSolution {

    private Exercise exercise;
    private Pyramid pyramid;

}
