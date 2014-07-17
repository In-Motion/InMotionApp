package in.inmotion.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;    
    private int currentPosition = 0;
    private boolean initFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();        

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        Log.v("Postion", ""+position);
        mTitle = getResources().getStringArray(R.array.nav_drawer_items)[position];
        Fragment fragment = null ;
        switch (position) {
		    case 0:		    
		    	fragment = new HomeFragment();
		        break;
		    case 1:		   
		    	fragment = new AdsSpaceFragment();
		        break;
		    case 2:		   
		    	fragment = new ReachFragment();
		        break;
		    case 3:		   
		    	fragment = new ClientsFragment();
		    	break;
		    case 4:		   
		    	fragment = new AwardsFragment();
		        break;
		    case 5:		    	
		    	fragment = new TeamFragment();
		    	break;
		    case 6:		    	
		    	fragment = new AboutFragment();
		        break;
	        default:
	        	fragment = new HomeFragment();
		} 
        if((currentPosition == position) && initFlag) {
        	// do nothing
        } else {
        	initFlag = true;
        	currentPosition = position;
        	fragmentManager.beginTransaction()
	          .setCustomAnimations(R.animator.slide_in_up,R.animator.slide_out_up)
	          .replace(R.id.container, fragment)
	          .addToBackStack("fragment_back")
	          .commit();
        }        
    }
    
    @Override
    public void onBackPressed() {
    	if(currentPosition == 0){
    		finish();
    	} else {
    		onNavigationDrawerItemSelected(0);
    	}
            return;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onPause() {
    	Log.v("PAUSE", "paused");
    	super.onPause();
    };
    @Override
    protected void onResume() {
    	Log.v("RESUME", "paused");
    	super.onResume();
    };    
    @Override
    protected void onStop() {
    	Log.v("STOP", "paused");
    	super.onStop();
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
  

}
