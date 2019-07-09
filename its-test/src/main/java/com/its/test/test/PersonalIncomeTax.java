package com.its.test.test;

/**
 * PersonalIncomeTax
 * @author 01115486
 */
public class PersonalIncomeTax {

    /**
     * 0元至1500元:3% <br>
     * 1500元至4500元的部分 :10% <br>
     * 超过4500元至9000元的部分:20% <br>
     * 超过9000元至35000元的部分:25% <br>
     * 超过35000元至55000元的部分:30% <br>
     * 超过55000元至80000元的部分:35% <br>
     * 超过80000元的部分:45%
     * 
     * @param salary
     * @param socialSecurity
     * @param lowestTaxableLimit
     * @return
     */
	public static double calculate(double salary, double socialSecurity, double lowestTaxableLimit) {
		double tax = 0.0;
		double calculateNum = salary - socialSecurity - lowestTaxableLimit;
		int a =1500;
		int b =4500;
		int c =9000;
		int d =35000;
		int e =55000;
		int f =80000;
		// 0元至1500元:3%
		if (calculateNum <= a) {
			tax = calculateNum * 0.03;
		} else if (calculateNum <= b) {
		    // 1500元至4500元的部分 :10%
			tax = 1500 * 0.03 + (calculateNum - 1500) * 0.1;
		} else if (calculateNum <= c) {
		    // 超过4500元至9000元的部分:20%
			tax = 1500 * 0.03 + 3000 * 0.1 + (calculateNum - 4500) * 0.2;
		} else if (calculateNum <= d) {
		    // 超过9000元至35000元的部分:25%
			tax = 1500 * 0.03 + 3000 * 0.1 + 4500 * 0.2 + (calculateNum - 9000) * 0.25;
		} else if (calculateNum <= e) {
		    // 超过35000元至55000元的部分:30%
			tax = 1500 * 0.03 + 3000 * 0.1 + 4500 * 0.2 + 26000 * 0.25 + (calculateNum - 35000) * 0.3;
		} else if (calculateNum <= f) {
		    // 超过55000元至80000元的部分:35%
			tax = 1500 * 0.03 + 3000 * 0.1 + 4500 * 0.2 + 26000 * 0.25 + 20000 * 0.3 + (calculateNum - 55000) * 0.35;
		} else {
		    // 超过80000元的部分:45%
			tax = 1500 * 0.03 + 3000 * 0.1 + 4500 * 0.2 + 26000 * 0.25 + 20000 * 0.3 + 25000 * 0.35
					+ (calculateNum - 80000) * 0.45;
		}
		return tax;
	}

    /**
     * 
     * 0元至3000元:3% <br>
     * 3000元至12000元的部分 :10% <br>
     * 超过12000元至25000元的部分:20% <br>
     * 超过25000元至35000元的部分:25% <br>
     * 超过35000元至55000元的部分:30% <br>
     * 超过55000元至80000元的部分:35% <br>
     * 超过80000元的部分:45%
     * 
     * @param salary
     * @param socialSecurity
     * @param lowestTaxableLimit
     * @return
     */
	public static double calculateNew(double salary, double socialSecurity, double lowestTaxableLimit) {
		double tax = 0.0;
		double calculateNum = salary - socialSecurity - lowestTaxableLimit;
		int a = 3000;
		int b = 12000;
		int c = 25000;
		int d = 35000;
		int e = 55000;
		int f = 80000;
		if (calculateNum <= a) {
		    // 0元至3000元:3%
			tax = calculateNum * 0.03;
		} else if (calculateNum <= b) {
		    // 3000元至12000元的部分 :10%
			tax = 3000 * 0.03 + (calculateNum - 3000) * 0.1;
		} else if (calculateNum <= c) {
		    // 超过12000元至25000元的部分:20%
			tax = 3000 * 0.03 + 9000 * 0.1 + (calculateNum - 12000) * 0.2;
		} else if (calculateNum <= d) {
		    // 超过9000元至35000元的部分:25%
			tax = 3000 * 0.03 + 9000 * 0.1 + 13000 * 0.2 + (calculateNum - 9000) * 0.25;
		} else if (calculateNum <= e) {
		    // 超过35000元至55000元的部分:30%
			tax = 3000 * 0.03 + 9000 * 0.1 + 13000 * 0.2 + 10000 * 0.25 + (calculateNum - 35000) * 0.3;
		} else if (calculateNum <= f) {
		    // 超过55000元至80000元的部分:35%
			tax = 3000 * 0.03 + 9000 * 0.1 + 13000 * 0.2 + 10000 * 0.25 + 20000 * 0.3 + (calculateNum - 55000) * 0.35;
		} else {
		    // 超过80000元的部分:45%
			tax = 3000 * 0.03 + 9000 * 0.1 + 13000 * 0.2 + 10000 * 0.25 + 20000 * 0.3 + 25000 * 0.35
					+ (calculateNum - 80000) * 0.45;
		}
		return tax;
	}
	
	public static void main(String[] args) {
		double salary2017 = 500000;
		double salary2018 = 100000;
		// 补发
		double makeUp = 0;
		double salary = salary2018 + makeUp;
		// 个人基本养老保险、医疗保险、失业保险、住房公积金扣款
		double socialSecurity = 220.56 + 16.70 + 6.6 + 5000;
		// 起征点
		double lowestTaxableLimit = 3500;
		// 起征点
		double lowestTaxableLimitNew = 5000;
		System.out.println( salary2018-socialSecurity);
		System.out.println("salary2018_add:" + (salary2018 - salary2017) + ",salary2018_add_percentage:"
				+ (salary2018 - salary2017) / salary2017 * 100 + "%" + ",salary:" + salary + ",socialSecurity:"
				+ socialSecurity + ",lowestTaxableLimit:" + lowestTaxableLimit);
		double tax = calculate(salary, socialSecurity, lowestTaxableLimit);
		double taxNew = calculateNew(salary, socialSecurity, lowestTaxableLimitNew);
		System.out.println("实发：" + (salary - socialSecurity - tax) + "应缴税款:" + tax);
		System.out.println("New--实发：" + (salary - socialSecurity - taxNew) + "应缴税款:" + taxNew);
	}
}
