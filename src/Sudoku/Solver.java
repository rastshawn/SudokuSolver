package Sudoku;


import java.util.PriorityQueue;

import Sudoku.Board.Square;

public class Solver {
	public Solver() {
		
	}
	boolean hasFinished = false;
	int numSolutions = 0;
	
	void solve(Board b) {
		//if (hasFinished) b = null;
		PriorityQueue<Square> queue = b.setDomains();
		

		if (queue == null){

			return;
			// this means it's invalid, kill this set
		}
		//System.out.println(queue.peek().row);
		if (queue.peek().row == -1){
			// this means solved
			hasFinished = true;
			System.out.printf("Solution #%d:%n----------%n", ++numSolutions );
			b.print();
			System.out.println("-----------------");
			return;
		}
		
		
		Square currentSquare = queue.poll();
		
		for (int i = 0; i<currentSquare.domainSize(); i++){
			Board newBoard = new Board(b);
			Square newSquare = newBoard.squares[currentSquare.row][currentSquare.col];
			newSquare.set(currentSquare.domain().get(i));
			solve(newBoard);
		}
		

	}
	void solve(Board b, PriorityQueue<Square> s){
		
	}
}
