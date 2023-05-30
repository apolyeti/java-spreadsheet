// Helper Class
public class Grid {
	static Cell[][] spreadsheet = new Cell[10][7];
	static Cell[][] backup = new Cell[10][7];
	
	public void printGrid() {
		// print out rest of grid
		System.out.printf("%8s", "|"); // top left of grid
		for (char c = 'A'; c <= 'G'; c++) { // rest of the top row
			System.out.printf("%4c%4s", c, "|");
		}
		System.out.println("\n----------------------------------------------------------------");
		
		for (int r = 0; r < 10; r++) { // print out rest of grid
			System.out.printf("%4d%4s", r+1, "|"); // label each row
			for (int c = 0; c < 7; c++) {
				if (spreadsheet[r][c] == null) {
					System.out.printf("%8s", "|"); // print spaces if nothing in given index
				} else {
					System.out.printf("%-7s%-1s", spreadsheet[r][c].truncate(), "|"); 
					// display value stored in given Cell, use other toString method to truncate
				}
			}
			System.out.println("\n----------------------------------------------------------------");
		}
	}
	
	public void assignCell(int r, int c, int value) {
		spreadsheet[r][c] = new Cell(value); // adds the value grabbed from the command from client class into the 2D array
	}
	
	public void assignTextCell(int r, int c, String text) { // adds a text cell into the spreadsheet
		spreadsheet[r][c] = new TextCell(text);
	}
	
	public void assignDateCell(int r, int c, int month, int day, int year) { // adds a date cell into the spreadsheet
		spreadsheet[r][c] = new DateCell(month, day, year);
	}
	
	public void assignFormulaCell(int r, int c, String formula) {
		spreadsheet[r][c] = new FormulaCell(formula);
	}
	
	public void clearGrid() {
		boolean hasNothing = true; 
		// this first traversal is to check if the user is clearing an already empty grid
		// which can cause issues with the restore command
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 7; c++) {
				if (spreadsheet[r][c] != null) {
					hasNothing = false;
				}
			}
		}
		if (hasNothing) {
			System.out.println("Nothing to clear");
		} else {
			for (int r = 0; r < 10; r++) {
				for (int c = 0; c < 7; c++) {
					backup[r][c] = spreadsheet[r][c]; 
					// in case user made a mistake they can restore to bring back the lost grid
					spreadsheet[r][c] = null;
				}
			}
			System.out.println("Grid cleared");
		}
	}
	
	public void restoreGrid() { 
		// just an additional feature so that the user can bring back any accidental cleared cells/grids
		boolean hasSomething = false;
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 7; c++) {
				if (backup[r][c] != null) {
					hasSomething = true;
				}
			}
		}
		
		if (hasSomething) {
			for (int r = 0; r < 10; r++) {
				for (int c = 0; c < 7; c++) {
					spreadsheet[r][c] = backup[r][c]; 
				}
			}
			System.out.println("Grid restored");
		} else {
			System.out.println("No backup found");
		}
	}
	
	public String grabCell(int r, int c) { 
		// print out what is stored in given index of cell
		return spreadsheet[r][c].toString(); 
		// use toString to show the entire value, in case it is truncated in the grid
	}
	
	public void clearCell(int r, int c) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++) {
				backup[i][j] = spreadsheet[i][j]; 
				// in case user made a mistake they can restore to bring back the lost cell
			}
		}
		spreadsheet[r][c] = null; 
		// clear the cell with given row and column index
	}
}
















//-1      0       1       2       3       4       5       6
//        |   A   |   B   |   C   |   D   |   E   |   F   |   G   |
// ----------------------------------------------------------------
//    1   |       |       |       |       |       |       |       |
// ----------------------------------------------------------------
//    2   |       |       |       |       |       |       |       |
// ----------------------------------------------------------------
//    3   |       |       |       |       |       |       |       |
// ----------------------------------------------------------------
//    4   |       |       |       |       |       |       |       |
// ----------------------------------------------------------------
//    5   |       |       |       |       |       |       |       |
// ----------------------------------------------------------------
//    6   |       |       |       |       |       |       |       |
// ----------------------------------------------------------------
//    7   |       |       |       |       |       |       |       |
// ----------------------------------------------------------------
//    8   |       |       |       |       |       |       |       |
// ----------------------------------------------------------------
//    9   |       |       |       |       |       |       |       |
// ----------------------------------------------------------------
//   10   |       |       |       |       |       |       |       |
