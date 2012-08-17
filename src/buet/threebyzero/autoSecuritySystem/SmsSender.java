package buet.threebyzero.autoSecuritySystem;

import android.telephony.SmsManager;

public class SmsSender implements Constants {
	boolean emergency;

	public SmsSender(boolean isEmergency) {
		emergency = isEmergency;
	}
	
	public void sendMessage(String number) {
		SmsManager manager = SmsManager.getDefault();
		String message = emergency ? EMERGENCY : WARNING;
		manager.sendTextMessage(number, null, message, null, null);
	}
}
