package translator.tailor.minion;

import translator.expression.*;
import java.util.ArrayList;
import java.util.HashMap;
import translator.normaliser.NormalisedModel;
import translator.solver.Minion;

public class MinionTailor {

	public final String MINION_AUXVAR_NAME = "_aux";
	int noMinionAuxVars;
	
	MinionModel minionModel;
	HashMap<String, int[]> offsetsFromZero;
	NormalisedModel normalisedModel;
	Minion solverSettings;
	
	
	// ======== CONSTRUCTOR ==================================
	
	public MinionTailor(NormalisedModel normalisedModel,
						Minion solverSettings) {
			
		this.offsetsFromZero = new HashMap<String,int[]>();
		this.normalisedModel = normalisedModel;
		this.solverSettings = solverSettings;
		this.noMinionAuxVars = 0;
	}
	
	// ====== TRANSLATION TO MINION REPRESENTATION ===========
	
	
	public MinionModel tailorToMinion() 
		throws MinionException {
		
		// 1. tailor the variables and create a new empty model
		this.minionModel = new MinionModel(new ArrayList<MinionConstraint>(),
				                           mapDecisionVariables(),
				                           this.normalisedModel.getDecisionVariablesNames(),
				                           this.normalisedModel.getAuxVariables(),
				                           this.solverSettings
				                           );
		
		// 2. tailor the constraints
		for(int i=this.normalisedModel.getConstraints().size()-1; i>=0; i--) 
			minionModel.addConstraint(toMinion(this.normalisedModel.getConstraints().remove(i)));
		
		
		return minionModel;
		
		
		
	}
	
	
	/**
	 * Tailors the normalised model, that was given in the constructor, to 
	 * a minion model.
	 * 
	 */
	public MinionModel tailorToMinion(NormalisedModel normalisedModel) 
		throws MinionException {
		
		this.normalisedModel = normalisedModel;
		
		// 1. tailor the variables and create a new empty model
		MinionModel minionModel = new MinionModel(new ArrayList<MinionConstraint>(),
				                           mapDecisionVariables(),
				                           this.normalisedModel.getDecisionVariablesNames(),
				                           this.normalisedModel.getAuxVariables(),
				                           this.solverSettings
				                           );
		
		// 2. tailor the constraints
		for(int i=this.normalisedModel.getConstraints().size()-1; i>=0; i--) 
			minionModel.addConstraint(toMinion(this.normalisedModel.getConstraints().remove(i)));
		
		
		return minionModel;
	}
	
	
	/**
	 * 
	 * @return
	 * @throws MinionException
	 */
	protected HashMap<String, ConstantDomain> mapDecisionVariables() 
		throws MinionException {
		
		HashMap<String,ConstantDomain> decisionVariables = new HashMap<String, ConstantDomain>();
		ArrayList<String> decisionVariablesNames = this.normalisedModel.getDecisionVariablesNames();
		
		for(int i=0; i<decisionVariablesNames.size(); i++) {
			Domain domain = this.normalisedModel.getDomainOfVariable(decisionVariablesNames.get(i));
			if(domain instanceof ConstantDomain)
				decisionVariables.put(decisionVariablesNames.get(i), (ConstantDomain) domain);
			else throw new MinionException("Found non-constant domain:"+domain+". Please define all parameters.");
			
		}
		
		return decisionVariables;
	}
	
