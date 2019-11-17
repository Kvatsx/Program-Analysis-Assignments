
public class Transaction {
	public String Name;
	public Transaction(String name)
	{
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
		Transaction b = new Transaction("B");
		a.start();
		a.modify();
		b.start();
		a.commit();
	}
}
