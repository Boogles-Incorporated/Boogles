package boogle;

public class Methods {
	
	//static class
	private Methods(){}
	
	/**
	 * Chooses an integer in the interval at random with equal probability
	 * @param lo Lower bound (inclusive)
	 * @param hi Upper bound (exclusive)
	 * @return A random integer in the interval [lo, hi)
	 */
	public static int rand(int lo, int hi){
		return (int) (Math.random()*(hi-lo))+lo;
	}
	
	/**
	 * Chooses an ordinal integer at random with the specified probabilities
	 * @param percentages List of doubles which represent normalized probabilities
	 * @return An integer from 0 to percentages.length-1, respectively
	 */
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
	
	/**
	 * Chooses a boolean at random with the specified probability
	 * @param trueProb Probability [0,1) of returning True
	 * @return True if random sample is less than trueProb, else False
	 */
	public static boolean flipCoin(double trueProb){
		return Math.random()<trueProb;
	}

}