	/**
	 * 
	 * @param constraint that should be flattened to Minion level.
	 * @return the String representation in Minion for the given 
	 * expression. 
	 */
	protected MinionConstraint toMinion(Expression constraint) 
		throws MinionException {
		
		if(constraint instanceof ArithmeticAtomExpression)
			return toMinion((ArithmeticAtomExpression) constraint);
		
		if(constraint instanceof CommutativeBinaryRelationalExpression)
			return toMinion((CommutativeBinaryRelationalExpression) constraint);
		
		if(constraint instanceof NonCommutativeRelationalBinaryExpression)
			return toMinion((NonCommutativeRelationalBinaryExpression) constraint);
		
		if(constraint instanceof SumConstraint)
			return toMinion((SumConstraint) constraint);
		
		return null;
	}
	
	
	
	
	/**
	 * Translate a sum constraint to Minion. A sum constraint is a collection
	 * of positive and negative arguments that are added up and in a relation
	 * (<,>,=,!=,<=,>=) to a certain result expression.
	 * 
	 * 
	 * @param sumConstraint
	 * @return
	 * @throws MinionException
	 */
	private MinionConstraint toMinion(SumConstraint sumConstraint) 
		throws MinionException {
		
		int operator = sumConstraint.getRelationalOperator();
		
		if(operator == Expression.LESS ||
				operator == Expression.GREATER ||
				operator == Expression.NEQ)
			return toMinionStrongIneqSumConstraint(sumConstraint);
		
		else 
			return toMinionWeakIneqSumConstraint(sumConstraint);
		
	}
	
	
	private MinionConstraint toMinionStrongIneqSumConstraint(SumConstraint sumConstraint)
		throws MinionException {
		
		int f;
		// TODO!
		return null;
	}
	
	
	/**
	 * Translate SumConstraints that have the following operators:
	 * <=. >=. =
	 * 
	 * @param sumConstraint
	 * @return
	 * @throws MinionException
	 */
	private MinionConstraint toMinionWeakIneqSumConstraint(SumConstraint sumConstraint)
		throws MinionException {
	
		// don't use watched or weighted sum then!
		// we have to reify the sum!!
		if(sumConstraint.isGonnaBeReified()) {
			
			Expression[] positiveArgs = sumConstraint.getPositiveArguments();
			Expression[] negativeArgs = sumConstraint.getNegativeArguments();
			
			MinionAtom[] arguments = new MinionAtom[positiveArgs.length+negativeArgs.length];
			
			for(int i=0; i<positiveArgs.length; i++) {
				positiveArgs[i].willBeFlattenedToVariable(true);
				arguments[i] = (MinionAtom) toMinion(positiveArgs[i]);
			}
			Expression resultExpression = sumConstraint.getResult();
			resultExpression.willBeFlattenedToVariable(true);
			MinionAtom result = (MinionAtom) toMinion(resultExpression);
			
			int operator = sumConstraint.getRelationalOperator();
			
			if(operator == Expression.LEQ) 
				return reifyMinionConstraint(new SumLeqConstraint(arguments, result));
			
			else if(operator == Expression.GEQ)
				return reifyMinionConstraint(new SumGeqConstraint(arguments,result));
			
			else if(operator == Expression.EQ) {
				SumLeqConstraint sum1 = new SumLeqConstraint(arguments, result);
				SumGeqConstraint sum2 = new SumGeqConstraint(arguments, result);
				
				MinionAtom auxVariable1 = reifyMinionConstraint(sum1);
				MinionAtom auxVariable2 = reifyMinionConstraint(sum2);
				
				MinionAtom reifiedVariable = createMinionAuxiliaryVariable();
				
				ProductConstraint conjunction = new ProductConstraint(auxVariable1,auxVariable2,reifiedVariable);
				this.minionModel.addConstraint(conjunction);
				
				return reifiedVariable;
				
			}
			else throw new MinionException("Interal error: expected only weak operator instead of operator '"
					+operator+"' in constraint:"+sumConstraint);
		}
		
		// else: we don't have to reify the constraint
		else {
			
			Expression[] positiveArgs = sumConstraint.getPositiveArguments();
			Expression[] negativeArgs = sumConstraint.getNegativeArguments();
			
			boolean hasLinearArguments = true;
			boolean hasNegativeArguments = (negativeArgs.length > 0) ? true : false;
			
			for(int i=0; i<positiveArgs.length; i++) {
				if(positiveArgs[i] instanceof UnaryMinus)
					hasNegativeArguments = true;
				
				if(positiveArgs[i] instanceof Multiplication) {
					ArrayList<Expression> productArgs = ((Multiplication) positiveArgs[i]).getArguments();
					if(productArgs.size() > 2) 
						throw new MinionException("Expected only binary product constraint instead of:"+positiveArgs[i]);
					positiveArgs[i].orderExpression(); // now a constant has to be in the first position
					if(positiveArgs[0].getType() != Expression.INT)
						hasLinearArguments = false;
					
				}
			}
			
			int operator = sumConstraint.getRelationalOperator();
			
			if(hasLinearArguments) {
				// TODO!!
				int f;
			}
			else if(hasNegativeArguments) {
			// TODO!!	
			}
			
			// positive, non linear
			else {
				boolean areBooleanArguments = true;
				
				MinionAtom[] arguments = new MinionAtom[positiveArgs.length];
				for(int i=0; i<positiveArgs.length; i++) {
					positiveArgs[i].willBeFlattenedToVariable(true);
					arguments[i] = (MinionAtom) toMinion(positiveArgs[i]);
					if(areBooleanArguments) {
						if(!this.minionModel.variableHasBooleanDomain(arguments[i].getVariableName())) {
							areBooleanArguments = false;
						}
					}
				}
				Expression resultExpression = sumConstraint.getResult();
				resultExpression.willBeFlattenedToVariable(true);
				MinionAtom result = (MinionAtom) toMinion(resultExpression);
				
				
				if(operator == Expression.LEQ) {
					if(this.minionModel.solverSettings.useWatchedSum()) {
						if(areBooleanArguments) {
							return new SumLeqConstraint(arguments, result, true);
						}
						else return new SumLeqConstraint(arguments, result, false);
					}
					else return new SumLeqConstraint(arguments, result);
				}
				
				else if(operator == Expression.GEQ) {
					if(this.minionModel.solverSettings.useWatchedSum()) {
						if(areBooleanArguments) {
							return new SumGeqConstraint(arguments, result, true);
						}
						else return new SumGeqConstraint(arguments, result, false);
					}
					else return new SumGeqConstraint(arguments, result);	
				}
				
				else if(operator == Expression.EQ) {
					if(this.minionModel.solverSettings.useWatchedSum()) {
						if(areBooleanArguments) {
							this.minionModel.addConstraint(new SumLeqConstraint(arguments, result, true));
							return new SumGeqConstraint(arguments, result, true);
						}
						else {
							this.minionModel.addConstraint(new SumLeqConstraint(arguments, result, false));
							return new SumGeqConstraint(arguments, result, false);
						}
					}
					else {
						this.minionModel.addConstraint(new SumLeqConstraint(arguments, result));
						return new SumGeqConstraint(arguments, result);	
					}
				}
				else throw new MinionException("Internal error: expected only sumconstraint with operators =,<=,>= instead of:"+sumConstraint);
			}
		}
		return null;
}
	
	
	
