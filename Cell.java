// helper class
public class Cell implements Comparable<Cell> {
	// stores data in here
	int value;
	// Cell holds one int value
	public Cell (int value) {
		// assigns an int as a parameter to be used as value 
		this.value = value; 
	}
	
	public String truncate() { 
		// to truncate it in the spreadsheet
		if ((value + "").length() > 6) { 
			// turns the int to a string, which allows for string class methods to be used
			return (value + "").substring(0, 7); 
			// truncation
		} else {
			return value + ""; 
			// turns the int to a string 
		}
	}
	
	public String toString() {
		// prints value as a string to be displayed when called individually
		return value + ""; 
		// turns the int to a string 
	}
	
	public int compareTo(Cell other) {
		// return positive value, cells out of order
		// return 0, each cell is equivalent
		// return negative, cells in order
		return this.value - other.value;
		
	}
	
}

