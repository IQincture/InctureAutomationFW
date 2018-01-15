package com.incture.utility.ftp.files;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.SocketException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.incture.utility.Constants;
import com.incture.utility.excel.Excel;
public class Ftp {

	public static String ExcelPath=".//src//com//incture//proj//testData//FileCompareRulesSet.xlsx";
	public static void main(String[] args) throws SocketException, IOException {


		dateFormate="dd-MMM-yyyy HH:mm:ss";

		/*		fromDateFormat = "11-Jan-2018 16:00:00";
		toDateFormat = "11-Jan-2018 18:00:00";
		 */
		/*fromDateFormat = "11-Jan-2018 16:00:00";
		toDateFormat = "13-Jan-2018 18:00:00";
		 */

		fromDateFormat = "13-Jan-2018 16:00:00";
		toDateFormat = "16-Jan-2018 18:00:00";


		compareIdocNumberes("/home/contintegration/Desktop/src/", "/home/contintegration/Desktop/dest/");


		//compareFTPFiles("/home/contintegration/Desktop/src/Folder1/", "/home/contintegration/Desktop/dest/Folder1/", "Gentran_Sample_File.txt", "Gentran_Sample_File.txt");
		//	compareFTPFilesDirectory_report();
		//oneToManyFileComparision("/home/contintegration/Desktop/src/Folder1/", "/home/contintegration/Desktop/dest/", "Asame.xml");
		//compareFTPFiles("/home/contintegration/Desktop/src/Folder1/", "/home/contintegration/Desktop/dest/Folder1/", "File_4.txt", "File_4.txt");
		//uploadDirectory("D:/uploadFiles/", "/home/contintegration/Desktop/Upload/");
		//download_Directory( "D:/download/", "/home/contintegration/Desktop/Upload/");
	}


	public static void compareFTPFilesDirectory_report()throws SocketException, IOException {
		wDir=dirReport.createReport("D:\\Reports\\FTPReports\\SummaryReport.html");
		dirReport.writeHeaderPart(wDir);


		compareFTPFilesDirectory("/home/contintegration/Desktop/src/","/home/contintegration/Desktop/dest/");

		dirReport.writeFotterPart(wDir);
		dirReport.closeReport(wDir);

	}


	/// first we are getting lengh of src aganist dest
	/**
	 * 
	 * @param srcDir
	 * @param destDir
	 * @param srcFileName
	 * @throws IOException 
	 * @throws SocketException 
	 */
	public static void oneToManyFileComparision(String srcDir,String destDir,String srcFileName) throws SocketException, IOException{

		int srcNoofLines=getLineCountFromFTPFile(srcDir+srcFileName);

		ArrayList<FTPFile> arrayDestFiles=new ArrayList<>();
		arrayDestFiles.addAll(getFTPFilesList(destDir));

		String srcIdocContent=getIDOCString(srcDir+srcFileName);

		for(FTPFile file:arrayDestFiles){

			if(file.isDirectory()){
				oneToManyFileComparision(srcDir, destDir+file.getName()+"/", srcFileName);
			}else{
				int destNoofLines=getLineCountFromFTPFile(destDir+file.getName());
				if(srcNoofLines==destNoofLines){

					if(srcIdocContent.equals(""))				
						compareFTPFiles(srcDir, destDir, srcFileName, file.getName());
					else{

						if(verifyIDOCString(destDir+file.getName(), srcIdocContent)){
							compareFTPFiles(srcDir, destDir, srcFileName, file.getName());
						}
					}
				}
			}


		}

	}


	/*public static String srcIdocFileName="";public static String destIdocFileName="";
	public static String srcIdocFileNamePath="";public static String destIdocFileNamePath="";
	 */
	public static String getIdocFileNamePath(String path,String idocNumbere) throws SocketException, IOException{
		idocNumbere=idocNumbere.trim();
		String idocFileName="";
		ArrayList<FTPFile> arrayDestFiles=new ArrayList<>();
		arrayDestFiles.addAll(getFTPFilesList(path));
		for(FTPFile file:arrayDestFiles){

			if(file.isDirectory()){
				getIdocFileNamePath(path+file.getName()+"/",idocNumbere);
			}else{
				String localIdocNumbere=getIDOCString(path+file.getName());
				if(localIdocNumbere.contains(idocNumbere))
				{
					idocFileName= path+file.getName();
					break;
				}

			}
		}
		return idocFileName;
	}