	/**
	 * Converts non commutative binary relations to Minion representation
	 * 
	 * @param constraint
	 * @return
	 * @throws MinionException
	 */
	private MinionConstraint toMinion(NonCommutativeRelationalBinaryExpression constraint) 
		throws MinionException {
		
		// lex-constraints!!!! -> move them to another expression type!!
		
		
		int operator = constraint.getOperator();
		Expression leftExpression = constraint.getLeftArgument();
		ArithmeticAtomExpression leftArgument = null;
		Expression rightExpression = constraint.getRightArgument();
		ArithmeticAtomExpression rightArgument = null;
		
		// get the left and right atoms 
		if(!(leftExpression instanceof ArithmeticAtomExpression)) {
			if(leftExpression instanceof RelationalAtomExpression) 
				leftArgument = ((RelationalAtomExpression)leftExpression).toArithmeticExpression();
			else throw new MinionException("Cannot translate constraint nested in another expression as in:"+constraint);	
				
		}
		else leftArgument = (ArithmeticAtomExpression) leftExpression;
		
		if(!(rightExpression instanceof ArithmeticAtomExpression)) {
			if(rightExpression instanceof RelationalAtomExpression) 
				rightArgument = ((RelationalAtomExpression)rightExpression).toArithmeticExpression();
			else throw new MinionException("Cannot translate constraint nested in another expression as in:"+constraint);	
				
		}
		else rightArgument = (ArithmeticAtomExpression) rightExpression;
		
		MinionConstraint minionConstraint = null;
		
		if(operator == Expression.LEQ)
			minionConstraint =new IneqConstraint(toMinion(leftArgument), toMinion(rightArgument),0);
		
		else if(operator == Expression.GEQ)
			minionConstraint = new IneqConstraint(toMinion(rightArgument), toMinion(leftArgument), 0);
		
		else if(operator == Expression.LESS)
			minionConstraint = new IneqConstraint(toMinion(leftArgument), toMinion(rightArgument),-1);
		
		else if(operator == Expression.GREATER)
			minionConstraint = new IneqConstraint(toMinion(rightArgument), toMinion(leftArgument), -1);
		
		else if(operator == Expression.IF)
			minionConstraint = new IneqConstraint(toMinion(leftArgument), toMinion(rightArgument),0);
		
		else throw new MinionException("Unknown non-commutative binary relation:"+constraint);
		
		
		if(constraint.isGonnaBeReified()) {
			return reifyMinionConstraint(minionConstraint);
		}
		else return minionConstraint;
	}
	
	
	
