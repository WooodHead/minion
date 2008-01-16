/* Minion Constraint Solver
   http://minion.sourceforge.net
   
   For Licence Information see file LICENSE.txt 

   $Id$
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

#include "minion.h"

int main(void)
{
  BigRangeVarRef b1 = big_rangevar_container.get_new_var(1,1);
  Controller::lock();
  D_ASSERT(b1.inDomain(1));
  D_ASSERT(b1.getMin() == 1);
  D_ASSERT(b1.getMax() == 1);
  D_ASSERT(b1.isAssigned());
  D_ASSERT(b1.getAssignedValue() == 1);
  Controller::finish();
}

