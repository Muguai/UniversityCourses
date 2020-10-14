%% @author Fredrik Hammar


-module(double).

-export([start/0, multiply/0, loop_5_print/0]).

-compile(export_any).



loop_5_print() ->
    receive
    after 5000 ->
            io:format("I'm still alive~n"),
            loop_5_print()
    end.

start() ->
      Pid = spawn(fun() -> multiply() end),
      register(double, Pid).
     
     

multiply() ->
       receive
		   {Pid, Ref, N} when is_integer(N) -> Pid ! {Ref, (N * 2)}, multiply();
	       {Pid, _, _} -> io:format("Error"), exit(Pid)	   
	   end.