	/**
	 * Converts a commutative binary relational expression into the appropriate minion
	 * constraint. IMPORTANT NOTE: requires the arguments to be atoms. 
	 * 
	 * @param constraint
	 * @return
	 * @throws MinionException
	 */
	private MinionConstraint toMinion(CommutativeBinaryRelationalExpression constraint) 
		throws MinionException {
		
		int operator = constraint.getOperator();
		Expression leftExpression = constraint.getLeftArgument();
		ArithmeticAtomExpression leftArgument = null;
		Expression rightExpression = constraint.getRightArgument();
		ArithmeticAtomExpression rightArgument = null;
		
		// get the left and right atoms 
		if(!(leftExpression instanceof ArithmeticAtomExpression)) {
			if(leftExpression instanceof RelationalAtomExpression) 
				leftArgument = ((RelationalAtomExpression)leftExpression).toArithmeticExpression();
			else throw new MinionException("Cannot translate constraint nested in another expression as in:"+constraint);	
				
		}
		else leftArgument = (ArithmeticAtomExpression) leftExpression;
		
		if(!(rightExpression instanceof ArithmeticAtomExpression)) {
			if(rightExpression instanceof RelationalAtomExpression) 
				rightArgument = ((RelationalAtomExpression)rightExpression).toArithmeticExpression();
			else throw new MinionException("Cannot translate constraint nested in another expression as in:"+constraint);	
				
		}
		else rightArgument = (ArithmeticAtomExpression) rightExpression;
		
		MinionConstraint minionConstraint = null;
		
		if(operator == Expression.EQ ||
				operator == Expression.IFF) {
			
			minionConstraint =  new EqConstraint(toMinion(leftArgument), 
					                toMinion(rightArgument));
		}
		else if(operator == Expression.NEQ) {
			minionConstraint =  new DiseqConstraint(toMinion(leftArgument), 
	                toMinion(rightArgument));
		}
		else throw new MinionException("Unknown commutative binary relation:"+constraint);	
		
		if(constraint.isGonnaBeReified()) {
			return reifyMinionConstraint(minionConstraint);
		}
		else return minionConstraint;
		
		
	}
	
	
	/**
	 * 
	 * @param constraint
	 * @return
	 * @throws MinionException
	 */
	private MinionAtom reifyMinionConstraint(MinionConstraint constraint)
		throws MinionException {
		

		MinionAtom auxVariable = createMinionAuxiliaryVariable();
		
		MinionConstraint reifiedConstraint = new Reify(constraint, auxVariable);
		this.minionModel.addConstraint(reifiedConstraint);
		
		return auxVariable;
	}
	
	
	private MinionAtom createMinionAuxiliaryVariable() {
		
		SingleVariable auxVar = new SingleVariable(MINION_AUXVAR_NAME+this.noMinionAuxVars++,
                new translator.expression.BoolDomain());
		MinionSingleVariable auxVariable = new MinionSingleVariable(MINION_AUXVAR_NAME+(this.noMinionAuxVars-1));
		this.minionModel.addAuxiliaryVariable(auxVar);
		
		return auxVariable;
	}
	
	/**
	 * Return the String representation of the Atomic arithmetic expression.
	 * 
	 * NOTE: there are ONLY CONSTANT indices allowed now. Every occurrence
	 * of another expression should have been flattened to an element constraint
	 * by now.
	 * 
	 * @param atom
	 * @return
	 * @throws MinionException
	 */
	private MinionAtom toMinion(ArithmeticAtomExpression atom) 
		throws MinionException {
		
		if(atom.getType() == Expression.INT)
			return new MinionConstant(atom.getConstant());
		
		else {
			Variable variable = atom.getVariable();
			
			if(variable instanceof ArrayVariable) {
				ArrayVariable arrayElement = (ArrayVariable) variable;
				
				if(this.offsetsFromZero.containsKey(arrayElement.getArrayNameOnly())) {
					int[] offsets = this.offsetsFromZero.get(((ArrayVariable) variable).getArrayNameOnly());
					
					int[] indices = arrayElement.getIntegerIndices();
					if(indices ==null) 
						throw new MinionException("Cannot translate array element with non-constant element index:"+atom);
					
					for(int i=0; i<indices.length;i++) {
						indices[i] = indices[i]-offsets[i];
					}
					return new MinionArrayElement(arrayElement.getArrayNameOnly(),indices);
				}
				else throw new MinionException("Cannot find offsets for array element:"+atom);
			}
			else return new MinionSingleVariable(variable.getVariableName());
		}

	}
	
}