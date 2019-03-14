package org.TestCases;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class getExcelData {


	public static ArrayList<Object[]> getExcelData() throws BiffException, IOException
	{
		ArrayList<Object[]> list = new ArrayList<Object[]> ();

		Workbook wb = Workbook.getWorkbook(new File(System.getProperty("user.dir")+"/resources/ListOfUsers.xls"));
		Sheet sh = wb.getSheet("UserList");



		for(int i=1; i < sh.getRows(); i++)
		{
			String fname = sh.getCell(0, i).getContents();
			String lname = sh.getCell(1, i).getContents();
			String user  = sh.getCell(2, i).getContents();
			String pass  = sh.getCell(3, i).getContents();
			String compa = sh.getCell(4, i).getContents();
			String role  = sh.getCell(5, i).getContents();
			String email = sh.getCell(6, i).getContents();
			String phone = sh.getCell(7, i).getContents();

			list.add(new Object[]{fname,lname,user,pass,compa,role,email,phone});

		}



		wb.close();

		return list;
	}


}
