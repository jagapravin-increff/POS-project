package ara_ara;
import java.util.Scanner;
import java.util.Arrays;  
import java.util.*;
public class recur {
   public static void main( String args[])
   {    Scanner sc=new Scanner(System.in);
   PriorityQueue<Integer> pQueue = new PriorityQueue<Integer>();
        int n=sc.nextInt();
        int m=sc.nextInt();
          rr[] ary=new rr[n+1];	  
          int  ans=0 ,i;
          for(i=1;i<=n;i++)
          {
        	  ary[i].start=sc.nextInt();
        	  ary[i].time=sc.nextInt();
          ary[i].time+=ary[i].start;
          }
          
          Arrays.sort(ary);
          pQueue.add(ary[i].time+m);
          for(i=2;i<=n;i++)
          {
           while(!pQueue.isEmpty()&&ary[i].start>pQueue.peek())
        	   pQueue.remove();
        	      if(ary[i].start>=pQueue.peek()-m&&ary[i].start<=pQueue.peek())
        	       {ans++;pQueue.remove();}
        	    pQueue.add(ary[i].time+m );
        	       }
        	       System.out.println(ans);
        	        }
        	   }
         
 
class rr
{   int start;
    int time;

 
}

class cmp
{

 public boolean comp(rr a,rr b)
{
	return a.start>b.start;
}
}
