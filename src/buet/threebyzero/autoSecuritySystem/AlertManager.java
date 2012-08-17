package buet.threebyzero.autoSecuritySystem;

import android.content.Context;

public class AlertManager {
	
	private String[] phoneNumbers;
	
	public AlertManager(Context context) {
		ClientManager clientManager = new ClientManager(context);
		phoneNumbers = clientManager.getPhoneNumbers();
	}
	
	public void sendEmergencyMessage() {
		SmsSender sender = new SmsSender(true);
		
		for(String number : phoneNumbers)
			sender.sendMessage(number);
	}
	
	public void sendWarningMessage() {
		SmsSender sender = new SmsSender(false);
	
		for(String number : phoneNumbers)
			sender.sendMessage(number);
		
	}
}
