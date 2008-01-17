package translator.expression;

/**
 * Represents a quantified sum. I thought long about just converting 
 * every quantified sum straight to the normal sum representation, but
 * this might cause quite an overhead, if the sum is very big. Also,
 * for some target solvers this representation might be better if the
 * expressions must not be nested and functions can be defined.
 * 
 * @author andrea
 *
 */

public class QuantifiedSum implements ArithmeticExpression {

	
	private String[] quantifiedVariables;
	
	private Domain domain;
	private Expression quantifiedExpression;
	
	
	// =========== CONSTRUCTORS =============================
	
	public QuantifiedSum(String[] quantifiedVariables,
			             Domain quantifiedDomain,
			             Expression quantifiedExpression) {
	
		this.quantifiedVariables = quantifiedVariables;
		this.domain = quantifiedDomain;
		this.quantifiedExpression = quantifiedExpression;
	}
	
	
	// ========== INHERITED METHODS =========================
	
	public ArithmeticExpression copy() {
		String[] copiedVariables = new String[this.quantifiedVariables.length];
		
		for(int i=0; i<this.quantifiedVariables.length; i++)
			copiedVariables[i] = new String(this.quantifiedVariables[i]);
		
		return new QuantifiedSum(copiedVariables,
				                 this.domain.copy(),
				                 (ArithmeticExpression) this.quantifiedExpression.copy());
	}

	public ArithmeticExpression evaluate() {
		this.quantifiedExpression = this.quantifiedExpression.evaluate();
		this.domain = this.domain.evaluate();
		return this;
	}

	public int[] getDomain() {
		int f;
		// TODO Auto-generated method stub
		return null;
	}

	public int getType() {
		return Q_SUM;
	}

	public Expression insertValueForVariable(int value, String variableName) {
		this.domain = this.domain.insertValueForVariable(value, variableName);
		this.quantifiedExpression = this.quantifiedExpression.insertValueForVariable(value, variableName);
		return this;
	}

	public char isSmallerThanSameType(Expression e) {
	
		QuantifiedSum otherSum = (QuantifiedSum) e;
		
		if(this.quantifiedVariables.length == otherSum.quantifiedVariables.length) {
			
			if(this.quantifiedExpression.getType() == otherSum.quantifiedExpression.getType()) {
				return this.quantifiedExpression.isSmallerThanSameType(otherSum.quantifiedExpression);
			}
			else return (this.quantifiedExpression.getType() < otherSum.quantifiedExpression.getType()) ?
					SMALLER : BIGGER;
		}
		else 
		return (this.quantifiedVariables.length < otherSum.quantifiedVariables.length) ?
				SMALLER : BIGGER;
	}

	public void orderExpression() {
		this.quantifiedExpression.orderExpression();

	}

	public Expression reduceExpressionTree() {
		this.quantifiedExpression = this.quantifiedExpression.reduceExpressionTree();
		return this;
	}
	
	public String toString() {
		
		String s = "sum "+quantifiedVariables[0];
		
		for(int i=1; i<this.quantifiedVariables.length; i++)
			s = s.concat(","+quantifiedVariables[i]);
		
		s = s.concat(": "+this.domain+"\n");
		s = s.concat("\t"+this.quantifiedExpression);
		
		
		return s;
		
	}

}
