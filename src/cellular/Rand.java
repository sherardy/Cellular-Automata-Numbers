import java.util.Deque;
import java.util.ArrayDeque;

public class Rand {
	private int s = 8;
	private Cell[][] grid = new Cell[s][s];
	private String[][] hexNums;
	private Deque<Long> values;

	public Rand() {
		values = new ArrayDeque<Long>();
		initialize();
	}

	// time)–which are treated collectively as a hexadecimal digit. These
	// hexadecimal hexNums are then juxtaposed–cell after cell and line
	// after line in a left-right, top-down manner–to create a sequence of
	// x y random numbers, where x; y are the grid's dimensions. The
	// process is then iterated. For example, an 8 8 grid will produce
	// 64 hexadecimal random hexNums every four time steps.
	public long getNext() {
		if (values.peekFirst() == null) {
			for (int i = 0; i < 8; i++) {
				step();
			}
			getHexNums();
			StringBuilder str = new StringBuilder();
			for (String[] nums : hexNums) {

				for (String hex : nums) {
					if (str.length() != 8) {
						str.append(hex);
						// System.out.println(hex);
					} else {
						// System.out.println()
						long l = Long.parseLong(str.toString(), 16);
						values.push(l);
						str.setLength(0);
					}
				}
			}
		}
		return values.removeFirst();
	}

	// public double getNextDouble() {
	// return Double.parseDouble(getNext());
	// }

	public void step() {
		Cell[][] next = new Cell[s][s];
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				int nextState = 1;
				int c = grid[i][j].getState();
				nextState ^= c;
				if (j < s - 1) { // east
					nextState ^= grid[i][j + 1].getState();
				}
				if (i < s - 1) { // south
					nextState ^= grid[i + 1][j].getState();
				}
				if (i > 0) { // north
					nextState ^= grid[i - 1][j].getState();
				}
				if (j > 0) { // west
					nextState ^= grid[i][j - 1].getState();
				}
				Cell n = grid[i][j];
				n.setState(nextState);

				next[i][j] = n;
			}
		}
		grid = next;

		// counter++;
	}

	public void initialize() {
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				grid[i][j] = new Cell((int) Math.floor(Math.random() * 2));
			}
		}
	}

	private void getHexNums() {
		hexNums = new String[s][s];
		StringBuilder str = new StringBuilder();
		// int i=0,j=0;
		for (int i = 0; i < s; i++) {
			// for (Cell[] cells : grid) {

			// for (Cell c : cells) {
			for (int j = 0; j < s; j++) {
				// System.out.println(grid[i][j].getBits());
				int[] bits = grid[i][j].getBits();
				for (int bit : bits) {
					str.append(bit);
				}
				// System.out.println(str);
				long value = Long.parseLong(str.toString(), 2);
				String hex = Long.toString(value, 16);
				hexNums[i][j] = hex;
				str = new StringBuilder();
			}
		}
	}
}