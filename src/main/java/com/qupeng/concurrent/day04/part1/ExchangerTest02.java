package com.qupeng.concurrent.day04.part1;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 线程间数据交换工具
 * 使用超时的方式进行交换
 * 早到的会抛超时异常，迟到一方就会一直等待
 * @author Peter
 */
public class ExchangerTest02 {

	public static void main(String[] args) {
		final Exchanger<String> echg = new Exchanger<>();

		Thread t1 = new Thread("线程1") {

			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				try {
					String changeData = "人民币";
					System.out.println(threadName + "：正准备把数据：" + changeData + "换出去");
					System.out.println(threadName+"：需要：1秒才能到达交易地点");
					Thread.sleep(1000);
					System.out.println(threadName+"：已经到达交易地点");
					String gainData = echg.exchange(changeData,2,TimeUnit.SECONDS);
					System.out.println(threadName + "：换回的数据为：" + gainData);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					System.out.println(threadName + "：等了2秒卖酱油的都还没到，不等了……");
				}
			}
		};

		Thread t2 = new Thread("线程2") {

			@Override
			public void run() {
				try {
					String changeData = "酱油";
					String threadName = Thread.currentThread().getName();
					System.out.println(threadName + "：正准备把数据：" + changeData + "换出去");
					System.out.println(threadName+"：需要：5秒才能到达交易地点");
					Thread.sleep(5000);
					System.out.println(threadName+"：已经到达交易地点");
					String gainData = echg.exchange(changeData);
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
