package com.github.andreasbraun5.thesis.latex;

import java.util.ArrayList;

/**
 * Created by Andreas Braun on 04.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class Pyramid {
	public ArrayList<ArrayList<Cell>> cells;

	// Initialising the pyramid with the given indexes.
	public Pyramid(int pyramidSize) {
		this.cells = new ArrayList<>( pyramidSize );
		{
			for ( int i = 0; i < pyramidSize; i++ ) {
				this.cells.add( new ArrayList<>( pyramidSize - i ) );
			}
		}
		this.cells.get( 0 ).add( new Cell( 0, 0 ) );
		this.cells.get( 0 ).get( 0 ).centerX = 0.5; // starting shift of cell_00
		this.cells.get( 0 ).get( 0 ).centerY = 0;
		double tempCenterY = 0.0;
		for ( int i = 0; i < pyramidSize; i++ ) {
			tempCenterY = tempCenterY - 0.433; // sqrt(1-(sin 30°)^2)
			for ( int j = 0; j < pyramidSize - i; j++ ) {
				if ( i == 0 && j == 0 ) {
					continue;
				}
				this.cells.get( i ).add( new Cell( i, j ) );
				if ( j == 0 ) {
					this.cells.get( i ).get( j ).centerX = this.cells.get( i - 1 ).get( j ).centerX + 0.5;
					this.cells.get( i ).get( j ).centerY = tempCenterY;
					continue;
				}
				// centerX = centerX from left + 1.0 // 0.5 = i*sin(30°)
				this.cells.get( i ).get( j ).centerX = this.cells.get( i ).get( j - 1 ).centerX + 1.0;
				this.cells.get( i ).get( j ).centerY = tempCenterY;
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for ( int i = 0; i < cells.size(); i++ ) {
			for ( int j = 0; j < cells.get( i ).size(); j++ ) {
				str.append( cells.get( i ).get( j ).toString() );
			}
		}
		return str.toString();
	}
}
