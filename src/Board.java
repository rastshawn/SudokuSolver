import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {

	class Square{
		private ArrayList<Integer> domain = getValuesList();
		
		public Square(int i){
			set(i);
		}
		void set(int i){
			if (i == -1) {
				// do nothing, domain already has all numbers
			}
			else {
				this.domain = new ArrayList<Integer>() {{
					add(i);
					}};
				
			}
		}
		
		boolean isUndefined() {
			return (domain.size() == 9);
		}
		
		boolean isSolved() {
			return (domain.size() == 1);
		}
		
		int domainSize() {
			return domain.size();
		}
		
		@Override
		public String toString(){
			if (isSolved()){
				return "" + domain.get(0);
			}
			else {
				return "-";
			}
		}
		
		ArrayList<Integer> domain() {
			return domain;
		}
		void limitDomain(ArrayList<Integer> possibleValues){
			
			ArrayList<Integer> newDomain = new ArrayList<Integer>();
			for (int i = 0; i<possibleValues.size(); i++){
				if (domain.contains(possibleValues.get(i))){
					newDomain.add(possibleValues.get(i));
				} 
			}
			
			this.domain = newDomain;
			
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
		System.out.print(this);
	}
	
	@Override 
	public String toString(){
		String print = "";
		for (int i = 0; i<9; i++){
			for (int j = 0; j<9; j++){
				print += squares[i][j];
			}
			print += "\n";
		}
		return print;
	}
	// checking values //////////////////////////
	
	/**
	 * Checks the row that a given square is in for validity. 
	 * Returns false if the proposed value for the square is invalid. 
	 * @param row
	 * @param col
	 * @param val
	 * @return
	 */
	boolean checkRow(int row, int col, int val) {
		ArrayList<Integer> values = getValuesList();
		for (int i = 0; i<9; i++){
			if (squares[row][i].isSolved()){
				int value = squares[row][i].domain.get(0);
				if (values.contains(value)){
					values.remove(value);
				} else {
					return false;
				}
			} else {
				if (i == col){ // current item in loop is testing value
					if (values.contains(val)){
						values.remove(val);
					} else {
						return false;
					}
				}
			}
		}
		return true;
	}
	boolean checkColumn(int row, int col, int val) {
		ArrayList<Integer> values = getValuesList();
		for (int i = 0; i<9; i++){
			if (squares[i][col].isSolved()){
				int value = squares[i][col].domain.get(0);
				if (values.contains(value)){
					values.remove(value);
				} else {
					return false;
				}
			} else {
				if (i == row){ // current item in loop is testing value
					if (values.contains(val)){
						values.remove(val);
					} else {
						return false;
					}
				}
			}
		}
		return true;
	}
	boolean check3x3(int row, int col, int val) {
		
		
		return true;
	}
	boolean check(int row, int col, int val) {
		boolean checkRow = checkRow(row, col, val);
		boolean checkColumn = checkColumn(row, col, val);
		boolean check3x3 = check3x3(row, col, val);
		return (checkRow && checkColumn && check3x3);
	}
	
	private void setDomainsRow(int row) {

		ArrayList<Integer> values = getValuesList();
		ArrayList<Integer> unsolved = new ArrayList<Integer>();
		for (int i = 0; i<9; i++){
			if (squares[row][i].isSolved()){
				Integer value = new Integer(squares[row][i].domain.get(0));
				if (values.contains(value)){
					values.remove(value);
				} 
			} else {
				unsolved.add(i);
			}
		}
		for (int i = 0; i<unsolved.size(); i++){
			squares[row][unsolved.get(i)].limitDomain(values);
		}
	}
	private void setDomainsCol(int col) {
		ArrayList<Integer> values = getValuesList();
		ArrayList<Integer> unsolved = new ArrayList<Integer>();
		for (int i = 0; i<9; i++){
			if (squares[i][col].isSolved()){
				Integer value = new Integer(squares[i][col].domain.get(0));
				if (values.contains(value)){
					values.remove(value);
				} 
			} else {
				unsolved.add(i);
			}
		}
		for (int i = 0; i<unsolved.size(); i++){
			squares[unsolved.get(i)][col].limitDomain(values);
		}
	}
	
	void setDomains3x3(int row, int col){
		ArrayList<Integer> values = getValuesList();
		ArrayList<Square> unsolved = new ArrayList<Square>();
		// find r, 
		// find c
		int r = (row/3) * 3;
		int c = (col/3) * 3;
		
		for (int i = 0; i<3; i++){
			for (int j = 0; j<3; j++){
				Square currentSquare = squares[r+i][c+j];
				
				if (currentSquare.isSolved()){
					Integer value = new Integer(currentSquare.domain.get(0));
				
					if (values.contains(value)){
						values.remove(value);
					}
				} else {
					unsolved.add(currentSquare);
					
				}		
			}

		}
		
		for (int i = 0; i<unsolved.size(); i++){
			unsolved.get(i).limitDomain(values);
		}
	}
	void setDomains() {
		// for each square
		for (int row = 0; row<9; row++){
			for (int col = 0; col<9; col++){
				// remove the values in its row
				// remove the values from its col
				// remove the values from its 3x3
				setDomainsRow(row);
				setDomainsCol(col);
				setDomains3x3(row, col);
			}
		}
		


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
