package Sudoku;

public class Main {
	public static void main(String[] args){
		Solver solver = new Solver();
		Board b = new Board("board2.txt");
		b.print();
		System.out.println("--------------");
		
		solver.solve(b);
		

		
		
	}
	
	

	

}
