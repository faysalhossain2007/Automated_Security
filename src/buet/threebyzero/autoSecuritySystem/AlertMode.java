package buet.threebyzero.autoSecuritySystem;

import android.util.Log;

public class AlertMode extends Capture {

	private static final String LOG_TAG = "Alert Mode";
	@Override
	public void processPicture() {
		Comparator imageComparator = new Comparator();
		imageComparator.setImages(primaryImage, currentImage);
		Log.d(LOG_TAG, imageComparator.difference + "");
		if(!imageComparator.isSame())
			sendWarning();
	}
	
	private void sendWarning() {
		
	}
}
