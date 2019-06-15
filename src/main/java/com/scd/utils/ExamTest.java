package com.scd.utils;

public class ExamTest {

	public static void main(String[] args) {
		Integer i01=59;
		int i02=59;
		Integer i03=Integer.valueOf(59);
		Integer i04=new Integer(59);
		System.out.println(i01 == i02);
		System.out.println(i01 == i03);
		System.out.println(i03 == i04);
		System.out.println(i02 == i04);
		int count = 0;
		for(int i = 0; i < 10; i++){
			count = count ++;
		}
		System.out.println(count);
	}

}
