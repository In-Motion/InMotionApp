package in.inmotion.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Thread timer = new Thread(){
			@Override
			public void run() {
				try{
					sleep(1000);
				} catch (InterruptedException e){
					e.printStackTrace();
				} finally {
					Intent openApp = new Intent("in.inmotion.app.HOME");
					startActivity(openApp);
				}
			}			
		};
		
		timer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}
