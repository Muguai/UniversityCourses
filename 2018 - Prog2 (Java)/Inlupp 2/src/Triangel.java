import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Triangel extends JComponent {
	private int Category;
	private int Mark = 0;
	private int Markie1, Markie2, Markie3 = 0;
	private String KeyName, Description;
	private int getPlace = 0;
	private int x, y;
	private int size2 = 40;
	private int size = 50;
	JTextArea Text;

	private JTextField text2 = new JTextField();
	Font font = new Font("Serif", Font.PLAIN, 66);

	public Triangel(int x, int y, int Category, String KeyName, String Description) {
		this.x = x;
		this.y = y;
		System.out.print(x + " " + y);
		setBounds((x - 25), (y - 40), size, size2);

		setLayout(new BorderLayout());
		this.Category = Category;
		this.KeyName = KeyName;
		this.Description = Description;
		text2.setText(KeyName);
	}

	public String getKeyName() {
		return this.KeyName;
	}

	public String getDescription() {
		return this.Description;
	}

	public int GetTheX() {
		return this.x;
	}

	public int GetTheY() {
		return this.y;
	}

	public void DetermineCoordinates(Position Position, Map<String, Set<Place>> platser) {
		System.out.print("Checking");
		Position p = new Position(this.x, this.y);
		System.out.print(p);

		if (Position.equals(p)) {
			SetMark(platser);
			return;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (Category == 1) {
			g.setColor(Color.RED);
			int xpoints[] = { 50, 0, 25 };
			int ypoints[] = { 0, 0, 40 };
			int npoints = 3;
			g.fillPolygon(xpoints, ypoints, npoints);
		} else if (Category == 2) {
			g.setColor(Color.BLUE);
			int xpoints[] = { 50, 0, 25 };
			int ypoints[] = { 0, 0, 40 };
			int npoints = 3;
			g.fillPolygon(xpoints, ypoints, npoints);

		} else if (Category == 3) {
			g.setColor(Color.GREEN);
			int xpoints[] = { 50, 0, 25 };
			int ypoints[] = { 0, 0, 40 };
			int npoints = 3;
			g.fillPolygon(xpoints, ypoints, npoints);
		} else {
			g.setColor(Color.BLACK);
			int xpoints[] = { 50, 0, 25 };
			int ypoints[] = { 0, 0, 40 };
			int npoints = 3;
			g.fillPolygon(xpoints, ypoints, npoints);
		}
		paintComponent2(g);
	}

	protected void paintComponent2(Graphics g) {
		super.paintComponent(g);

		System.out.println("Draw Mark");
		g.setColor(Color.MAGENTA);
		int xpoints[] = { Markie3, 0, Markie1 };
		int ypoints[] = { 0, 0, Markie2 };
		int npoints = 3;
		g.drawPolygon(xpoints, ypoints, npoints);

	}

	public void removeforgodsake() {
		if (Mark == 1) {
			Markie1 = 0;
			Markie2 = 0;
			Markie3 = 0;
			size = 0;
			size2 = 0;
			setVisible(false);
			System.out.print(Markie1);
		}
	}

	public void SetMark(Map<String, Set<Place>> platser) {

		if (Mark == 0) {
			Mark = 1;
			for (Place p : platser.get(getKeyName())) {
				if (p.getPos().getY() == y && p.getPos().getX() == x) {
					boolean d = true;
					p.getMarked(d);
				}
			}
			Markie1 = 25;
			Markie2 = 40;
			Markie3 = 50;
			repaint();

		} else if (Mark == 1) {
			DeMark(platser);
		}

	}

	public void DeMark(Map<String, Set<Place>> platser) {
		Mark = 0;
		for (Place p : platser.get(getKeyName())) {
			if (p.getPos().getY() == y && p.getPos().getX() == x) {
				boolean d = true;
				p.getMarked(d);
			}
		}
		Markie1 = 0;
		Markie2 = 0;
		Markie3 = 0;
		repaint();

	}

	public void SetPlace() {
		getPlace = 1;

	}

	public void SetMarkSpecial(String input, Map<String, Set<Place>> platser) {
		System.out.print("Into");
		System.out.print(input);
		System.out.print(KeyName);
		System.out.print(getKeyName());
		String Input2 = input;
		String challenger = getKeyName();
		if (Input2.equalsIgnoreCase(challenger)) {
			System.out.print("It worked");
			SetMark(platser);
			setVisible(true);

		}
	}

	public void HideMarked(Map<String, Set<Place>> platser) {
		if (Mark == 1) {
			setVisible(false);
			DeMark(platser);
		}

	}

	public void HideCategory(int SelectedCategory, Map<String, Set<Place>> platser) {
		if (SelectedCategory == Category) {
			setVisible(false);
			DeMark(platser);
		}
	}

	public void SetVisibility(int SelectedCategory) {

		System.out.print("Checking2");
		if (SelectedCategory == Category) {
			System.out.print("Checking");
			setVisible(true);
		}
	}

	public void IfMark() {
		if (Mark == 1) {
			setVisible(false);
		}
	}

	public int getMark() {
		return Mark;
	}

	public int getPlace() {
		return getPlace;
	}
}
