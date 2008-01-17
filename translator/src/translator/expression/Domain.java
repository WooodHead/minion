package translator.expression;

/**
 * This interface represents Domains: domains are given in correspondence with
 * a decision variable or quantified variables.
 * 
 * @author andrea
 *
 */

public interface Domain {

	
	public final int BOOL = 0;
	public final int INT_BOUNDS = 1;
	public final int INT_SPARSE = 2;
	public final int INT_MIXED = 3;
	public final int EXPR_BOUNDS = 4;
	public final int EXPR_SPARSE = 5;
	public final int EXPR_MIXED = 6;
	public final int IDENTIFIER = 7;
	public final int ARRAY = 10;
	
	
	// for indexing
	public final int INT_INDEX = 20;
	public final int VAR_INDEX = 21;
	public final int RANGE_INDEX = 22;
	
	
	/**
	 * Evaluate all the expressions in this domain. This concerns mainly
	 * evaluation of lower and upper bounds, for instance. If all expressions
	 * int the domain can be reduced to integer values, the domain is supposed
	 * to be transformed to an IntRange (or MixedIntRange, respectively). 
	 * @return
	 */
	public Domain evaluate();
	
	public String toString();
	
	public Domain copy();
	
	public int getType();
	
	public boolean isConstantDomain();
	
	/**
	 * Insert the int value for every occurrence of variable with parameter variableName
	 * in the bounds of the given domain.
	 * 
	 * @param value
	 * @param variableName
	 * @return the domain with every occurrence of variableName substituted with value.
	 */
	public Domain insertValueForVariable(int value, String variableName);
}