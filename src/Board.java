import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {

	int[][] squares = new int[9][9];
	
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
						squares[row][i] = -1;
					} else {
						squares[row][i] = Character.getNumericValue(line.charAt(i));
					}
				}
				row++;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Board(int[][]squares){
		this.squares = squares;
	}
	
	void print() {
		String print = "";
		for (int i = 0; i<9; i++){
			for (int j = 0; j<9; j++){
				if (squares[i][j] == -1){
					print += '-';
				} else {
					print += squares[i][j];
				}
			}
			print += "\n";
		}
		System.out.print(print);
	}
}
