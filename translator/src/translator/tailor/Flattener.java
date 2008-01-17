package translator.tailor;

import translator.solver.*;
import java.util.ArrayList;
import java.util.HashMap;
import translator.expression.*;
import translator.normaliser.NormalisedModel;


public class Flattener {

	public final String AUXVARIABLE_NAME = "_aux";
	
	
	/** the target solver we want to tailor the model to */
	TargetSolver targetSolver;
	/** the list of all flattened constraints. constraints are added on the fly. */
	ArrayList<Expression> constraintBuffer;
	
	/** contains every (flattened) subexpression and it's corresponding variable*/
	HashMap<Expression,Variable> subExpressions;
	
	/** the normalised model contains the list of expression variables etc */
	NormalisedModel normalisedModel;
	
	// ========== CONSTRUCTOR ============================
	
	public Flattener(TargetSolver targetSolver,
			         NormalisedModel normalisedModel) {
		this.targetSolver = targetSolver;	
		this.normalisedModel = normalisedModel;
		this.constraintBuffer = new ArrayList<Expression>();
		this.subExpressions = new HashMap<Expression,Variable>();
	}
	
	// ========== METHODS ================================
	
	/**
	 * Flattens the model according to the target solver that has been specified.
	 * 
	 */
	public NormalisedModel flattenModel() {
		
		return this.normalisedModel;
	}
	
	
	/**
	 * Flatten the parameter constraint and return the corresponding constraint
	 * 
	 * @return the list of flattened constraints that represents the parameter constraint
	 */
	protected ArrayList<Expression> flattenConstraint(Expression constraint) 
		throws TailorException {
		
		this.constraintBuffer.clear();
		
		if(this.targetSolver.supportsNestedExpressions()) {
			this.constraintBuffer.add(constraint);
			return this.constraintBuffer;
		}
		
		// else flatten the constraint
		Expression topExpression = flattenExpression(constraint);
		ArrayList<Expression> flattenedSubExpressions = this.constraintBuffer;
		flattenedSubExpressions.add(topExpression);
		
		// and return the constraint list
		return flattenedSubExpressions;
	}
	
	/**
	 * Flatten the parameter expression. Constraints that are added during the flattening process are stored 
	 * in the constraintList.
	 * 
	 * @param expression
	 * @return the eflattened xpression that is representative for the parameter expression. If other constraints
	 * have been added during the flattening process, they are stored in the constraintList.
	 * @throws TailorException
	 */
	protected Expression flattenExpression(Expression expression) 
		throws TailorException {
		
		if(expression instanceof RelationalExpression)
			return flattenRelationalExpression((RelationalExpression) expression);
		
		else if(expression instanceof ArithmeticExpression)
			return flattenArithmeticExpression((ArithmeticExpression) expression);
		
		else throw new TailorException("Unknown expression type (neither relational nor arithmetic):"+expression);
	}
	
	/**
	 * Flatten a relational expression
	 * 
	 * @param expression
	 * @return
	 */
	private  Expression flattenRelationalExpression(RelationalExpression expression) 
		throws TailorException {
		
		if(expression instanceof RelationalAtomExpression) 
			return flattenRelationalAtomExpression((RelationalAtomExpression) expression);
		
		else if(expression instanceof UnaryRelationalExpression) 
			return flattenUnaryRelationalExpression( (UnaryRelationalExpression) expression);
		
		else if(expression instanceof NonCommutativeRelationalBinaryExpression)
			return flattenNonCommutativeRelationalBinaryExpression((NonCommutativeRelationalBinaryExpression) expression);
		
		else if(expression instanceof QuantifiedExpression)
			return flattenQuantifiedExpression((QuantifiedExpression) expression);
		
		else throw new TailorException("Cannot tailor relational expression yet, or unknown expression:"+expression);
	}
	
	
	
