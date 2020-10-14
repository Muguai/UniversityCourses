

import java.util.Random;

public class RandomHeight{

  
    private int maxLevel;
    private int result;
    private int heads = 0;
    private int tails = 1;
    Random randomNum = new Random();

    public RandomHeight(int maxLevel){
        this.maxLevel = maxLevel;
    }
    
    public int coinFlip(){
        result = randomNum.nextInt(2);
        if(result == 0){
            return 0;
        }else{
            return 1;
        }
      }

    

    public int Levels(){
        int row = 0;

        while(coinFlip() == heads) {
        	if (row >= maxLevel) {
        		row = maxLevel;
        		row--;
                break;
            }
            row++;
        }
        if (row >= maxLevel) {
    		row = maxLevel;
    		row--;
        }
        return row;
    }
}