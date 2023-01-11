package ara_ara;
import java.util.*;
public class anime {
	public static void main(String[] args)
	{	
linked_list n=new linked_list(6);
 n.node=new linked_list(7);
 n.node.node=new linked_list(8);
n.display();
	    
	}

public static void merge_search(int[] arr,int l,int h)
{
	int r=l +(h-l)/2;
	if(r>l)
	{
		merge_search(arr,l,r);
		merge_search(arr,r+1,h);
		merge(arr,l,r,h);
	}
	else
	   return;
}
	

public static void merge(int[] arr,int a,int b,int c)
{
	int left=(b-a)+1;
	int right=c-b;
	int [] arr1=new int[left];
	int[] arr2=new int[right];
	
	for(int i=0;i<left;i++)
	{
		arr1[i]=arr[a+i];
	}
	for(int i=0;i<right;i++)
	{
		arr2[i]=arr[b+i+1];
	}
	
	
	int i=0,j=0,k=0;
	while(i<left && j<right)
	{
		if(arr1[i]<=arr2[j])
		{
			arr[k]=arr1[i];
			k++;
			i++;
		}
		else
		{
			arr[k]=arr2[j];
			k++;
			j++;
		}
	}
	while(i<left)
	{
		arr[k]=arr1[i];
		i++;
		k++;
	}
	while(j<right)
	{
		arr[k]=arr2[j];
		k++;
		j++;
	}
}
public static boolean is_prime(int a)
{
	if(a==2)
	{
		return true;
	
	}
	
	
	for(int i=2;i<a;i++)
	{
		if(a%i==0)
		{
			return false;
		}
	}
	return true;
}


public static void factors(int m)
{
for(int i=2;i<=m;i++)
{
	if(is_prime(i))
	{
		int x=i;
		while(m%x==0)
		{
			System.out.print(i+" ");
			x=x*i;
		}
	}
	}
}

}

class linked_list
{
	int num;
	linked_list node;
	
	linked_list(int x)
	{
		num=x;
		node=null;
	}
	
	
	 public  void display()
	{
		linked_list n=node;
		while(n!=null)
		{
			System.out.print(n.num + " ");
			n=n.node;
		}
	}
}

