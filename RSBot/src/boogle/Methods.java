package boogle;

public class Methods {
	
	//static class
	private Methods(){}
	
	public static int rand(int lo, int hi){
		return (int) (Math.random()*(hi-lo))+lo;
	}
	
	public static int pick(double... percentages){
		double cur=0.0;
		double target=Math.random();
		for(int i=0; i<percentages.length; ++i){
			cur+=percentages[i];
			if(target <= cur)
				return i;
		}
		
		return percentages.length-1;
	}

}
