%% @author Fredrik Hammar


-module(bank_server).

-export([start_link/0,init/1, handle_cast/2,handle_info/2,terminate/2, handle_call/3, set/3, balance/2, withdraw/3, deposit/3, lend/4]).

-behavior(gen_server).
-record(state, {db = #{}, num_request = 0}).

start_link() ->
	gen_server:start_link(?MODULE, [], []).

init(_) ->
        {ok, #state{db = #{}, num_request = 0}}.

%% ====================================================================
%% Handle_Calls
%% ====================================================================

handle_call({balance, {Account}}, _From, #state{db = Db, num_request = NumReq}) ->
     Response = case maps:find(Account, Db) of
				            error -> not_found;
				            {ok, Amount} -> {ok, Amount}								 
			        end,				   
	{reply,Response,#state{db = Db, num_request = NumReq +1}};
handle_call({deposit, {Account, DepositAmount}},_From,  #state{db = Db, num_request = NumReq}) ->		
	Response =  case maps:find(Account, Db) of
				            error -> 
								NewDb = Db#{Account => DepositAmount},
								{ok, DepositAmount};
				            {ok, Amount} when is_integer(Amount) ->
								NewAmount = Amount + DepositAmount,
								NewDb = Db#{Account => NewAmount},
								{ok, (NewAmount)}
			        end,			
          {reply,Response, #state{db = NewDb, num_request = NumReq +1}};
handle_call({withdraw, {Account, WithdrawAmount}}, _From,  #state{db = Db, num_request = NumReq}) ->      
	Response = case maps:find(Account, Db) of
				            error -> NewDb = Db,not_found; 
				            {ok, Amount} -> case Amount >= WithdrawAmount of
												true ->
													 NewAmount = Amount - WithdrawAmount,
													 NewDb = Db#{Account => NewAmount},
													 {ok, NewAmount};
											    false ->
													NewDb = Db,insufficient_funds
                                                end
			        end,		     	
          {reply,Response, #state{db = NewDb, num_request = NumReq +1}};
handle_call({lend, {FromAccount, ToAccount, LendAmount}}, _From,  #state{db = Db, num_request = NumReq}) -> 
	Response = case maps:find(FromAccount, Db) of
				           error -> 
							   case maps:find(ToAccount, Db) of
								    error -> 
											NewDb = Db, both;
								    {ok, _ToAmount} -> 
										NewDb = Db, {noaccount, FromAccount}
							   end;
			               {ok, FromAmount} ->
                                case maps:find(ToAccount, Db) of
								    error -> 
										NewDb = Db, {noaccount, ToAccount};
								    {ok, ToAmount} -> 
										case FromAmount >= LendAmount of
													   false -> 
                                                             NewDb = Db, insufficient_funds;
													   true ->
                                                             NewAmountTo = ToAmount + LendAmount,
                                                             NewAmountFrom = FromAmount - LendAmount,
                                                             NewDb = Db#{FromAccount => NewAmountFrom, ToAccount => NewAmountTo},
															 ok
											    end
							    end 
									
			end,
	    
        {reply, Response,   #state{db = NewDb, num_request = NumReq +1}}.

%% ====================================================================
%% Handle_Casts
%% ====================================================================

handle_cast({set, {Account, Amount}},  State = #state{db = Db}) ->
	      NewDb = Db#{Account => Amount},
          {noreply, State#state{db = NewDb}}.


%% ====================================================================
%% Handle_Info
%% ====================================================================

handle_info(AnyTerm, State) ->
	io:format("Got ~p~n", [AnyTerm]),
    {noreply, State}.

terminate(_Reason, #state{num_request = NumReq}) ->
	io:format("got ~p requests ~n", [NumReq]),   
	ok.

%% ====================================================================
%% functions
%% ====================================================================

set(Pid, Name, Adress) ->
          gen_server:cast(Pid, {set, {Name,Adress}}),
          ok.

balance(Pid, Account) ->
          gen_server:call(Pid, {balance, { Account}}).

deposit(Pid, Account, DepositAmount)->
	     gen_server:call(Pid, {deposit, {Account,DepositAmount}}).
		
withdraw(Pid, Account, WithdrawAmount)->
	     gen_server:call(Pid, {withdraw, {Account,WithdrawAmount}}).
         

lend(Pid, FromAccount, ToAccount, LendAmount)->
	     gen_server:call(Pid, {lend, {FromAccount,ToAccount, LendAmount}}).