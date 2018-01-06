package com.incture.utility.ftp.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class LocalFile {

	public static void main(String[] args) throws IOException {

		
		compareFilesDirectory_report();

		//compareFiles("D:\\Reports\\FTPReports\\CompareFiles\\", "D:\\Reports\\FTPReports\\CompareFiles\\","SENDFILE20170208-103248-843", "a810cngp.292043");


	}
	static FileReporting dirReport=new FileReporting();
	public static Writer wDir;
	public static void compareFilesDirectory_report()throws SocketException, IOException {
		wDir=dirReport.createReport("D:\\Reports\\LocalReports\\SummaryReport.html");
		dirReport.writeHeaderPart(wDir);


		compareFilesDirectory("D:\\Reports\\CompareFiles\\src\\","D:\\Reports\\CompareFiles\\dest\\");
		dirReport.writeFotterPart(wDir);
		dirReport.closeReport(wDir);

	}
	/**
	 * compareFTPFilesDirectory --> is to compare files & folder in the src & destination directory
	 * @param dir1 --> source directory eg:-/home/contintegration/Desktop/demo/
	 * @param dir2 --> destination directory eg:-/home/contintegration/Desktop/Final_Demo/
	 * @throws SocketException
	 * @throws IOException
	 */
	public static void compareFilesDirectory(String dir1,String dir2) throws SocketException, IOException{


		ArrayList<File> arrayDir1Files=new ArrayList<>();
		arrayDir1Files.addAll(getFilesList(dir1));
		ArrayList<File> arrayDir2Files=new ArrayList<>();
		arrayDir2Files.addAll(getFilesList(dir2));


		findUniqueFiles( dir1, dir2);

		for(File dir1File:arrayDir1Files)
		{
			for(File dir2File:arrayDir2Files)
			{


				String dri1FileName=dir1File.getName(),dir2FileName=dir2File.getName();
				if(dir1File.getName().contains("."))
					dri1FileName=dir1File.getName().replaceAll(dir1File.getName().substring(dir1File.getName().lastIndexOf("."), dir1File.getName().length()), "");
				if(dir2File.getName().contains("."))
					dir2FileName=dir2File.getName().replaceAll(dir2File.getName().substring(dir2File.getName().lastIndexOf("."), dir2File.getName().length()), "");


				if(dri1FileName.equals(dir2FileName))
				{
					if(dir1File.isDirectory())
						compareFilesDirectory(dir1+dir1File.getName()+"/", dir2+dir2File.getName()+"/");
					else if(dir1File.isFile()){
						Map<String, String> compare=compareFiles(dir1, dir2,dir1File.getName(),dir2File.getName());
						if(compare.get("flag").equals("true"))
							dirReport.writeTableData_HyperLink(wDir, dir1File.getName(),"", dir2File.getName(),"", "pass");
						else
							dirReport.writeTableData_HyperLink(wDir, dir1File.getName()," has (Total:"+compare.get("S_T")+",Unique:"+compare.get("S_U")+",Diff:"+compare.get("S_D")+",Blank:"+compare.get("S_B")+")",
									dir2File.getName()," has (Total:"+compare.get("D_T")+",Unique:"+compare.get("D_U")+",Diff:"+compare.get("D_D")+",Blank:"+compare.get("D_B")+")", "fail");
					}
				}

			}
		}


		/*int desLength=0;
		if(dir1Files.length>dir2Files.length)
		{
			desLength=dir2Files.length;
			arrayDir1Files.removeAll(arrayDir2Files);
			for(FTPFile file:arrayDir1Files)
			{
				System.out.println(file.getName());
			}

		}else{
			desLength=dir1Files.length;
		}

		for(int i=0;i<desLength;i++){

			if(dir1Files[i].isFile())
			compareFTPFiles(dir1, dir2,dir1Files[i].getName() );
			else if(dir1Files[i].isDirectory())
				compareFTPFilesDire(dir1+dir1Files[i]+"/", dir2+dir1Files[i]+"/");
		}*/




	}

	public static void findUniqueFiles(	String dir1,String dir2) throws SocketException, IOException{


		ArrayList<File> arrayDir1Files=new ArrayList<>();
		arrayDir1Files.addAll(getFilesList(dir1));
		ArrayList<File> arrayDir2Files=new ArrayList<>();
		arrayDir2Files.addAll(getFilesList(dir2));


		ArrayList<String> dir1ListFileNames=new ArrayList<>();ArrayList<String> dir2ListFileNames=new ArrayList<>();
		//filename.replaceAll(filename.substring(filename.lastIndexOf("."), filename.length()), "")
		for(File filedir1:arrayDir1Files){
			String filename=filedir1.getName();
			if(filename.contains("."))
				dir1ListFileNames.add(filename.replaceAll(filename.substring(filename.lastIndexOf("."), filename.length()), ""));
			else
				dir1ListFileNames.add(filename);
		}
		for(File filedir2:arrayDir2Files){
			String filename=filedir2.getName();
			if(filename.contains("."))
				dir2ListFileNames.add(filename.replaceAll(filename.substring(filename.lastIndexOf("."), filename.length()), ""));
			else
				dir2ListFileNames.add(filename);
		}






		/* dir1ListFileNames=new ArrayList<>();
		dir1ListFileNames.add("a");dir1ListFileNames.add("b");dir1ListFileNames.add("c");dir1ListFileNames.add("d");
		 dir2ListFileNames=new ArrayList<>();
		dir2ListFileNames.add("a");dir2ListFileNames.add("b");dir2ListFileNames.add("e");dir2ListFileNames.add("f");
		 */
		for(String fdir:dir1ListFileNames){
			if(dir2ListFileNames.contains(fdir))
			{
				System.out.println(fdir+"- is present both in dir");
				//System.out.println("Common file or folder in both directory		"+fdir+" - "+"directory1 is "+dir1+" & directory2 is "+dir2);
				dirReport.writeTableData(wDir, fdir +" -> is present in both dir "+dir1, dir2, "pass");
			}
		}
		for(String fdir1:dir1ListFileNames){
			if(!dir2ListFileNames.contains(fdir1))
			{
				System.out.println(fdir1+"- is present only in dir1");
				//System.out.println("Only in directory1	"+fdir1+"	"+"Path is "+dir1);
				dirReport.writeTableData(wDir, fdir1 +" -> is present only in "+dir1, "", "fail");
			}
		}
		for(String fdir2:dir2ListFileNames){

			if(!dir1ListFileNames.contains(fdir2))
			{
				System.out.println(fdir2+"- is present only in dir2");
				//System.out.println("Only in directory2	"+fdir2+"	"+"Path is "+dir2);
				dirReport.writeTableData(wDir, "", fdir2 +" -> is present only in "+dir2, "fail");
			}

		}

	}
	public static ArrayList<File> getFilesList(String dir) throws SocketException, IOException{


		File[] dir1Files=new File(dir).listFiles();


		ArrayList<File> arrayDir1Files=new ArrayList<>();
		arrayDir1Files.addAll(Arrays.asList(dir1Files));


		return arrayDir1Files;
	}

	/**
	 * compareFTPFiles --> this method is to compare the files only in current src & destination directorys
	 * @param compPath1 Eg:- /home/contintegration/Desktop/demo/
	 * @param compPath2
	 * @param fileName  Eg:- File_2.odt
	 * @throws IOException
	 */
	public static Map<String, String> compareFiles(String compPath1,String compPath2,String srcFileName,String destFileName) throws IOException{

		Map<String, String>	results=new HashMap<>();

		new File("D:\\Reports\\LocalReports\\").mkdirs();
		FileReporting ftp=new FileReporting();
		Writer w=ftp.createReport("D:\\Reports\\LocalReports\\"+srcFileName+"_"+destFileName+".html");
		ftp.writeHeaderPart(w);


		InputStream inSR=new FileInputStream(new File(compPath1+srcFileName));
		InputStream outSR=new FileInputStream(new File(compPath2+destFileName));
		InputStreamReader inISR=new InputStreamReader(inSR);
		InputStreamReader ourISR=new InputStreamReader(outSR);


		BufferedReader reader1 = new BufferedReader(inISR);

		BufferedReader reader2 = new BufferedReader(ourISR);

		String line1 =  reader1.readLine();

		String line2 = reader2.readLine();

		boolean areEqual = true;

		int srcLineNo = 1,destLineNo = 1,uniqueLinesNo = 0,diffLinesNo = 0;
		ArrayList<Integer> srcblankLine=new ArrayList<>();
		ArrayList<Integer> destblankLine=new ArrayList<>();

		do
		{


			if(line1 == null || line2 == null)
			{
				areEqual = false;

				//break;
			}
			else if(! line1.equals(line2))
			{

				if(Ftp.verifyRuleSet(line1, line2)){

					ftp.writeTableData(w, line1, line2, "warning");
				}else{


					areEqual = false;
					System.out.println(srcFileName+"(Src) & "+destFileName+"(Dest)-  Mismatch data  @   "+line1+" --and destination file  has --"+line2+" at line"+srcLineNo);
					diffLinesNo++;
					ftp.writeTableData(w, line1, line2, "fail");
				}

				//break;
			}else{
				uniqueLinesNo++;
				ftp.writeTableData(w, line1, line2, "pass");	
			}

			do{
				line1 = reader1.readLine();
				srcLineNo++;

				if(line1==null)break;
				if(line1.equals(""))srcblankLine.add(srcLineNo);
			}while(line1.equals(""));
			do{
				line2 = reader2.readLine();
				destLineNo++;

				if(line2==null)break;
				if(line2.equals(""))destblankLine.add(destLineNo);
			}while(line2.equals(""));


		}while (line1 != null || line2 != null);

		if(areEqual)
		{	
			System.out.println(srcFileName+"(Src) & "+destFileName+"(Dest)- has same content.");
			ftp.writeTableData(w, srcFileName, destFileName, "pass");	

		}
		if(srcLineNo>destLineNo){
			System.out.println(srcFileName+"(Src) file contains "+srcblankLine.size()+" blank line at "+srcblankLine+" and "+destFileName+"(Dest) file contains "+destblankLine.size()+"  blank line  at "+destblankLine );
			ftp.writeTableData(w, srcFileName+"(Src) file contains "+srcblankLine.size()+" blank line at "+srcblankLine, destFileName+"(Dest) file contains "+destblankLine.size()+"  blank line  at "+destblankLine, "fail");	
		}
		if(srcLineNo<destLineNo)
		{
			System.out.println(srcFileName+"(Src) file contains "+srcblankLine.size()+" blank line at "+srcblankLine+" and "+destFileName+"(Dest) file contains "+destblankLine.size()+"  blank line  at "+destblankLine );
			ftp.writeTableData(w, srcFileName+"(Src) file contains "+srcblankLine.size()+" blank line at "+srcblankLine, destFileName+"(Dest) file contains "+destblankLine.size()+"  blank line  at "+destblankLine, "fail");
		}

		/*if(srcLineNo<destLineNo)System.out.println(destFileName+" Destination file has "+(destLineNo-srcLineNo)+"blank No number of lines ");
		if(srcLineNo>destLineNo)System.out.println(srcFileName+" Src file has "+(srcLineNo-destLineNo)+"blank No number of lines ");
		 */

		reader1.close();

		reader2.close();

		ftp.writeFotterPart(w);
		ftp.closeReport(w);

		results.put("S_T", srcLineNo+"");
		results.put("S_U", uniqueLinesNo+"");
		results.put("S_B",srcblankLine.size()+"");
		results.put("S_D", diffLinesNo+"");

		results.put("D_T", destLineNo+"");
		results.put("D_U", uniqueLinesNo+"");
		results.put("D_B",destblankLine.size()+"");
		results.put("D_D", diffLinesNo+"");

		results.put("flag",areEqual+"");

		return results;
	}
}
