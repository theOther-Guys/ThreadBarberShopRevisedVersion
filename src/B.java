
class B extends Thread {

	private Barbers barbershop;
	private boolean threadStop = false;
	private long startTime = 0;
	private long endTime = 0;

	public B(Barbers barbershop) {
		this.barbershop = barbershop;
	}

	public boolean isRunning() {
		if (threadStop)
			return false;
		else
			return true;
	}

	@Override
	public void run() {
		startTime = System.currentTimeMillis();
		while (!threadStop) {
			barbershop.WorkingTime();
			endTime = System.currentTimeMillis();

			if ((endTime - startTime) > 100) {
				threadStop = true;
				RunBS.waitSecond();
				RunBS.waitSecond();
				RunBS.waitSecond();
				RunBS.waitSecond();
				RunBS.waitSecond();
				System.out
						.println("barber is going home now.!");
				try {
					Thread.sleep(3000);
				}catch(InterruptedException e){
					
				}
				System.out.println("ghizzzzzh   tagh!");
				System.out.println(" he has left the shop");
				
			}
		}
	}
}
