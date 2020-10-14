module(task1).
-export([eval/1, eval/2, map/2, filter/2, split/2, groupby/2]).
-import(lists, [ seq/2, nth/2]).
-compile(export_any). %% add

	
eval(A) when is_tuple(A) ->
    case A of 
      {mul, E1, E2} -> {ok, E1 * E2};
      {add, E1, E2} -> {ok, E1 + E2};
      {sub, E1, E2} -> {ok, E1 - E2};
      {divide, E1, E2} -> {ok, E1 div E2};
      _ -> "Error"
    end.


eval({Do, E1, E2}, M) when is_tuple(E1), is_tuple(E2)->    
     case {Do, E1, E2} of 
      {mul, E1, E2} -> {ok, eval(E1, M) + eval(E2, M)};
      {add, E1, E2} ->  {ok, eval(E1, M) + eval(E2, M)};
      {sub, E1, E2} -> {ok, eval(E1, M) + eval(E2, M)};
      {divide, E1, E2} ->{ok, eval(E1, M) + eval(E2, M)};
      _ -> "Error"
    end;
eval({Do, E1, E2}, M) when is_tuple(E1)->    
     case {Do, E1, E2} of 
      {mul, E1, E2} -> {ok, eval(E1, M) + E2};
      {add, E1, E2} ->  {ok, eval(E1, M) + E2};
      {sub, E1, E2} -> {ok, eval(E1, M) + E2};
      {divide, E1, E2} ->{ok, eval(E1, M) + E2};
      _ -> "Error"
    end;
eval({Do, E1, E2}, M) when is_tuple(E2)->    
     case {Do, E1, E2} of 
      {mul, E1, E2} -> {ok, E1 + eval(E2, M)};
      {add, E1, E2} ->  {ok, E1 + eval(E2, M)};
      {sub, E1, E2} -> {ok, E1+ eval(E2, M)};
      {divide, E1, E2} ->{ok, E1 + eval(E2, M)};
      _ -> "Error"
    end;
eval({Do, E1, E2} , M) when is_map(M) ->
	case E1 of
				a -> NewE1 = maps:get("a",M);
                b -> NewE1 = maps:get("b",M);
                E1 -> NewE1 = E1
     end,
     case E2 of
				a -> NewE2 = maps:get("a",M);
                b -> NewE2 = maps:get("b",M);
                E2 -> NewE2 = E2
     end,
	 eval({Do, NewE1, NewE2}, {});	 
eval ({}, _) -> {};
eval(A, {})  ->    
    case A of 
      {mul, E1, E2} -> E1 * E2;
      {add, E1, E2} -> E1 + E2;
      {sub, E1, E2} -> E1 - E2;
      {divide, E1, E2} -> E1 div E2;
      _ -> "Error"
    end.

	

map(_, []) -> [];
map(F, [H|T]) -> [F(H)|map(F,T)].

filter(_F, []) -> [];
filter(F, [H|T]) -> 
	case F(H) of
		false -> filter(F,T);
		true -> [H|filter(F,T)]	    
	end.

split(_F, {True, False, []}) -> io:fwrite("write out thing\n"), {True, False};
split(List1, List2)when is_list(List1) -> List1 ++ List2;
split(F, List)when is_list(List) ->	{True, False} = split(F, {[], [],List}); 
split(F, {True, False, [H|T]}) -> 
	case F(H) of
		false -> split(F, {True, split(False, [H]) , T});
	    true ->  split(F, {split(True, [H]), False, T})	
    end.

groupby(_F, {P, N, Z, []}) -> #{positive => P, negative => N, zero => Z};
groupby(List1, List2)when is_list(List1) -> List1 ++ List2;
groupby(F, List)when is_list(List) -> #{} == groupby(F, {[],[],[],List});
groupby(F, {P,N,Z,[H|T]}) ->
    case F(H) of
		negative -> groupby(F, {P,groupby(N,[H]),Z,T});
		positive -> groupby(F, {groupby(P,[H]),N,Z,T});
		zero -> groupby(F, {P,N,groupby(Z,[H]),T})
	end.

