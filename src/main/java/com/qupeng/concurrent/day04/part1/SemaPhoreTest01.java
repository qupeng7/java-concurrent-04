package com.qupeng.concurrent.day04.part1;

import java.util.concurrent.Semaphore;

/**
 * 信号灯
 * 
 * 类似于锁，但是锁只能有一个，而这个
 * 信号灯可以有多个且其可以实现灯的释放可以是不同线程，
 * 就可以解决死锁的问题；
 * 如果构造器为1，那么就和ReentrantLock用法类似
 * @author qupeng
 */
public class SemaPhoreTest01 {
	
	private static  int a=10;
	
	private static Semaphore sema=new Semaphore(1);
	
	public static  void decrement(){
		try {
			sema.acquire();
			a--;
			System.out.println(Thread.currentThread().getName()+"：a的值为："+a);
			Thread.sleep(1_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			sema.release();
		}
	}

	public static void main(String[] args) {
		
		new Thread("线程1"){
			@Override
			public void run() {
				for(int i=1;i<=5;i++){
					decrement();
				}
			}
		}.start();
		
		
		new Thread("线程2"){
			@Override
			public void run() {
				for(int i=1;i<=5;i++){
					decrement();
				}
			}
		}.start();
	}

}
