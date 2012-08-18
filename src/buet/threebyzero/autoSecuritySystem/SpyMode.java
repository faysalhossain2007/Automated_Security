package buet.threebyzero.autoSecuritySystem;

import android.util.Log;


public class SpyMode extends Capture {

	private static final String LOG_TAG = "Spy Mode";

	@Override
	public void processPicture() {
		Comparator imageComparator = new Comparator();
		imageComparator.setImages(primaryImage, currentImage);
		Log.d(LOG_TAG, imageComparator.difference + "");
		
		if(!imageComparator.isSame())
			sendPicture();
	}

	private void sendPicture() {
		
	}
}
