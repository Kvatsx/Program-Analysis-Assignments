
class Exception1 extends RuntimeException {
	public Exception1(Transaction t) {
		System.err.println(t+": "+"Every commit should have a preceding start.");
	}
}

class Exception2 extends RuntimeException {
	public Exception2(Transaction t) {
		System.err.println(t+": "+"Every start should have a commit.");
	}
}

class Exception3 extends RuntimeException {
	public Exception3(Transaction t) {
		System.err.println(t+": "+"Start followed by immediate start is an error.");
	}
}

class Exception4 extends RuntimeException {
	public Exception4(Transaction t) {
		System.err.println(t+": "+"Commit followed by immediate commit is also an error.");
	}
}

class Exception5 extends RuntimeException {
	public Exception5(Transaction t) {
		System.err.println(t+": "+"Modify can only happen between start and commit.");
	}
}

// Bonus
class RollBack2 extends RuntimeException {
	public RollBack2(Transaction t) {
		System.err.println(t+": "+"Every rollback should have a preceding start.");
	}
}

class RollBack3 extends RuntimeException {
	public RollBack3(Transaction t) {
		System.err.println(t+": "+"Rollback followed by immediate rollback is also an error.");
	}
}
