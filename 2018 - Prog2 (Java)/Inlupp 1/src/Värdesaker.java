// Fredrik Hammar, frha2022

abstract class Vardesak implements Comparable<Vardesak> {

	protected String namn;

	public Vardesak(String namn) {
		this.namn = namn;
	}

	public String getNamn() {
		return namn;
	}

	public abstract double getVarde();

	public int compareTo(Vardesak t1) {
		return (int) (t1.getVarde() - this.getVarde());

	}

}