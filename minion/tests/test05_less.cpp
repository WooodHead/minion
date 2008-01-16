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
#include "test_functions.h"

int main(void)
{
  vector<LRangeVarRef> vars;
  vector<LRangeVarRef> var1(2);
  vector<LRangeVarRef> var2(2);
  for(int i=0;i<4;i++)
    vars.push_back(rangevar_container.get_new_var(0,30));
  
  for(int i=0;i<2;i++)
  {
    var1[i]=vars[i];
    var2[i]=vars[i+2];
  }
  
  Constraint* c = LeqCon(var1[0],var1[1],runtime_val(1));
  Constraint* d = LeqCon(var2[0],var2[1],runtime_val(1));
  Constraint* e = c->get_table_constraint();
  Controller::add_constraint(d);
  Controller::add_constraint(e);
  Controller::lock();
  test_equal(var1,var2);
}





