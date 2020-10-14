// Fredrik Hammar, frha2022

public class Aktier extends Vardesak {

	private int antal;
	private double kurs;
	private double varde;

	public Aktier(String namn, int antal, double kurs) {
		super(namn);
		this.antal = antal;
		this.kurs = kurs;
	}

	@Override
	public double getVarde() {
		varde = antal * kurs;
		varde = varde * 0.75;
		return varde;
	}

	public String toString() {
		return namn + ": antal:  " + antal + ", kurs: " + kurs + ", varde: " + getVarde();

	}

	public void borskrasch() {
		this.kurs = 0;
	}

}
