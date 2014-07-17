package in.inmotion.app;

import in.inmotion.app.resources.ListArrayAdapter;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClientsFragment extends ListFragment {
	public ClientsFragment() {
		//
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.fragment_team, container, false);
		
		String[] names = getResources().getStringArray(R.array.client_names);
		String[] desc = getResources().getStringArray(R.array.client_desc);
		int[] id_sets = {
							R.drawable.client_001,
							R.drawable.client_002,
							R.drawable.client_003,
							R.drawable.client_004,
							R.drawable.client_005,
							R.drawable.client_006,
							R.drawable.client_007,
							R.drawable.client_008,
							R.drawable.client_009
						};
		
		ListArrayAdapter mAdapter = new ListArrayAdapter(getActivity(), names, desc, id_sets, ListArrayAdapter.MODE_SINGLE_LEFT);
		setListAdapter(mAdapter);
		
		return rootView;
	}
}
