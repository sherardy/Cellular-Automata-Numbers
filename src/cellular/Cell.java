import java.util.ArrayDeque;

public class Cell {

	private int state;
	private ArrayDeque<Integer> history;

	public Cell() {
		this.state = 0;
		history = new ArrayDeque<Integer>();
	}

	public Cell(int state) {
		this.state = state;
		history = new ArrayDeque<Integer>();
	}

	public int[] getBits() {
		int[] bits = new int[4];
		if (this.history.size() >= 4) {
			for (int i = 0; i < 4; i++) {
				// System.out.println(history.peekFirst());
				bits[i] = history.removeFirst();
			}
			return bits;
		} else {
			return null;
		}
	}

	public void setState(int state) {
		history.push(this.state);
		this.state = state;
	}

	public int getState() {
		return this.state;
	}
}