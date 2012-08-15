package buet.threebyzero.autoSecuritySystem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Capture extends Activity {
	private Bitmap currentImage;
	private Bitmap primaryImage;
	private String imageFileName;
	private File albumF;
	private Camera camera;
	private SurfaceView preview;
	private ToggleButton controlButton;
	private int imageNumber = 0;
	private Timer timer;
	private Comparator imageComparator;
	
	private static final int INTERVAL = 10000;
	private static final int INITIAL_DELAY = 2000;
	private static final String CAMERA = "camera";
	
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo);
		preview = (SurfaceView) findViewById(R.id.preview);
		controlButton = (ToggleButton) findViewById(R.id.controlButton);
		controlButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					if(camera == null)
						initializeCamera();
					startCamera();
				}
				else
					stopCamera();
			}
		});
		imageComparator = new Comparator();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		controlButton.setChecked(false);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(camera != null) {
			stopCamera();
			camera.release();
		}
	}

	private void initializeCamera() {
		camera = Camera.open();
		try {
			camera.setPreviewDisplay(preview.getHolder());
		} catch (IOException exception) {
			Log.e(CAMERA, "Cannot set display", exception);
		}
		Log.d(CAMERA, "Camera initialized");
	}
	
	public void startCamera() {
		camera.startPreview();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				camera.setOneShotPreviewCallback(new Camera.PreviewCallback() {
					
					public void onPreviewFrame(byte[] data, Camera camera) {
						camera.takePicture(null, null, new PictureProcessor());
					}
				});
			}
		}, INITIAL_DELAY, INTERVAL);
	}
	
	public void stopCamera() {
		timer.cancel();
		camera.stopPreview();
	}

	public File fileCreate() {

		String timeForPic = new SimpleDateFormat("yyyyMMdd_HHmmss")
			.format(new Date());
		imageFileName = "IMG_" + timeForPic;

		File albumF;
		boolean success = (albumF = new File("SaveImage")).mkdirs();

		File imageF = null;
		try {
			imageF = File.createTempFile(imageFileName, ".JPG", albumF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d(CAMERA, "File created");
		return imageF;
	}

	public boolean saveImage(){
		File f;
		f = fileCreate();

		OutputStream out = null;
		try {
			out = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		byte buf[] = new byte[1024];

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		currentImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();

		try {
			out.write(byteArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		Log.d(CAMERA, "Image Saved");
		return true;
	}
	
	/** Returns the image captured most recently */
	public Bitmap getImage() {
		return currentImage;
	}
	
	private class PictureProcessor implements PictureCallback{							
		public void onPictureTaken(byte[] data, Camera camera) {
			currentImage = BitmapFactory.decodeByteArray(data, 0, data.length);
			if(primaryImage == null)
				primaryImage = Bitmap.createBitmap(currentImage);
			Log.d(CAMERA, "Image captured: " + imageNumber);
			//Toast.makeText(getApplicationContext(), "Image: " + imageNumber, Toast.LENGTH_SHORT).show();
			imageNumber++;
			camera.startPreview();
			
			imageComparator.setImages(primaryImage, currentImage);
			if(!imageComparator.isSame())
				Toast.makeText(getApplicationContext(), "Not Same", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getApplicationContext(), "Not Same", Toast.LENGTH_SHORT).show();
		}
	}
}