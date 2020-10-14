import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.*;
import javax.swing.filechooser.*;

public class Window extends JFrame {
	PicturePanel pp;
	JButton newButton;
	JFileChooser jfc = new JFileChooser(".");
	JMenu iMen = new JMenu("Archive");
	JMenuBar mb = new JMenuBar();
	JMenuItem exit = new JMenuItem("Exit");
	JMenuItem map = new JMenuItem("New Map");
	JMenuItem places = new JMenuItem("Load Places");
	JMenuItem save = new JMenuItem("Save");
	JPanel Pp = new JPanel();
	JRadioButton namedButton;
	JRadioButton describedButton;
	JScrollPane scroll = null;
	JTextArea categoriesDisplay;
	JTextField searchField = new JTextField("Search", 6);
	boolean changed = false;

	Map<String, Set<Place>> Platser = new TreeMap<String, Set<Place>>();
	Set<Triangel> Triangels = new HashSet<Triangel>();
	int selectedList;

	private String[] categories = { "Bus", "Underground", "Train" };
	JList<String> list = new JList<>(categories);

	MouseLyss mouseLyss = new MouseLyss();
	MusLyss MusLyss = new MusLyss();

	// Places must be collected in a data structure. ArrayList is NOT ALLOWED for
	// this project.

	Window() {
		super("Inlupp 2");

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu archiveMenu = new JMenu("Archive");
		menuBar.add(archiveMenu);
		JMenuItem newItem = new JMenuItem("New Map");
		archiveMenu.add(newItem);
		newItem.addActionListener(new NewMapLyss());
		JMenuItem loadItem = new JMenuItem("Load Places");
		archiveMenu.add(loadItem);

		loadItem.addActionListener(new LoadLyss());
		JMenuItem saveItem = new JMenuItem("Save");
		archiveMenu.add(saveItem);
		saveItem.addActionListener(new SaveLyss());
		JMenuItem exitItem = new JMenuItem("Exit");
		archiveMenu.add(exitItem);
		exitItem.addActionListener(new ExitLyss());

		JPanel radioButtons = new JPanel();
		radioButtons.setLayout(new GridLayout(2, 1));
		namedButton = new JRadioButton("Named", true);
		radioButtons.add(namedButton);
		describedButton = new JRadioButton("Described");
		radioButtons.add(describedButton);
		ButtonGroup bg = new ButtonGroup();
		bg.add(namedButton);
		bg.add(describedButton);

		JPanel northPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);
		newButton = new JButton("New");
		newButton.addActionListener(new NewLyss());
		northPanel.add(newButton);
		newButton.setEnabled(false);
		northPanel.add(radioButtons);
		northPanel.add(searchField);
		JButton searchButton = new JButton("Search");
		northPanel.add(searchButton);
		searchButton.addActionListener(new SokLyss());
		JButton hideButton = new JButton("Hide");
		northPanel.add(hideButton);
		hideButton.addActionListener(new HideLyss());
		JButton removeButton = new JButton("Remove");
		northPanel.add(removeButton);
		removeButton.addActionListener(new RemoveLyss());
		JButton coordinatesButton = new JButton("Coordinates");
		northPanel.add(coordinatesButton);
		coordinatesButton.addActionListener(new CoordinateLyss());

		JPanel eastPanel = new JPanel();
		add(eastPanel, BorderLayout.EAST);
		eastPanel.add(new JLabel("Categories"));
		list.setVisibleRowCount(10);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFixedCellWidth(100);
		eastPanel.add(new JScrollPane(list));
		list.addListSelectionListener(new ListLyss());
		JButton categoryButton = new JButton("Hide category");
		eastPanel.add(categoryButton);
		categoryButton.addActionListener(new HideCategoryLyss());

