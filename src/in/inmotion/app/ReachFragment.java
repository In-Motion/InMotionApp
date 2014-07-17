package in.inmotion.app;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ReachFragment extends Fragment {
	private String[] categories;
	private String[][] categoryItems;
	private ExpandableListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    categories = getResources().getStringArray(R.array.reach_state);

	    String[] category1 = getResources().getStringArray(R.array.reach_1);
	    String[] category2 = getResources().getStringArray(R.array.reach_2);
	    String[] category3 = getResources().getStringArray(R.array.reach_3);
	    categoryItems = new String[][]{category1, category2, category3};

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.my_expandable, container, false);
		
	    listView = (ExpandableListView) rootView.findViewById(R.id.expList);
	    
	    View header = inflater.inflate(R.layout.book_button, null);
        header.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
//				Toast.makeText(getActivity(), "Booking!!", Toast.LENGTH_LONG).show();
				Intent callIntent = new Intent(Intent.ACTION_CALL);          
	            callIntent.setData(Uri.parse("tel:+917398753559"));          
	            startActivity(callIntent);  
			}
		});
        listView.addHeaderView(header);
	    
	    listView.setAdapter(new GroupsListAdapter(categories, categoryItems));
	    listView.setGroupIndicator(null);
	    return rootView;
	}

	
	
	public class GroupsListAdapter extends BaseExpandableListAdapter {
	    private final LayoutInflater inflater;
	    private String[] groups;
	    private String[][] children;

	    public GroupsListAdapter(String[] groups, String[][] children) {
	        this.groups = groups;
	        this.children = children;
	        inflater = LayoutInflater.from(getActivity());
	    }

	    @Override
	    public int getGroupCount() {
	        return groups.length;
	    }

	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return children[groupPosition].length;
	    }

	    @Override
	    public Object getGroup(int groupPosition) {
	        return groups[groupPosition];
	    }

	    @Override
	    public Object getChild(int groupPosition, int childPosition) {
	        return children[groupPosition][childPosition];
	    }

	    @Override
	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }

	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }

	    @Override
	    public boolean hasStableIds() {
	        return true;
	    }

	    @Override
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	        ViewHolder holder;
	        if (convertView == null) {
	            convertView = inflater.inflate(R.layout.expandable_row_parent, parent, false);
	            holder = new ViewHolder();

	            holder.text = (TextView) convertView.findViewById(R.id.parent_header);
	            holder.icon = (ImageView) convertView.findViewById(R.id.expand_collapse);
	            convertView.setTag(holder);
	        } else {
	            holder = (ViewHolder) convertView.getTag();
	        }

	        holder.text.setText(getGroup(groupPosition).toString());
	        if (isExpanded) {
	            holder.icon.setBackgroundResource(R.drawable.arrow_up);
	        } else {
	            holder.icon.setBackgroundResource(R.drawable.arrow_down);
	        }

	        return convertView;
	    }

	    @Override
	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	        final ChildViewHolder holder;
	        if (convertView == null) {
	            convertView = inflater.inflate(R.layout.expandable_row_child, parent, false);
	            holder = new ChildViewHolder();

	            holder.text = (TextView) convertView.findViewById(R.id.child_header);
	            holder.indicator = (TextView) convertView.findViewById(R.id.data_indicator);
	            convertView.setTag(holder);
	        } else {
	            holder = (ChildViewHolder) convertView.getTag();
	        }
	        String rawString = getChild(groupPosition, childPosition).toString();
	        String city = rawString.substring(0, rawString.indexOf(","));
	        String count = rawString.substring(rawString.indexOf(",")+1);
	        holder.text.setText(city);
	        holder.indicator.setText(count);
	        
	        
	        return convertView;
	    }

	    @Override
	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return true;
	    }

	    private class ViewHolder {
	        TextView text;
	        ImageView icon;
	    }
	    
	    private class ChildViewHolder {
	    	TextView text;
	    	TextView indicator;
	    }

	}
}
