#include "../minion.h"
/* Minion Constraint Solver
   http://minion.sourceforge.net
   
   For Licence Information see file LICENSE.txt 
*/

#include "../constraints/constraint_pow.h"

template<typename V1, typename V2>
inline AbstractConstraint*
BuildCT_POW(StateObj* stateObj, const V1& vars, const V2& var2, ConstraintBlob&)
{
  D_ASSERT(vars.size() == 2);
  D_ASSERT(var2.size() == 1);
  return new PowConstraint<typename V1::value_type, typename V1::value_type,
                           typename V2::value_type>(stateObj, vars[0], vars[1], var2[0]);
}

BUILD_CT(CT_POW, 2)