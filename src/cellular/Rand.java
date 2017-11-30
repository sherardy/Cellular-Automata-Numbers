import java.util.Deque;
import java.util.ArrayDeque;

public class Rand {
	private int s = 8; // size. needs to change to be a constant 
	private Cell[][] grid = new Cell[s][s];
	private String[][] hexNums; // 2d grid of hexadecimal digits created from the CA
	private Deque<Long> values; // numbers produced from the generated hex digits

	public Rand() {
		values = new ArrayDeque<Long>();
		//initialize();
	}

	// time)Ðwhich are treated collectively as a hexadecimal digit. These
	// hexadecimal hexNums are then juxtaposedÐcell after cell and line
	// after line in a left-right, top-down mannerÐto create a sequence of
	// x y random numbers, where x; y are the grid's dimensions. The
	// process is then iterated. For example, an 8 8 grid will produce
	// 64 hexadecimal random hexNums every four time steps.
	public long getNext() {
		if (values.peekFirst() == null) { // dont need to generate more random nums unless we're actually out of them
			// run for 4 time steps, so each cell will have 
			// generated 4 bits to then create a hexadecimal digit from
			for (int i = 0; i < 4; i++) { 
				step();
			}
			getHexNums(); // create the grid of hex digits from the CA grid after 4 time steps
			
			// next part generates takes the 8 hex digits on each row and uses that to create a 32 bit integer
			// so on an 8x8 grid it creates 8 32-bit integers using 64 hexadecimal digits
			// could possibly create an another 8 by also taking the hex values from top to bottom?
			StringBuilder str = new StringBuilder();
			for (String[] nums : hexNums) {
				for (String hex : nums) {
					if (str.length() != 8) {
						str.append(hex);
						// System.out.println(hex);
					} else {
						// System.out.println()
						long l = Long.parseLong(str.toString(), 16); // converts hex value to long
						values.push(l); // add it to list of random values
						str.setLength(0); // reset the string builder
					}
				}
			}
		}
		return values.removeFirst();
	}

	// public double getNextDouble() {
	// return Double.parseDouble(getNext());
	// }
	
	// updates the grid by 1 time step using rule 63 (1 XOR C XOR N XOR S XOR E XOR W)
	// does not apply changes to the grid until all the new states have been calculated first
	// so a cell wont affect the outcome of the other cells by updating its state before they have
	public void step() {
		Cell[][] next = new Cell[s][s];
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				int nextState = 1;
				int c = grid[i][j].getState();
				nextState ^= c;
				// for cells on the edges, itll ignore them 
				// so a cell on the top row wont take into account cells to the north in the rule for example
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
				// copy the cell and its new state to the temporary grid with the updated cells
				Cell n = grid[i][j];
				n.setState(nextState);

				next[i][j] = n;
			}
		}
		// apply the new states to the actual grid
		grid = next;

		// counter++;
	}

	public void seed(long seed) {
	/*	for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				grid[i][j] = new Cell((int) Math.floor(Math.random() * 2));
			}
		} */
    	String bits=Long.toBinaryString(seed);
    	int len=bits.length();
    	while (len>0) {
        for (int i = 0; i < s; i++) {
    	    for (int j = 0; j < s; j++) {
    		    if (len>0) {
    		        grid[i][j]=new Cell(Integer.parseInt(Character.toString(bits.charAt(len-1))));
    		        len--;
    		    }
                else
    		       grid[i][j]=new Cell(0);
                
    		  
    	    }
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
