package com.qupeng.concurrent.day04.part1;

import java.util.concurrent.Semaphore;

/**
 * 信号灯
 * 这个信号灯可以一次性拿多个和释放多个
 * 这样就可以动态地去进行线程的锁的释放和持有
 * 并且SemaPhore还提供有
 * 获取当前灯的个数和当前等待线程数的API
 * 这样就可以在运行过程中对齐进行控制
 * @author qupeng
 */
public class SemaPhoreTest02 {
	

	public static void main(String[] args) throws InterruptedException {
		
		final Semaphore sema=new Semaphore(3);
		new Thread("线程1："){
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				try {
					sema.acquire(2);
					System.out.println(threadName+"我要开始修车了，我拿走了2个扳手……");
					//拿走2个扳手之后修车用时3秒
					Thread.sleep(8000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					System.out.println(threadName+"车修好了，我放回去了1个扳手……");
					sema.release();
				}
			}
		}.start();
		
		
		new Thread("线程2："){
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				try {
					Thread.sleep(2000);
					sema.acquire();
					System.out.println(threadName+"我要开始修车了，我拿走了1个扳手……");
					//拿走1个扳手之后修车用时6秒
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					System.out.println(threadName+"车修好了，我放回去了1个扳手……");
					sema.release();
				}
			}
		}.start();
		
		
		new Thread("线程3："){
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				try {
					System.out.println(threadName+"我要开始修车了，我拿走了1个扳手……");
					sema.acquire();
					//拿走1个扳手之后修车用时5秒
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					System.out.println(threadName+"车修好了，我放回去了1个扳手……");
					sema.release();
				}
			}
		}.start();
		
		while(true){
			System.out.println("主线程：当前的扳手还有"+sema.availablePermits()+"个");
			Thread.sleep(1000);
			System.out.println("主线程：当前估计有"+sema.getQueueLength()+"个线程排队等候拿扳手。");
		}
		
	}

}
