package minionModel;

/**
   MinionWeightedGeqSumConstraint 
*/

public class MinionWeightedSumGeqConstraint extends MinionReifiableConstraint {

    MinionIdentifier[] variables;
    MinionConstant[] constants;
    MinionIdentifier result; 
    boolean[] argPolarities;   
    boolean resultPolarity;
    
    public MinionWeightedSumGeqConstraint(MinionIdentifier[] vars, MinionConstant[] consts, MinionIdentifier r) 
	throws MinionException {
	if(consts.length != vars.length)
	    throw new MinionException
		("Weighted geq-sum with different amount of variables and constants: "
		 +vars.toString()+","+consts.toString());
	variables = vars;
	constants = consts;
	result = r;
	
	argPolarities = adjustVariablePolarity(vars);
	resultPolarity = true;
	if(result.getPolarity() ==0) {
		resultPolarity = false;
		result.setPolarity(1);
	}
    }

    public String toString() {
    	
		String r = "weightedsumgeq(["+constants[0];

		for(int i = 1; i < constants.length; i++) 
		    r = r.concat(", "+constants[i]);
	  	String p = (argPolarities[0]) ? "" : "n";
		r = r.concat("],["+p+variables[0]);

		for(int i = 1; i < variables.length; i++) {
			String polarity = (argPolarities[i]) ? "" : "n";
		    r = r.concat(", "+polarity+variables[i]);		
		}
		String resultP = (resultPolarity) ? "" : "n";
		return r.concat("], "+resultP+result+")\n");
    	
    
    }

}
