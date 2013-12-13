
class C extends Thread {

	Barbers barbershop;
	int id;

	boolean stop = false;
	boolean currentThread = false;
	boolean sitted = false;

	public C(Barbers bshop, int id) {
		this.barbershop = bshop;
		this.id = id;

	}

	public Barbers getBarbershop() {
		return this.barbershop;
	}

	public void setBarbershop(Barbers barbershop) {
		this.barbershop = barbershop;
	}

	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void run() {

		try {
			barbershop.getHairCut(this);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (!currentThread) {
			try {
				sleep(900);
			} catch (InterruptedException e) {
			}
			if (stop) {
				currentThread = true;
			}
		}
	}

}
