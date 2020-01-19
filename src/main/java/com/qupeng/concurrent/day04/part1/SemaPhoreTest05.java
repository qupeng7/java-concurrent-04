package com.qupeng.concurrent.day04.part1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 信号灯
 * 那么是否可以先放再拿呢？
 * @author Peter
 */
public class SemaPhoreTest05 {
	
	private    static  int   a=10;
	
	private static Lock myLock=new ReentrantLock();
	
	private static Semaphore mySema=new Semaphore(1);
	
	
	private static void decrementFive(){
		try {
			String threadName = Thread.currentThread().getName();
//			boolean isMyLocked =false;
//			isMyLocked= myLock.tryLock();
//			System.out.println(threadName+"尝试加锁完毕，加锁成功了么："+isMyLocked);
//			myLock.lock();
			mySema.tryAcquire();
			for(int i=0;i<5;i++){
				Thread.sleep(1_000);
				a--;
				System.out.println(threadName+"当前a的值是："+a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			mySema.release();
//			myLock.unlock();
		}
	}
	

	public static void main(String[] args) throws InterruptedException {
		
		
//		final Semaphore sema=new Semaphore(2);
//		final Lock rLock=new ReentrantLock();
////		rLock.unlock();
////		rLock.lock();
////		sema.release(4);
////		System.out.println("当前有"+sema.availablePermits()+"个灯");
////		sema.acquire();
//		
//		new Thread("子线程："){
//			@Override
//			public void run() {
//				decrementFive();
//			}
//		}.start();
//		
//		Thread.sleep(2000);
//		
//		decrementFive();
//		System.out.println("主线程执行结束！");
//		
	}

}
