package in.inmotion.app;

import in.inmotion.app.resources.TYPEFACE;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment{
	
	private TextView orgNameView;
	public HomeFragment() {
		// TODO Auto-generated constructor stub
	}	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		
		orgNameView = (TextView) rootView.findViewById(R.id.in_motion_name);
		orgNameView.setTypeface(TYPEFACE.LexiaBold(getActivity()));
		
		return rootView;
	}
	
	
}
