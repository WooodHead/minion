/* Minion Constraint Solver
   http://minion.sourceforge.net
   
   For Licence Information see file LICENSE.txt 

   $Id: constraint_alldiff_gac_slow.h 668 2007-09-26 15:14:50Z pete_n $
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


#include "alldiff_common.h"

template<typename VarArray>
AbstractConstraint*
GacAlldiffCon(StateObj* stateObj, const VarArray& var_array)
{ return new GacAlldiff<VarArray>(stateObj, var_array); }

BUILD_CONSTRAINT1(CT_GACALLDIFF, GacAlldiffCon)


