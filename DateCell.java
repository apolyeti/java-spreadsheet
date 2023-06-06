// helper that inherits from Cell
public class DateCell extends Cell {
	int month;
	int day;
	int year;
	
	public DateCell(int month, int day, int year) { 
		super(0);
		this.month = month;
		this.day = day;
		this.year = year;
	}
	public String truncate() { 
		// use this to display date when its cell is called
		String date = month + "/" + day + "/" + year;
		if (date.length() > 6) {
			return date.substring(0, 7); // truncation
		} else {
			return date;
		}
	}
	
	public String toString() {
		return month + "/" + day + "/" + year;
	}
	// stores date in here 
	// month, day, year (3 numbers)
}
