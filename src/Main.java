
public class Main {
	public static void main(String[] args){
		
		Board b = new Board("board.txt");
		b.print();
		System.out.println("--------------");
		
		int i = 0;
		String str = b.toString();
		while(str.indexOf('-')!=-1){
			b.setDomains();
			b.print();
			str = b.toString();
			
			
			System.out.println("---------------");
			i++;
		}
		
		System.out.printf("Ran limiting algorithm %d times%n", i);
		
		
		
	}
}
