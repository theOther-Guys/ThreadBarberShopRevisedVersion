import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barbers {

	private boolean isOpen = false;
	private ReentrantLock lockOfShop = new ReentrantLock(true);
	private Condition sit = lockOfShop.newCondition();
	private Condition seeAvailableCustomer = lockOfShop.newCondition();
	private int barbers = 2;
	private int availableChair;
	private ArrayList<C> listOfCustomer = new ArrayList<C>();
	private int waiting = 0;
	private C readyCustomer;

	public Barbers(int numberOfChairs) {

		this.availableChair = numberOfChairs;

	}

	public void getHairCut(C readyc) throws InterruptedException {

		if (waiting < availableChair) {
			listOfCustomer.add(readyc);
			waiting++;
			System.out.println("new customer " + readyc.getID()
					+ ": has came ine...!");
			System.out.println(" barber is cutting hair of new customer..");
		} else if (waiting >= availableChair) {
			System.out.println("customer " + readyc.getID()
					+ " must go, he sees the shop is full...!");
			readyc.stop = true;
		}

		if (!isOpen) {
			System.out.println("shop is closed now...!");
			return;
		}
		try {
			lockOfShop.lock();
			while (barbers == 0) {

				try {
					sit.await();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}

			}
			RunBS.waitSecond();
			
			barbers--;
			seeAvailableCustomer.signal();

			barbers++;
			sit.signalAll();
			if(availableChair / 5 == 0){
				System.out.println(" please wait i am going to laterine...");
				Thread.sleep(4000);
				System.out.println(" i came back now i'll start work again.!");
			}
		} finally {
			lockOfShop.unlock();
		}
		readyc.stop = true;
		System.out.println("my work time is finished i must go . good bye...");
		try {
			System.out.println("customer " + readyCustomer.id
					+ " left the shop");
		} catch (NullPointerException w) {
			System.out.println(" customer not available ....");
			Thread.sleep(3000);
		}
	}

	public void WorkingTime() {
		try {

			lockOfShop.lock();
			isOpen = true;
			while (isOpen) {
				try {
					while (waiting <= 0) {
						System.out.println("the Barber is sleeping now!");
						seeAvailableCustomer.await();
					}

				} catch (InterruptedException ex) {
					if (!isOpen)
						break;
					else
						ex.printStackTrace();
				}
				readyCustomer = listOfCustomer.get(0);
				System.out.println("The barber is cutting hair of customer "
						+ readyCustomer.id);
				listOfCustomer.remove(0);
				waiting--;
				try {
					Random r = new Random();
					Thread.sleep((long) (r.nextDouble() * 2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			lockOfShop.unlock();
		}
	}

}
