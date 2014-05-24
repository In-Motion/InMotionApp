package in.inmotion.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashScreen extends Activity {

	ImageView imageView;
	AnimationDrawable splashAnimation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		imageView = (ImageView) findViewById(R.id.splashView);
		imageView.setBackgroundResource(R.drawable.splash_anim);
		
		splashAnimation = (AnimationDrawable) imageView.getBackground();
		splashAnimation.start();		
		
		Thread timer = new Thread(){
			@Override
			public void run() {
				try{
					sleep(2000);
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
