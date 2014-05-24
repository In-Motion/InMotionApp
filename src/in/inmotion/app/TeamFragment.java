package in.inmotion.app;

import in.inmotion.app.resources.ListArrayAdapter;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TeamFragment extends ListFragment {
	public TeamFragment() {
		//
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.fragment_team, container, false);
		
		String[] names = new String[] { "Aaloo", "papad", "chaat",
		        "samosa", "laal", "tamatar", "pich", "pich",
		        "pich"};
		String[] desc = new String[] { "Kachaloo beta kahan gye the", "lizzat papad", "ashok chaat masala",
		        "chai samosa garam garam", "laal baal shanks", "tamatar khane se shakti aati h", "pich perfect", "pich-kari",
		        "pich-er plant desu"};
		
		ListArrayAdapter mAdapter = new ListArrayAdapter(getActivity(), names, desc);
		setListAdapter(mAdapter);
		
		return rootView;
	}
}
