%% @author Fredrik Hammar
-module(bank).

-export([start/0, bank/0,loop/0, balance/2, deposit/3, withdraw/3, lend/4]).

-compile(export_any).


start()-> spawn(fun bank/0).

bank() ->
    ets:new(bank, [set,private,named_table]),
    loop().

loop()-> io:format("Reset"),
	            receive					
					{From,_Ref,{balance, Account}} ->
		                io:format("Balance\n"),
						case ets:lookup(bank, Account) of
							[{_ ,CurrentAmount}] -> 
					 		   From ! {self(),{ok, CurrentAmount}},						
							   loop();
						    [] -> 
					     		From ! {self(), not_found},
						     	loop()
						end;
					{From,_Ref,{deposit, Account, Amount}} ->
						 io:format("Deposit\n"),
					   case ets:lookup(bank, Account) of
							[{_ ,CurrentAmount}] -> 				            		
									NewAmount = CurrentAmount + Amount,
									ets:insert(bank, {Account, NewAmount}),
				             	    From ! {self(),{ok, NewAmount}},	
				                	loop();
						    [] -> 
								    NewAmount = Amount,
									ets:insert(bank, {Account, NewAmount}),
									From ! {self(), {ok, NewAmount}},
						        	loop()
						end;
					{From,_Ref,{withdraw, Account, Amount}} ->
					   io:format("Withdraw\n"),
					   case ets:lookup(bank, Account) of
							[{_,CurrentAmount}] -> 
								 case CurrentAmount >= Amount of
					                true ->  
										NewAmount = CurrentAmount - Amount,
					                    ets:insert(bank, {Account, NewAmount}),
					                    From ! {self(),{ok, NewAmount}},		
				     	                loop();
									 false -> 
										 From ! {self(), insufficient_funds},
						               	loop()
									 end;
                                 
						   [] -> 
							   From ! {self(), not_found},
							   loop()
						end;
					{From,_Ref,{lend, FromAccount, ToAccount, Amount}} ->
	                     io:format("Lend\n"),
						 case ets:lookup(bank, FromAccount) of
							[{_,CurrentAmountFrom}] ->
				                 case ets:lookup(bank, ToAccount) of
							        [{_,CurrentAmountTo}] ->
										case CurrentAmountFrom >= Amount of
											true ->
				            	 	            NewAmountTo = CurrentAmountTo + Amount,
                                                NewAmountFrom = CurrentAmountFrom - Amount,
												ets:insert(bank, [{ToAccount, NewAmountTo}, {FromAccount, NewAmountFrom}]),				
				             	                From ! {self(), ok},	
				                	            loop();
											false ->
												From ! {self(), insufficent_funds},
						        	            loop()
										end;
												
						            [] ->
					        		   From ! {self(), {noaccount, ToAccount}},
						        	   loop()
						            end;
						   [] -> 
							     case ets:lookup(bank, ToAccount) of
								   [{_,_CurrentAmountTo}] ->
					        		From ! {self(), {noaccount, FromAccount}},
						        	loop();
							       [] ->
                                      From ! {self(), both},
						        	  loop()
                                 end
						end;				
                {_, _Ref, _} ->
					no_bank
				end.
              



	   

	   


balance(Bank, Account) when is_pid(Bank)->
	MRef = monitor(process, Bank),
    Ref = make_ref(),
	Bank ! {self(),Ref,{balance, Account}},
	receive
		{Bank,{ok, Total}} -> {ok, Total};
		{Bank, not_found} -> no_account;
		{'DOWN', MRef, _, _, _} -> demonitor(MRef), no_bank;
	    _ -> no_message
	after 10000 ->
		 timeout
	end.

deposit(Bank, Account, Amount) when is_pid(Bank)->
	MRef = monitor(process, Bank),
	Ref = make_ref(),
	Bank ! {self(),Ref,{deposit, Account, Amount}},
	receive
		{Bank,{ok, NewAmount}} ->  {ok, NewAmount};
		{Bank, insufficient_funds} -> insufficient_funds;
        {Bank, not_found} -> no_account;
		{'DOWN', MRef, _, _, _} -> demonitor(MRef), no_bank;
	    _ -> no_message
	after 10000 ->
		 timeout
	end.

withdraw(Bank, Account, Amount) when is_pid(Bank)->
	MRef = monitor(process, Bank),
    Ref = make_ref(),
	Bank ! {self(),Ref,{withdraw, Account, Amount}},
	receive
		{Bank,{ok, AmountLeft}} ->  {ok, AmountLeft};
		{Bank, insufficient_funds} -> insufficient_funds;
		{Bank, not_found} -> no_account;
		{'DOWN', MRef, _, _, _} -> demonitor(MRef), no_bank;
	    _ -> no_message
	after 10000 ->
		 timeout
	end.

lend(Bank, FromAccount, ToAccount, Amount) when is_pid(Bank)->
	MRef = monitor(process, Bank),
	Ref = make_ref(),
	Bank ! {self(),Ref,{lend, FromAccount, ToAccount, Amount}},
	receive
		{Bank, ok} -> ok;
		{Bank, insufficient_funds} -> insufficient_funds;
		{Bank, {noaccount, Who}} -> {noaccount, Who};
		{Bank, both} -> both;		
	    {'DOWN', MRef, _, _, _} -> demonitor(MRef),no_bank;
		_ -> no_message
	after 10000 ->
		 timeout
	end.
	


	