	/**
	 * Flatten unary expressions. Depending on if the expression will be reified and if it
	 * has to be reified for the solver and if the solver supports the reification of the 
	 * constraint, it is flattened.
	 * 
	 * @param expression
	 * @return
	 * @throws TailorException
	 */
	private Expression flattenUnaryRelationalExpression(UnaryRelationalExpression expression) 
		throws TailorException {
		
		// first flatten the argument
		Expression argument = flattenExpression(expression.getArgument());
		
		
		// then continue to flatten the whole expression
		if(expression.getType() == Expression.NEGATION) {
			if(this.targetSolver.supportsUnnestedNegation())
				return new Negation(argument);
			else {
			   return reifyConstraint(new Negation(argument));
			}
		}
		else if(expression.getType() == Expression.ALLDIFFERENT) {
			if(expression.isGonnaBeReified()) {
                return reifyConstraint(new AllDifferent(argument));	
			}
			else return new AllDifferent(argument);
		}
		
		else throw new TailorException("Unknown unary relational expression:"+expression); 
	}
	
	
	
	

	
	/**
	 * Flatten atom expressions. If the target solver supports indexing with decision variables,
	 * we need not further flatten expressions. If it does not, we have to flatten the 
	 * atom expression to an element constraint
	 * @param atom
	 * @return
	 * @throws TailorException
	 */
	private Expression flattenRelationalAtomExpression(RelationalAtomExpression atom) 
		throws TailorException {
		
		// if the target solver allows stuff like m[x,y+1] where m,x,y are decision variables,
		// we need not have to flatten it
		if(this.targetSolver.supportsVariableArrayIndexing())
			return atom;
		
		if(atom.getType() == Expression.BOOL_VARIABLE_ARRAY_ELEM) {
			Variable arrayVariable = atom.getVariable();
			if(arrayVariable.getType() == Expression.ARRAY_VARIABLE) {
				Expression[] indices = ((ArrayVariable) arrayVariable).getExpressionIndices();
				if(indices != null) {
					// translate to an element constraint if the target solver supports it
					// find out if the element constraint has to be nested too!!
				}
			}
			else throw new TailorException("Cannot dereference variable that is not of type array:"+atom);
		}
		
		return atom;
	}
	
	
	/**
	 * Flattens non-commutative binary relational expressions, such as <,>=, =>, lex> etc. Simply flattens the 
	 * subexpressions and returns a new expression
	 * @param expression
	 * @return
	 * @throws TailorException
	 */
	private Expression flattenNonCommutativeRelationalBinaryExpression(NonCommutativeRelationalBinaryExpression expression )
		throws TailorException {
		// we need to variables representing left and right argument
		expression.getLeftArgument().willBeReified(true);
		expression.getRightArgument().willBeReified(true);
		
		// flatten the subexpressions
		Expression leftFlattenedArgument = flattenExpression(expression.getLeftArgument());
		Expression rightFlattenedArgument = flattenExpression(expression.getRightArgument());
		
		if(expression.isGonnaBeReified()) {
			return reifyConstraint(new NonCommutativeRelationalBinaryExpression(leftFlattenedArgument,
					                                                           expression.getOperator(),
					                                                           rightFlattenedArgument));
		}
		
		else return new NonCommutativeRelationalBinaryExpression(leftFlattenedArgument,
				                            expression.getOperator(),
				                            rightFlattenedArgument);
	}
	
