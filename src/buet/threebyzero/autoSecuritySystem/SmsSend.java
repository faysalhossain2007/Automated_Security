package buet.threebyzero.autoSecuritySystem;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;

public class SmsSend extends Activity {
	final static String emergencyHelp = "Subject is in trouble";// Subject=
																// shop/office address
	String address = "5556";// it will be the number of police

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms);
		sendMessage();
	}

	private void sendMessage() {
		// TODO Auto-generated method stub

		SmsManager manager = SmsManager.getDefault();
		manager.sendTextMessage(address, null, emergencyHelp, null, null);

	}

}
