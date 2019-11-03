package CallGraphCreation;



//Test Case 1:

public class Sample_test
{
	
int foofoo(int x)
{
	return 0;
}
int func (int x)
{
  int t = x;
  System.out.println("hi");
  t=-x;
  int s=-t+x;
  t = 100;
  if(t>6)
  {
	  t=0;
  }
  else
  {
	  t=-100;
  }
  x=x+1;
  return t;
}

int foo (int p)
{
  int a = p;
  
  a=func(2);
  int l = a  + func(-1);//
  int k=foofoo(4);
  int g = 1+ func(l);
  return g;
}

public static void main (String[] args)
{
    int play = Integer.parseInt(args[0]);
    Sample_test s = new Sample_test();
    int x=0;
    int y = 0;
    if(play > 0)
      x = s.foo(play);
    else
    {
      y = s.foo(4);
      System.out.println(y);
    }
    System.out.println(x);
}
}

