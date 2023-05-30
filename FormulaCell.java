// helper that inherits from Cell
import java.util.*;

public class FormulaCell extends Cell {
	String formula;
	Grid g = VisiCalc.g;
	ArrayList<String> formulaData = new ArrayList<String>();
	
	public FormulaCell(String formula) { 
		super(0);
		this.formula = formula;
	}
	
	public void calculate() {
		String[] formulaComponents = formula.split(" ");
		for (String partsOfEquation : formulaComponents) {
			formulaData.add(partsOfEquation);
		}
		if (formulaComponents[0].equals("AVG")) {
			// check if we are averaging
			average(formulaData);
		} else if (formulaComponents[0].equals("SUM")) {
			// check if we are summing
			sum(formulaData);
		} else {
			// if not, just treat it as a normal formula cell
			String first = "";
			String oper = "";
			String second = "";
			if (formulaData.size() > 1) {
				// if there are operators in the formula
				// deal with 3 tokens at at time then
				// add the result into the front of the list
				// until the arraylist is one element.
				while (formulaData.size() > 1) {
					first = formulaData.remove(0);
					oper = formulaData.remove(0);
					second = formulaData.remove(0);
					formulaData.add(0, math(first, oper, second));
				}
			value = Integer.parseInt(formulaData.remove(0));
		} else {
			value = convertNum(formulaData.remove(0));
		}
	}
		
		// A1 + A2
	}
	
	public String truncate() {
		calculate();
		if ((value + "").length() > 7) {
			return (value + "").substring(0,7);
		} else {
			return value + "";
		}
	}
	
	public String toString() {
		return formula;
	}
	
	public String math(String first, String oper, String next) {
		int firstNum = convertNum(first);
		int secondNum = convertNum(next);
		// find out which operator is being used for the math
		if (oper.equals("+")) {
			return add(firstNum, secondNum);
		} else if (oper.equals("-")) {
			return subtract(firstNum, secondNum);
		} else if (oper.equals("*")) {
			return multiply(firstNum, secondNum);
		} else if (oper.equals("/")) {
			return divide(firstNum, secondNum);
		} else {
			return "0";
		}
	}
	
	public boolean isNumber(String number) {
		// this method tests if the input given into the formula cell is either
		// an actual number or a cell, which then it will use this information
		// to deal with math later
		for (char c = 'A'; c < 'G'; c++) {
			if (number.charAt(0) == c) {
				return false;
			}
		}
		return true;
	}
	
	public int convertNum(String value) {
		// this is to convert the value into its number if it is a cell,
		// if not a cell then just return the value given, since it's a number
		// this is to reduce the number of if statements in each math method.
		if (isNumber(value)) {
			return Integer.parseInt(value);
		} else {
			int rowIndex = VisiCalc.getRowIndex(value);
			int colIndex = VisiCalc.getColIndex(value);
			return g.spreadsheet[rowIndex][colIndex].value;
		}
	}
	
	public String add(int first, int next) {
		return first + next + "";
	}
	
	public String subtract(int first, int next) {
		return first - next + "";
	}
	
	public String multiply(int first, int next) {
		return first * next + "";
	}
	
	public String divide(int first, int next) {
		return (first / next) + "";
	}
	
	public String average(ArrayList<String> list) {
		list.remove(0); 
		// remove the first command, not needed
		// grab each cell address
		String firstCellAddress = list.remove(0);
		// filler token, not needed
		list.remove(0);
		// grab second cell 
		String secondCellAddress = list.remove(0);
		int rowOne = VisiCalc.getRowIndex(firstCellAddress);
		int rowTwo = VisiCalc.getRowIndex(secondCellAddress);
		int colOne = VisiCalc.getColIndex(firstCellAddress);
		int colTwo = VisiCalc.getColIndex(secondCellAddress);
		int sum = 0;
		int numOfRuns = 0;
		if (rowOne == rowTwo) {
			// going horizontally
			for (int c = colOne; c <= colTwo; c++) {
				sum += g.spreadsheet[rowOne][c].value;
				numOfRuns++;
			}
		} else {
			// going vertically
			for (int r = rowOne; r <= rowTwo; r++) {
				sum += g.spreadsheet[r][colOne].value;
				numOfRuns++;
			}
		}
		value = sum/numOfRuns;
		return sum/numOfRuns + "";
	}
	
	public String sum(ArrayList<String> list) {
		list.remove(0); 
		// remove "SUM" not needed in calculation
		String firstCellAddress = list.remove(0);
		// grab the first cell index
		list.remove(0);
		// not needed, filler token
		String secondCellAddress = list.remove(0);
		// grab second cell index
		int rowOne = VisiCalc.getRowIndex(firstCellAddress);
		int rowTwo = VisiCalc.getRowIndex(secondCellAddress);
		int colOne = VisiCalc.getColIndex(firstCellAddress);
		int colTwo = VisiCalc.getColIndex(secondCellAddress);
		int sum = 0;
		if (rowOne == rowTwo) { 
			// going horizontally
			for (int c = colOne; c <= colTwo; c++) {
				sum += g.spreadsheet[rowOne][c].value;
			}
		} else {
			// going vertically
			for (int r = rowOne; r <= rowTwo; r++) {
				sum += g.spreadsheet[r][colOne].value;
			}
		}
		value = sum;
		return sum + "";
	}
	
	// can store formulas in here that interacts with other cells
}

