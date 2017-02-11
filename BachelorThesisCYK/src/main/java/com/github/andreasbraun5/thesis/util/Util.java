package com.github.andreasbraun5.thesis.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.RightHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.grammar.VariableKWrapper;
import com.github.andreasbraun5.thesis.resultcalculator.Result;

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
	public static int getMaxVarPerCellForSetV(Set<VariableKWrapper>[][] setV) {
		Set<Variable>[][] tempSetV = Util.getVarsFromSetDoubleArray( setV );
		int numberOfVarsPerCell = 0;
		int temp = tempSetV.length;
		for ( int i = 0; i < temp; i++ ) {
			for ( int j = 0; j < temp; j++ ) {
				if ( tempSetV[i][j].size() > numberOfVarsPerCell ) {
					numberOfVarsPerCell = tempSetV[i][j].size();
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
		for ( int i = 0; i < result.length; i++ ) {
			try {
				File file = new File( "./" + "Test" + i + ".txt" );
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

	// Its structure is very similar to stepIIAdvanced and calculateSetVAdvanced.
	public static Grammar removeUselessProductions(
			Grammar grammar,
			Set<VariableKWrapper>[][] setV,
			List<Terminal> word) {
		int wordLength = setV.length;
		Map<Variable, List<Production>> productions = grammar.getProductionsMap();
		Set<Production> onlyUsefulProductions = new HashSet<>();
		// Similar to stepIIAdvanced
		// Look at each terminal of the word
		for ( int i = 1; i <= wordLength; i++ ) {
			RightHandSideElement tempTerminal = word.get( i - 1 );
			// Get all productions that have the same leftHandSide variable. This is done for all unique variables.
			// So all production in general are taken into account.
			for ( Map.Entry<Variable, List<Production>> entry : grammar.getProductionsMap().entrySet() ) {
				VariableKWrapper var = new VariableKWrapper( entry.getKey(), i );
				List<Production> prods = entry.getValue();
				// Check if there is one rightHandSideElement that equals the observed terminal.
				for ( Production prod : prods ) {
					if ( prod.isElementAtRightHandSide( tempTerminal ) ) {
						setV[i - 1][i - 1].add( var );
						// This here was added.
						onlyUsefulProductions.add( prod );
					}
				}
			}
		}
		// Similar to calculateSetVAdvanced
		for ( int l = 1; l <= wordLength - 1; l++ ) {
			// i loop of the described algorithm.
			// Needs to be 1 <= i <= n-1-l, because of index starting from 0 for an array.
			for ( int i = 0; i <= wordLength - l - 1; i++ ) {
				// k loop of the described algorithm
				// Needs to be i <= k <= i+l, because of index starting from 0 for i already.
				for ( int k = i; k < i + l; k++ ) {
					// tempSetX contains the newly to be added variables, regarding the "X-->YZ" rule.
					// If the substring X can be concatenated with the substring Y and substring Z, whereas Y and Z
					// must be element of its specified subsets, then add the element X to setV[i][i+l]
					Set<Variable> tempSetX = new HashSet<>();
					Set<Variable> tempSetY = Util.getVarsFromSet( setV[i][k] );
					Set<Variable> tempSetZ = Util.getVarsFromSet( setV[k + 1][i + l] );
					Set<VariableCompound> tempSetYZ = new HashSet<>();
					// All possible concatenations of the variables yz are constructed. And so its substrings, which
					// they are able to generate
					for ( Variable y : tempSetY ) {
						for ( Variable z : tempSetZ ) {
							@SuppressWarnings("SuspiciousNameCombination")
							VariableCompound tempVariable = new VariableCompound( y, z );
							tempSetYZ.add( tempVariable );
						}
					}
					// Looking at all productions of the grammar, it is checked if there is one rightHandSideElement that
					// equals any of the concatenated variables tempSetYZ. If yes, the LeftHandSideElement or more
					// specific the variable of the production is added to the tempSetX. All according to the "X-->YZ" rule.
					for ( List<Production> tempProductions : productions.values() ) {
						for ( Production tempProduction : tempProductions ) {
							for ( VariableCompound yz : tempSetYZ ) {
								if ( tempProduction.isElementAtRightHandSide( yz ) ) {
									// This here is changed.
									onlyUsefulProductions.add( tempProduction );
								}
							}
						}
					}
					for ( Variable var : tempSetX ) {
						// ( k + 1) because of index range of k  because of i.
						setV[i][i + l].add( new VariableKWrapper( var, ( k + 1 ) ) );
					}
				}
			}
		}
		grammar.removeAllProductions();
		Production[] productionsArray = new Production[onlyUsefulProductions.size()];
		onlyUsefulProductions.toArray( productionsArray );
		grammar.addProduction( productionsArray );
		return grammar;
	}

	/**
	 * Set<VariableKWrapper> --> Set<Variable>
	 */
	public static Set<Variable> getVarsFromSet(Set<VariableKWrapper> setVWrapper) {
		Set<Variable> setVVariable = new HashSet<>();
		for ( VariableKWrapper variableKWrapper : setVWrapper ) {
			setVVariable.add( variableKWrapper.getVariable() );
		}
		return setVVariable;
	}

	/**
	 * Set<VariableKWrapper>[][] --> Set<Variable>[][]
	 */
	public static Set<Variable>[][] getVarsFromSetDoubleArray(Set<VariableKWrapper>[][] setVWrapper) {
		int length = setVWrapper.length;
		@SuppressWarnings("unchecked")
		Set<Variable>[][] setVVariable = new Set[length][length];
		for ( int i = 0; i < length; i++ ) {
			for ( int j = 0; j < length; j++ ) {
				setVVariable[i][j] = new HashSet<>(); // this generates a set with size = 0
			}
		}
		for ( int i = 0; i < length; i++ ) {
			for ( int j = 0; j < length; j++ ) {
				setVVariable[i][j] = getVarsFromSet( setVWrapper[i][j] );
			}
		}
		return setVVariable;
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


	/**
	 * Method to get the setV as a String for printing purposes.
	 */
	@SuppressWarnings("Duplicates")
	public static String getSetVVariableKAsStringForPrintingAsLowerTriangularMatrix(
			Set<VariableKWrapper>[][] setV,
			String setName) {
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
	 * Method to get the setV as a String for printing purposes.
	 */
	@SuppressWarnings("Duplicates")
	public static String getSetVVariableAsStringForPrintingAsLowerTriangularMatrix(
			Set<Variable>[][] setV,
			String setName) {
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
	 * Method to get the setV as a String for printing purposes.
	 */
	@SuppressWarnings("Duplicates")
	public static String getSetVVariableAsStringForPrintingAsUpperTriangularMatrix(
			Set<Variable>[][] setV,
			String setName) {
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

	// TODO: Maybe add methods that set<Variable>[][] --> List<Variable> and the same for VariableKWrapper.

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
