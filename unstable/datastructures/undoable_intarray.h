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

typedef int value_type;

class UndoableIntArray
{



  int _size;
  int _max_undos;
  int _max_depth;             

  int _local_depth;             // could be unsigned 
  ReversibleInt _backtrack_depth;

  MemOffset _array;
  MemOffset _undo_values;
  MemOffset _undo_indexes;
  
  BackTrackOffset backtrack_ptr;

  value_type& undo_values(int i)
  { return static_cast<value_type*>(_undo_values.get_ptr())[i]; }

  int& undo_indexes(int i)
  { return static_cast<int*>(_undo_indexes.get_ptr())[i]; }

public:
  // following allows external types destructive changes to array 
  // but we probably do not want to allow this to force them to use set()
  value_type& array(int i)
  { 
    D_ASSERT( i >= 0 && i < size());
    return static_cast<value_type*>(_array.get_ptr())[i]; 
  }

  bool needs_undoing()
  {
    D_ASSERT( _local_depth < _max_depth && _local_depth >= _backtrack_depth.get());

    return _local_depth > _backtrack_depth.get();
  }

  void undo()
  {
    int bt_depth = _backtrack_depth.get();

    // print_state(); cout << "About to undo: " ;
    D_ASSERT( _local_depth < _max_depth && _local_depth >= bt_depth && bt_depth >=0);

    for(; _local_depth > bt_depth; ) 
    {
      -- _local_depth;
      array(undo_indexes(_local_depth)) = undo_values(_local_depth);
    }
    // print_state(); 

    D_ASSERT(_local_depth == bt_depth);
  }

  void set(const int& index, const value_type& newval)
  {
    // cout << "index: " << index << " value: " << newval << " local: " << _local_depth << " bt: " << _backtrack_depth.get() << endl; 

    D_ASSERT( !needs_undoing() && 0 <= index && index < size());
    undo_indexes(_local_depth) = index;
    undo_values(_local_depth) = array(index);

    _local_depth++;
    _backtrack_depth.increment();
    
    array(index) = newval;
  }

  int size()
  {
    return _size;    
  }

void initialise(const int& size, const int& max_undos)
  { 
    _size = size;
    _max_undos = max_undos;

    // should put in a D_ASSERT on MAXINT here
    // D_ASSERT( max_undos < MAXINT - size);
    
    _max_depth = size+max_undos;
    _local_depth = 0;
    _backtrack_depth.set(0);

    _array.request_bytes(_size*sizeof(value_type)); 
    _undo_values.request_bytes(_max_depth*sizeof(value_type));
    _undo_indexes.request_bytes(_max_depth*sizeof(int));
    
#ifdef DEBUG
    cout << "initialising Undoable Array with value of size= " << size << endl;
#endif

#ifdef DEBUG
    // print_state();
#endif
  }


void print_state()
{
  cout << "printing state of UndoableIntArray: " ;
  cout << "array size: " << _size;
  cout << " local depth: " << _local_depth;
  cout << " backtracking depth: " << _backtrack_depth.get() ; 
  cout << endl << "   values: " ;
  for(int i = 0; i < _size; ++i) 
  { 
    cout << array(i) << " ";
  }
  cout << endl;
  cout << "  history: ";
  for(int i = 0; i < _local_depth ; ++i) 
  { 
    cout << "[" << undo_indexes(i) << ":" << undo_values(i) << "] " ;
  }
  cout << endl ;
}


};

