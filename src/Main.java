
public class Main {
	public static void main(String[] args){
		
		Board b = new Board("board.txt");
		b.print();
		System.out.println("--------------");
		Board c;
		do {
			c = b;
			c.setDomains();
			c.print();
			System.out.println("---------------");
		} while (!c.toString().equals(b.toString()));
		
	
		
		
	}
}
