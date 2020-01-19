package com.qupeng.concurrent.day04.part1;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 线程间数据交换工具
 * 交换时后是操作的同一个引用和同一个堆内存对象
 * 所以必须考虑交换数据的线程安全问题
 * @author qupeng
 */
public class ExchangerTest03 {

	public static void main(String[] args) {
		final Exchanger<int[]> echg = new Exchanger<>();

		Thread t1 = new Thread("线程1") {

			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				try {
					int[] changeData ={3,2,1};
					System.out.println(threadName + "：正准备把数据：" + changeData + "换出去");
					System.out.println(threadName+"：需要：1秒才能到达交易地点");
					Thread.sleep(1000);
					System.out.println(threadName+"：已经到达交易地点");
					int[] gainData = echg.exchange(changeData);
					System.out.println(threadName + "：换回的数据为：" + gainData);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		};
		
		Thread t2 = new Thread("线程2") {
			@Override
			public void run() {
				try {
					int[] changeData = {1,2,3};
					String threadName = Thread.currentThread().getName();
					System.out.println(threadName + "：正准备把数据：" + changeData + "换出去");
					System.out.println(threadName+"：需要：3秒才能到达交易地点");
					Thread.sleep(3000);
					System.out.println(threadName+"：已经到达交易地点");
					int[] gainData = echg.exchange(changeData);
					System.out.println(threadName + "：换回的数据为：" + gainData);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		t1.start();
		t2.start();

	}

}
