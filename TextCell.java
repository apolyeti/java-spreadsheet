// Helper inherits from Cell
public class TextCell extends Cell {
	// stores text in here
	String text;
	public TextCell(String text) { 
		super(text.length()); 
		// use this to display text when its cell is called
		this.text = text;
	}
	public String truncate() {
		if (value > 6) {
			return text.substring(0, 7); 
			// truncation
		} else {
			return text;
		}
	}
	
	public String toString() {
		return text;
	}
}
