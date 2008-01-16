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

// This file is designed to encapsulate all time keeping done by Minion.

VARDEF(clock_t _internal_start_time);
VARDEF(clock_t _last_check_time);

inline void start_clock()
{
  _internal_start_time = clock();
  _last_check_time = _internal_start_time;
}

inline bool check_timeout(unsigned seconds)
{ return (clock() - _internal_start_time) >= (seconds * CLOCKS_PER_SEC); }

inline void print_timestep_without_reset(char* time_name)
{
  cout << time_name << (clock() - _last_check_time) / (1.0 * CLOCKS_PER_SEC) << endl;
} 
inline void print_timestep(char* time_name)
{
  clock_t current_time = clock();
  cout << time_name << (current_time - _last_check_time) / (1.0 * CLOCKS_PER_SEC) << endl;
  _last_check_time = current_time;
}

inline void print_finaltimestep(char* time_name)
{
  print_timestep(time_name);
  cout << "Total Time: " << (_last_check_time - _internal_start_time) / (1.0 * CLOCKS_PER_SEC) << endl;
  
}
