import javax.swing.JComponent;

public class DescribedPlace extends Place {

	private String text;

	public DescribedPlace(String name, Position pos, String category, JComponent triangel, String text) {
		super(name, pos, category, triangel);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public String toString() {
		return getName() + ", " + getPos() + ", " + getCategory() + ", " + text;
	}

}
