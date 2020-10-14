%% @author Fredrik Hammar

-module(monitor).

-export([start/0, monitor_loop/1, my_monitor/1]).

-compile(export_any).


start() ->
	  double:start(),
	  my_monitor(double).

my_monitor(AName) ->
    case whereis(AName) of
        undefined -> {error, no_such_registration};

        _Pid -> spawn_monitor(ex, monitor_loop, [AName])
    end.

monitor_loop(AName) ->
    Pid = whereis(AName),
    io:format("monitoring PID ~p~n", [Pid]),
    receive
        {'DOWN', _Ref, process, Pid, Why} ->
            io:format("~p died because ~p~n",[AName, Why]),            
            monitor_loop(AName)
    end.
	  