/*
* Minion http://minion.sourceforge.net
* Copyright (C) 2006-09
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

#include <cmath>
#include <algorithm>

std::vector<std::vector<int> > 
build_graph(std::vector<std::set<int> > graph, const std::vector<std::set<int> >& partition);

template<typename Name = string, typename Colour = string>
struct Graph
{
  set<pair<Name, Name> > graph;
  map<Name, set<Colour> > aux_vertex_colour;
  map<Name, set<Colour> > var_vertex_colour;
  int free_vertices;
  
  Graph() : free_vertices(0) { }
    
  Name new_vertex()
  {
    free_vertices++;
    return "F." + to_string(free_vertices);
  }
  
  Name new_vertex(string colour)
  {
    free_vertices++;
    string s =  "F." + colour + "." + to_string(free_vertices);
    aux_vertex_colour[colour].insert(s);
    return s;
  }
  
  void output_graph()
   {
     for(map<string, set<string> >::iterator it = var_vertex_colour.begin();
     it != var_vertex_colour.end();
     ++it)
     {
       cout << it->first << " : ";
       for(set<string>::iterator it2 = it->second.begin(); it2 != it->second.end(); it2++)
         cout << *it2 << " ";
       cout << endl;
     }


     for(map<string, set<string> >::iterator it = aux_vertex_colour.begin();
     it != aux_vertex_colour.end();
     ++it)
     {
       cout << it->first << " : ";
       for(set<string>::iterator it2 = it->second.begin(); it2 != it->second.end(); it2++)
         cout << *it2 << " ";
       cout << endl;
     }

     cout << endl;

     for(set<pair<string, string> >::iterator it = graph.begin();
     it != graph.end();
     ++it)
     {
       cout << it->first << ", " << it->second << endl;
     }
   }

   void output_nauty_graph(CSPInstance& csp)
   {

     map<string, int> v_num;

     int var_vertex_count = 0, aux_vertex_count = 0;
     for(map<string, set<string> >::iterator it = var_vertex_colour.begin(); it != var_vertex_colour.end(); ++it)  
       var_vertex_count += it->second.size();

     for(map<string, set<string> >::iterator it = aux_vertex_colour.begin(); it != aux_vertex_colour.end(); ++it)
       aux_vertex_count += it->second.size();
         
     cout << "varnames := [";
     for(int i = 0; i < csp.sym_order.size(); ++i)
     {
       cout << "\"" << name(csp.sym_order[i], csp) << "\", ";
       v_num[name(csp.sym_order[i], csp)] = i + 1;
     }
     cout << "];" << endl;

     int vertex_counter = v_num.size() + 1;

     // Now output partitions

     vector<set<int> > partitions;
     
     set<int> zero;
     zero.insert(0);
     partitions.push_back(zero);
     
     for(map<string, set<string> >::iterator it = var_vertex_colour.begin();
     it != var_vertex_colour.end();
     ++it)
     {
       D_ASSERT(it->second.size() > 0);
       set<int> partition;
       for(set<string>::iterator it2 = it->second.begin(); it2 != it->second.end(); it2++)
         partition.insert(v_num[*it2]);
       
       partitions.push_back(partition);
     }

    
     for(map<string, set<string> >::iterator it = aux_vertex_colour.begin();
     it != aux_vertex_colour.end();
     ++it)
     {
       set<int> partition;
       D_ASSERT(it->second.size() > 0);
       for(set<string>::iterator it2 = it->second.begin(); it2 != it->second.end(); it2++)
       {
         v_num[*it2] = vertex_counter;
         partition.insert(vertex_counter);
         vertex_counter++;
       }
       
       partitions.push_back(partition);
     }

     vector<set<int> > edges(var_vertex_count + aux_vertex_count + 1);
    
     
     for(set<pair<string, string> >::iterator it = graph.begin(); it != graph.end(); ++it)
     {
       //cout << it->first << ":" << it->second << endl;
       D_ASSERT(v_num.count(it->first) == 1);
       D_ASSERT(v_num.count(it->second) == 1);
       int first_v = v_num[it->first];
       int second_v = v_num[it->second];
       D_ASSERT(first_v != 0 && second_v != 0 && first_v != second_v);
       edges[first_v].insert(second_v);
     }
     
#ifdef USE_NAUTY
     vector<vector<int> > perms = build_graph(edges, partitions);
     cout << "generators := [()" << endl;  
     for(int i = 0; i < perms.size(); ++i)
     {
       cout << ", PermList([";
       bool first_pass = true;
       D_ASSERT(perms[i][0] == 0);
       for(int j = 1; j <= var_vertex_count; ++j)
       {
         D_ASSERT(perms[i][j] <= var_vertex_count);
         if(first_pass)
         {
           first_pass = false;
           cout << perms[i][j];
         }
         else
           cout << ", " << perms[i][j];
       }
       cout << "])" << endl;
     }
     cout << "];" << endl;
#else
     cerr << "Need to compile Minion with nauty included!" << endl;
     exit(1);
#endif     
   }
   
   
   string name(Var v, CSPInstance& csp)
   { 
     if(v.type() == VAR_CONSTANT)
     {
       string const_name = "CONSTANT_" + to_string(v.pos());
       aux_vertex_colour[const_name].insert(const_name);
       return const_name;
     }
     else
       return csp.vars.getName(v); 
   }
};

struct GraphBuilder
{
  CSPInstance& csp;
  Graph<> g;  
 
  
  GraphBuilder(CSPInstance& _csp) : csp(_csp)
  { 
    csp.add_variable_names();
    if(csp.sym_order.empty())
      D_FATAL_ERROR("Symmetry detection doesn't work with input formats 1 and 2. Upgrade!");
    
    build_graph(); 
  }
  
  
  void build_graph()
  {
    colour_vertices();
    for(list<ConstraintBlob>::iterator it = csp.constraints.begin(); 
        it != csp.constraints.end(); ++it)
      colour_constraint(*it);
  }
  
  void colour_vertices()
  {
    vector<Var> vars = csp.vars.get_all_vars();
    for(int i = 0; i < vars.size(); ++i)
    {
      g.var_vertex_colour[to_string(csp.vars.get_domain(vars[i]))].insert(csp.vars.getName(vars[i]));
    }
  }
  
  string name(Var v)
  { 
    if(v.type() == VAR_CONSTANT)
    {
      string const_name = "CONSTANT_" + to_string(v.pos());
      g.aux_vertex_colour[const_name].insert(const_name);
      return const_name;
    }
    else if(v.type() == VAR_NOTBOOL)
    {
      cerr << "No symmetry !bool support yet, sorry" << endl;
      exit(1);
//      string aux_v = g.new_vertex("NOTBOOL");
//      add_edge(aux_v, csp.vars.getName(Var(VAR_BOOL, v.pos())));
//      return aux_v;
    }
    else
      return csp.vars.getName(v); 
  }
  
  void add_edge(string s1, string s2)
  { g.graph.insert(make_pair(s1, s2)); }
  
  void add_edge(Var v1, string s2)
  { g.graph.insert(make_pair(name(v1), s2)); }
  
  void add_edge(string s1, Var v2)
  { g.graph.insert(make_pair(s1, name(v2))); }
  
  string colour_element(const ConstraintBlob& b, string name)
  {
     string v = g.new_vertex(name + "_MASTER");
     for(int i = 0; i < b.vars[0].size(); ++i)
     {
       string t = g.new_vertex(name + "_CHILD_" + to_string(i));
       add_edge(v,t);
       add_edge(t, b.vars[0][i]);
     }
     
     string v_index = g.new_vertex(name + "_INDEX");
     add_edge(v, v_index);
     add_edge(v_index, b.vars[1][0]);
     
     string v_result = g.new_vertex(name + "_RESULT");
     add_edge(v, v_result);
     add_edge(v_result, b.vars[2][0]);
     
     return v;
  }
  
  // A constraint where each array is independantly symmetric
  string colour_symmetric_constraint(const ConstraintBlob& b, string name)
  {
    string v = g.new_vertex(name + "_MASTER");  
      
    for(int i = 0; i < b.vars.size(); ++i)
    {
      string nv = g.new_vertex(name + "_CHILD" + to_string(i));
      add_edge(v, nv);
      for(int j = 0; j < b.vars[i].size(); ++j)
        add_edge(nv, b.vars[i][j]);
    }
    
    for(int i = 0; i < b.constants.size(); ++i)
    {
      string nv = g.new_vertex(name + "_CHILD_CONST" + to_string(i));
      add_edge(v, nv);
      for(int j = 0; j < b.constants[i].size(); ++j)
        add_edge(nv, Var(VAR_CONSTANT, b.constants[i][j]) );      
    }
    
    return v;
  }
  
  string colour_eq(const ConstraintBlob& b, string name)
  {
    string v = g.new_vertex(name + "_MASTER");
    add_edge(v, b.vars[0][0]);
    add_edge(v, b.vars[1][0]);
    return v;
  }
  
  string colour_no_symmetry(const ConstraintBlob& b, string name)
  {
    string v = g.new_vertex(name + "_MASTER");
    
    for(int i = 0; i < b.vars.size(); ++i)
      for(int j = 0; j < b.vars[i].size(); ++j)
      {
        string vij = g.new_vertex(name + "_CHILD_" + to_string(i) + ";" + to_string(j));
        add_edge(v, vij);
        add_edge(vij, b.vars[i][j]);
      }
      
    return v;
  }
  
  // Symmetries where the first array can be permuted, if the same permutation
  // is applied to the second array, and also pairs X[i] and Y[i] can be independantly swapped.
  // Other variables are assumed not symmetric.
  // Example: Hamming(M1, M2, C) - The hamming distance of arrays M1 and M2 is C
  string colour_array_swap_each_index(const ConstraintBlob& b, string name)
  {
    D_ASSERT(b.vars[0].size() == b.vars[1].size());
    
    string v = g.new_vertex(name + "_MASTER");
    
    // Force each array to stay together.
    for(int i = 0; i < 2; ++i)
    {
      string vi = g.new_vertex(name + "_ARRAY_STAY_TOGETHER");
      add_edge(v, vi);
      for(int j = 0; j < b.vars[i].size(); ++j)
        add_edge(vi, b.vars[i][j]);
    }
    
    for(int i = 0; i < b.vars[0].size(); ++i)
    {
      string vi = g.new_vertex(name + "_INDEX");
      add_edge(v, vi);
      add_edge(vi, b.vars[0][i]);
      add_edge(vi, b.vars[1][i]);
    }
    
    for(int i = 2; i < b.vars.size(); ++i)
    {
      D_ASSERT(b.vars[i].size() == 1);
      string vi = g.new_vertex(name + "_POS_" + to_string(i));
      add_edge(v, vi);
    }
    
    return v;
  }
  
  // The first array can be permuted, if the same permutation is applied to the second array.
  string colour_symmetric_indexes(const ConstraintBlob& b, string name)
  {
    D_ASSERT(b.vars[0].size() == b.vars[1].size());

    string v = g.new_vertex(name + "_MASTER");
    
    for(int i = 0; i < b.vars[0].size(); ++i)
    {
      string vm = g.new_vertex(name + "_INDEX");
      string v1 = g.new_vertex(name + "_ARRAY1");
      string v2 = g.new_vertex(name + "_ARRAY2");
      
      add_edge(v,vm);
      add_edge(vm,v1);
      add_edge(vm,v2);
      add_edge(v1, b.vars[0][i]);
      add_edge(v2, b.vars[1][i]);
    }
    
    for(int i = 2; i < b.vars.size(); ++i)
    {
      D_ASSERT(b.vars[i].size() == 1);
      string vi = g.new_vertex(name + "_POS_" + to_string(i));
      add_edge(v, vi);
    }
    
    return v;
  }
  
  // The first array can be permuted, if the same permutation is applied to the second array.
  string colour_weighted_sum(const ConstraintBlob& b, string name)
  {
    D_ASSERT(b.vars[0].size() == b.constants[0].size());


    string v = g.new_vertex(name + "_MASTER");
    
    for(int i = 0; i < b.vars[0].size(); ++i)
    {
      string vm = g.new_vertex(name + "_INDEX");
      string v1 = g.new_vertex(name + "_ARRAY1");
      string v2 = g.new_vertex(name + "_ARRAY2");
      
      add_edge(v,vm);
      add_edge(vm,v1);
      add_edge(vm,v2);
      add_edge(v1, b.vars[0][i]);
      add_edge(v2, Var(VAR_CONSTANT, b.constants[0][i]));
    }
    
    for(int i = 1; i < b.vars.size(); ++i)
    {
      D_ASSERT(b.vars[i].size() == 1);
      string vi = g.new_vertex(name + "_POS_" + to_string(i));
      add_edge(v, vi);
    }  
    return v;
  }
  
  string colour_reify(const ConstraintBlob& b, string name)
  {
    D_ASSERT(b.vars.size() == 1 && b.vars[0].size() == 1);
    D_ASSERT(b.internal_constraints.size() == 1);
        
    string v = g.new_vertex(name + "_HEAD");
    
    string reify_var = g.new_vertex(name + "_REIFYVAR");
    
    add_edge(v, reify_var);
    add_edge(reify_var, b.vars[0][0]);
    
    string child_con = colour_constraint(b.internal_constraints[0]);
    
    add_edge(v, child_con);
    
    return v;
  }
  
  string colour_constraint(const ConstraintBlob& b)
  {
    switch(b.constraint->type)
    {
#ifdef CT_REIFY_ABC
      case CT_REIFY: return colour_reify(b, "REIFY");
#endif
#ifdef CT_REIFYIMPLY_ABC
      case CT_REIFYIMPLY: return colour_reify(b, "REIFYIMPLY");
#endif
#ifdef CT_REIFYIMPLY_QUICK_ABC
      case CT_REIFYIMPLY_QUICK: return colour_reify(b, "REIFYIMPLY_QUICK");
#endif
#ifdef CT_ELEMENT_ABC
      case CT_ELEMENT: return colour_element(b, "ELEMENT");
#endif
#ifdef CT_WATCHED_ELEMENT_ABC
      case CT_WATCHED_ELEMENT: return colour_element(b, "ELEMENT");
#endif
#ifdef CT_GACELEMENT_ABC
      case CT_GACELEMENT: return colour_element(b, "ELEMENT");
#endif
#ifdef CT_ELEMENT_ONE_ABC
      case CT_ELEMENT_ONE: return colour_element(b, "ELEMENT_ONE");
#endif
#ifdef CT_WATCHED_ELEMENT_ONE_ABC
      case CT_WATCHED_ELEMENT_ONE: return colour_element(b, "ELEMENT_ONE");
#endif
      
#ifdef CT_ALLDIFF_ABC
      case CT_ALLDIFF: return colour_symmetric_constraint(b, "ALLDIFF");
#endif
#ifdef CT_GACALLDIFF_ABC
      case CT_GACALLDIFF: return colour_symmetric_constraint(b, "ALLDIFF");
#endif
        
#ifdef CT_WATCHED_NEQ_ABC
      case CT_WATCHED_NEQ: return colour_eq(b, "DISEQ");
#endif
#ifdef CT_DISEQ_ABC
      case CT_DISEQ: return colour_eq(b, "DISEQ");
#endif
#ifdef CT_EQ_ABC
      case CT_EQ:    return colour_eq(b, "EQ");
#endif
      
#ifdef CT_MINUSEQ_ABC
      case CT_MINUSEQ: return colour_no_symmetry(b, "MINUSEQ");
#endif
#ifdef CT_ABS_ABC
      case CT_ABS:     return colour_no_symmetry(b, "ABS");
#endif
#ifdef CT_INEQ_ABC
      case CT_INEQ:    return colour_no_symmetry(b, "INEQ");
#endif
#ifdef CT_WATCHED_LESS_ABC
      case CT_WATCHED_LESS: return colour_no_symmetry(b, "LESS");
#endif
      
#ifdef CT_LEXLEQ_ABC
      case CT_LEXLEQ: return colour_no_symmetry(b, "LEXLEQ");
#endif
#ifdef CT_LEXLESS_ABC
      case CT_LEXLESS: return colour_no_symmetry(b, "LEXLESS");
#endif
      
#ifdef CT_MAX_ABC
      case CT_MAX: return colour_symmetric_constraint(b, "MAX");
#endif
#ifdef CT_MIN_ABC
      case CT_MIN: return colour_symmetric_constraint(b, "MIN");
#endif
      
#ifdef CT_OCCURRENCE_ABC
      case CT_OCCURRENCE: return colour_symmetric_constraint(b, "OCCURRENCE");
#endif
#ifdef CT_LEQ_OCCURRENCE_ABC
      case CT_LEQ_OCCURRENCE: return colour_symmetric_constraint(b, "OCC_LEQ");
#endif
#ifdef CT_GEQ_OCCURRENCE_ABC
      case CT_GEQ_OCCURRENCE: return colour_symmetric_constraint(b, "OCC_GEQ");
#endif
      
#ifdef CT_PRODUCT2_ABC
      case CT_PRODUCT2: return colour_symmetric_constraint(b, "PRODUCT");
#endif
      
#ifdef CT_DIFFERENCE_ABC
      case CT_DIFFERENCE: return colour_symmetric_constraint(b, "DIFFERENCE");
#endif
      
#ifdef CT_WEIGHTGEQSUM_ABC
      case CT_WEIGHTGEQSUM: return colour_weighted_sum(b, "WEIGHT_GEQSUM");
#endif
#ifdef CT_WEIGHTLEQSUM_ABC
      case CT_WEIGHTLEQSUM: return colour_weighted_sum(b, "WEIGHT_LEQSUM");
#endif
      
#ifdef CT_GEQSUM_ABC
      case CT_GEQSUM: return colour_symmetric_constraint(b, "GEQSUM");
#endif
#ifdef CT_WATCHED_GEQSUM_ABC
      case CT_WATCHED_GEQSUM: return colour_symmetric_constraint(b, "GEQSUM");
#endif
      
#ifdef CT_LEQSUM_ABC
      case CT_LEQSUM: return colour_symmetric_constraint(b, "LEQSUM");
#endif
#ifdef CT_WATCHED_LEQSUM_ABC
      case CT_WATCHED_LEQSUM: return colour_symmetric_constraint(b, "LEQSUM");
#endif
      
#ifdef CT_WATCHED_TABLE_ABC
      case CT_WATCHED_TABLE: return colour_no_symmetry(b, "TABLE");
#endif
#ifdef CT_WATCHED_NEGATIVE_TABLE_ABC
      case CT_WATCHED_NEGATIVE_TABLE: return colour_no_symmetry(b, "NEG_TABLE");
#endif
      
#ifdef CT_WATCHED_VECNEQ_ABC
      case CT_WATCHED_VECNEQ: return colour_array_swap_each_index(b, "VECNEQ");
#endif
#ifdef CT_WATCHED_LITSUM_ABC
      case CT_WATCHED_LITSUM: return colour_no_symmetry(b, "LITSUM");
#endif
      
#ifdef CT_POW_ABC
      case CT_POW: return colour_no_symmetry(b, "POW");
#endif
#ifdef CT_DIV_ABC
      case CT_DIV: return colour_no_symmetry(b, "DIV");
#endif
#ifdef CT_MODULO_ABC
      case CT_MODULO: return colour_no_symmetry(b, "MOD");
#endif
      
#ifdef CT_WATCHED_VEC_OR_AND_ABC
      case CT_WATCHED_VEC_OR_AND: return colour_array_swap_each_index(b, "VEC_OR_AND");
#endif
      
#ifdef CT_WATCHED_VEC_OR_LESS_ABC
      case CT_WATCHED_VEC_OR_LESS: return colour_symmetric_indexes(b, "VEC_OR_LESS");
#endif
#ifdef CT_WATCHED_HAMMING_ABC
      case CT_WATCHED_HAMMING: return colour_array_swap_each_index(b, "HAMMING");
#endif
      
      default:
        abort();
    }
    
  }
  
};


struct InstanceStats
{
  CSPInstance& csp;
  
  InstanceStats(CSPInstance& _csp) : csp(_csp)
  { }
  
  void output_stats()
  {
      string s("stats_"); // common prefix
      // Variables statistics
      VarContainer& v=csp.vars;
      int varcount=v.BOOLs+v.bound.size()+v.sparse_bound.size()+v.discrete.size();
      cout << s << "varcount:" << varcount <<endl;
      cout << s << "var_bool:" <<v.BOOLs <<endl;
      cout << s << "var_discrete:" << v.discrete.size() << endl;
      cout << s << "var_bound:" << v.bound.size() << endl;
      cout << s << "var_sparsebound:" << v.sparse_bound.size() << endl;
      
      // collect all domain sizes into an array
      vector<int> domsizes;
      for(int i=0; i<v.BOOLs; i++)
          domsizes.push_back(2);
      for(int i=0; i<v.bound.size(); i++)
          domsizes.push_back(v.bound[i].second.upper_bound-v.bound[i].second.lower_bound+1);
      for(int i=0; i<v.discrete.size(); i++)
          domsizes.push_back(v.discrete[i].second.upper_bound-v.discrete[i].second.lower_bound+1);
      for(int i=0; i<v.sparse_bound.size(); i++)
          domsizes.push_back(v.sparse_bound[i].second.size());
      
      std::sort(domsizes.begin(), domsizes.end());
      // Some rubbish which does not give you the real medians, quartiles
      cout << s << "dom_0:" << domsizes[0] <<endl;
      cout << s << "dom_25:" << domsizes[domsizes.size()/4] <<endl;
      cout << s << "dom_50:" << domsizes[domsizes.size()/2] <<endl;
      cout << s << "dom_75:" << domsizes[(domsizes.size()*3)/4] <<endl;
      cout << s << "dom_100:" << domsizes.back() <<endl;
      
      int totaldom=std::accumulate(domsizes.begin(), domsizes.end(), 0);
      cout << s << "dom_mean:" << ((double)totaldom)/(double) domsizes.size() << endl;
      
      int num2s= std::count(domsizes.begin(), domsizes.end(), 2);
      cout << s << "dom_not2_2_ratio:" << ((double) (varcount-num2s) )/(double)num2s << endl;
      
      cout << s << "discrete_bool_ratio:" << ((double) v.discrete.size())/(double)v.BOOLs <<endl;
      
      int branchingvars=0;
      int auxvars=0;
      for(int i=0; i<csp.search_order.size(); i++)
      {
          if(csp.search_order[i].find_one_assignment)
          {
              auxvars=auxvars+csp.search_order[i].var_order.size();
          }
          else
          {
              branchingvars=branchingvars+csp.search_order[i].var_order.size();
          }
      }
      cout << s << "branchingvars:" << branchingvars <<endl;
      cout << s << "auxvars:" << auxvars <<endl;
      cout << s << "auxvar_branching_ratio:" << ((double)auxvars)/(double)branchingvars <<endl;
      
      //////////////////////////////////////////////////////////////////////////
      // Constraint stats
      list<ConstraintBlob> & c=csp.constraints;
      
      cout <<s << "conscount:" << c.size() <<endl;
      vector<int> arities;
      for(list<ConstraintBlob>::iterator i=c.begin(); i!=c.end(); ++i)
      {
          arities.push_back(arity(*i));
      }
      std::sort(arities.begin(), arities.end());
      
      cout << s << "arity_0:" << arities[0] <<endl;
      cout << s << "arity_25:" << arities[arities.size()/4] <<endl;
      cout << s << "arity_50:" << arities[arities.size()/2] <<endl;
      cout << s << "arity_75:" << arities[(arities.size()*3)/4] <<endl;
      cout << s << "arity_100:" << arities.back() <<endl;
      
      int totalarity=std::accumulate(arities.begin(), arities.end(), 0);
      cout << s << "arity_mean:" << ((double)totalarity)/(double) arities.size() << endl;
      cout << s << "cts_per_var_mean:" << ((double)totalarity)/(double) varcount << endl;
      
      // six categories of constraint, output their proportion and count
      int alldiff=0, sums=0, wor=0, ternary=0, binary=0, table=0, reify=0, lex=0;
      for(list<ConstraintBlob>::iterator i=c.begin(); i!=c.end(); ++i)
      {
          ConstraintType ct=(*i).constraint->type;
          switch(ct)
          {
            case CT_ALLDIFF:
            case CT_GACALLDIFF:
                alldiff++;
                break;
            case CT_GEQSUM:
            case CT_LEQSUM:
            case CT_WEIGHTGEQSUM:
            case CT_WEIGHTLEQSUM:
            case CT_WATCHED_GEQSUM:
            case CT_WATCHED_LEQSUM:
                sums++;
                break;
            case CT_WATCHED_OR:
                wor++;
                break;
            case CT_PRODUCT2:
            case CT_MODULO:
            case CT_DIV:
            case CT_POW:
                ternary++;
                break;
            case CT_ABS:
            case CT_INEQ:
            case CT_EQ:
                binary++;
                break;
            case CT_REIFY:
            case CT_DISEQ_REIFY:
            case CT_EQ_REIFY:
            case CT_MINUSEQ_REIFY:
            case CT_REIFYIMPLY_QUICK:
            case CT_REIFYIMPLY:
            case CT_REIFYIMPLY_OLD:
            case CT_REIFYIMPLY_NEW:
                reify++;
                break;
            case CT_WATCHED_TABLE:
            case CT_WATCHED_NEGATIVE_TABLE:
            case CT_LIGHTTABLE:
                table++;
                break;
            case CT_GACLEXLEQ:
            case CT_QUICK_LEXLEQ:
            case CT_LEXLEQ:
            case CT_LEXLESS:
            case CT_QUICK_LEXLESS:
                lex++;
                break;
            default:
                cerr << "Stats: Uncategorised constraint:" << (*i).constraint->name <<endl;
          }
      }
      
      cout << s << "alldiff_count:" << alldiff << endl;
      cout << s << "alldiff_proportion:" << ((double)alldiff)/(double)c.size() << endl;
      cout << s << "sums_count:" << sums << endl;
      cout << s << "sums_proportion:" << ((double)sums)/(double)c.size() << endl;
      cout << s << "wor_count:" << wor << endl;
      cout << s << "wor_proportion:" << ((double)wor)/(double)c.size() << endl;
      cout << s << "ternary_count:" << ternary << endl;
      cout << s << "ternary_proportion:" << ((double)ternary)/(double)c.size() << endl;
      cout << s << "binary_count:" << binary << endl;
      cout << s << "binary_proportion:" << ((double)binary)/(double)c.size() << endl;
      cout << s << "reify_count:" << reify << endl;
      cout << s << "reify_proportion:" << ((double)reify)/(double)c.size() << endl;
      cout << s << "table_count:" << table << endl;
      cout << s << "table_proportion:" << ((double)table)/(double)c.size() << endl;
      cout << s << "lex_count:" << lex << endl;
      cout << s << "lex_proportion:" << ((double)lex)/(double)c.size() << endl;
      
      int count_2_overlaps=0;
      for(list<ConstraintBlob>::iterator i=c.begin(); i!=c.end(); ++i)
      {
          for(list<ConstraintBlob>::iterator j=i; j!=c.end(); ++j)
          {
              if(j!=i)  // can't say i+1 above.
              {
                  set<Var> c1vars=find_all_vars(*i);
                  set<Var> c2vars=find_all_vars(*j);
                  vector<Var> inter;
                  
                  set_intersection(c1vars.begin(), c1vars.end(), c2vars.begin(), c2vars.end(), back_inserter(inter));
                  if(inter.size()>=2)
                  {
                      count_2_overlaps++;
                  }
              }
          }
      }
      
      int conspairs=((double)(c.size()*(c.size()-1)))/2.0;
      
      cout << s << "multi_shared_vars:" << ((double)count_2_overlaps)/conspairs <<endl;
  }
  
  // pretend each top-level constraint is just one constraint (i.e. no constraint trees)
  // naive arity count that eliminates repeated variables but 
  // counts constants in place of variables as variables.
  int arity(ConstraintBlob& ct)
  {
      return find_all_vars(ct).size();
          
      /*    
      int count=0;
      for(int i=0; i< ct.vars.size(); i++)
      {
          count=count+ct.vars[i].size();
      }
      // sub-constraints
      for(int i=0; i<ct.internal_constraints.size(); i++)
      {
          count=count+arity(ct.internal_constraints[i]);
      }
      return count;*/
  }
  
  // counts constants as vars still.
  set<Var> find_all_vars(ConstraintBlob& ct)
  {
      set<Var> t2;
      for(int i = 0; i < ct.vars.size(); ++i )
      {
          for(int j=0; j<ct.vars[i].size(); j++)
          {
            t2.insert(ct.vars[i][j]);
          }
      }
      
      for(int i=0; i<ct.internal_constraints.size(); i++)
      {
          set<Var> t3=find_all_vars(ct.internal_constraints[i]);
          for(set<Var>::iterator j=t3.begin(); j!=t3.end(); ++j)
          {
              t2.insert(*j);
          }
      }
      
      // filter out constants here.
      
      return t2;
  }
  
  
};
