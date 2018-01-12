package com.incture.utility.ftp.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

public class xmlfilecheck {
	public static void main(String[] args) throws IOException { 
		File file1 = new File("E:/Asame1.xml");
		File file2 = new File("E:/Asame2.xml");
		
		FileReporting ftp=new FileReporting();
		Writer w=ftp.createReport("E:\\Reports\\"+file1.getName()+"_"+file2.getName()+".html");
		ftp.writeHeaderPart(w); 
			 

		ArrayList<String> linesinFile1 = getLines(file1);
		ArrayList<String> linesinFile2 = getLines(file2);
		ArrayList<String> uniqueItemsFromList1 = compareAndGetNotMatched(getLines(file1), getLines(file2));
		ArrayList<String> uniqueItemsFromList2 = compareAndGetNotMatched(getLines(file2), getLines(file1));
		HashMap<Integer,String> diff1=new HashMap<Integer,String>();
		HashMap<Integer,String> diff2=new HashMap<Integer,String>(); 
		for(String s : uniqueItemsFromList1){
			diff1.put(linesinFile1.indexOf(s),s);
		}
		for(String s : uniqueItemsFromList2){
			diff2.put(linesinFile2.indexOf(s), s);
		}
		String temp = "";
		if(linesinFile1.size()>linesinFile2.size()){
			for(int key1 : diff1.keySet()){
				String d1 = diff1.get(key1);
				temp = temp+d1.trim();
				for(int key2 : diff2.keySet()){
					String d2 = diff2.get(key2);
					Boolean condition1 = false;
					Boolean condition2 = false;
					if(StringUtils.isBlank(d1)){
						condition1 = d2.contains(d1);
					}
					else{
						condition1 = d2.contains(d1.substring(d1.indexOf("&lt;")+4, d1.indexOf("&gt;")));
						String s = "";
						try{
							s = d1.substring(d1.indexOf("&gt;")+1, d1.indexOf("&lt;/"));
							}
							catch(Exception e){
							
								s = d1.substring(d1.indexOf("&gt;")+1, d1.length());
							}
						condition2 = d2.contains(s);
					}
					if(condition1 || condition2){
						if(d2.contains(d1.trim())){
							linesinFile1.set(key1, "<span style=\"background-color: yellow\">"+d1+"</span");
							linesinFile2.set(key2, "<span style=\"background-color: yellow\">"+d2+"</span");
						}
						else{
							linesinFile1.set(key1, "<span style=\"background-color: red\">"+d1+"</span");
							linesinFile2.set(key2, "<span style=\"background-color: red\">"+d2+"</span");
						}
					}
					if(temp.contains("&lt;/")){
						temp = "";
					}
					
				}
				
			}
		}
		
		else if(linesinFile1.size()<linesinFile2.size()){
			for(int key1 : diff2.keySet()){
				String d1 = diff2.get(key1);
				temp = temp+d1.trim();
				for(int key2 : diff1.keySet()){
					String d2 = diff1.get(key2);
					Boolean condition1 = false;
					Boolean condition2 = false;
					if(StringUtils.isBlank(d1)){
						condition1 = d2.contains(d1);
					}
					else{
						condition1 = d2.contains(d1.substring(d1.indexOf("&lt;")+4, d1.indexOf("&gt;")));
						String s = "";
						try{
							s = d1.substring(d1.indexOf("&gt;")+1, d1.indexOf("&lt;/"));
							}
							catch(Exception e){
							
								s = d1.substring(d1.indexOf("&gt;")+1, d1.length());
							}
						condition2 = d2.contains(s);
					}
					if(condition1 || condition2){
						if(d2.contains(d1.trim())){
							linesinFile2.set(key1, "<span style=\"background-color: yellow\">"+d1+"</span");
							linesinFile1.set(key2, "<span style=\"background-color: yellow\">"+d2+"</span");
						}
						else{
							linesinFile2.set(key1, "<span style=\"background-color: red\">"+d1+"</span");
							linesinFile1.set(key2, "<span style=\"background-color: red\">"+d2+"</span");
						}
					}
					if(temp.contains("&lt;/")){
						temp = "";
					}
					
				}
				
			}
		}
		
		if(linesinFile1.size()>=linesinFile2.size()){
			for(int i=0; i<linesinFile1.size(); i++){
				System.out.println("Priniting");
				w.write("<tr>");
				w.write("<td>");
				w.write("<pre lang=\"xml\" >"+linesinFile1.get(i)+"</pre>");
				w.write("</td>");		
				w.write("<td>");
				if(i<linesinFile2.size())
					w.write("<pre lang=\"xml\" >"+linesinFile2.get(i)+"</pre>");
				w.write("</td>");
				w.write("<td>");
				if(linesinFile1.get(i).contains("yellow")){
					w.write("warning");
				}
				else if(linesinFile1.get(i).contains("red")){
					w.write("fail");
				}
				else{
					w.write("pass");
				}
				w.write("</td>");
				w.write("</tr>");
			}	
		}
		else{
			for(int i=0; i<linesinFile2.size(); i++){
				System.out.println("Priniting");
				w.write("<tr>");
				w.write("<td>");
				if(i<linesinFile1.size())
					w.write("<pre lang=\"xml\" >"+linesinFile1.get(i)+"</pre>");
				w.write("</td>");		
				w.write("<td>");
				//if(i<linesinFile2.size())
				w.write("<pre lang=\"xml\" >"+linesinFile2.get(i)+"</pre>");
				w.write("</td>");
				w.write("<td>");
				if(linesinFile2.get(i).contains("yellow")){
					w.write("warning");
				}
				else if(linesinFile2.get(i).contains("red")){
					w.write("fail");
				}
				else{
					w.write("pass");
				}
				w.write("</td>");
				w.write("</tr>");
			}	
		}
		
		ftp.writeFotterPart(w);
		ftp.closeReport(w);
		
	}
	
	public static ArrayList<String> getLines(File fileName) throws IOException{
		System.out.println("Printing");
		ArrayList<String> lines = new ArrayList<String>();
		FileReader fr1 = new FileReader(fileName);
        BufferedReader br1 = new BufferedReader(fr1);
        String line1 = null;
        int i =0;
        while ((line1 = br1.readLine()) != null) {
        	line1 = line1.replaceAll("<", "&lt;");
        	line1 = line1.replaceAll(">", "&gt;");
            lines.add(i, line1);
            i++;
        }
        fr1.close();
        br1.close();
        return lines;
	}
	
	public static ArrayList<String> compareAndGetNotMatched(ArrayList<String> list1, ArrayList<String> list2){
		list1.removeAll(list2);
		return list1;
	}
}
