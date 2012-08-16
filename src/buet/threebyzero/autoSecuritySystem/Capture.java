package buet.threebyzero.autoSecuritySystem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Capture extends Activity {
	private Bitmap currentImage;
	private Bitmap primaryImage;
	private String imageFileName;
	private Camera camera;
	private SurfaceView preview;
	private ToggleButton controlButton;
	private int imageNumber = 0;
	private Timer timer;
	
	/** Interval time between capturing two consecutive pictures */
	private static final int INTERVAL = 6000;
	/** Amount of time before taking the first picture after start button is pressed */
	private static final int INITIAL_DELAY = 1000;
	private static final String CAMERA = "camera";
	
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo);
		preview = (SurfaceView) findViewById(R.id.preview);
		preview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
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

	/** Prepare the camera for the first time */
	private void initializeCamera() {
		camera = Camera.open();
		try {
			camera.setPreviewDisplay(preview.getHolder());
		} catch (IOException exception) {
			Log.e(CAMERA, "Cannot set display", exception);
		}
		Log.d(CAMERA, "Camera initialized");
	}
	
	/** Start showing preview and capturing pictures */
	public void startCamera() {
		camera.startPreview();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				camera.setOneShotPreviewCallback(new Camera.PreviewCallback() {
					
					public void onPreviewFrame(byte[] data, Camera camera) {
						camera.takePicture(null, null, new PictureProcessor());
						Log.d(CAMERA, "Picture taken");
					}
				});
			}
		}, INITIAL_DELAY, INTERVAL);
	}
	
	/** Stop capturing pictures and showing the preview */
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
	
	/** Processes the picture when image data is found after capturing the picture */
	private class PictureProcessor implements PictureCallback{							
		public void onPictureTaken(final byte[] data, Camera camera) {
			Log.d(CAMERA, "Processed picture found");
			camera.startPreview();
			
			Thread imageProcessThread = new Thread(new Runnable() {
				
				public void run() {
					currentImage = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(data, 0, data.length)
							, 120, 160, true);
					if(primaryImage == null)
						primaryImage = Bitmap.createBitmap(currentImage);
					Log.d(CAMERA, "Image captured: " + imageNumber);
					imageNumber++;
					
					Comparator imageComparator = new Comparator();
					imageComparator.setImages(primaryImage, currentImage);
					if(imageComparator.isSame())
						Log.d(CAMERA, "Same: " + imageComparator.difference);
					else
						Log.d(CAMERA, "Not Same: " + imageComparator.difference);
					currentImage.recycle();
				}
			});
			imageProcessThread.start();
		}
	}
}