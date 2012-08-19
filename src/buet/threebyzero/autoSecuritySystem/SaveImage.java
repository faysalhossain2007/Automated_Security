package buet.threebyzero.autoSecuritySystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class SaveImage extends Activity {

	String state;
	boolean Write, Read;
	File file = null;
	File path = null;
	private String dateForPic;
	private String timeForPic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saveimage);
		path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		checkState();
		saveImage();
	}

	private void checkState() {
		// TODO Auto-generated method stub
		state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// read and write
			Write = Read = true;

		} else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			// read but can't write

			Write = false;
			Read = true;

		} else {

			Write = Read = false;
		}
	}

	public void saveImage() {
		dateForPic = new SimpleDateFormat("yyyyMMdd").format(new Date());
		timeForPic = new SimpleDateFormat("HHmmss").format(new Date());

		String f = dateForPic + '_' + timeForPic;

		file = new File(path, f + ".JPG");

		checkState();
		if (Write == Read == true) {

			path.mkdirs();

			try {
				InputStream is = getResources().openRawResource(
						R.drawable.ic_launcher);
				OutputStream os = new FileOutputStream(file);
				byte[] data = new byte[is.available()];
				is.read(data);
				os.write(data);
				is.close();
				os.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
	}

}