	/**
	 * 
	 * @param quantification
	 * @return
	 * @throws TailorException
	 */
	private Expression flattenQuantifiedExpression(QuantifiedExpression quantification)
		throws TailorException {
		
		// ----- 1. get the domain over which the quantification ranges ------------
		Domain bindingDomain = quantification.getQuantifiedDomain();
		int domainType = bindingDomain.getType();
		if(domainType != Domain.BOOL &&
			domainType!= Domain.INT_BOUNDS &&
			  domainType != Domain.INT_SPARSE)
			throw new TailorException("Cannot unfold quantified expression '"+quantification
					+"'. The binding domain is not entirely constant:"+bindingDomain);
		
		int[] domainElements = null;
		if(domainType == Domain.BOOL)
			domainElements = ((BoolDomain) bindingDomain).getFullDomain();
		
		else if(domainType == Domain.INT_BOUNDS) 
			domainElements = ((BoundedIntRange) bindingDomain).getFullDomain();
		
		else domainElements = ((SparseIntRange) bindingDomain).getFullDomain();
		
		
		// 2. ----- get the binding variables ----------------------------------
		String[] bindingVariables = quantification.getQuantifiedVariables();
		ArrayList<String> variableList = new ArrayList<String>();
		for(int i=0; i<bindingVariables.length; i++)
			variableList.add(bindingVariables[i]);
		
		ArrayList<Expression> unfoldedExpressions = insertVariablesForValues(variableList,
				                                                             domainElements,
				                                                             quantification.getQuantifiedExpression());
		
		
		// insert binding variables' values into the expression
		
		// put every variant into the disjunction/conjunction
		
		
		return null;
	}
	
	
	/**
	 * This is a helper function for flattening quantified expressions (the unfolding of the quantification). The variablelist containts all binding 
	 * variables that should be inserted intp the expression.
	 * 
	 * @param variableList
	 * @param values
	 * @param expression
	 * @return
	 * @throws TailorException
	 */
	private ArrayList<Expression> insertVariablesForValues(ArrayList<String> variableList, int[] values, Expression expression)
		throws TailorException {
		
		ArrayList<Expression> unfoldedExpressions = new ArrayList<Expression>();
		
		// this is the last variable we have to insert
		if(variableList.size() == 1) {
			String variableName = variableList.get(0);
			for(int i=0; i<values.length; i++) {
				Expression unfoldedExpression = expression.copy().insertValueForVariable(values[i], variableName);
				unfoldedExpressions.add(unfoldedExpression);
			}
			return unfoldedExpressions;
		}
		// we have some more variables to insert into the expression
		else {
			String variableName = variableList.remove(0);
			for(int i=0; i<values.length; i++) {
				Expression unfoldedExpression = expression.copy().insertValueForVariable(values[i], variableName);
				ArrayList<Expression> furtherUnfoldedExpressions = insertVariablesForValues(variableList, values, unfoldedExpression);
				for(int j=0; j<furtherUnfoldedExpressions.size(); j++)
					unfoldedExpressions.add(furtherUnfoldedExpressions.remove(i));
			}
			
		}
		
		return unfoldedExpressions;
	}
	/**
	 * 
	 * @param expression
	 * @return
	 * @throws TailorException
	 */
	private Expression flattenCommutativeBinaryRelationalExpression(CommutativeBinaryRelationalExpression expression )
	throws TailorException {
	
	// detect products, sums etc. before flattening the arguments!	
		return expression;
	}
	
	
	/**
	 * 
	 * @param expression
	 * @return
	 */
	private Expression flattenArithmeticExpression(ArithmeticExpression expression) {
		
		return expression;
	}
	
	
	
	// ========================= GENERAL HELPER METHODS ===========================
	
	/**
	 * reify the constraint and return the auxiliary variable that "represents" it.
	 * If there is a common subexpression, the corresponding auxiliary variable is 
	 * picked.
	 * @param constraint that is definetly reifiable by the target solver
	 * @return the auxiliary variable that represents the expression
	 */
	private RelationalAtomExpression reifyConstraint(Expression constraint) 
		throws TailorException {
		
		if(!this.targetSolver.supportsReificationOf(constraint.getType()))
			throw new TailorException
			("Cannot reify constraint because its reification is not supported by the target solver:"+constraint);
		
		Variable auxVariable = null;
		
		// if we have a common subexpression, use the corresponding variable
		if(this.subExpressions.containsKey(constraint))
			auxVariable = this.subExpressions.get(constraint);
		
		// if we have no common subexpression, create a new auxiliary variable
		// and add the constraint to the list of subexpressions
		else  {
			auxVariable = createAuxVariable(0, 1);
			this.subExpressions.put(constraint,auxVariable);
		}
		
		// add the flattened constraint to the constraint buffer
		this.constraintBuffer.add(new Reification(constraint,
				                                  auxVariable));
		
		return new RelationalAtomExpression(auxVariable);
	}
	
	/**
	 * Create an auxiliary variable. It is added to the normalised model
	 * and is set to be searched on if specified in the target solver.
	 * 
	 * @param lb
	 * @param ub
	 * @return an auxiliary variables over the bounds lb,ub.
	 */
	private Variable createAuxVariable(int lb, int ub) {
		
		Variable auxVariable = null;
		
		if(lb ==0 && ub == 1)
			auxVariable = new SingleVariable(AUXVARIABLE_NAME,
					                         new BoolDomain());
		else auxVariable = new SingleVariable(AUXVARIABLE_NAME, 
				                                  new BoundedIntRange(lb,ub));
		
		if(!this.targetSolver.willSearchOverAuxiliaryVariables())
			auxVariable.setToSearchVariable(false);
		
		this.normalisedModel.addAuxiliaryVariable(auxVariable);
		
		return auxVariable;
	}
}
