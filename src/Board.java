import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {

	class Square{
		int[] domain = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		
		public Square(int i){
			set(i);
		}
		void set(int i){
			if (i == -1) {
				// do nothing, domain already has all numbers
			}
			else {
				domain = new int[] {i};
			}
		}
		
		boolean isUndefined() {
			return (domain.length == 9);
		}
		
		boolean isSolved() {
			return (domain.length == 1);
		}
		
		int domainSize() {
			return domain.length;
		}
		
		@Override
		public String toString(){
			if (isSolved()){
				return "" + domain[0];
			}
			else {
				return "-";
			}
		}
		
		public int[] domain() {
			return domain;
		}
	}
	
	Square[][] squares = new Square[9][9];
	
	/**
	 * Reads a sudoku file and creates a representation of a board. 
	 * @param filename File from which the board is read. 
	 * Should be formatted like this:
	 * 
	 1-5--46--
	 --72---31
	 4--63--9-
	 ---31-825
	 9-------4
	 851-72---
	 -7--56--8
	 34---71--
	 --89--2-6
	 * where dashes are unknown numbers. 
	 * Only valid sudoku puzzles are accepted. 
	 */
	public Board(String filename) {
		File f = new File(filename);
		Scanner in;
		int row = 0;
		try {
			in = new Scanner(f);
			while (in.hasNextLine()){
				String line = in.nextLine();
				for (int i = 0; i<line.length(); i++){
					if (line.charAt(i) == '-') {
						squares[row][i] = new Square(-1);
					} else {
						int num = Character.getNumericValue(line.charAt(i));
						squares[row][i] = new Square(num);
					}
				}
				row++;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Prints the board into the same format of the input file. 
	 */
	void print() {
		String print = "";
		for (int i = 0; i<9; i++){
			for (int j = 0; j<9; j++){
				print += squares[i][j];
			}
			print += "\n";
		}
		System.out.print(print);
	}
	
	
	// checking values //////////////////////////
	boolean checkRow(int row, int col, int val) {

		for (int i = 0; i<9; i++){
			
		}
		return true;
	}
	boolean checkColumn(int row, int col, int val) {
		return true;
	}
	boolean check9x9(int row, int col, int val) {
		return true;
	}
	boolean check(int row, int col, int val) {
		boolean checkRow = checkRow(row, col, val);
		boolean checkColumn = checkColumn(row, col, val);
		boolean check9x9 = check9x9(row, col, val);
		return (checkRow && checkColumn && check9x9);
	}
	
	private ArrayList<Integer> getValuesList(){
		ArrayList<Integer> values = new ArrayList<Integer>() {{
			add(1);
			add(2);
			add(3);
			add(4);
			add(5);
			add(6);
			add(7);
			add(8);
			add(9);
		}};
		
		return values;
	}
}
