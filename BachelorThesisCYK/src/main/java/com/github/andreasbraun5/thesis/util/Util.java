package com.github.andreasbraun5.thesis.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.andreasbraun5.thesis.resultcalculator.Result;
import com.github.andreasbraun5.thesis.grammar.RightHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;

/**
 * Created by Andreas Braun on 05.01.2017.
 */
public abstract class Util {
	/**
	 * Removing duplicates from a collection.
	 */
	public static <T extends RightHandSideElement> List<T> withoutDuplicates(Collection<T> ruleElements) {
		List<T> ret = new ArrayList<>();
		ret.addAll( new HashSet<>( ruleElements ) );
		return ret;
	}

	/**
	 * Counting the stored elements in each entry of the setV matrix and looking for the max count.
	 */
	// TODO: duplicate maxVarPerCell
	public static int getMaxVarPerCellForSetV(Set<Variable>[][] setV) {
		int numberOfVarsPerCell = 0;
		int temp = setV.length;
		for ( int i = 0; i < temp; i++ ) {
			for ( int j = 0; j < temp; j++ ) {
				if ( setV[i][j].size() > numberOfVarsPerCell ) {
					numberOfVarsPerCell = setV[i][j].size();
				}
			}
		}
		return numberOfVarsPerCell;
	}

	/**
	 * Storing the result output in a text file.
	 */
	public static void writeToFile(
			Result... result) {
		for ( int i=0; i< result.length; i++) {
			try {
				File file = new File( "./" + "Test" + i +".txt" );
				file.getParentFile().mkdirs();
				PrintWriter out = new PrintWriter( file );
				out.println( result[i] );
				out.println( result[i].getRepresentativeResultSamples().toString() );
				out.close();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Converting a String word into a List<Terminal> word.
	 */
	public static List<Terminal> stringToTerminalList(String word) {
		List<Terminal> wordAsTerminalList = new ArrayList<>();
		for ( int i = 0; i < word.length(); i++ ) {
			wordAsTerminalList.add( new Terminal( String.valueOf( word.charAt( i ) ) ) );
		}
		return wordAsTerminalList;
	}


	// TODO: Remove duplicate code.!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	/**
	 * Method to get the setV as a String for printing purposes.
	 */
	public static String getSetVAsStringForPrintingAsLowerTriangularMatrix(Set<Variable>[][] setV, String setName) {
		StringBuilder stringBuilder = new StringBuilder( setName ).append( "\n" );
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
	 * Method for printing the set matrix.
	 */
	public static void printSetVAsLowerTriangularMatrix(Set<Variable>[][] setV, String setName) {
		System.out.println();
		System.out.println( setName );
		int wordlength = setV.length;
		int maxLen = 0;
		for ( int i = 0; i < wordlength; i++ ) {
			for ( int j = 0; j < wordlength; j++ ) {
				maxLen = Math.max( maxLen, setV[j][i].toString().length() );

			}
		}
		for ( int i = 0; i < wordlength; i++ ) {
			for ( int j = 0; j < wordlength; j++ ) {
				System.out.print( uniformStringMaker( setV[j][i].toString(), maxLen ) );
			}
			System.out.println();
		}
	}

	/**
	 * Method to get the setV as a String for printing purposes.
	 */
	public static String getSetVAsStringForPrintingAsUpperTriangularMatrix(Set<Variable>[][] setV, String setName) {
		StringBuilder stringBuilder = new StringBuilder( setName ).append( "\n" );
		int wordLength = setV.length;
		int maxLen = 0;
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

	/**
	 * Method for printing the set matrix.
	 */
	public static void printSetVAsUpperTriangularMatrix(Set<Variable>[][] setV, String setName) {
		System.out.println();
		System.out.println( setName );
		int wordlength = setV.length;
		int maxLen = 0;
		for ( int i = 0; i < wordlength; i++ ) {
			for ( int j = 0; j < wordlength; j++ ) {
				maxLen = Math.max( maxLen, setV[i][j].toString().length() );

			}
		}
		for ( int i = 0; i < wordlength; i++ ) {
			for ( int j = 0; j < wordlength; j++ ) {
				System.out.print( uniformStringMaker( setV[i][j].toString(), maxLen ) );
			}
			System.out.println();
		}
	}

	/**
	 * helper method used by printSetVAsLowerTriangularMatrix
	 */
	private static String uniformStringMaker(String str, int length) {
		StringBuilder builder = new StringBuilder( str );
		for ( int i = str.length(); i < length; ++i ) {
			builder.append( " " );
		}
		return builder.toString();
	}

}
