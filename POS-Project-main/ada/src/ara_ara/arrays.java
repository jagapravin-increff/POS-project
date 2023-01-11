package ara_ara;

import java.util.Scanner;

public class arrays {
	public static void main(String[] args)
	{	
		
	Scanner sc=	new Scanner(System.in);
     String a=sc.next();
     
     int m =binary_int(a);
     System.out.print(m);
}

	
	
	
	public static int binary_int(String a)
	{   int k=1;
	    int m=0;
		for(int i=a.length()-1;i>=0;i--)
		{
			m+=(a.charAt(i)-'0')*k;
			k+=2;
		}
	return m;
	}
	
}