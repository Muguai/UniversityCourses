
public class Position {
	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String toString() {
		return x + "-" + y;
	}

	public static Position parsePosition(String str) {
		int pos = str.indexOf("-");
		int x = Integer.parseInt(str.substring(0, pos));
		int y = Integer.parseInt(str.substring(pos + 1));
		return new Position(x, y);
	}

//	public boolean equals(Object other) {
//		if (other instanceof Position) {
//			Position p = (Position) other;
//			if (p.x == x && p.y == y) {
//				return true;
//			} else {
//				return false;
//			}
//		} else {
//			return false;
//		}
//	}
//
//	public int hashCode() {
//		return x * 10000 + y;
//	}
//
//	public int compareTo(Position other) {
//		if (x < other.x)
//			return -1;
//		else if (x > other.x)
//			return 1;
//		else if (y < other.y)
//			return -1;
//		else if (y > other.y)
//			return 1;
//		else
//			return 0;
//	}
}
