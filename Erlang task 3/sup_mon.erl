%% @author Fredrik Hammar


-module(sup_mon).

-export([init/1, start_link/0]).
-behavior(supervisor).


start_link() ->
	 supervisor:start_link(?MODULE, []),
	 sup_mon:start_link().

init(_) ->
	SupFlags = #{strategy => one_for_one, intensity => 10, period => 5},
	ChildSpec = [#{id => double_id, start => {double, start_link, []}}],
    {ok, {SupFlags, ChildSpec}}.
