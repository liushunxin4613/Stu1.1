package com.fengyang.util;

public class IndentityCard {
	/**
	 * 验证身份证号是否合法
	 * @param number 身份证号
	 * @return
	 */
	public static boolean isLegal(String number) {
		if(number.length() != 18)
			return false;
		int[] no = new int[18];
		int[] xa = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		int[] xb = { 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
		char[] cc = new char[18];
		cc = number.toCharArray();
		int sum = 0;
		for (int i = 0; i < 17; i++) {
			no[i] = cc[i] - 48;
			sum += no[i] * xa[i];
		}
		if (cc[17] == 'X')
			no[17] = 10;
		else
			no[17] = cc[17] - 48;
		if (no[17] == xb[sum % 11])
			return true;
		else
			return false;
	}
}
