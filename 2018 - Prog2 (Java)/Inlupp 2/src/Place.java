import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JComponent;

public class Place extends JComponent {
	private String name;
	private Position pos;
	private Boolean marked = false;
	private Boolean Described = false;
	private String category; // Color is dependent on the category.
	private JComponent Triangel;

	public Place(String name, Position pos, String category, JComponent Triangel) {
		this.name = name;
		this.pos = pos;
		this.category = category;
		this.Triangel = Triangel;
	}

	protected void paintComponentVisible(Graphics g) {
		this.Triangel.setVisible(false);
	}

	public String getName() {
		return name;
	}

	public Position getPos() {
		return pos;
	}

	public String getCategory() {
		return category;
	}

	public Boolean getMarked() {
		return marked;

	}

	public void getMarked(boolean d) {
		marked = d;

	}

	public Boolean getDescribed() {
		return Described;

	}

	public void setDescribed() {
		Described = true;

	}
	public String Xstring() {
		String x = Integer.toString(pos.getX());
		return x;
	}

	public String Ystring() {
		String y = Integer.toString(pos.getY());
		return y;
	}

	public void Remove(String Name, Map<String, Place> Platser) {
		if (marked == true) {
			Platser.remove(Name);
		}
	}
}
