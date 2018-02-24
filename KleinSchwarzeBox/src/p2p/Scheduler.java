package p2p;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {
	public static void main(String[] args) {
		Runnable job = new Job1();
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

		Random rand = new Random();
		for(int i=0; i<100; i++) {
			scheduler.schedule(job, rand.nextInt(1440), TimeUnit.SECONDS);
		}
//		scheduler.scheduleAtFixedRate(job, 0, 1, TimeUnit.MINUTES);
//		scheduler.scheduleWithFixedDelay(job, 0, 2, TimeUnit.MINUTES);
		while(true) {
			for(int i=0; i<120; i++) {
				System.out.print(".");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println();
		}
	}
}
