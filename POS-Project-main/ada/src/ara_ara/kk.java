package ara_ara;

public class kk {
public static  void main(String [] args)
{
	fo a= new fo();
	int j=a.func(7);
	System.out.print(j);
}
}


class fo
{
	int count=0;
	
	 public int func(int n)
	 {
		 while(n>0)
		 {
			 n&=(n-1);
			 count++;
			 
		 }
		 return count;
	 }
}