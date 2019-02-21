package com.its.test.test;

public class PersonalIncomeTax {

	// 0元至1500元:3%
	// 1500元至4500元的部分 :10%
	// 超过4500元至9000元的部分:20%
	// 超过9000元至35000元的部分:25%
	// 超过35000元至55000元的部分:30%
	// 超过55000元至80000元的部分:35%
	// 超过80000元的部分:45%
	public static double calculate(double salary, double socialSecurity, double lowestTaxableLimit) {
		double tax = 0.0;
		double calculateNum = salary - socialSecurity - lowestTaxableLimit;
		// select 1500*0.03+3000*0.1+4500*0.2+1138.98*0.25 from dual
		if (calculateNum <= 1500) {// 0元至1500元:3%
			tax = calculateNum * 0.03;
		} else if (calculateNum <= 4500) {// 1500元至4500元的部分 :10%
			tax = 1500 * 0.03 + (calculateNum - 1500) * 0.1;
		} else if (calculateNum <= 9000) {// 超过4500元至9000元的部分:20%
			tax = 1500 * 0.03 + 3000 * 0.1 + (calculateNum - 4500) * 0.2;
		} else if (calculateNum <= 35000) {// 超过9000元至35000元的部分:25%
			tax = 1500 * 0.03 + 3000 * 0.1 + 4500 * 0.2 + (calculateNum - 9000) * 0.25;
		} else if (calculateNum <= 55000) {// 超过35000元至55000元的部分:30%
			tax = 1500 * 0.03 + 3000 * 0.1 + 4500 * 0.2 + 26000 * 0.25 + (calculateNum - 35000) * 0.3;
		} else if (calculateNum <= 80000) {// 超过55000元至80000元的部分:35%
			tax = 1500 * 0.03 + 3000 * 0.1 + 4500 * 0.2 + 26000 * 0.25 + 20000 * 0.3 + (calculateNum - 55000) * 0.35;
		} else {// 超过80000元的部分:45%
			tax = 1500 * 0.03 + 3000 * 0.1 + 4500 * 0.2 + 26000 * 0.25 + 20000 * 0.3 + 25000 * 0.35
					+ (calculateNum - 80000) * 0.45;
		}
		return tax;
	}
	
	// 0元至3000元:3%
	// 3000元至12000元的部分 :10%
	// 超过12000元至25000元的部分:20%
	// 超过25000元至35000元的部分:25%
	// 超过35000元至55000元的部分:30%
	// 超过55000元至80000元的部分:35%
	// 超过80000元的部分:45%
	public static double calculateNew(double salary, double socialSecurity, double lowestTaxableLimit) {
		double tax = 0.0;
		double calculateNum = salary - socialSecurity - lowestTaxableLimit;
		if (calculateNum <= 3000) {// 0元至3000元:3%
			tax = calculateNum * 0.03;
		} else if (calculateNum <= 12000) {// 3000元至12000元的部分 :10%
			tax = 3000 * 0.03 + (calculateNum - 3000) * 0.1;
		} else if (calculateNum <= 25000) {// 超过12000元至25000元的部分:20%
			tax = 3000 * 0.03 + 9000 * 0.1 + (calculateNum - 12000) * 0.2;
		} else if (calculateNum <= 35000) {// 超过9000元至35000元的部分:25%
			tax = 3000 * 0.03 + 9000 * 0.1 + 13000 * 0.2 + (calculateNum - 9000) * 0.25;
		} else if (calculateNum <= 55000) {// 超过35000元至55000元的部分:30%
			tax = 3000 * 0.03 + 9000 * 0.1 + 13000 * 0.2 + 10000 * 0.25 + (calculateNum - 35000) * 0.3;
		} else if (calculateNum <= 80000) {// 超过55000元至80000元的部分:35%
			tax = 3000 * 0.03 + 9000 * 0.1 + 13000 * 0.2 + 10000 * 0.25 + 20000 * 0.3 + (calculateNum - 55000) * 0.35;
		} else {// 超过80000元的部分:45%
			tax = 3000 * 0.03 + 9000 * 0.1 + 13000 * 0.2 + 10000 * 0.25 + 20000 * 0.3 + 25000 * 0.35
					+ (calculateNum - 80000) * 0.45;
		}
		return tax;
	}
	
	public static void main(String[] args) {
		double salary_2017 = 500000;
		double salary_2018 = 100000;
		double makeUp = 0;// 补发
		double salary = salary_2018 + makeUp;
		// 个人基本养老保险、医疗保险、失业保险、住房公积金扣款
		double socialSecurity = 220.56 + 16.70 + 6.6 + 5000;
		double lowestTaxableLimit = 3500;// 起征点
		double lowestTaxableLimitNew = 5000;// 起征点
		System.out.println( salary_2018-socialSecurity);
		System.out.println("salary_2018_add:" + (salary_2018 - salary_2017) + ",salary_2018_add_percentage:"
				+ (salary_2018 - salary_2017) / salary_2017 * 100 + "%" + ",salary:" + salary + ",socialSecurity:"
				+ socialSecurity + ",lowestTaxableLimit:" + lowestTaxableLimit);
		double tax = calculate(salary, socialSecurity, lowestTaxableLimit);
		double taxNew = calculateNew(salary, socialSecurity, lowestTaxableLimitNew);
		System.out.println("实发：" + (salary - socialSecurity - tax) + "应缴税款:" + tax);
		System.out.println("New--实发：" + (salary - socialSecurity - taxNew) + "应缴税款:" + taxNew);
	}
}
