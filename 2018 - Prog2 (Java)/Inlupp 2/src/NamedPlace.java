import javax.swing.JComponent;

public class NamedPlace extends Place {

	public NamedPlace(String name, Position pos, String category, JComponent triangel) {
		super(name, pos, category, triangel);
	}

	public String toString() {
		return getName() + ", " + getPos() + ", " + getCategory();
	}

}