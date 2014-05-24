package in.inmotion.app;

import in.inmotion.app.resources.ListArrayAdapter;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AwardsFragment extends ListFragment {
	public AwardsFragment() {
		//
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.fragment_team, container, false);
		
		String[] names = new String[] { "Awwwards", "FIFA", "Wimbledon",
		        "Noble Prize", "Code Jam", "Brit Awards", "Grammy", "Booker",
		        "Oscars"};
		String[] desc = new String[] { "Awwwwwwwwwww", "Yeah FIFA!", "Mada mada dane!",
		        "Well, thank you!", "Google code jam.", "Cheeeeeeze", "Do re mi fa so la te", "Ahem ahem",
		        "dun dun dun dun dun dun dun"};
		
		ListArrayAdapter mAdapter = new ListArrayAdapter(getActivity(), names, desc);
		setListAdapter(mAdapter);
		
		return rootView;
	}
}
