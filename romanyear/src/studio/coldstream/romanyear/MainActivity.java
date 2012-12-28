package studio.coldstream.romanyear;

//import com.google.ads.AdRequest;
//import com.google.ads.AdSize;
//import com.google.ads.AdView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.text.method.TextKeyListener;
import android.text.method.TextKeyListener.Capitalize;
//import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;


public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	//private static final String TAG = "ROMAN_MAIN";
	//protected static final String MY_AD_UNIT_ID = "a14dc0735c74b46";
	
	protected static final int UPDATE_BIN = 0x8008;
	protected static final int UPDATE_ROM = 0x8009;
	
	EditText binaryView;
	EditText romanView;
	
	int binary;
	String roman;
		
	RomanConversion conv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		conv = new RomanConversion();
		binaryView = (EditText) findViewById(R.id.editText1);
		romanView = (EditText) findViewById(R.id.editText2);
				
        Typeface font1 = Typeface.createFromAsset(getAssets(), "DejaVuSerifBold.ttf");
	    romanView.setTypeface(font1);
	    
	    /*AdView adView1 = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
        LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);
		layout.addView(adView1);
		AdRequest request1 = new AdRequest();
		request1.setTesting(false);
		adView1.loadAd(request1);*/
		
		binaryView.setKeyListener(new NumberKeyListener(){
			@Override
		    protected char[] getAcceptedChars() {
		       char[] numberChars = {'1','2','3','4','5','6','7','8','9','0'};
		       return numberChars;
		    }			
			
			@Override
			public int getInputType() {
				// TODO Auto-generated method stub
				return InputType.TYPE_CLASS_NUMBER;
			}			
		});
		
		romanView.setKeyListener(new TextKeyListener(Capitalize.CHARACTERS, true){
			
			/*protected char[] getAcceptedChars() {
		       char[] numberChars = {'X','C','V','D','I','M','L'};
		       return numberChars;
		    }		*/
			@Override
			public boolean onKeyUp(View view, Editable content,
                    int keyCode, KeyEvent event){
				//romanView.performClick();
				return true;
			}
			
			/*@Override
			public int getInputType() {
				// TODO Auto-generated method stub
				return InputType.TYPE_CLASS_TEXT;
			}			*/
		});		
		
		binaryView.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length() > 0){
					if(Integer.parseInt(s.toString()) < 1 || Integer.parseInt(s.toString()) >= 4000)
						s.delete(s.length() - 1, s.length());
					else{
						//romanView.setText(conv.binaryToRoman( Integer.parseInt(s.toString()) ), TextView.BufferType.EDITABLE);
						//romanView.setText("XXX", TextView.BufferType.EDITABLE);
						binary = Integer.parseInt(s.toString());
						roman = conv.binaryToRoman( Integer.parseInt(s.toString()) );
						Message m1 = new Message();
		    			m1.what = UPDATE_ROM;                            
			        	messageHandler.sendMessage(m1);
					}
				}
				else if(romanView.getText().length() > 0)
					romanView.setText("");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}			
		});
		
		romanView.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length() > 0){
					if(conv.romanToBinary(s.toString()) < 1){
						s.delete(s.length() - 1, s.length());
						romanView.setSelection(romanView.getText().length());
					}
					else{
						roman = s.toString();
						binary = conv.romanToBinary(s.toString());
						//binaryView.setText(Integer.toString(conv.romanToBinary(s.toString())), TextView.BufferType.EDITABLE);
						//binaryView.setText("123", TextView.BufferType.EDITABLE);
						Message m1 = new Message();
		    			m1.what = UPDATE_BIN;                            
			        	messageHandler.sendMessage(m1);
					}
				}
				else if(binaryView.getText().length() > 0)
					binaryView.setText("");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}			
		});		
		
		//Log.d(TAG, Integer.toString(conv.romanToBinary("MMMM")) );
	}
	
	private Handler messageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
			//handle messages
			
			case UPDATE_BIN:
				//Log.d(TAG, Integer.toString(binary));
                //Log.d(TAG, "After: " + roman );   
                if(binaryView.getText().toString().length() > 0){
					if(binary != Integer.valueOf(binaryView.getText().toString())){
						binaryView.setText(Integer.toString(binary));
						binaryView.performClick();
						romanView.setSelection(romanView.getText().length());
					}
                }
                else if(binary > 0){
                	binaryView.setText(Integer.toString(binary));
					binaryView.performClick();
					romanView.setSelection(romanView.getText().length());
                }
		        break;
			case UPDATE_ROM:
				
				romanView.setText(roman);
				romanView.setSelection(romanView.getText().length());
				binaryView.performClick();
				break;		
			default:
				
			}
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {	      
		    case R.id.about:
		        showAbout();
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
	    }
	}
   
   public void showAbout(){
       Intent mainIntent = new Intent(MainActivity.this, AboutActivity.class);
       MainActivity.this.startActivityForResult(mainIntent, -1);
       return;
	}
	
	
}