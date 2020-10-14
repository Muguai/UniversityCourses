// Fredrik Hammar, Frha2022.

package paradis.assignment2;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Bank{
	// Instance variables.
	private final List<Account> accounts = new ArrayList<Account>();


	
	// Instance methods.

	int newAccount(int balance) {
		int accountId;
		// what if two threads do this at the same time and get the same account id	. Disaster. So i made it so noone could do this at the same time	
		synchronized(this) {
	    accountId = accounts.size(); // FIX ORIGINAL				
		System.out.println("accountId = " + accountId);
		accounts.add(new Account(accountId, balance));
		}
		return accountId;
	}
	
	int getAccountBalance(int accountId) {
		Account account = null;
		account = accounts.get(accountId);
		//är det okej att en thread checkar balance på en account meddans en RunOperation har börjat för den accounten. Nej man bör vänta tills RunOperationen för den accounten är klar. Samma sak vice versea. om man först tittar på en account balance så kan det bli krångel om en Runoperation startas för det accountet efter man börjat tittat på det
		synchronized(account) {
		return account.getBalance();
		}
	}
	
	void runOperation(Operation operation) {
		Account account = null;		
		account = accounts.get(operation.getAccountId());
		//If someone is working on an account and someone else needs to work on that same account someone else needs to wait for the current worker to be done. This is cause the operation depends on the acounts current balance. So if two threads updated the same account at the same time the balance would not be right.
		synchronized(account) {
		System.out.println("getlock" + account.getId());
		int balance = account.getBalance();
		balance = balance + operation.getAmount();
		account.setBalance(balance);
		
		System.out.println("throwAwaylock" + account.getId());

		}

	}
		
	void runTransaction(Transaction transaction) {
		ReentrantLock lock = new ReentrantLock();
		 List<Operation> currentOperations = transaction.getOperations();
		 lock.lock();
	     try {
		 for (Operation operation : currentOperations) {
			runOperation(operation);
		 }
	     }finally {
			 lock.unlock();
	     }
	}


}
