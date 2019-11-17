import java.util.*;

public aspect TransactionVerifier
{
	Set<Transaction> Start = new HashSet<Transaction>();
	Set<Transaction> Commit = new HashSet<Transaction>();
	HashMap<Transaction, Integer> ModifyCount = new HashMap<Transaction, Integer>();
	Set<Transaction> RollBack = new HashSet<Transaction>();
	
	before(Transaction t):
		call(* Transaction.commit()) && target(t) {
			if (Commit.contains(t)) {
				throw new Exception4(t);
			}
			else if (!Start.contains(t)) {
				throw new Exception1(t);
			}
			if (RollBack.contains(t)) {
				RollBack.remove(t);
			}
			Start.remove(t);
			Commit.add(t);
			ModifyCount.remove(t);
		}
	
	before(Transaction t):
		call(* Transaction.start()) && target(t) {
			if (Commit.contains(t)) {
				Commit.remove(t);
			}
			if (Start.contains(t) && !ModifyCount.containsKey(t)) {
				throw new Exception3(t);
			}
			if (Start.contains(t)) {
				throw new Exception2(t);
			}
			Start.add(t);
		}
	
	before(Transaction t):
		call(* Transaction.modify()) && target(t) {
			if (!(Start.contains(t) && !Commit.contains(t))) {
				throw new Exception5(t);
			}
			if (ModifyCount.containsKey(t)) {
				ModifyCount.put(t, ModifyCount.get(t)+1);
			}
			else {
				ModifyCount.put(t, 1);
			}
			if (RollBack.contains(t)) {
				RollBack.remove(t);
			}
		}
	
	void around(): 
		execution(* *.main(..)) {
			proceed();
			for (Transaction e: Start) {
				throw new Exception2(e);
			}
		}
	
	// Bonus
	before(Transaction t):
		call(* Transaction.rollback()) && target(t) {
			if (!Start.contains(t)) {
				throw new RollBack2(t);
			}
			if (RollBack.contains(t)) {
				throw new RollBack3(t);
			}
			if (ModifyCount.containsKey(t)) {
				int mCount = ModifyCount.get(t);
				for (int i=mCount; i>0; i--) {
					System.out.println("["+Integer.toString(i)+"] " +"Rolling Back " + t +": modify()");
				}
				ModifyCount.remove(t);
			}
			System.out.println("[0] " +"Rolling Back " + t +": start()");
			RollBack.add(t);
		}
	
}

