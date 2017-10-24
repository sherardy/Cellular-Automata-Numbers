public class Main {
	public static void main(String[] args) {
		Rand r = new Rand();
		for (int i = 0; i < 100; i++) {
			long num = r.getNext() % 10;
			System.out.println(num);
		}
	}

}