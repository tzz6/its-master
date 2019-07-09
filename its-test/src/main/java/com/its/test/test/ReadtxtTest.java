package com.its.test.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 
 * @author tzz
 */
public class ReadtxtTest {

	private static Logger logger = Logger.getLogger(ReadtxtTest.class);
	
	public static void main(String[] args) {
		try {

			FileReader reader = new FileReader("D:\\Test\\sql\\ti_tcms_send.sql");
			BufferedReader br = new BufferedReader(reader);

			String str = null;
			int i = 1;
			int fileIndex = 1;
			StringBuffer sb = new StringBuffer("");
			while ((str = br.readLine()) != null) {
				sb.append(str + "\n");
				// System.out.println("Line" + i + ":" + str);
				System.out.println("Line" + i);
				i++;
				if (i >= 300000) {
					i = 1;
					FileWriter writer = new FileWriter("D:\\Test\\sql\\ti_tcms_send_30W_" + fileIndex + ".sql");
					fileIndex++;
					BufferedWriter bw = new BufferedWriter(writer);
					bw.write(sb.toString());
					bw.close();
					writer.close();
					sb.delete(0, sb.length());
				}
			}
			br.close();
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1() {
		List<String> list = new ArrayList<>();
		int end = 15;
		for (int i = 1; i < end; i++) {
			list.add("test" + i);
		}
		for (String str : list) {
			try {
				int result = test1F(str);
				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("Test-finally");
			}
		}
	}

	public int test1F(String str) {
		int flag = 1;
		try {
		    String s = "0";
			if (str.contains(s)) {
				System.out.println(1 / 0);
			}
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
		} finally {
			System.out.println("Test-return");
		}
		return flag;
	}
	
    /**
     * finally块的语句在try或catch中的return语句执行之后返回之前执行且finally里的修改语句不能影响try或catch中 return已经确定的返回值， <br>
     * 若finally里也有return语句则覆盖try或catch中的return语句直接返回。
     */
    @Test
    public void test2() {
        int result = test2F("test20");
        System.out.println(result);
    }
	
	public int test2F(String str) {
		try {
		    String s = "0";
			if (str.contains(s)) {
				System.out.println(1 / 0);
			}
			System.out.println(str);
			return 1;
		} catch (Exception e) {
			logger.error("Exception", e);
			return 2;
		} finally {
			System.out.println("Test-return");
			//return 3;//一直return3
		}
	}
	

}