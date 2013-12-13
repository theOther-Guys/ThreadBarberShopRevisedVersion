
public class RunBS {

	public static void main(String[] args) {

		int chairs = 1;
		while (true) {
			chairs = 1;

			System.out.println("the program has started ... !");

			Barbers barbershop = new Barbers(chairs);

			B barber = new B(barbershop);
			barber.start();

			int count = 1;

			while (barber.isRunning()) {
				C customer = new C(barbershop, count);

				customer.start();
				waitSecond();
				count++;
			}

			System.exit(count);
		}
	}

	public static void waitSecond() {
		long start = System.currentTimeMillis();
		long last = 0;
		boolean pause = false;
		while (!pause) {
			last = System.currentTimeMillis();
			if ((last - start) > 2000)
				pause = true;
		}
	}
}
