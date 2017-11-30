public class Main {
	public static void main(String[] args) {
		
		Rand r = new Rand();
		r.seed(2856);
		double timeAvg, sum=0;
		int x=0;
		for (; x<10; x++) {
			final long start = System.currentTimeMillis();
			for (int i = 0; i < 100000; i++) {
				long num = r.getNext() % 10;
			}
			final long stop = System.currentTimeMillis();
			long ms=stop-start;
			sum+=ms;
			System.out.println("Execution #"+x+": "+(double)ms/1000.0+" seconds");
		}
		timeAvg=sum/x;
		System.out.println("Average: "+(double)timeAvg/1000.0+" seconds");
		
		
	}

}
