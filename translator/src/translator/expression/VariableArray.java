package translator.expression;

/**
 * Variable arrays are arrays that contain a set of independent 
 * variables, such as [a,x,y,z]. They are one-dimensional.
 * 
 * @author andrea
 *
 */

public class VariableArray implements SingleArray {

	
	AtomExpression[] variables;
	String name;
	
	public VariableArray(AtomExpression[] variables) {
		this.variables = variables;
		this.name = Expression.VARIABLE_ARRAY_NAME;
	}
	
	public VariableArray(AtomExpression[] variables, String name) {
		this.variables = variables;
		this.name = name;
	}
	
	
	//============ INHERITED METHODS ======================
	
	public String getArrayName() {
		return this.name;
	}

	public Domain getBaseDomain() {
		// TODO: return the greatest common domain of the variables
		return new BoundedIntRange(Expression.LOWER_BOUND, Expression.UPPER_BOUND);
	}

	public Expression copy() {
		AtomExpression[] copiedVariables = new AtomExpression[this.variables.length];
		for(int i=0; i<copiedVariables.length; i++)
			copiedVariables[i] = (AtomExpression) variables[i].copy();
		
		return new VariableArray(copiedVariables, new String(name));
	}

	public Expression evaluate() {
		return this;
	}

	public int[] getDomain() {
		// TODO: return the greatest common domain of the variables
		return new int[] {Expression.LOWER_BOUND, Expression.UPPER_BOUND};
	}

	public int getType() {
		return Expression.VARIABLE_ARRAY;
	}

	public Expression insertDomainForVariable(Domain domain, String variableName) {
		return this;
	}

	public Expression insertValueForVariable(int value, String variableName) {
		
		for(int i=0; i<this.variables.length; i++) {
			variables[i] = (AtomExpression) variables[i].insertValueForVariable(value, variableName);
		}
		
		return this;
	}

	public Expression insertValueForVariable(boolean value, String variableName) {
		for(int i=0; i<this.variables.length; i++) {
			variables[i] = (AtomExpression) variables[i].insertValueForVariable(value, variableName);
		}
		
		return this;
	}

	public boolean isGonnaBeFlattenedToVariable() {
		return true;
	}

	public boolean isNested() {
		return true;
	}

	public char isSmallerThanSameType(Expression e) {
		VariableArray otherArray = (VariableArray) e;
		
		if(otherArray.variables.length < this.variables.length) 
			return Expression.BIGGER;
		else if(otherArray.variables.length > this.variables.length)
			return Expression.SMALLER;
		else { // same amount of variables
			for(int i=0; i<this.variables.length; i++) {
				if(otherArray.variables[i].getType() == this.variables[i].getType()) {
					char diff = variables[i].isSmallerThanSameType(otherArray.variables[i]);
					if(diff != Expression.EQUAL)
						return diff;
				}
				else return (otherArray.variables[i].getType() > this.variables[i].getType()) ?
						Expression.SMALLER : Expression.BIGGER;
			}
			return Expression.EQUAL;
		}
	}

	public void orderExpression() {
		// do nothing

	}

	public Expression reduceExpressionTree() {
		return this;
	}

	public Expression replaceVariableWith(Variable oldVariable,
			Variable newVariable) {
		
		for(int i=0; i<this.variables.length; i++) 
			variables[i] = (AtomExpression) variables[i].replaceVariableWith(oldVariable, newVariable);
		return this;
	}

	public Expression restructure() {
		return this;
	}

	public void setIsNotNested() {
		// do nothing -> this structure will always be nested...

	}

	public void willBeFlattenedToVariable(boolean reified) {
		// do nothing -> this structure will always be a variable

	}
	
	// ================== ADDITIONAL METHODS ===================
	
	public AtomExpression[] getVariables() {
		return this.variables;
	}

}