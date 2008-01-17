package translator.tailor;

//import translator.expression.Expression;
import translator.normaliser.NormalisedModel;
import translator.solver.TargetSolver;
//import java.util.ArrayList;
import translator.solver.*;
import translator.tailor.minion.MinionTailor;
import translator.tailor.minion.MinionException;

public class Tailor implements TailorSpecification {

	NormalisedModel problemModel;
	TargetSolver targetSolver;
	
	
	// =================== CONSTRUCTOR ========================
	
	public Tailor(NormalisedModel model,
		          TargetSolver targetSolver) {
		
		this.problemModel = model;
		this.targetSolver= targetSolver;
	}
	 
	// ================== INHERITED METHODS ==================
	
	
	public String tailor(NormalisedModel normalisedModel)
			throws TailorException, MinionException  {
		this.problemModel = normalisedModel;
		
		if(targetSolver instanceof Minion) {
			MinionTailor minionTailor = new MinionTailor(this.problemModel,
					                                    (Minion) targetSolver);
			return minionTailor.tailorToMinion().toString();
		}
		
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	public String tailor(NormalisedModel normalisedModel,
			             TargetSolver targetSolver)
	throws TailorException {
		this.problemModel = normalisedModel;
		this.targetSolver = targetSolver;
         // 	TODO Auto-generated method stub
		
		return null;
	}


	/**
	 * Flatten the problem model that was given in the constructor
	 * and return its String representation.
	 * @return the String representation of the flattened model
	 * 
	 * @throws TailorException
	 */
	public NormalisedModel flattenModel() throws TailorException {
		
		Flattener flattener = new Flattener(this.targetSolver,
										    this.problemModel);
		
		this.problemModel =  flattener.flattenModel();
		return this.problemModel;
	}
	
	
	
	
}
