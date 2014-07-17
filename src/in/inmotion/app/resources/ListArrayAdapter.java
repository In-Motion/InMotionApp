package in.inmotion.app.resources;

import in.inmotion.app.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListArrayAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final String[] names;
	private final String[] desc;
	private final int[] resIds;
	
	private final int mode;
	
	public static final int MODE_DUAL = 0;
	public static final int MODE_SINGLE_LEFT = 1;
	public static final int MODE_SINGLE_RIGHT = 2;
	
	private static final int TYPE_LEFT = 0;
    private static final int TYPE_RIGHT = 1;
	
	public ListArrayAdapter(Context context,
			String[] names, String[] desc, int[] resId, int mode) {
		super(context, R.layout.list_row_even, names);
		
		this.context = context;
		this.names = names;
		this.desc = desc;
		this.resIds = resId;
		this.mode = mode;
	}
		
    private int getType(int position) {
        return (position%2 == 0)? TYPE_LEFT : TYPE_RIGHT;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.list_row_even, parent, false);;
		
		if(mode == MODE_DUAL){
			switch(getType(position)){
				case TYPE_LEFT:
					rowView = inflater.inflate(R.layout.list_row_even, parent, false);
					break;
				case TYPE_RIGHT:
					rowView = inflater.inflate(R.layout.list_row_odd, parent, false);
					break;
			}
		} else if(mode == MODE_SINGLE_LEFT){
			rowView = inflater.inflate(R.layout.list_row_even, parent, false);
		} else if(mode == MODE_SINGLE_RIGHT){ 
			rowView = inflater.inflate(R.layout.list_row_odd, parent, false);
		}
		
		TextView nameTextView = (TextView)rowView.findViewById(R.id.team_label);
		TextView descTextView = (TextView)rowView.findViewById(R.id.team_desc);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.team_icon);
					
		nameTextView.setText(names[position]);
		descTextView.setText(desc[position]);		
		imageView.setImageResource(resIds[position]);
		
		return rowView;
	}
}
