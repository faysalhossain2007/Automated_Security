package buet.threebyzero.autoSecuritySystem;

import android.graphics.Bitmap;

public class PassiveMode extends Capture {

	private static final String LOG_TAG = "Passive Mode";
	
	@Override
	public void processPicture() {
		sendPicture(currentImage);
	}

	private void sendPicture(Bitmap currentImage) {
		
	}
}
