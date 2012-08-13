package buet.threebyzero.autoSecuritySystem;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class Call extends Activity {

	private Button btnCall;
	private String phoneNumber;
	private boolean emergency;// it will be initialized in the alert manager
								// class

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call);
		emergency = true;
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub

		boolean checkAlert = emergency; // alert manager class will set
										// emergency value
		if (checkAlert == true) {
			phoneNumber = "5556";// client number
			performDial();

		}
	}

	public void performDial() {
		Intent i;
		i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
		startActivity(i);
	}

}
