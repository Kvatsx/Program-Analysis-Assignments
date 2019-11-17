import java.util.*;

public aspect TransactionVerifier
{
	Set<Transaction> Start = new HashSet<Transaction>();
	Set<Transaction> Commit = new HashSet<Transaction>();
	
	before(Transaction t):
		call(* Transaction.commit()) && target(t) {
			if (!Start.contains(t)) {
				throw new Exception1(t);
			}
			else if (Commit.contains(t)) {
				throw new Exception4(t);
			}
			else {
				Commit.add(t);
			}
		}
	
	after(Transaction t):
		call(* Transaction.commit()) && target(t) {
			Start.remove(t);
		}
	
	before(Transaction t):
		call(* Transaction.start()) && target(t) {
			if (Commit.contains(t)) {
				Commit.remove(t);
			}
			if (Start.contains(t)) {
				throw new Exception3(t);
			}
		}
	
	after(Transaction t):
		call(* Transaction.start()) && target(t) {
			Start.add(t);
		}
	
	before(Transaction t):
		call(* Transaction.modify()) && target(t) {
			if (!(Start.contains(t) && !Commit.contains(t))) {
				throw new Exception5(t);
			}
		}
	
	after(): 
		execution(* *.main(..)) {
			for (Transaction e: Start) {
				throw new Exception2(e);
//				System.out.println(e+": "+"Every start should have a commit.");
			}
		}
}

