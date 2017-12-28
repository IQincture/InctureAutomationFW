
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FolderList {

	public static ArrayList<String> fileList() throws IOException {

		int num = 0;
		num = Countfiles(new File(System.getProperty("user.dir")+"/src/main/java/testdata"));
		//System.out.println("Total Num of files is "+num);
		ArrayList<String> filename=new ArrayList<String>();

		//List all files and directory
		File dir = new File(System.getProperty("user.dir")+"/src/main/java/testdata");
		File[] list = dir.listFiles();
		for(File file : list){	
			filename.add(file.getName());

		}

		
		//count all files
return filename;
		
	}
	public static int Countfiles(File file){
		int num = 0;
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(int i=0;files !=null & i<files.length;i++){
				num = num  + Countfiles(files[i]);
			}}
		else{
			num = num+1;
		}
		return num;
	}
}
