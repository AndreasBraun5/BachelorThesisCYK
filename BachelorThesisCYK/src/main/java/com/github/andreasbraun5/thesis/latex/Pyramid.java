package com.github.andreasbraun5.thesis.latex;

import java.util.Arrays;

/**
 * Created by Andreas Braun on 04.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class Pyramid {
	public Cell[][] cells;

	// Initialising the pyramid with the given indexes.
	public Pyramid(int pyramidSize) {
		this.cells = new Cell[pyramidSize][];
		{
			for ( int i = 0; i <= pyramidSize; i++ ) {
				this.cells = new Cell[i][pyramidSize - i];
			}
		}
		this.cells[0][0] = new Cell( 0, 0 );
		this.cells[0][0].centerX = 0.5; // starting shift of cell_00
		double tempCenterY = 0.0;
		for ( int i = 0; i <= cells.length; i++ ) {
			tempCenterY -= 0.866; // sqrt(1-(sin 30°)^2)
			for ( int j = 0; j <= cells[i].length; j++ ) {
				if ( i == 0 && j == 0 ) {
					continue;
				}
				this.cells[i][j] = new Cell( 0, 0 );
				// centerX = centerX from left + 0.5 // 0.5 = i*sin(30°)
				this.cells[i][j].centerX = this.cells[i][j - 1].centerX + 0.5;
				this.cells[i][j].centerY = tempCenterY;
			}
		}
	}
}
