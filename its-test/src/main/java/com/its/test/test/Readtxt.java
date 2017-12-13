package com.its.test.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Readtxt {

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
//				System.out.println("Line" + i + ":" + str);
				System.out.println("Line" + i);
				i++;
				if (i>=300000) {
					i=1;
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

}