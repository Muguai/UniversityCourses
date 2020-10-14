// Fredrik Hammar, frha2022	

public class Apparat extends Vardesak {

	private double inkopspris;
	private double slitage; // bara mellan 1 och 10!
	private double varde;

	public Apparat(String namn, double inkopspris, double slitage) {
		super(namn);
		this.inkopspris = inkopspris;
		this.slitage = slitage;
	}

	@Override
	public double getVarde() {
		varde = inkopspris * (slitage / 10);
		varde = varde * 0.75;
		return varde;
	}

	public String toString() {
		return namn + ": inkopspris:  " + inkopspris + ", slitage: " + slitage + ", varde: " + getVarde();
	}
}