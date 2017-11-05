package Sudoku;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

import Sudoku.Board.Square;


public class Board {
	class SquareComparator implements Comparator<Square> {

		@Override
		public int compare(Square o1, Square o2) {
			if (o1.domainSize() < o2.domainSize()){
				return -1;
			} else if (o1.domainSize() > o2.domainSize()){
				return 1;
			}
			return 0;
		}
		
	}
	public class Square{
		int row, col;
		private ArrayList<Integer> domain = getValuesList();
		
		public Square(int row, int col, int i){
			this.row = row;
			this.col = col;
			set(i);
		}
		public Square(Square s){
			ArrayList<Integer> d = new ArrayList<Integer>();
			for (int i = 0; i<s.domainSize(); i++){
				d.add(new Integer(s.domain().get(i)));
			}
			this.domain = d;
			this.row = s.row;
			this.col = s.col;
		}
		
		void set(int i){
			if (i == -1) {
				// do nothing, domain already has all numbers
			}
			else {
				if (this.domain.contains(new Integer(i))){
					this.domain = new ArrayList<Integer>() {{
						add(i);
						}};
				} else {
					System.out.println("asdf");
					this.domain = new ArrayList<Integer>();
				}

				
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
						squares[row][i] = new Square(row, i, -1);
					} else {
						int num = Character.getNumericValue(line.charAt(i));
						squares[row][i] = new Square(row, i, num);
					}
				}
				row++;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Board(Board b){
		this.squares = new Square[9][9];
		for (int i = 0; i<9; i++) {
			for (int j = 0; j<9; j++){
				squares[i][j] = new Square(b.squares[i][j]);
			}
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

	private boolean setDomainsRow(int row) {

		ArrayList<Integer> values = getValuesList();
		ArrayList<Integer> unsolved = new ArrayList<Integer>();
		for (int i = 0; i<9; i++){
			if (squares[row][i].isSolved()){
				Integer value = new Integer(squares[row][i].domain.get(0));
				if (values.contains(value)){
					values.remove(value);
				} else {
					return false;
				}
			} else {
				unsolved.add(i);
			}
		}
		for (int i = 0; i<unsolved.size(); i++){
			squares[row][unsolved.get(i)].limitDomain(values);
		}
		return true;
	}
	private boolean setDomainsCol(int col) {
		ArrayList<Integer> values = getValuesList();
		ArrayList<Integer> unsolved = new ArrayList<Integer>();
		for (int i = 0; i<9; i++){
			if (squares[i][col].isSolved()){
				Integer value = new Integer(squares[i][col].domain.get(0));
				if (values.contains(value)){
					values.remove(value);
				} else {
					return false;
				}
			} else {
				unsolved.add(i);
			}
		}
		for (int i = 0; i<unsolved.size(); i++){
			squares[unsolved.get(i)][col].limitDomain(values);
		}
		return true;
	}
	
	private boolean setDomains3x3(int row, int col){
		ArrayList<Integer> values = getValuesList();
		ArrayList<Square> unsolved = new ArrayList<Square>();
		
		int r = (row/3) * 3;
		int c = (col/3) * 3;
		
		for (int i = 0; i<3; i++){
			for (int j = 0; j<3; j++){
				Square currentSquare = squares[r+i][c+j];
				
				if (currentSquare.isSolved()){
					Integer value = new Integer(currentSquare.domain.get(0));
				
					if (values.contains(value)){
						values.remove(value);
					} else {
						return false;
					}
				} else {
					unsolved.add(currentSquare);
				}		
			}
		
		}
		
		for (int i = 0; i<unsolved.size(); i++){
			unsolved.get(i).limitDomain(values);
		}
		return true;
	}
	PriorityQueue<Square> setDomains() {
		// for each square
		String str = "";
		int i = 0;
		
		SquareComparator sc = new SquareComparator();
		PriorityQueue<Square> squareQueue;
		while(true){
			str = toString();
			squareQueue = new PriorityQueue<Square> (10, sc);
			for (int row = 0; row<9; row++){
				for (int col = 0; col<9; col++){
					// remove the values in its row
					// remove the values from its col
					// remove the values from its 3x3
					boolean isValid = (
							setDomainsRow(row) &&
							setDomainsCol(col) &&
							setDomains3x3(row, col)
						);
					
					if (!isValid){
						//System.out.println("INVALID, setDomains()");
						return null;
					}
					
					if (!squares[row][col].isSolved()){
						squareQueue.add(squares[row][col]);
					} else if (squares[row][col].domainSize() == 0){
						// if any of the squares don't have any remaining domain
						// then this chain is invalid. 
						//System.out.println("INVALID, setDomains()");
						return null;
					}
				}
			}
			i++;
			if (toString().equals(str)) break;
		}
		
		if (squareQueue.isEmpty()) {
			// if solved
			squareQueue.add(new Square(-1, -1, -1));
		}
		return squareQueue;
	}
	
	public boolean isSolved() {
		return (toString().indexOf('-') == -1);
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
