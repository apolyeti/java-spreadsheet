// Client Class
import java.util.*;
import java.io.*;

public class VisiCalc { 
	static Grid g = new Grid();
	static ArrayList<String> commands = new ArrayList<String>();
	static boolean hasSaved = true;
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("----------- WELCOME TO VISICALC ----------");
		Scanner userInput = new Scanner(System.in);
		// initialize scanner
		String input = userInput.nextLine(); 
		// grab token from input
		while(!input.equalsIgnoreCase("quit")) { 
			// keep running as long as user doesn't write "quit"
			processCommand(input);
			input = userInput.nextLine();
		}
		if (hasSaved) { 
			// just to make sure the user is sure that they want to save their work before exiting the program
			System.out.println("---------------- GOODBYE -----------------");
		} else {
			System.out.println("Exit without saving?\nSave and exit or type anything to exit without saving");
			input = userInput.nextLine();
			if (input.split(" ")[0].equalsIgnoreCase("save")) {
				saveFile(input.split(" ")[1]);
				System.out.println("---------------- GOODBYE -----------------");
			} else {
				System.out.println("---------------- GOODBYE -----------------");
			}
		}
	}
	
	public static void help() {
		System.out.println("--------------- HOW TO USE ---------------\n");
		System.out.println("Columns: 1 - 10");
		System.out.println("Rows: A - G\n");
		System.out.println("---------------- COMMANDS ----------------\n");
		System.out.println("PRINT: Prints the entire grid");
		System.out.println("SAVE: Saves progress in a file Ex: save <filename>.txt");
		System.out.println("LOAD: Loads a file of previous spreadsheet Ex: load <filename>.txt");
		System.out.println("CLEAR: Clears the whole grid");
		System.out.println("CLEAR <cell number>: clears the individual cell Ex: CLEAR B1 will clear cell B1");
		System.out.println("Grab values from cells Ex: \"B3\" will show the value at B3");
		System.out.println("Assign numbers to  cells Ex: B2 = <num>");
		System.out.println("Assign values to text cells Ex: B2 = \" text \"");
		System.out.println("Assign dates to cells Ex: C1 = <mm/dd/yyyy>");
		System.out.println("Assign formula cells, which changes values based on other cells\n");
		System.out.println("--------- EXAMPLE OF FORMULA CELL --------\n");
		System.out.println("INPUT:");
		System.out.println("B3 = 9");
		System.out.println("C3 = 2");
		System.out.println("D3 = ( C3 * B3 )");
		System.out.println("Value of D3 will be 18.");
		System.out.println("------------------------------------------\n");
	}
	
	public static int getRowIndex(String s) { 
		// grabs the second part of the string 
		return Integer.parseInt(s.substring(1)) - 1; 
		// subtract 1 because spreadsheet index starts at 0, and address index starts at 1
	}
	
	public static int getColIndex(String s) { 
		// grabs the char from the cell address and finds the corresponding int
		int index = 0; 
		// if column is A then the for loop will not run and index will be 0
		for (char c = 'A'; c < s.charAt(0); c++) { 
			// as it navigates to the correct char, the int will correspond with that value
			index++;
		}
		return index;
	}
	
	public static void assigningCells(String input) {
		String[] inList = input.split(" "); 
		// grabbing the command given to process the command given to a cell address
		String cellAddress = inList[0];
		int rowIndex = getRowIndex(cellAddress); 
		// grabbing indexes of given cell address
		int colIndex = getColIndex(cellAddress);
		if (inList.length > 1) {
			hasSaved = false; 
			// if the user has saved before and now is making any changes to the grid, automatically set the grid to not saved, unless they are calling a cell.
			if (inList[2].equals("\"")) { 
				// checking if user wants to assign text into a cell
				String text = "";
				for (int i = 3; i < inList.length - 1; i++) { 
					// grab text between the two quotes
					text += inList[i] + " ";
				}
				g.assignTextCell(rowIndex, colIndex, text); // add it in the 2D array
			} else if (inList[2].contains("/")) { 
				// testing if user wants to assign a date into the cell
				String date[] = inList[2].split("/"); 
				// split up the date into month, day, and year
		        int month = Integer.parseInt(date[0]);
				int day = Integer.parseInt(date[1]);
				int year = Integer.parseInt(date[2]);
				g.assignDateCell(rowIndex, colIndex, month, day, year);
			} else if (inList[2].equals("(")) {
				// A3 = ( A1 + A2 + 1 ) length 20
				String formula = "";
				// grab the formula
				for (int i = 3; i < inList.length-1; i++) {
					formula += inList[i] + " ";
				}
				g.assignFormulaCell(rowIndex, colIndex, formula);
			} else { 
				// just assigning a normal int in a cell
				int value = Integer.parseInt(inList[2]);
				g.assignCell(rowIndex, colIndex, value);
			}
		} else { 
			// only has one token, so just call what value is stored in given cell
			System.out.println(g.grabCell(rowIndex, colIndex));
			commands.remove(commands.size() - 1); 
			// remove any commands that dont change the spreadsheet
		}
	}
	
	public static void processCommand(String input) throws FileNotFoundException {
		String[] inList = input.split(" ");
		if (inList[0].equalsIgnoreCase("help")) {
			// gives user a demo on how to use program as well as other commands
			help();
			// prints out series of instructions, will be fixed once everything is implemented for VisiCalc
		} else if (input.equalsIgnoreCase("print")) { 
			g.printGrid();
			// prints out the grid in the console
		} else if (inList[0].equalsIgnoreCase("load")) { // will load given file
			loadFile(inList[1]);
		} else if (inList[0].equalsIgnoreCase("save")) { 
			// will save all SPREADSHEET commands in a file
			saveFile(inList[1]);
		} else if (inList[0].equalsIgnoreCase("clear")) {
			if (inList.length > 1) {
				String cellAddress = inList[1];
				int rowIndex = getRowIndex(cellAddress);
				int colIndex = getColIndex(cellAddress);
				g.clearCell(rowIndex, colIndex);
			} else {
				g.clearGrid();
			}
		} else if (inList[0].equalsIgnoreCase("restore")) {
			g.restoreGrid();
		} else if (inList[0].equalsIgnoreCase("SORTA")) {
			// sort a
			// SORTA A1 - A3
			sortAscending(inList[1], inList[3]);
		} else if (inList[0].equalsIgnoreCase("SORTD")) {
			// sort d
			sortDescending(inList[1], inList[3]);
		} else {
			// save the command and run it
			commands.add(input);
			assigningCells(input); 
			// splits the strings up into an array of strings so the value and cell address can be grabbed
		}
	}
	
	public static void loadFile(String fileName) throws FileNotFoundException {
		File commandList = new File(fileName);
		Scanner scanFile = new Scanner(commandList);
		while (scanFile.hasNext()) {
			processCommand(scanFile.nextLine());
		}
		System.out.println("File loaded");
	}
	
	public static void saveFile(String fileName) throws FileNotFoundException {
		File saveFile = new File(fileName);
		PrintStream fileWriter = new PrintStream(saveFile);
		for (int i = 0; i < commands.size(); i++) { 
			// grabs all the commands by the user and writes it in a new text file
			fileWriter.println(commands.get(i));
		}
		System.out.println("Progress saved"); 
		// lets the user know that they can safely exit the program without losing progress
		hasSaved = true;
	}
	
	public static void sortAscending(String firstCell, String secondCell) {
		// grab both addresses of cells
		// need to know if we are going horizontally or vertically
		// make an array of cells 
		int rowOne = getRowIndex(firstCell);
		int rowTwo = getRowIndex(secondCell);
		int colOne = getColIndex(firstCell);
		int colTwo = getColIndex(secondCell);
		
		if (rowOne == rowTwo) {
			// sorting horizontally
			Cell[] temp = new Cell[colTwo - colOne + 1];
			int tempIndex = 0;
			for (int c = colOne; c <= colTwo; c++) {
				// now put all the elements within the spreadsheet into the temporary array
				temp[tempIndex] = g.spreadsheet[rowOne][c];
				tempIndex++;
			}
			// now sort the array
			Arrays.sort(temp);
			// now put the sorted cells back in the spreadsheet
			for (int i = 0; i < temp.length; i++) {
				g.spreadsheet[rowOne][colOne + i] = temp[i];
			}
		} else {
			// sorting vertically
			Cell[] temp = new Cell[rowTwo - rowOne + 1];
			int tempIndex = 0;
			for (int r = rowOne; r <= rowTwo; r++) {
				// now put all the elements within the spreadsheet into the temporary array
				temp[tempIndex] = g.spreadsheet[r][colOne];
				tempIndex++;
			}
			// now sort the array
			Arrays.sort(temp);
			// now put the sorted cells back in the spreadsheet
			for (int i = 0; i < temp.length; i++) {
				g.spreadsheet[rowOne + i][colOne] = temp[i];
			}
		}
	}
	
	public static void sortDescending(String firstCell, String secondCell) {
		// grab both addresses of cells
		// need to know if we are going horizontally or vertically
		// make an array of cells 
		int rowOne = getRowIndex(firstCell);
		int rowTwo = getRowIndex(secondCell);
		int colOne = getColIndex(firstCell);
		int colTwo = getColIndex(secondCell);
				
		if (rowOne == rowTwo) {
			// sorting horizontally
			Cell[] temp = new Cell[colTwo - colOne + 1];
			int tempIndex = 0;
			for (int c = colOne; c <= colTwo; c++) {
				// now put all the elements within the spreadsheet into the temporary array
				temp[tempIndex] = g.spreadsheet[rowOne][c];
				tempIndex++;
			}
			// now sort the array
			Arrays.sort(temp);
			// now put the sorted cells back in the spreadsheet
			int c = colOne;
			for (int i = 0; i < temp.length; i++) {
				g.spreadsheet[rowOne][c] = temp[i];
				c++;
			}
		} else {
			// sorting vertically
			Cell[] temp = new Cell[rowTwo - rowOne + 1];
			int tempIndex = 0;
			for (int r = rowOne; r <= rowTwo; r++) {
				// now put all the elements within the spreadsheet into the temporary array
				temp[tempIndex] = g.spreadsheet[r][colOne];
				tempIndex++;
			}
			// now sort the array
			Arrays.sort(temp);
			// now put the sorted cells back in the spreadsheet
			int r = rowOne;
			for (int i = temp.length - 1; i >= 0; i--) {
				g.spreadsheet[r][colOne] = temp[i];
				r++;
			}
		}		
	}
	
}
