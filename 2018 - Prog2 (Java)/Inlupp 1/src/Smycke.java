// Fredrik Hammar, frha2022

public class Smycke extends Vardesak {

	private int adelstenar;
	private boolean avGuld;
	private double varde;

	public Smycke(String namn, int adelstenar, boolean avGuld) {
		super(namn);
		this.adelstenar = adelstenar;
		this.avGuld = avGuld;
	}

	@Override
	public double getVarde() {
		if (avGuld = true) {
			varde = 2000;
			varde += (500 * adelstenar);
			varde = varde * 0.75;
			return varde;
		} else {
			varde = 700;
			varde += (500 * adelstenar);
			varde = varde * 0.75;
			return varde;
		}

	}

	public String toString() {
		if (avGuld)
			return namn + ": adelstenar: " + adelstenar + ", av guld" + ", varde: " + getVarde();
		else
			return namn + ": adelstenar: " + adelstenar + ", av silver" + ", varde: " + getVarde();

	}
}
