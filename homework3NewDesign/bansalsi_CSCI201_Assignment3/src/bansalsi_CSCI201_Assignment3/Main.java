package bansalsi_CSCI201_Assignment3;


public class Main {
	
	public static void main(String [] args)
	{
		servertester st = new servertester();
		st.run();
		
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Client c1 = new Client();
		
		new clienttester();
		//System.exit(1);
	}
}

class servertester extends Thread
{
	servertester()
	{
		s = new Server();
	}
	public void run()
	{
		s.startServer(6000);
	}
	
	Server s;
}

class clienttester extends Thread
{
	clienttester()
	{
		c1 = new Client();
		c2 = new Client();
		
	}
	
	
	Client c1;
	Client c2;
}