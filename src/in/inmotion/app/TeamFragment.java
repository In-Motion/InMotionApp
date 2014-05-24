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
		
		String[] names = getResources().getStringArray(R.array.team_member_names);
		String[] desc = getResources().getStringArray(R.array.team_member_desc);
		int[] id_sets = {
				R.drawable.team_001,
				R.drawable.team_004,
				R.drawable.team_005,
				R.drawable.team_006,
				R.drawable.team_002
			};
		
		ListArrayAdapter mAdapter = new ListArrayAdapter(getActivity(), names, desc, id_sets);
		setListAdapter(mAdapter);
		
		return rootView;
	}
}
