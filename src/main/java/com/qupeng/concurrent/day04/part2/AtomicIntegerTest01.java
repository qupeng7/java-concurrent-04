package com.qupeng.concurrent.day04.part2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子int类型
 * @author Peter
 */
public class AtomicIntegerTest01 {
	

	public static void main(String[] args) {
		AtomicInteger atmInteger=new AtomicInteger();
		
		System.out.println("获取值："+atmInteger.get());
		atmInteger=new AtomicInteger(5);
		System.out.println("获取值："+atmInteger.get());
		atmInteger.set(10);
		System.out.println("获取值："+atmInteger.get());
		int getAndAddValue = atmInteger.getAndAdd(10);
		System.out.println("获取值："+getAndAddValue);
		System.out.println("获取值："+atmInteger.get());

	}

}
