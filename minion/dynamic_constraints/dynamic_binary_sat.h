/* Minion Constraint Solver
   http://minion.sourceforge.net
   
   For Licence Information see file LICENSE.txt 

   $Id: dynamic_sum.h 398 2006-10-17 09:49:19Z gentian $
*/

/* Minion
* Copyright (C) 2006
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/



// VarToCount = 1 means leq, = 0 means geq.
template<typename VarArray>
struct BoolBinarySATConstraintDynamic : public DynamicConstraint
{
  virtual string constraint_name()
  { return "BoolBinarySATDynamic"; }
  
  typedef typename VarArray::value_type VarRef;
  
  VarRef var1;
  VarRef var2;
  
  BoolBinarySATConstraintDynamic(const VarArray& _var_array) :
	var1(_var_array[0]), var2(_var_array[1])
  { D_ASSERT(_var_array.size() == 2); }
  
  int dynamic_trigger_count()
  {
	D_INFO(2,DI_DYSUMCON,"Setting up Dynamic Trigger Constraint for BoolSATDinaryConstraintDynamic");
	return 2;
  }
    
  virtual void full_propogate()
  {
	DynamicTrigger* dt = dynamic_trigger_start();

	if(var1.isAssignedValue(false))
	{
	  var2.propogateAssign(true);
	  return;
	}
	
	if(var2.isAssignedValue(false))
	{
	  var1.propogateAssign(true);
	  return;
	}
	
	dt->trigger_info() = 0;
	var1.addDynamicTrigger(dt, UpperBound);
	
	++dt;
	
	dt->trigger_info() = 1;
	var2.addDynamicTrigger(dt, UpperBound);
	
	return;
  }
    
  DYNAMIC_PROPAGATE_FUNCTION(DynamicTrigger* dt)
  {
	int propval = dt->trigger_info();
	
	if(propval)
	  var1.propogateAssign(true);
	else
	  var2.propogateAssign(true);
  }
  
  virtual BOOL check_assignment(vector<int> v)
  {
    return v[0] || v[1];
  }
  
  virtual vector<AnyVarRef> get_vars()
  { 
    vector<AnyVarRef> vars;
	vars.reserve(2);
	vars.push_back(var1);
	vars.push_back(var2);
	return vars;  
  }
};

template<typename VarArray>
DynamicConstraint*
BoolSATBinaryConDynamic(const VarArray& _var_array)
{ return new BoolSATConstraintDynamic<VarArray>(_var_array); }