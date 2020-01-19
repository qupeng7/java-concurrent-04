package com.qupeng.concurrent.day04.part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 线程间数据交换工具
 * 是否能进行多次交换
 * @author qupeng
 */
public class ExchangerTest04 {

	public static void main(String[] args) {
		final Exchanger<List<String>> echg = new Exchanger<>();

		Thread t1 = new Thread("线程1") {

			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				try {
					List<String> syncChangeData = Collections.synchronizedList(new ArrayList<String>());
					syncChangeData.add("D");
					syncChangeData.add("E");
					syncChangeData.add("F");
					System.out.println(threadName + "：正准备把数据：" + syncChangeData + "换出去");
					System.out.println(threadName+"：需要：1秒才能到达交易地点");
					for(int i=0;i<5;i++){
						Thread.sleep(1000);
						System.out.println(threadName+"：已经到达交易地点");
						List<String> gainData = echg.exchange(syncChangeData);
						syncChangeData=gainData;
						System.out.println(threadName + "：换回的数据为：" + gainData);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		};
		
		Thread t2 = new Thread("线程2") {
			@Override
			public void run() {
				try {
					String threadName = Thread.currentThread().getName();
					List<String> syncChangeData = Collections.synchronizedList(new ArrayList<String>());
					syncChangeData.add("A");
					syncChangeData.add("B");
					syncChangeData.add("C");
					System.out.println(threadName + "：正准备把数据：" + syncChangeData + "换出去");
					for (int i = 0; i < 3; i++) {
						System.out.println(threadName+"：需要：2秒才能到达交易地点");
						Thread.sleep(2000);
						System.out.println(threadName+"：已经到达交易地点");
						List<String> gainData = echg.exchange(syncChangeData);
						syncChangeData=gainData;
						System.out.println(threadName + "：换回的数据为：" + syncChangeData);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		t1.start();
		t2.start();

	}

}
