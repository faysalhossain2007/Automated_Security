package buet.threebyzero.autoSecuritySystem;

import android.util.Log;

public abstract class ActiveMode extends Capture {

	protected boolean isOK = true;
	private boolean warningSent = false;
	private static final String LOG_TAG = "Active Mode";
	
	@Override
	public void processPicture() {
		Comparator imageComparator = new Comparator();
		imageComparator.setImages(primaryImage, currentImage);
		Log.d(LOG_TAG, imageComparator.difference + "");
		
		if(!imageComparator.isSame()) {
			isOK = false;
			if(!warningSent)
				sendWarning();
		}
		else {
			isOK = true;
			warningSent = false;
		}
	}

	protected abstract void sendWarning();
}
