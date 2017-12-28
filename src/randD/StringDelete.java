package randD;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StringDelete {

	public static void main(String[] args) {


		method1();


	}
	public static  void method1(){
		
		
		for(int i=0;i<100;i++)
		{
			System.out.println(Instant.now ().toString ());
			try{Thread.sleep(1);}catch(Exception e){}
		}
		System.out.println("------------");
		for(int i=0;i<100;i++)
		{
			System.out.println(LocalDateTime.now());
			try{Thread.sleep(1);}catch(Exception e){}
		}
		
		System.out.println(LocalDateTime.now());
		System.out.println(LocalDate.now().toString());
		
		
		String filename="demo.1.1.txt";
		
		System.out.println(filename.substring(0, filename.lastIndexOf(".")));
		
		System.out.println(filename.substring(filename.lastIndexOf("."), filename.length()));
		
		System.out.println(filename.replaceAll(filename.substring(filename.lastIndexOf("."), filename.length()), ""));
		
	}
}
