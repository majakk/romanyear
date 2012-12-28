package studio.coldstream.romanyear;

import android.util.Log;

public class RomanConversion{
	private static final String TAG = "ROMAN";
    
    private static final String[] RCODE = {"M", "CM", "D", "CD", "C", "XC", "L",
                                           "XL", "X", "IX", "V", "IV", "I"};
    private static final int[]    OVAL  = {4,  1,  1,  1,  3,  1,  1,
        								   1,  3,  1,  1,  1,  3};
    private static final int[]    BVAL  = {1000, 900, 500, 400,  100,   90,  50,
                                           40,   10,    9,   5,   4,    1};
    
    
    public String binaryToRoman(int binary) {
        if (binary <= 0 || binary >= 4000) {
            throw new NumberFormatException("Not a valid number");
        }
        String roman = "";         // Roman notation will be accumualated here.
        
        // Loop from biggest value to smallest, successively subtracting,
        // from the binary value while adding to the roman representation.
        for (int i = 0; i < RCODE.length; i++) {
            while (binary >= BVAL[i]) {
                binary -= BVAL[i];
                roman  += RCODE[i];
            }
        }
        return roman;
    }  
    
    public int romanToBinary(String roman) {
        int binary = 0;         
                
        for (int i = 0; i < RCODE.length; i++) {
        	for (int j = 0; j < OVAL[i]; j++) {
	            //Those darn special cases
        		//if(OVAL[i] > 1 && j == 0 && i < RCODE.length - 1){
        		//check one ahead
        		if(i < RCODE.length - 1){
	        		if(RCODE[i+1].length() > 1 && i < RCODE.length - 1){
		            	if(roman.toUpperCase().indexOf(RCODE[i+1], 0) >= 0 && roman.toUpperCase().indexOf(RCODE[i+1], 0) < roman.length()){		   		
		            		Log.d(TAG, "Before1: " + roman );
			                binary += BVAL[i+1];
			                roman = roman.replaceFirst(RCODE[i+1], "");
			                Log.d(TAG, Integer.toString(i));
			                Log.d(TAG, Integer.toString(binary));
			                Log.d(TAG, "After1: " + roman );
			                
		            	}
	        		}
        		}
        		if(roman.toUpperCase().indexOf(RCODE[i], 0) >= 0 && roman.toUpperCase().indexOf(RCODE[i], 0) < roman.length()){		   		
            		Log.d(TAG, "Before2: " + roman );
	                binary += BVAL[i];
	                roman = roman.replaceFirst(RCODE[i], "");
	                Log.d(TAG, Integer.toString(binary));
	                Log.d(TAG, "After2: " + roman );            			
            	}
	            
        	}
        }
        if(roman.length() > 0)
        	binary = -1;
        
        return binary;
    }     
    
}

