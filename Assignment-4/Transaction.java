
public class Transaction {
	public String Name;
	public Transaction(String name) {
		this.Name = name;
	}
	
	public String toString() {
		return this.Name;
	}

	public void start() {
		System.out.println(this.Name+": "+"Transaction started");
	}
	
	public void modify() {
		System.out.println(this.Name+": "+"Transaction modified");
	}
	
	public void commit() {
		System.out.println(this.Name+": "+"Transaction commited");
	}
	
	public void rollback() {
		System.out.println(this.Name+": "+"Transaction rollbacked");
	}
	
	public static void main(String[] args) throws Exception {
		Transaction a = new Transaction("A");
//		Transaction b = new Transaction("B");
//		a.rollback();
		a.start();
		a.modify();
		a.commit();
		a.start();
		a.modify();
//		a.commit();
		a.rollback();
		a.rollback();
		a.modify();
		a.commit();
//		b.modify();
//		b.modify();
//		b.modify();
//		b.rollback();
//		b.rollback();
//		b.commit();
	}
}