		addWindowListener(new Slutlyss());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(1000, 200);
		setVisible(true);
	}

	class HideCategoryLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			int Category = selectedList;
			Triangels.forEach(e -> e.HideCategory(Category, Platser));
			changed = true;
		}
	}

	class ExitLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if (changed) {
				int svar = JOptionPane.showConfirmDialog(Window.this,
						"EYYO DU HAR GJORT ANDRINGAR VILL DU FORTFARANDE AVSLUTA DU KANSKE BOR SPARA!!#¤");
				if (svar == JOptionPane.OK_OPTION)
					System.exit(0);
			} else {
				System.exit(0);
			}
		}
	}

	class SokLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			try {

				Triangels.forEach(e -> e.DeMark(Platser));
				String Input = searchField.getText();

				Triangels.forEach(e -> e.SetMarkSpecial(Input, Platser));

			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(Window.this, "Platsen Hittades Ej!");
			}
		}
	}

	class HideLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			try {

				Triangels.forEach(e -> e.HideMarked(Platser));
				changed = true;

			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(Window.this, "Platsen Hittades Ej!");
			}
		}
	}

	class ListLyss implements ListSelectionListener {
		// What will happen when a category is chosen
		@Override
		public void valueChanged(ListSelectionEvent lev) {
		
			if (!lev.getValueIsAdjusting()) {
				String word = list.getSelectedValue();
				if (word == "Bus") {
					selectedList = 1;
				} else if (word == "Underground") {
					selectedList = 2;
				} else if (word == "Train") {
					selectedList = 3;
				}
				
				if(list.getSelectedValue() == "Bus") {
					Triangels.forEach(e -> e.SetVisibility(selectedList));
				}else if (list.getSelectedValue() == "Underground") {
					Triangels.forEach(e -> e.SetVisibility(selectedList));
				}else if (list.getSelectedValue() == "Train") {
					Triangels.forEach(e -> e.SetVisibility(selectedList));
				}
			}
			System.out.print(list.getSelectedValue());
			
		}
	}

	class Slutlyss extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent ave) {
			if (changed) {
				int svar = JOptionPane.showConfirmDialog(Window.this,
						"Du har gjort andringar! Vill du avsluta utan att spara?");
				if (svar == JOptionPane.OK_OPTION)
					System.exit(0);
			} else {
				System.exit(0);
			}
		}

	}

	class CoordinateApparat extends JPanel {
		JTextField xFalt = new JTextField(3);
		JTextField yFalt = new JTextField(3);

		CoordinateApparat() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("x:"));
			rad1.add(xFalt);
			add(rad1);
			JPanel rad2 = new JPanel();
			rad2.add(new JLabel("y:"));
			rad2.add(yFalt);
			add(rad2);

		}

		private int getXFalt() {
			return Integer.parseInt(xFalt.getText());
		}

		private int getYFalt() {
			return Integer.parseInt(yFalt.getText());
		}
	}

	class CoordinateLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			Triangels.forEach(e -> e.DeMark(Platser));
			CoordinateApparat c = new CoordinateApparat();
			int svar = JOptionPane.showConfirmDialog(Window.this, c, "Input Name and Description",
					JOptionPane.OK_CANCEL_OPTION);
			if (svar != JOptionPane.OK_OPTION) {
				return;
			}
			int x = c.getXFalt();
			int y = c.getYFalt();
			Position p = new Position(x, y);
			Triangels.forEach(e -> e.DetermineCoordinates(p, Platser));

		}
	}

	class LoadLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			try {
				int i = jfc.showOpenDialog(Window.this);
				if (i != JFileChooser.APPROVE_OPTION)
					return;

				File file = jfc.getSelectedFile();
				String fileName = file.getAbsolutePath();
				FileReader infil = new FileReader(file);
				BufferedReader in = new BufferedReader(infil);
				String line;
				while ((line = in.readLine()) != null) {
					String[] tokens = line.split(",");

					System.out.print(tokens[0] + tokens[1] + tokens[2] + tokens[3] + tokens[4]);
					if (tokens[0].equalsIgnoreCase("Named")) {
						String Category = tokens[1];
						int x = Integer.parseInt(tokens[2]);
						int y = Integer.parseInt(tokens[3]);
						boolean duplicate = false;
						for (Triangel t : Triangels) {
							if (t.GetTheX() == x && t.GetTheY() == y) {
								duplicate = true;
							}
						}
						String namn = tokens[4];
						Position d = new Position(x, y);
						int Category1 = 4;
						if (Category.equalsIgnoreCase("bus")) {
							Category1 = 1;
						} else if (Category.equalsIgnoreCase("underground")) {
							Category1 = 2;
						} else if (Category.equalsIgnoreCase("train")) {
							Category1 = 3;
						}
						String Description = null;

						if (duplicate == false) {
							Triangel ddr = new Triangel(x, y, Category1, namn, Description);
							Pp.add(ddr);
							ddr.addMouseListener(new MusLyss());
							Triangels.add((Triangel) ddr);
							NamedPlace p = new NamedPlace(namn, d, Category, ddr);
							Set<Place> Places = (Set<Place>) Platser.get(namn);
							if (Places == null) {
								Places = new HashSet<Place>();
								Platser.put(namn, Places);

							}

							places.add(p);
						}
						duplicate = false;
					} else if (tokens[0].equalsIgnoreCase("Described")) {
						String Category = tokens[1];

						int x = Integer.parseInt(tokens[2]);
						int y = Integer.parseInt(tokens[3]);
						boolean duplicate = false;
						for (Triangel t : Triangels) {
							if (t.GetTheX() == x && t.GetTheY() == y) {
								duplicate = true;
							}
						}
						String namn = tokens[4];
						String Description = tokens[5];
						Position d = new Position(x, y);
						int Category1 = 4;
						if (Category.equalsIgnoreCase("bus")) {
							Category1 = 1;
						} else if (Category.equalsIgnoreCase("underground")) {
							Category1 = 2;
						} else if (Category.equalsIgnoreCase("train")) {
							Category1 = 3;
						}

						if (duplicate == false) {
							Triangel ddr = new Triangel(x, y, Category1, namn, Description);
							Pp.add(ddr);
							ddr.addMouseListener(new MusLyss());
							Triangels.add((Triangel) ddr);
							DescribedPlace p = new DescribedPlace(namn, d, Category, ddr, Description);
							Set<Place> Places = (Set<Place>) Platser.get(namn);
							if (Places == null) {
								Places = new HashSet<Place>();
								Platser.put(namn, Places);

							}

							places.add(p);
						}

						duplicate = false;

					}
				}
				Pp.validate();
				Pp.repaint();
				in.close();
				infil.close();

				changed = false;
			} catch (FileNotFoundException e) {
				System.err.print("Kan inte öppna filen");
			} catch (IOException e) {

				System.err.print("fel: " + e.getMessage());
			}
		}
	}
	public void LoadEach() {
		
	}

	class SaveLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

			try {
				int svar = jfc.showSaveDialog(Window.this);
				if (svar != JFileChooser.APPROVE_OPTION) {
					System.out.println("bye");
					return;
				}

				System.out.println("gothere");
				System.out.println(jfc.getCurrentDirectory() + jfc.getDialogTitle());
				File file = jfc.getSelectedFile();
				String fileName = file.getAbsolutePath();
				FileWriter utfil = new FileWriter(fileName);
				PrintWriter ut = new PrintWriter(utfil);

				for (Set<Place> R : Platser.values()) {
					for (Place D : R) {

						String category = null;
						if (D instanceof NamedPlace) {
							String Place = "Named";
							category = D.getCategory();
							ut.println(Place + "," + category + "," + D.getPos().getX() + "," + D.getPos().getY() + ","
									+ D.getName());

						} else if (D instanceof DescribedPlace) {
							String named = "Described";
							category = D.getCategory();
							DescribedPlace d = (DescribedPlace) D;
							String Described = d.getText();

							ut.println(named + "," + category + "," + D.getPos().getX() + "," + D.getPos().getY() + ","
									+ D.getName() + "," + Described);
						}
					}
				}
				ut.close();
				utfil.close();
				changed = false;
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(Window.this, "Kan inte öppna filen");
			} catch (IOException e) {

				JOptionPane.showMessageDialog(Window.this, "FEL");
			}

		}
	}

	class MouseLyss extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mev) {
			String Svar = null;
			String Description = null;
			int x = mev.getX();
			int y = mev.getY();
			Position p = new Position(x, y);
			for (Triangel t : Triangels) {
				if (t.GetTheX() == p.getX() && t.GetTheY() == p.getY()) {
					JOptionPane.showMessageDialog(null, "Det finns redan en plats pa  dessa koordinater");
					;
					return;
				}
			}
			try {

				if (namedButton.isSelected()) {
					Svar = JOptionPane.showInputDialog(Window.this, Svar, "Input Name", JOptionPane.OK_CANCEL_OPTION);
					if (Svar == null) {
						return;
					}
				} else if (describedButton.isSelected()) {

					System.out.println("Description");
					DescritionApparat d = new DescritionApparat();
					int svar = JOptionPane.showConfirmDialog(Window.this, d, "Input Name and Description",
							JOptionPane.OK_CANCEL_OPTION);
					if (svar != JOptionPane.OK_OPTION) {
						return;
					}

					Svar = d.getNamn();
					Description = d.getDescription();
				}

			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(Window.this, "Fel indata!");
			}

			Position position = new Position(x, y);
			String Categories = null;
			int Category = 0;
			JComponent Triangel = null;
			if (selectedList == 1) {
				Category = 1;
				Categories = "Bus";

			} else if (selectedList == 2) {
				Category = 2;
				Categories = "Underground";

			} else if (selectedList == 3) {
				Category = 3;
				Categories = "Train";

			} else {
				Category = 4;
				Categories = "None";

			}
			Triangel ddr = new Triangel(mev.getX(), mev.getY(), Category, Svar, Description);
			if (Description == null) {
				ddr.SetPlace();
			}

			Pp.add(ddr);
			Triangels.add((Triangel) ddr);
			for (Triangel t : Triangels) {
				t.addMouseListener(MusLyss);
			}
			Set<Place> Places = (Set<Place>) Platser.get(Svar);
			if (Places == null) {
				Places = new HashSet<Place>();
				Platser.put(Svar, Places);

			}

			if (Description == null) {
				NamedPlace NamedPlace = new NamedPlace(Svar, position, Categories, Triangel);

				Places.add(NamedPlace);
			} else {
				DescribedPlace DescribedPlace = new DescribedPlace(Svar, position, Categories, Triangel, Description);

				DescribedPlace.setDescribed();

				Places.add(DescribedPlace);
			}
			changed = true;
			Pp.validate();
			Pp.repaint();
			Pp.removeMouseListener(mouseLyss);

			newButton.setEnabled(true);
			Pp.setCursor(Cursor.getDefaultCursor());
		}
	}

	class NewLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			Pp.addMouseListener(mouseLyss);
			for (Triangel t : Triangels) {
				t.removeMouseListener(MusLyss);
			}
			newButton.setEnabled(false);
			Pp.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
	}

	class NewMapLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			int i = jfc.showOpenDialog(Window.this);
			if (i != JFileChooser.APPROVE_OPTION)
				return;

			File file = jfc.getSelectedFile();
			String fileName = file.getAbsolutePath();
			if (scroll != null)
				remove(scroll);
			Pp = new PicturePanel(fileName);

			scroll = new JScrollPane(Pp);
			add(scroll, BorderLayout.CENTER);

			newButton.setEnabled(true);
			validate();
			repaint();
		}
	}

	class RemoveLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

			Triangels.forEach(e -> e.IfMark());
			for (Map.Entry<String, Set<Place>> entry : Platser.entrySet()) {
				Set set = Platser.get(entry.getKey());
				System.out.print("remove1");
				Iterator iterator = set.iterator();
				while (iterator.hasNext()) {
					System.out.print("remove");
					Place sentry = (Place) iterator.next();
					if (sentry.getMarked() == true) {
						System.out.print("removeDDD");
						iterator.remove();
					}

				}
			}
			Set set2 = Triangels;
			Iterator iterator = set2.iterator();
			while (iterator.hasNext()) {
				System.out.print("remove");
				Triangel sentry = (Triangel) iterator.next();
				if (sentry.getMark() == 1) {
					System.out.print("removeDDD");
					iterator.remove();
				}
			}

			changed = true;
			repaint();
		}
	}

	class MusLyss extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			if (mev.getButton() == MouseEvent.BUTTON1) {

				Triangel b = (Triangel) mev.getSource();
				System.out.print("CheckingSource");

				b.SetMark(Platser);
			} else if (mev.getButton() == MouseEvent.BUTTON3) {

				Triangel b = (Triangel) mev.getSource();
				System.out.println("Right CLicking");
				String Name = null;
				Name = ((Triangel) mev.getSource()).getKeyName();

				System.out.println(Name);
				if (b.getPlace() == 0) {
					String Description = ((Triangel) mev.getSource()).getDescription();
					System.out.println("Enter if 2");
					int x = ((Triangel) mev.getSource()).GetTheX();
					int y = ((Triangel) mev.getSource()).GetTheY();
					Position p = new Position(x, y);

					JOptionPane.showMessageDialog(Window.this,
							"" + Name + ": [" + p.getX() + ", " + p.getY() + "] " + Description);
				} else if (b.getPlace() == 1) {

					int x = ((Triangel) mev.getSource()).GetTheX();
					int y = ((Triangel) mev.getSource()).GetTheY();
					Position p = new Position(x, y);
					JOptionPane.showMessageDialog(Window.this, "" + Name + ": [" + p.getX() + ", " + p.getY() + "] ");
				}

			}
		}

	}

	class DescritionApparat extends JPanel {
		JTextField NamnFalt = new JTextField(10);
		JTextField DescriptionFalt = new JTextField(20);

		DescritionApparat() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Namn:"));
			rad1.add(NamnFalt);
			add(rad1);
			JPanel rad2 = new JPanel();
			rad2.add(new JLabel("Description:"));
			rad2.add(DescriptionFalt);
			add(rad2);

		}

		private String getNamn() {
			return NamnFalt.getText();
		}

		private String getDescription() {
			return DescriptionFalt.getText();
		}
	}

	public static void main(String[] args) {
		new Window();
	}
}
