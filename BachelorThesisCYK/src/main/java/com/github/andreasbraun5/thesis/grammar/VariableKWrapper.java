package com.github.andreasbraun5.thesis.grammar;

/**
 * Created by Andreas Braun on 24.01.2017.
 * https://github.com/AndreasBraun5/
 * The VariableKWrapper class is need for the calculation of the setV for the advancedCYK algorithm. The index k of the
 * loop is additionally stored to the Variable.
 */
public class VariableKWrapper extends Variable {

	private Variable variable;
	private int k;

	public VariableKWrapper(Variable variable, int k) {
		super(variable.toString() + k);
		this.variable = variable;
		this.k = k;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}

		VariableKWrapper that = (VariableKWrapper) o;

		if ( k != that.k ) {
			return false;
		}
		return variable != null ? variable.equals( that.variable ) : that.variable == null;

	}

	@Override
	public int hashCode() {
		int result = variable != null ? variable.hashCode() : 0;
		result = 31 * result + k;
		return result;
	}

	public Variable getVariable() {
		return variable;
	}

	@Override
	public String toString() {
		return variable.toString() + k ;
	}
}
