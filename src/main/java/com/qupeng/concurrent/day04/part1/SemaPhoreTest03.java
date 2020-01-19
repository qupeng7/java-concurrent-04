package com.qupeng.concurrent.day04.part1;

import java.util.concurrent.Semaphore;

/**
 * 信号灯
 * 对中断的感知
 * @author qupeng
 */
public class SemaPhoreTest03 {
	

	public static void main(String[] args) throws InterruptedException {
		
		final Semaphore sema=new Semaphore(2);
		Thread t1 = new Thread("线程1："){
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				try {
					System.out.println(threadName+"我要开始修车了，我准备拿走1个扳手……");
					sema.acquire();
					System.out.println(threadName+"我已经拿走了1个扳手……");
					//拿走1个扳手之后修车用时8秒
					Thread.sleep(8000);
					System.out.println(threadName+"车修好了，我放回去了1个扳手……");
				} catch (InterruptedException e) {
					
				}finally {
					sema.release();
				}
			}
		};
		t1.start();
		
		
		Thread t2 = new Thread("线程2："){
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				try {
					System.out.println(threadName+"我要开始修车了，我准备拿走1个扳手……");
					sema.acquire();
					System.out.println(threadName+"我已经拿走了1个扳手……");
					//拿走1个扳手之后修车用时6秒
					Thread.sleep(6000);
					System.out.println(threadName+"车修好了，我放回去了1个扳手……");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					sema.release();
				}
			}
		};
		t2.start();
		
		//为了让线程3是最后执行的这里休息一下
		Thread.sleep(100);
		
		Thread t3 = new Thread("线程3："){
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				//使用acquireUninterruptibly方法就是隔绝对中断的感知
				sema.acquireUninterruptibly();
				try {
					System.out.println(threadName+"我要开始修车了，我准备拿走1个扳手……");
//					sema.acquire();
					System.out.println(threadName+"我已经拿走了1个扳手……");
					try {
						//拿走1个扳手之后修车用时5秒
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						System.out.println(threadName+"由sleep抛出的中断异常！");
						throw new InterruptedException();
					}
					System.out.println(threadName+"车修好了，我放回去了1个扳手……");
				} catch (InterruptedException e) {
					System.out.println(threadName+"此时还有"+sema.availablePermits()+"个扳手");
					System.out.println(threadName+"我在通过SemaPhore等待的过程中被中断了！");
				}finally {
					sema.release();
				}
			}
		};
		t3.start();
		
		/*
		 * 3秒后，线程1和线程2还没有把车修好
		 * 主线程就中断正在等待的线程3
		 */
		Thread.sleep(3000);
		t3.interrupt();
		System.out.println("主线程：已对线程3进行了中断。");
		System.out.println("主线程执行结束！");
		
	}

}
