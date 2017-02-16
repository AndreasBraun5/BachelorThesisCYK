package com.github.andreasbraun5.thesis.util;

import java.util.HashSet;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.SetVMatrixRuntimeException;
import com.github.andreasbraun5.thesis.grammar.LeftHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableKWrapper;

/**
 * Created by Andreas Braun on 14.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class SetVMatrix<T extends LeftHandSideElement> {

	private Set<T>[][] setV;
	private String name = "setV";

	public static <T extends LeftHandSideElement> SetVMatrix buildEmptySetVMatrixWrapper(int wordLength) {
		@SuppressWarnings("unchecked")
		Set<T>[][] setVTemp = new Set[wordLength][wordLength];
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				setVTemp[i][j] = new HashSet<>();
			}
		}
		return new SetVMatrix<T>().setSetV( setVTemp );
	}
	/**
	 * Method to get the setV as a String for printing purposes.
	 * The setV pyramid points downwards (reflection on the diagonal).
	 */
	@SuppressWarnings("Duplicates")
	public String getStringToPrintAsLowerTriangularMatrix() {
		StringBuilder stringBuilder = new StringBuilder( name ).append( "\n" );
		int wordLength = setV.length;
		int maxLen = 0;
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				maxLen = Math.max( maxLen, setV[j][i].toString().length() );
			}
		}
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				stringBuilder.append( uniformStringMaker( setV[j][i].toString(), maxLen ) );
			}
			stringBuilder.append( "\n" );
		}
		return stringBuilder.toString();

	}

	/**
	 * Method to get the setV as a String for printing purposes.
	 */
	@SuppressWarnings("Duplicates")
	public String getStringToPrintAsUpperTriangularMatrix() {
		StringBuilder stringBuilder = new StringBuilder( name ).append( "\n" );
		int wordLength = setV.length;
		int maxLen = 0;
		if ( setV[0][0] instanceof VariableKWrapper || setV[0][0] instanceof Variable ) {
			for ( int i = 0; i < wordLength; i++ ) {
				for ( int j = 0; j < wordLength; j++ ) {
					maxLen = Math.max( maxLen, setV[i][j].toString().length() );
				}
			}
			for ( int i = 0; i < wordLength; i++ ) {
				for ( int j = 0; j < wordLength; j++ ) {
					stringBuilder.append( uniformStringMaker( setV[i][j].toString(), maxLen ) );
				}
				stringBuilder.append( "\n" );
			}
			return stringBuilder.toString();
		}
		else {
			throw new SetVMatrixRuntimeException( "Wrong Type of setV for getting a string." );
		}
	}

	public Set<T>[][] getSetV() {
		return setV;
	}

	public SetVMatrix setSetV(Set<T>[][] setV) {
		this.setV = setV;
		return this;
	}

	/**
	 * helper method used by printSetVAsLowerTriangularMatrix
	 */
	private String uniformStringMaker(String str, int length) {
		StringBuilder builder = new StringBuilder( str );
		for ( int i = str.length(); i < length; ++i ) {
			builder.append( " " );
		}
		return builder.toString();
	}

	/**
	 * Set<VariableKWrapper>[][] --> Set<Variable>[][]
	 */
	public Set<Variable>[][] getSimpleMatrix() {
		int length = setV.length;
		Set<Variable>[][] setVVariable = Util.getInitialisedHashSetArray( length );
		for ( int i = 0; i < length; i++ ) {
			for ( int j = 0; j < length; j++ ) {
				setVVariable[i][j] = Util.varKSetToVarSet( setV[i][j] );
			}
		}
		return setVVariable;
	}
}
