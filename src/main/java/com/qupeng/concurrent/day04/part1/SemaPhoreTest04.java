package com.qupeng.concurrent.day04.part1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号灯
 * 拿走全部的灯、尝试获取并设置超时时间
 * @author qupeng
 */
public class SemaPhoreTest04 {
	

	public static void main(String[] args) throws InterruptedException {
		
		final Semaphore sema=new Semaphore(10);
		Thread t1 = new Thread("线程1："){
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				try {
					System.out.println(threadName+"我要开始修车了，我准备拿走全部的扳手……");
					int gainNum = sema.drainPermits();
					System.out.println(threadName+"我已经拿走了全部的扳手……一共拿走了"+gainNum+"个");
					//拿走1个扳手之后修车用时3秒     1秒
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					
				}finally {
					System.out.println(threadName+"车修好了，我放回去了1个扳手……");
					sema.release();
				}
			}
		};
		t1.start();
		
		/*
		Thread t2 = new Thread("线程2："){
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				try {
					//让线程2，1秒后再去拿扳手
					Thread.sleep(1000);
					System.out.println(threadName+"我要开始修车了，我准备拿走1个扳手……");
					sema.acquire();
					System.out.println(threadName+"我已经拿走了1个扳手……");
					//拿走1个扳手之后修车用时1秒
					Thread.sleep(1000);
					System.out.println(threadName+"车修好了，我放回去了1个扳手……");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					sema.release();
				}
			}
		};
		t2.start();*/
		
		
		Thread t3 = new Thread("线程3："){
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				try {
					Thread.sleep(2000);
					System.out.println(threadName+"我要开始修车了，我准备拿走1个扳手……");
					System.out.println(threadName+"tryAcquire之前有"+sema.availablePermits()+"个扳手");
					boolean isAcquired = sema.tryAcquire();//这里尝试拿扳手，拿不到就直接往下执行，而不会在这里等待
					System.out.println(threadName+"tryAcquire之后有"+sema.availablePermits()+"个扳手");
					
//					System.out.println(threadName+"我要开始修车了，我准备拿走2个扳手……");
//					System.out.println(threadName+"tryAcquire之前有"+sema.availablePermits()+"个扳手");
//					boolean isAcquired = sema.tryAcquire(2, 3, TimeUnit.SECONDS);
//					System.out.println(threadName+"tryAcquire过了3秒之后有"+sema.availablePermits()+"个扳手");
					
					
					if(!isAcquired){
						System.out.println(threadName+"已经等了3秒了，还是没有2个扳手，那么我走了……");
						System.out.println(threadName+"既然没有扳手那我不修车了，我要自己先干别的事，不在这里傻等！");
						return;
					}
//					System.out.println(threadName+"我已经拿走了1个扳手……");
					
//					System.out.println(threadName+"我已经拿走了2个扳手……");
					//拿走1个扳手之后修车用时5秒
					Thread.sleep(5000);
					System.out.println(threadName+"车修好了，我放回去了1个扳手……");
				} catch (InterruptedException e) {
					
				}finally {
					sema.release(1);
				}
			}
		};
		t3.start();
		
		System.out.println("主线程执行结束！");
		
	}

}
