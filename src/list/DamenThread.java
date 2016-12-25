package list;

import java.util.concurrent.Callable;

public class DamenThread implements Runnable{
	
	public static void main(String[] args) {
		DamenThread t1 = new DamenThread();
		Thread t =  new Thread(t1," ÿª§œﬂ≥Ã");
		t.setDaemon(true);
		t.start();
		for(int i=0;i<10;i++){
			System.out.println(Thread.currentThread().getName()+":"+i);
		}
	}

	@Override
	public void run() {
		for(int i=0;i<1000;i++){
			System.out.println(Thread.currentThread().getName()+":"+i);
		}
	}

}
