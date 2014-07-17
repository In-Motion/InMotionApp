package in.inmotion.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class AdsSpaceFragment extends Fragment{
	private View rootView;
	
	private Button clickButton;
	private Button uploadButton;
	private Button saveButton;
	private ImageView imgView;
	private ImageView background;
	
	private final int CAMERA_REQ = 1;
	private final int CROP_REQ = 2;
	private final int GALLERY_REQ = 3;
	
	private String imagePath;
	private File carmeraFile;
	private Uri imageCarmeraUri;
	
	private Uri photoURI;
	
	public AdsSpaceFragment() {
		// 
	}	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		rootView = inflater.inflate(R.layout.fragment_ads_space, container, false);
		
		clickButton = (Button) rootView.findViewById(R.id.click_button);
		uploadButton = (Button) rootView.findViewById(R.id.upload_button);
		saveButton =  (Button) rootView.findViewById(R.id.save_button);		
		imgView = (ImageView) rootView.findViewById(R.id.clicked);
		background = (ImageView) rootView.findViewById(R.id.bg_auto_1);
		
		clickButton.setOnClickListener(new View.OnClickListener() {


			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				File fileDir = new File(Environment.getExternalStorageDirectory()
			            + "/InMotion");
			    if (!fileDir.exists()) {
			        fileDir.mkdirs();
			    }
			    
			    imagePath = Environment.getExternalStorageDirectory() + "/InMotion/"
			            + System.currentTimeMillis() + ".jpg";
			    carmeraFile = new File(imagePath);
			    imageCarmeraUri = Uri.fromFile(carmeraFile);
			    
			    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
			            imageCarmeraUri);
				
				intent.putExtra("crop", "true");
				intent.putExtra("outputX", 200); 
				intent.putExtra("outputY", 200); 
				intent.putExtra("aspectX", 1.5); 
				intent.putExtra("aspectY", 1); 

				try {
					intent.putExtra("return-data", true);
					startActivityForResult(intent, CAMERA_REQ);
				} catch (ActivityNotFoundException e) {
					// Do nothing
				}
			}
		});
		uploadButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, GALLERY_REQ);
			}
		});
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveImage();
			}
		});		
		return rootView;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		Log.v("CODES :: ", resultCode+","+requestCode);
		
		if (resultCode != Activity.RESULT_OK)
			return;

		switch (requestCode) {
			case CAMERA_REQ:
				if(data!=null){
					photoURI = data.getData();
				} else {
					photoURI = imageCarmeraUri;
					Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
				}
				
				doCrop();
				break;
			case CROP_REQ:
				Bitmap bmp = (Bitmap) data.getExtras().get("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
	
				bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray();
	
				Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
						byteArray.length);
	
				imgView.setImageBitmap(bitmap);
				break;
			case GALLERY_REQ:
	
				photoURI = data.getData();
				doCrop();
				break;
			default:
				break;
		}			
	}
	
	public void doCrop(){
		
		try {			
			final int h = imgView.getMeasuredHeight();
			final int w = imgView.getMeasuredWidth();
			  //call the standard crop action intent (the user device may not support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP"); 
			    //indicate image type and Uri
			cropIntent.setDataAndType(photoURI, "image/*");
			    //set crop properties
			cropIntent.putExtra("crop", "true");
			    //indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1.5);
			cropIntent.putExtra("aspectY", 1);
			    //indicate output X and Y
			cropIntent.putExtra("outputX", w);
			cropIntent.putExtra("outputY", h);
			    //retrieve data on return
			cropIntent.putExtra("return-data", true);
			    //start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, CROP_REQ);
		}
		catch(ActivityNotFoundException anfe){
		    String errorMessage = "Whoops - your device doesn't support the crop action!";
		    Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
		    toast.show();
		}
	}
	
	private boolean saveImage(){
			
		background.buildDrawingCache();
		Bitmap bottomLayer = background.getDrawingCache(); //BitmapFactory.decodeResource(getResources(), R.drawable.size01);
		imgView.buildDrawingCache();	
		Bitmap topLayer =  imgView.getDrawingCache(); //BitmapFactory.decodeResource(getResources(), R.drawable.plus_icon);
		
		final int h = background.getMeasuredHeight();
		final int w = background.getMeasuredWidth();
		final int h1 = imgView.getMeasuredHeight();
		final int w1 = imgView.getMeasuredWidth();
		int left = (h-h1)/2;
		int top = (w-w1)/2;
		Canvas comboImage = new Canvas(bottomLayer);
		comboImage.drawBitmap(topLayer, left, top, null);
		
		OutputStream outputStream = null;
		
		try {
			outputStream = new FileOutputStream(imagePath.substring(0,imagePath.length()-4)+"_IM"+imagePath.substring(imagePath.length()-4, imagePath.length()));
			bottomLayer.compress(CompressFormat.PNG, 100, outputStream);
			Log.i("TAG", "Saving");
			galleryAddPic();
			outputStream.flush();
			outputStream.close();
		} catch(Exception e){
			e.printStackTrace();
			Log.i("TAG", "not saved");
			return false;
		}
		Toast.makeText(getActivity(), "The Ad has been saved.", Toast.LENGTH_LONG).show();
		Log.i("TAG", "Saved");
		return true;
	}
	private void galleryAddPic() {
//	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//	    File f = new File(mCurrentPhotoPath);
//	    Uri contentUri = Uri.fromFile(f);
//	    mediaScanIntent.setData(contentUri);
//	    getActivity().sendBroadcast(mediaScanIntent);
		Log.i("TAG", "send broadcast");
		getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, imageCarmeraUri));
	}
}
