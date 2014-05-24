package in.inmotion.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class AdsSpaceFragment extends Fragment{
	
	private GridView gridView;
	private Animator mCurrentAnimator;
	private int mShortAnimDuration;
	private int thumbs[] = {
			R.drawable.size01,
			R.drawable.size02,
			R.drawable.size03,
			R.drawable.size04
//			R.drawable.splash000,
//			R.drawable.splash001,
//			R.drawable.splash002,
//			R.drawable.splash003,
//			R.drawable.splash004,
//			R.drawable.splash005
	};
	
	public AdsSpaceFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub	
		
		final View rootView = inflater.inflate(R.layout.fragment_ads_space, container, false);
		
		gridView = (GridView) rootView.findViewById(R.id.gridView);
		gridView.setAdapter(new ImageAdapter(getActivity()));
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long id) {
				zoomImageFromThumb(rootView, v, thumbs[pos]);
			}
			
		});
		
		mShortAnimDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
		
		return rootView;
	}
	
	class ImageAdapter extends BaseAdapter {
		
		LayoutInflater layoutInflater;
		
		public ImageAdapter(Activity activity) {
			layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return thumbs.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View listItem = convertView;
			int pos = position;
			if(listItem == null){
				listItem = layoutInflater.inflate(R.layout.grid_item, null);
			}
			ImageView imageView = (ImageView) listItem.findViewById(R.id.thumb);
			imageView.setBackgroundResource(thumbs[pos]);
			return listItem;
		}		
	}
	
	private void zoomImageFromThumb(View rootView, final View thumbView, int imgResId){
		if(mCurrentAnimator!=null){
			mCurrentAnimator.cancel();
		}
		
		final ImageView zoomedImageView = (ImageView)rootView.findViewById(R.id.expanded_image);
		zoomedImageView.setImageResource(imgResId);
		
		final Rect startBounds = new Rect();
		final Rect finalBounds = new Rect();
		final Point globalOffset = new Point();
		
		thumbView.getGlobalVisibleRect(startBounds);
		rootView.findViewById(R.id.container).getGlobalVisibleRect(finalBounds,globalOffset);
		startBounds.offset(-globalOffset.x, -globalOffset.y);
		finalBounds.offset(-globalOffset.x, -globalOffset.y);
		
		float startScale = 0;
		
		if((float)finalBounds.width()/finalBounds.height() > (float)startBounds.width()/startBounds.height()){
			//horizontal scale
			startScale = (float)startBounds.height()/startBounds.height();
			
			float startWidth = startScale*finalBounds.width();
			float deltaWidth = (startWidth - startBounds.width())/2;
			
			startBounds.left -= deltaWidth;
			startBounds.right += deltaWidth;
			
			// vertical scale
			startScale = (float)startBounds.width()/startBounds.width();
			
			float startHeight = startScale*finalBounds.height();
			float deltaHeight = (startHeight - startBounds.height())/2;
			
			startBounds.top -= deltaHeight;
			startBounds.bottom += deltaHeight;
		}
		
		thumbView.setAlpha(0f);
		zoomedImageView.setVisibility(View.VISIBLE);
		
		zoomedImageView.setPivotX(0f);
		zoomedImageView.setPivotY(0f);
		
		final AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(ObjectAnimator.ofFloat(zoomedImageView, View.X, startBounds.left, finalBounds.left))
			.with(ObjectAnimator.ofFloat(zoomedImageView, View.Y, startBounds.top, finalBounds.top))
			.with(ObjectAnimator.ofFloat(zoomedImageView, View.SCALE_X, startScale, 1f))
			.with(ObjectAnimator.ofFloat(zoomedImageView, View.SCALE_Y, startScale, 1f));
		
		
		animatorSet.setDuration(mShortAnimDuration);
		animatorSet.setInterpolator(new DecelerateInterpolator());
		animatorSet.addListener(new AnimatorListenerAdapter() {
			//override
			public void onAnimationEnd(Animator animation){
				thumbView.setAlpha(1f);
//				zoomedImageView.setVisibility(View.GONE);
				mCurrentAnimator = null;
			}
			//override			
			public void onAnimationCancel(Animator animation) {
				thumbView.setAlpha(1f);
				zoomedImageView.setVisibility(View.GONE);
				mCurrentAnimator = null;
				}		
			//override
			public void onAnimationStart(Animator animation){
				Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);		            
	            zoomedImageView.startAnimation(in);
			}
		});
		animatorSet.start();
		zoomedImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				animatorSet.end();
//				thumbView.setAlpha(1f);		
//				if(zoomedImageView.getVisibility() == View.VISIBLE) {
				if(zoomedImageView.getVisibility() != View.VISIBLE){
					zoomedImageView.setVisibility(View.VISIBLE); 
				}
		            Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);		            
		            zoomedImageView.startAnimation(out);
		            zoomedImageView.setVisibility(View.GONE);			            	                      
//		        }				
//				mCurrentAnimator = null;
			}
		});
		mCurrentAnimator = animatorSet;
	}
}
