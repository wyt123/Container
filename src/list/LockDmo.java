package list;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ��������ģʽ
 * 		lock������
 * 		��ʵ�൱��һ��controller�����������װ��+��-����������˼·��д
 * @author wyt
 *
 */
public class LockDmo {

	public static void main(String[] args) {
		C c = new C();
		A a = new A(c);
		B b = new B(c);
		Thread t1 = new Thread(a);
		Thread t2 = new Thread(b);
		t1.start();
		t2.start();
	}
}
class A implements Runnable{
	C c ;
	public A(C c){
		this.c = c;
	}
	public void run() {
		for(int i=0;i<10;i++){
			c.add(i);
		}
	}
}
class B implements Runnable{
	C c;
	public B(C c){
		this.c = c;
	}
	public void run() {
		for(int i=0;i<10;i++){
			c.remove(i);
		}
	}
}
class C{
	Object obj;
	Map<Integer,Integer> map = new ConcurrentHashMap<>(10);
	Lock lock = new ReentrantLock();
	Condition cond = lock.newCondition();
	public void add(int index) {
		lock.lock();
		try{
			if(map.size()<10){
				map.put(index,index);
				System.out.println("�ŵ�"+index+"��");
				cond.signal();
			}else{
				System.out.println("�Ų��£�����");
				cond.await();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	public void remove(int index){
		lock.lock();
		try{
			if(map.size()>0){
				map.remove(index);
				System.out.println("��ȥ"+index+"��");
				cond.signal();
			}else{
				System.out.println("���ˣ��ȴ�����");
				cond.await();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
}