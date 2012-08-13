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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Camera extends Activity implements View.OnClickListener {
	private Button btnPic;
	private ImageView iv;
	private Bitmap bmp;
	private Intent i;
	private String imageFileName;
	private File albumF;
	final static int cameraData = 0;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		btnPic = (Button) findViewById(R.id.btnTkPic);
		iv = (ImageView) findViewById(R.id.imageView1);
		InputStream is = getResources().openRawResource(R.drawable.ic_launcher);
		bmp = BitmapFactory.decodeStream(is);
	}

	public void openCamera() {
		try {
			android.hardware.Camera.open();
			System.out.print("hello");
		} catch (Exception aec) {
			aec.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnTkPic:
			openCamera();
			i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, cameraData);
			System.out.print("hello");
		}
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

		return imageF;
	}

	public boolean saveImage(){
		File f;
		f = fileCreate();

		OutputStream out = null;
		try {
			out = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		byte buf[] = new byte[1024];

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();

		try {
			out.write(byteArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			bmp = (Bitmap) extras.get("data");
			iv.setImageBitmap(bmp);
			boolean success=saveImage();
		}
	}

}