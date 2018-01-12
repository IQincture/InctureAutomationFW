package com.incture.utility.ftp.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class FileReporting {




	public static void main(String[] args) {
		FileReporting ftp=new FileReporting();
		Writer w=ftp.createReport("E:\\Reports\\FtpReport.html");
		ftp.writeHeaderPart(w);
		ftp.writeTableData(w, "HI Hello", "HI Hello", "pass");	
		ftp.writeTableData(w, "HI Hello", "HI Hellod", "fail");	
		ftp.writeFotterPart(w);
		ftp.closeReport(w);
	}

	public Writer createReport(String fileName){
		try{

			File statText = new File(fileName);
			FileOutputStream is = new FileOutputStream(statText);
			OutputStreamWriter osw = new OutputStreamWriter(is);    
			Writer w = new BufferedWriter(osw);
			/*w.write("POTATO!!!");
			w.close();*/
			return w;
		}catch (Exception e) {
			System.out.println("Exception in CreateReport file creation");	
		}
		return null;	
	}
	public void closeReport(Writer w){
		try {
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeHeaderPart(Writer w){
		try{
			String headerHTML="<!DOCTYPE html>"+
					"<html>"+
					"<head>"+
					"<style>"+
					"* {"+
					"  box-sizing: border-box;"+
					"}"+
					""+
					"#myInput {"+
					"  background-image: url('/css/searchicon.png');"+
					"  background-position: 10px 10px;"+
					"  background-repeat: no-repeat;"+
					"  width: 100%;"+
					"  font-size: 16px;"+
					"  padding: 12px 20px 12px 40px;"+
					"  border: 1px solid #ddd;"+
					"  margin-bottom: 12px;"+
					"}"+
					""+
					"#myTable {"+
					"  border-collapse: collapse;"+
					"  width: 100%;"+
					"  border: 1px solid #ddd;"+
					"  font-size: 18px;"+
					"}"+
					""+
					"#myTable th, #myTable td {"+
					"border: 1px solid black;"+
					"  text-align: left;"+
					"  padding: 5px;"+
					"}"+
					""+
					"#myTable tr {"+
					"  border-bottom: 1px solid #ddd;"+
					"}"+
					""+
					"#myTable tr.header, #myTable tr:hover {"+
					"  background-color: #778899;"+
					"}"+
					".fail {"+
					"  background-color:red;"+
					"  font-size: medium;"+
					"}"+
					".warning {"+
					"  background-color:yellow;"+
					"  font-size: medium;"+
					"}"+
					"</style>"+
					""+
					"<script>"+
					"function myFunction() {"+
					"  var input, filter, table, tr, td, i;"+
					"  input = document.getElementById('myInput');"+
					"  filter = input.value.toUpperCase();"+
					"  table = document.getElementById('myTable');"+
					"  tr = table.getElementsByTagName('tr');"+
					"  for (i = 0; i < tr.length; i++) {"+
					"    td = tr[i].getElementsByTagName('td')[2];"+
					"    if (td) {"+
					"      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {"+
					"        tr[i].style.display = '';"+
					"      } else {"+
					"        tr[i].style.display = 'none';"+
					"      }"+
					"    }       "+
					"  }"+
					"}"+
					"</script>"+
					"</head>"+
					"<body>"+
					""+
					"<h2>Compare Report</h2>"+
					""+
					"<input type='text' id='myInput' onkeyup='myFunction()' placeholder='Search for names..' title='Type in a name'>"+
					""+
					"<table id='myTable'>"+
					"  <tr class='header'>"+
					"    <th style='width:60%;'>Source</th>"+
					"    <th style='width:40%;'>Destination</th>"+
					"	<th style='width:40%;'> Status</th>"+
					"  </tr>";

			w.write(headerHTML);
		}catch (Exception e) {
			System.out.println("ExceptionWhileWritingFile"+e.getMessage());

		}
	}

	public void writeTableData(Writer w,String srcText,String destText,String status){

		try {
			w.write("  <tr>");

			if(status.equals("pass")){
				w.write("<td>"+srcText+"</td>");
				w.write("<td>"+destText+"</td>");
				w.write("<td>"+status+"</td>");

			}else if(status.equals("fail")){
				w.write("<td >"+findDiffStrings(srcText, destText,"fail")+"</td>");
				w.write("<td >"+findDiffStrings(destText, srcText,"fail")+"</td>");
				w.write("<td class='fail' >"+status+"</td>");


				/**
				 * w.write("<td class='fail' >"+srcText+"</td>");
			w.write("<td class='fail'>"+destText+"</td>");
			w.write("<td class='fail'>"+status+"</td>");
				 */
			}	else if(status.equals("warning")){
				/* w.write("<td class='warning' >"+srcText+"</td>");
					 w.write("<td class='warning'>"+destText+"</td>");
					 w.write("<td >"+get_Str1_Html(srcText, destText,"warning")+"</td>");
					w.write("<td >"+get_Str2_Html(srcText, destText,"warning")+"</td>");
					 w.write("<td class='warning'>"+status+"</td>");
				 */

				w.write("<td >"+charDiff(srcText, destText,"warning")+"</td>");
				w.write("<td >"+charDiff(destText, srcText,"warning")+"</td>");
				w.write("<td class='warning'>"+status+"</td>");

			}		
			w.write("  </tr>");

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	public void writeTableData_HyperLink(Writer w,String srcFileName,String srcFileInfo,String destFileName,String destFileInfo,String status){

		try {
			w.write("  <tr>");

			if(status.equals("pass")){
				w.write("<td><a href='"+srcFileName+"_"+destFileName+".html' target='_blank'>"+srcFileName+"</a></td>");//<a href="File_4.txt_File_4.txt.html" target="_blank">Src.txt</a>
				w.write("<td><a href='"+srcFileName+"_"+destFileName+".html' target='_blank'>"+destFileName+"</a></td>");//<a href="File_4.txt_File_4.txt.html" target="_blank">Src.txt</a>
				w.write("<td><a href='"+srcFileName+"_"+destFileName+".html' target='_blank'>"+status+"</a></td>");//<a href="File_4.txt_File_4.txt.html" target="_blank">Src.txt</a>


			}else if(status.equals("fail")){
				w.write("<td class='fail'><a href='"+srcFileName+"_"+destFileName+".html' target='_blank'>"+srcFileName+srcFileInfo+"</a></td>");
				w.write("<td class='fail'><a href='"+srcFileName+"_"+destFileName+".html' target='_blank'>"+destFileName+destFileInfo+"</a></td>");
				w.write("<td class='fail'><a href='"+srcFileName+"_"+destFileName+".html' target='_blank'>"+status+"</a></td>");

			}		
			w.write("  </tr>");

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	public void writeFotterPart(Writer w){
		try{
			String fotter="</table>"+

				""+
				""+
				"</body>"+
				"</html>";
			w.write(fotter);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());			
		}
	}

	public static String get_Str1_Html(String str1,String str2,String fail_warning){


		String[] strList1 = str1.split(" ");
		String[] strList2 = str2.split(" ");

		List<String> list1 = Arrays.asList(strList1);
		List<String> list2 = Arrays.asList(strList2);

		String temp="";

		for(int i=0;i<list1.size();i++){

			if(list2.contains(list1.get(i)))
			{
				if(i<list1.size()-1){
					//	System.out.print(list1.get(i)+" ");
					temp=temp+list1.get(i)+" ";
				}
				else{
					//System.out.print(list1.get(i));
					temp=temp+list1.get(i);
				}

			}else{

				if(i<list1.size()-1){

					temp=temp+"<span class='"+fail_warning+"'>"+list1.get(i)+" </span>";
				}
				else{

					temp=temp+"<span class='"+fail_warning+"'>"+list1.get(i)+"</span>";
				}
			}	
		}
		return temp;

	}

	public static String get_Str2_Html(String str1,String str2,String fail_warning){


		String[] strList1 = str1.split(" ");
		String[] strList2 = str2.split(" ");

		List<String> list1 = Arrays.asList(strList1);
		List<String> list2 = Arrays.asList(strList2);

		String temp="";


		for(int i=0;i<list2.size();i++){

			if(list1.contains(list2.get(i)))
			{
				if(i<list2.size()-1){
					//System.out.print(list2.get(i)+" ");
					temp=temp+list2.get(i)+" ";
				}
				else{
					//System.out.print(list2.get(i));
					temp=temp+list2.get(i);
				}
			}else{

				if(i<list2.size()-1){

					temp=temp+"<span class='"+fail_warning+"'>"+list2.get(i)+" </span>";
				}
				else{

					temp=temp+"<span class='"+fail_warning+"'>"+list2.get(i)+"</span>";
				}
			}	
		}
		return temp;

	}

	public static StringBuffer charDiff(String str1,String str2,String fail_warning){

		StringBuffer result=new StringBuffer();

		char[] cStr1=str1.toCharArray();
		char[] cStr2=str2.toCharArray();


		for(int i=0;i<cStr1.length;i++){

			for(int j=i;j<cStr2.length;j++){

				if(cStr1[i]==cStr2[j]){
					result=result.append(cStr1[i]);
					break;
				}else{
					result=result.append("<span class='"+fail_warning+"'>"+cStr1[i]+"</span>");	
					break;
				}

			}

		}


		if(str1.length()>str2.length()){
			result=result.append("<span class='"+fail_warning+"'>"+str1.substring(str2.length(), str1.length())+"</span>");
		}

		return result;


	}
	//---------- new code ---------

	public static StringBuffer findDiffStrings(String s1, String s2,String fail_warning){
		
		if(s1.length()==s2.length())
		{
			return charDiff(s1, s2, fail_warning);
		}else {
			return printCommonSubstrings(s1, s2, fail_warning);
		}
		
		
	}
	
public static StringBuffer printCommonSubstrings(String s1, String s2,String fail_warning) {
	StringBuffer sb=new StringBuffer();
	String lastMatch="";
	String s2Lenth="";
   for (int i = 0; i < s1.length();) {
       List<String> list = new ArrayList<String>();
       for (int j = i; j < s1.length(); j++) {
           String subStr = s1.substring(i, j + 1);
           if (isSubstring(subStr, s2)) {
               list.add(subStr);
           }
       }
       if (!list.isEmpty()) {
           String s = list.get(list.size() - 1);
           lastMatch=s;
           s2Lenth=s2Lenth+lastMatch;
           s2=s2.replaceFirst(Pattern.quote(lastMatch),"");
          // commonSubstrings.add(s);
         //  System.out.print(s);
           sb.append(s);
           i += s.length();
       }else{
    	   //System.out.print("<red>"+s1.charAt(i)+"</red>");
    	   sb.append("<span class='fail'>"+s1.charAt(i)+"</span>");
    	   i++;
       }
   }
   return sb;
}

public static boolean isSubstring(String s1, String s2) {
   boolean isSubstring = true;
   int strLen = s2.length();
   int strToCheckLen = s1.length();
   if (strToCheckLen > strLen) {
       isSubstring = false;
   } else {
       for (int i = 0; i <= (strLen - strToCheckLen); i++) {
           int index = i;
           int startingIndex = i;
           for (int j = 0; j < strToCheckLen; j++) {
               if (!(s1.charAt(j) == s2.charAt(index))) {
                   break;
               } else {
                   index++;
               }
           }
           if ((index - startingIndex) < strToCheckLen) {
               isSubstring = false;
           } else {
               isSubstring = true;
               break;
           }
       }
   }
   return isSubstring;
}

}