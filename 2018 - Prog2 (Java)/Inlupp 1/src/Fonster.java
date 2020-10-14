// Fredrik Hammar, frha2022

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Fonster extends JFrame {

	String[] Vardesaker = { "Smycken", "Apparater", "Aktier" };
	JComboBox<String> boxen = new JComboBox<>(Vardesaker);
	JTextArea display;
	JRadioButton namnButton;
	JRadioButton vardeButton;
	ArrayList<Vardesak> alla = new ArrayList<>();

	Fonster() {
		super("saksregister");

		// Center Text area where we list our things
		display = new JTextArea();
		add(display, BorderLayout.CENTER);

		JScrollPane scroll = new JScrollPane(display);
		display.setEditable(false);
		add(scroll, BorderLayout.CENTER);

		JPanel ovre = new JPanel();
		// ovre.setLayout(new BoxLayout(ovre, BoxLayout.X_AXIS));
		add(ovre, BorderLayout.NORTH);
		ovre.add(new JLabel("Vardesaker"));
		
		// East Panel with radio button that sorts through either name or worth
		JPanel hogra = new JPanel();
		hogra.setLayout(new BoxLayout(hogra, BoxLayout.Y_AXIS));
		add(hogra, BorderLayout.EAST);
		hogra.add(new JLabel("Sortering"));
		namnButton = new JRadioButton("Namn");
		hogra.add(namnButton);
		vardeButton = new JRadioButton("Varde");
		hogra.add(vardeButton);
		ButtonGroup bg = new ButtonGroup();
		bg.add(namnButton);
		bg.add(vardeButton);

		// Lower panel with buttons and combo box
		JPanel nedre = new JPanel();
		add(nedre, BorderLayout.SOUTH);
		nedre.add(new JLabel("Nytt:"));
		nedre.add(boxen);
		boxen.addActionListener(new VardesakLyss());
		JButton visaButton = new JButton("Visa");
		nedre.add(visaButton);
		visaButton.addActionListener(new VisaLyss());
		JButton borskrachButton = new JButton("Borskrach");
		nedre.add(borskrachButton);
		borskrachButton.addActionListener(new BorsKrachLyss());
		// Makes the frame visible and sets it size and also exists the program on
		// closing
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 600);
		setVisible(true);
	}

	class FormularSmycke extends JPanel {
		JTextField namnFalt = new JTextField(10);
		JTextField stenFalt = new JTextField(3);
		JCheckBox guldBox = new JCheckBox("av Guld");

		FormularSmycke() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Namn:"));
			rad1.add(namnFalt);
			add(rad1);
			JPanel rad2 = new JPanel();
			rad2.add(new JLabel("Stenar:"));
			rad2.add(stenFalt);
			add(rad2);
			add(guldBox);

		}

		private String getSmyckeNamn() {
			return namnFalt.getText();
		}

		private int getSten() {
			return Integer.parseInt(stenFalt.getText());
		}

		private boolean getGuld() {
			return guldBox.isSelected();
		}
	}

	class FormularAktie extends JPanel {
		JTextField antalNamnFalt = new JTextField(10);
		JTextField antalFalt = new JTextField(3);
		JTextField kursFalt = new JTextField(3);

		FormularAktie() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Namn:"));
			rad1.add(antalNamnFalt);
			add(rad1);
			JPanel rad2 = new JPanel();
			rad2.add(new JLabel("Antal:"));
			rad2.add(antalFalt);
			add(rad2);
			JPanel rad3 = new JPanel();
			rad3.add(new JLabel("Kurs:"));
			rad3.add(kursFalt);
			add(rad3);

		}

		private String getAktieNamn() {
			return antalNamnFalt.getText();
		}

		private int getAntal() {
			return Integer.parseInt(antalFalt.getText());
		}

		private double getKurs() {
			return Double.parseDouble(kursFalt.getText());
		}
	}

	class FormularApparat extends JPanel {
		JTextField apparatNamnFalt = new JTextField(10);
		JTextField prisFalt = new JTextField(3);
		JTextField slitageFalt = new JTextField(3);

		FormularApparat() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Namn:"));
			rad1.add(apparatNamnFalt);
			add(rad1);
			JPanel rad2 = new JPanel();
			rad2.add(new JLabel("Pris:"));
			rad2.add(prisFalt);
			add(rad2);
			JPanel rad3 = new JPanel();
			rad3.add(new JLabel("Slitage:"));
			rad3.add(slitageFalt);
			add(rad3);

		}

		private String getApparatNamn() {
			return apparatNamnFalt.getText();
		}

		private double getPris() {
			return Double.parseDouble(prisFalt.getText());
		}

		private double getSlitage() {
			return Integer.parseInt(slitageFalt.getText());
		}
	}

	class VardesakLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			try {
				String valt = (String) boxen.getSelectedItem();
				if (valt == "Smycken") {
					FormularSmycke f = new FormularSmycke();
					int svar = JOptionPane.showConfirmDialog(Fonster.this, f, "Ny", JOptionPane.OK_CANCEL_OPTION);
					if (svar != JOptionPane.OK_OPTION)
						return;
					String namn = f.getSmyckeNamn();
					int sten = f.getSten();
					boolean avGuld = f.getGuld();
					Vardesak v = new Smycke(namn, sten, avGuld);
					alla.add(v);
				} else if (valt == "Apparater") {
					FormularApparat f = new FormularApparat();
					int svar = JOptionPane.showConfirmDialog(Fonster.this, f, "Ny", JOptionPane.OK_CANCEL_OPTION);
					if (svar != JOptionPane.OK_OPTION)
						return;
					String namn = f.getApparatNamn();
					double inkopspris = f.getPris();
					double slitage = f.getSlitage();
					if (slitage > 10 || slitage < 1) {
						JOptionPane.showMessageDialog(Fonster.this,
								"Fel indata! Slitage maste vara ett nummer mellan 1-10");
						return;
					}
					Vardesak a = new Apparat(namn, inkopspris, slitage);
					alla.add(a);
				} else if (valt == "Aktier") {
					FormularAktie f = new FormularAktie();
					int svar = JOptionPane.showConfirmDialog(Fonster.this, f, "Ny", JOptionPane.OK_CANCEL_OPTION);
					if (svar != JOptionPane.OK_OPTION)
						return;
					String namn = f.getAktieNamn();
					int antal = f.getAntal();
					double kurs = f.getKurs();
					Vardesak v = new Aktier(namn, antal, kurs);
					alla.add(v);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(Fonster.this, "Fel indata!");
			}
		}
	}

	class VisaLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			display.setText("");
			if (namnButton.isSelected()) {
				Collections.sort(alla, new Comparator<Vardesak>() {
					@Override
					public int compare(Vardesak item, Vardesak t1) {
						String s1 = item.getNamn();
						String s2 = t1.getNamn();
						return s1.compareToIgnoreCase(s2);

					}
				});
			}
			if (vardeButton.isSelected()) {
				Collections.sort(alla);
			}

			for (Vardesak va : alla)
				display.append(va.toString() + "\n");

		}
	}

	class BorsKrachLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			for (Vardesak va : alla)
				if (va instanceof Aktier) {
					Aktier k = (Aktier) va;
					k.borskrasch();
				}

		}
	}

	public static void main(String[] args) {

		// Creates the frame
		new Fonster();
	}
}