	public static void compareIdocNumberes(String srcDir,String destDir){

		String sheetName="IDOC";
		wDir=dirReport.createReport("D:\\Reports\\FTPReports\\IdocSummeryReport.html");
		dirReport.writeHeaderPart(wDir);



		Excel excel=new Excel();
		try {
			String idoc[]=excel.xlReadColumn(ExcelPath, sheetName, "IDOC_NO");
			ArrayList<String> idocList=new ArrayList<>();
			idocList.addAll(Arrays.asList(idoc));

			for(String idocEach:idocList){

				String srcIdocFile=	getIdocFileNamePath(srcDir, idocEach);
				String destIdocFile=	getIdocFileNamePath(destDir, idocEach);
				if(!srcIdocFile.equals("")&&!destIdocFile.equals("")){

					String compPath1=srcIdocFile.substring(0, srcIdocFile.lastIndexOf("/")+1);
					String compPath2=destIdocFile.substring(0, destIdocFile.lastIndexOf("/")+1);
					String srcFileName=srcIdocFile.substring(srcIdocFile.lastIndexOf("/")+1, srcIdocFile.length());
					String destFileName=destIdocFile.substring(destIdocFile.lastIndexOf("/")+1, destIdocFile.length());

					Map<String, String> compare=compareFTPFiles(compPath1, compPath2, srcFileName, destFileName);
					if(compare.get("flag").equals("true"))
						dirReport.writeTableData_HyperLink(wDir, srcFileName,"("+idocEach+")", destFileName,"("+idocEach+")", "pass");
					else
						dirReport.writeTableData_HyperLink(wDir, srcFileName,"("+idocEach+")"+" has (Total:"+compare.get("S_T")+",Unique:"+compare.get("S_U")+",Diff:"+compare.get("S_D")+",Blank:"+compare.get("S_B")+")",
								destFileName,"("+idocEach+")"+" has (Total:"+compare.get("D_T")+",Unique:"+compare.get("D_U")+",Diff:"+compare.get("D_D")+",Blank:"+compare.get("D_B")+")", "fail");

				}else{

					dirReport.writeTableData(wDir, !srcIdocFile.equals("") ? idocEach+" idoc has found in Src @ "+srcIdocFile:idocEach+" idoc has not found in Src", !destIdocFile.equals("") ? idocEach+" idoc has found in dest @ "+destIdocFile:idocEach+" idoc has not found in dest", "fail");
				}
			}			

			dirReport.writeFotterPart(wDir);
			dirReport.closeReport(wDir);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}


	public static String getIDOCString(String path) throws IOException{

		BufferedReader reader1 = new BufferedReader(new InputStreamReader(getFTPFile(path)));
		String line=reader1.readLine();

		do{
			/*if(line.contains("<IDOC")){
				return line;
			}*/
			if(line.contains("<DOCNUM>")){
				return line;
			}
			line=reader1.readLine();
		}while(!(line==null));
		return "";
	}

	public static boolean verifyIDOCString(String path,String idocString) throws IOException{

		BufferedReader reader1 = new BufferedReader(new InputStreamReader(getFTPFile(path)));
		String line=reader1.readLine();

		do{
			if(line.equals(idocString)){
				return true;
			}
			line=reader1.readLine();
		}while(!(line==null));
		return false;
	}

	static FileReporting dirReport=new FileReporting();
	public static Writer wDir;
	/**
	 * compareFTPFilesDirectory --> is to compare files & folder in the src & destination directory
	 * @param dir1 --> source directory eg:-/home/contintegration/Desktop/demo/
	 * @param dir2 --> destination directory eg:-/home/contintegration/Desktop/Final_Demo/
	 * @throws SocketException
	 * @throws IOException
	 */
	public static void compareFTPFilesDirectory(String dir1,String dir2) throws SocketException, IOException{


		ArrayList<FTPFile> arrayDir1Files=new ArrayList<>();
		arrayDir1Files.addAll(getFTPFilesList(dir1));
		ArrayList<FTPFile> arrayDir2Files=new ArrayList<>();
		arrayDir2Files.addAll(getFTPFilesList(dir2));


		findUniqueFiles( dir1, dir2);

		for(FTPFile dir1File:arrayDir1Files)
		{
			for(FTPFile dir2File:arrayDir2Files)
			{


				String dri1FileName=dir1File.getName(),dir2FileName=dir2File.getName();
				if(dir1File.getName().contains("."))
					dri1FileName=dir1File.getName().replaceAll(dir1File.getName().substring(dir1File.getName().lastIndexOf("."), dir1File.getName().length()), "");
				if(dir2File.getName().contains("."))
					dir2FileName=dir2File.getName().replaceAll(dir2File.getName().substring(dir2File.getName().lastIndexOf("."), dir2File.getName().length()), "");


				if(dri1FileName.equals(dir2FileName))
				{
					if(dir1File.isDirectory())
						compareFTPFilesDirectory(dir1+dir1File.getName()+"/", dir2+dir2File.getName()+"/");
					else if(dir1File.isFile()){
						Map<String, String> compare=compareFTPFiles(dir1, dir2,dir1File.getName(),dir2File.getName());
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


		ArrayList<FTPFile> arrayDir1Files=new ArrayList<>();
		arrayDir1Files.addAll(getFTPFilesList(dir1));
		ArrayList<FTPFile> arrayDir2Files=new ArrayList<>();
		arrayDir2Files.addAll(getFTPFilesList(dir2));


		ArrayList<String> dir1ListFileNames=new ArrayList<>();ArrayList<String> dir2ListFileNames=new ArrayList<>();
		//filename.replaceAll(filename.substring(filename.lastIndexOf("."), filename.length()), "")
		for(FTPFile filedir1:arrayDir1Files){
			String filename=filedir1.getName();
			if(filename.contains("."))
				dir1ListFileNames.add(filename.replaceAll(filename.substring(filename.lastIndexOf("."), filename.length()), ""));
			else
				dir1ListFileNames.add(filename);
		}
		for(FTPFile filedir2:arrayDir2Files){
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

	public static InputStream getFTPFile(String path) throws SocketException, IOException{


		FTPClient ftpCon1=Ftp.getFTPConnection();


		InputStream inSR=ftpCon1.retrieveFileStream(path);

		closeFTPConnection(ftpCon1);

		return inSR;

	}

	public static String toDateFormat,fromDateFormat,dateFormate;

	public static ArrayList<FTPFile> getFTPFilesList(String dir) throws SocketException, IOException{

		FTPClient connection=getFTPConnection();

		FTPFile[] dir1Files=connection.listFiles(dir);
		ArrayList<FTPFile> arrayDir1Files=new ArrayList<>();


		if (toDateFormat!=null && fromDateFormat!=null) {

			Date fromDate=getDate(dateFormate, fromDateFormat);
			Date toDate=getDate(dateFormate, toDateFormat);


			for(FTPFile file:dir1Files)
			{
				Date ftpFileDate=file.getTimestamp().getTime();
				if(fromDate.before(ftpFileDate)&&toDate.after(ftpFileDate)){
					arrayDir1Files.add(file);
					//System.out.println(file.getName()+":"+file.getTimestamp().getTime());
				}
			}


		}else{
			arrayDir1Files.addAll(Arrays.asList(dir1Files));

		}




		closeFTPConnection(connection);

		return arrayDir1Files;
	}

	public static FTPClient getFTPConnection() throws SocketException, IOException{

		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("192.168.5.36", 21);
		int replyCode = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(replyCode)) {
			System.out.println("Connection failed");
			return null;
		}

		boolean success = ftpClient.login("contintegration", "ConTIntegratioN@321");
		if (!success) {
			System.out.println("Could not login to the server");
			return null;
		}
		return ftpClient;	
	}

	public static void closeFTPConnection(FTPClient ftpClient) throws IOException{
		if(ftpClient.isConnected())
			ftpClient.disconnect();

	}

	/**
	 * compareFTPFiles --> this method is to compare the files only in current src & destination directorys
	 * @param compPath1 Eg:- /home/contintegration/Desktop/demo/
	 * @param compPath2
	 * @param fileName  Eg:- File_2.odt
	 * @throws IOException
	 */
	public static void compareFTPFiles(String compPath1,String compPath2,String fileName) throws IOException{

		/*if(fileName.equals("same.txt")){
			System.out.println();
		}*/

		FTPClient ftpCon1=Ftp.getFTPConnection();
		FTPClient ftpCon2=Ftp.getFTPConnection();


		InputStream inSR=ftpCon1.retrieveFileStream(compPath1+fileName);
		InputStream outSR=ftpCon2.retrieveFileStream(compPath2+fileName);
		InputStreamReader inISR=new InputStreamReader(inSR);
		InputStreamReader ourISR=new InputStreamReader(outSR);


		BufferedReader reader1 = new BufferedReader(inISR);

		BufferedReader reader2 = new BufferedReader(ourISR);

		String line1 =  reader1.readLine();

		String line2 = reader2.readLine();

		boolean areEqual = true;

		int srcLineNo = 1,destLineNo = 1;

		do
		{


			if(line1 == null || line2 == null)
			{
				areEqual = false;

				//break;
			}
			else if(! line1.equalsIgnoreCase(line2))
			{
				areEqual = false;
				System.out.println(fileName+"-  Mismatch data  @   "+line1+" --and destination file  has --"+line2+" at line"+srcLineNo);
				//break;
			}

			do{
				line1 = reader1.readLine();
				srcLineNo++;
				if(line1==null)break;
			}while(line1.equals(""));
			do{
				line2 = reader2.readLine();
				destLineNo++;
				if(line2==null)break;
			}while(line2.equals(""));


		}while (line1 != null || line2 != null);

		if(areEqual)
		{
			System.out.println(fileName+"- has same content.");

		}
		if(srcLineNo<destLineNo)System.out.println(fileName+"Destination file has "+(destLineNo-srcLineNo)+"blank No number of lines ");
		if(srcLineNo>destLineNo)System.out.println(fileName+"Src file has "+(srcLineNo-destLineNo)+"blank No number of lines ");


		reader1.close();

		reader2.close();

		Ftp.closeFTPConnection(ftpCon1);
		Ftp.closeFTPConnection(ftpCon2);
	}

	/**
	 * compareFTPFiles --> this method is to compare the files only in current src & destination directorys
	 * @param compPath1 Eg:- /home/contintegration/Desktop/demo/
	 * @param compPath2
	 * @param fileName  Eg:- File_2.odt
	 * @throws IOException
	 */
	public static Map<String, String> compareFTPFiles(String compPath1,String compPath2,String srcFileName,String destFileName) throws IOException{

		Map<String, String>	results=new HashMap<>();


		/*if(fileName.equals("same.txt")){
			System.out.println();
		}*/
		FileReporting ftp=new FileReporting();
		Writer w=ftp.createReport("D:\\Reports\\FTPReports\\"+srcFileName+"_"+destFileName+".html");
		ftp.writeHeaderPart(w);

		FTPClient ftpCon1=Ftp.getFTPConnection();
		FTPClient ftpCon2=Ftp.getFTPConnection();
		///home/contintegration/Desktop/src/Folder1/Demo.xml

		InputStream inSR=ftpCon1.retrieveFileStream(compPath1+srcFileName);
		InputStream outSR=ftpCon2.retrieveFileStream(compPath2+destFileName);
		InputStreamReader inISR=new InputStreamReader(inSR);
		InputStreamReader ourISR=new InputStreamReader(outSR);


		BufferedReader reader1 = new BufferedReader(inISR);

		BufferedReader reader2 = new BufferedReader(ourISR);

		String line1 =  reader1.readLine();

		String line2 = reader2.readLine();

		boolean areEqual = true;


		String newStr="";

		if(line1.length()!=line2.length()){

			if(line1.length()>line2.length()){

			}

		}

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

				if(verifyRuleSet(line1, line2)){

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

		Ftp.closeFTPConnection(ftpCon1);
		Ftp.closeFTPConnection(ftpCon2);
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
	/**
	 * uploadFile --> is to upload file to remote ftp server
	 * @param localFilePath eg:- D:/uploadFiles/Testing.pdf
	 * @param remotePath eg:-/home/contintegration/Desktop/Final_Demo/
	 * @return
	 * @throws IOException
	 */
	public static boolean uploadFile(String localFilePath, String remotePath) throws IOException {
		FTPClient ftpClient=Ftp.getFTPConnection();

		File localFile = new File(localFilePath);
		boolean flag=false;
		InputStream inputStream = new FileInputStream(localFile);
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			flag= ftpClient.storeFile(remotePath+localFile.getName(), inputStream);
		}catch(Exception e){

			System.out.println(e.getMessage());

		}
		finally {
			inputStream.close();
		}
		Ftp.closeFTPConnection(ftpClient);
		return flag;
	}

	/**
	 * uploadDirectory --> is to upload from local drive to remote FTP server 
	 * @param localDir  -->D:/uploadFiles/
	 * @param remoteDir -->/home/contintegration/Desktop/Final_Demo/
	 * @throws IOException
	 */
	public static void uploadDirectory( String localDir, String remoteDir)throws IOException {

		FTPClient ftpClient=Ftp.getFTPConnection();
		ftpClient.makeDirectory(remoteDir);

		File localDirFiles = new File(localDir);
		File[] files = localDirFiles.listFiles();

		if (files != null && files.length > 0) {
			for (File file : files) {
				if (file.isFile()) {
					// upload the file
					String localFilePath = file.getAbsolutePath();
					System.out.println("About to upload the file: " + localFilePath);
					boolean uploaded = uploadFile(localFilePath, remoteDir);
					if (uploaded) {
						System.out.println("UPLOADED a file to: " + remoteDir);
					} else {
						System.out.println("COULD NOT upload the file: " + localFilePath);
					}
				} else if(file.isDirectory()) {

					String remoteFileDir=remoteDir+file.getName()+"/";
					boolean created = ftpClient.makeDirectory(remoteFileDir);
					if (created) {
						System.out.println("CREATED the directory: " + remoteFileDir);
					} else {
						System.out.println("COULD NOT create the directory: " + remoteFileDir);
					}
					String  localFileDir = file.getAbsolutePath()+"\\";
					uploadDirectory(localFileDir, remoteFileDir);
				}
			}
		}
	}
	/**
	 * "/home/contintegration/Desktop/Final_Demo/PayloadXL3.xml", "D:/uploadFiles/"
	 * @param remoteFilePath /home/contintegration/Desktop/Final_Demo/PayloadXL3.xml
	 * @param localPath  D:/uploadFiles/
	 * @throws SocketException 
	 * @throws IOException
	 */
	public static void downloadFile(String remoteFilePath, String localPath) throws SocketException, IOException {

		FTPClient ftpclient=  Ftp.getFTPConnection();

		String fileName=remoteFilePath.split("/")[remoteFilePath.split("/").length-1];

		FileOutputStream fos = new FileOutputStream(localPath+fileName);
		ftpclient.retrieveFile(remoteFilePath, fos);
		fos.flush();
		fos.close();
		System.out.println("File downloaded");
		Ftp.closeFTPConnection(ftpclient);

	}


	/**
	 * Downloading Complete Folder from FTP server to Local system
	 * @param ftpClient an instance of  org.apache.commons.net.ftp.FTPClient
	 * @param localDir is path of the Local system Directory where  Folder will be stored
	 * @param remoteDir is path of Server directory 
	 * @throws IOException if any network or IO error occurred
	 */

	public static void download_Directory( String localDir, String remoteDir)throws IOException {
		/* File localDirFiles = new File(localDir);
	    File[] files = localDirFiles.listFiles();
	    if (files != null && files.length > 0) {
	        for (File file : files) {*/
		new File(String.valueOf(localDir)).mkdir();

		FTPClient ftpClient=Ftp.getFTPConnection();
		FTPFile[] subFiles = ftpClient.listFiles(remoteDir);

		if (subFiles != null && subFiles.length > 0) {
			for (FTPFile aFile : subFiles) {
				String currentFileName = aFile.getName();


				if (aFile.isFile()) {
					// download the file
					//  String remoteFilePath = aFile.
					System.out.println("About to download the file: " + remoteDir);
					boolean downloaded = downloadsingleFile(remoteDir+aFile.getName(), localDir);
					if (downloaded) {
						System.out.println("DOWNLOAD a file to: " + localDir);
					} else {
						System.out.println("COULD NOT Download  the file: " + remoteDir);
					}
				} else if(aFile.isDirectory()) {

					// create directory on the Local
					String localFileDir=localDir+aFile.getName()+"/";
					String RemoteFileDir=remoteDir+aFile.getName()+"/";
					File directory = new File(String.valueOf(localFileDir));
					if (!directory.exists()) {
						directory.mkdir();
					}


					//String  remoteFileDir = aFile.getAbsolutePath();
					download_Directory(localFileDir, RemoteFileDir);
				}
			}
		}
	}
	/**
	 * Downloading single file from remote directory to local directory
	 * @param remoteFilePath is the path of file on server
	 * @param localPath is the path of File in local system
	 * @return true if file is downloaded successfully, otherwise false
	 * @throws IOException if any network or IO error occurred
	 */

	public static boolean downloadsingleFile(String remoteFilePath, String localPath) throws IOException {

		FTPClient ftpclient=  Ftp.getFTPConnection();

		String fileName=remoteFilePath.split("/")[remoteFilePath.split("/").length-1];

		FileOutputStream fos = new FileOutputStream(localPath+fileName);
		ftpclient.retrieveFile(remoteFilePath, fos);
		fos.flush();
		fos.close();
		Ftp.closeFTPConnection(ftpclient);
		return true;

	}


	public static int getLineCountFromFTPFile(String path) throws IOException{
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(getFTPFile(path)));
		String line=reader1.readLine();
		int noofLines=0;
		do{
			if(!line.equals("")){
				noofLines++;
			}
			line=reader1.readLine();
		}while(!(line==null));
		reader1.close();
		return noofLines;
	}

	public static boolean verifyRuleSet(String str1,String str2){
		try{

			String sheetName="Rules";

			Excel ex=new Excel();
			String[][] sheetData=ex.xlReadSheet(ExcelPath, sheetName);

			int RuleName=0,RuleExampleData=1,RuleRegExpression=2,RuleRegReplace=3;


			Map<String, String> formatStringEg=new HashMap<>();
			Map<String, String> formatExpression=new HashMap<>();
			Map<String, String> formatReplace=new HashMap<>();

			for(int i=1;i<sheetData.length;i++){

				formatStringEg.put(sheetData[i][RuleName], sheetData[i][RuleExampleData]);
				formatExpression.put(sheetData[i][RuleName],sheetData[i][RuleRegExpression]);
				formatReplace.put(sheetData[i][RuleName],sheetData[i][RuleRegReplace]);
			}

			/*
			formatStringEg.put("format1", "HEADER 2/17/2016 1:17:31 PM  ");
			formatExpression.put("format1", ".*\\s\\d{1,2}[\\/|-]{1}\\d{1,2}[\\/|-]{1}\\d{1,4}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\s[APM]{2}.*");
			formatReplace.put("format1", "\\d{1,2}[\\/|-]{1}\\d{1,2}[\\/|-]{1}\\d{1,4}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\s[APM]{2}");

			formatStringEg.put("format2", "<order>12345675</order>");
			formatExpression.put("format2", ".*<order>\\d{8}</order>*.");
			formatReplace.put("format2", "\\d{8}");
			 */




			for(Entry<String, String> format:formatExpression.entrySet()){

				if(str1.matches(format.getValue())){
					str1=str1.replaceAll(formatReplace.get(format.getKey()), "");
					//break;
				}

			}
			for(Entry<String, String> format:formatExpression.entrySet()){


				if(str2.matches(format.getValue())){
					str2=str2.replaceAll(formatReplace.get(format.getKey()), "");
					//break;
				}			
			}


			if(str1.equals(str2)){
				return true;
			}

		}catch (Exception e) {
			System.out.println("Excpeiton in Regular Expression Matching"+e.getMessage());
		}
		return false;
	}

	public static Date getDate(String formate,String date){
		try{
			SimpleDateFormat formatter = new SimpleDateFormat(formate);
			return formatter.parse(date);			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}



}
