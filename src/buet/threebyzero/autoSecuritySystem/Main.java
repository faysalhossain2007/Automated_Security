package buet.threebyzero.autoSecuritySystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity{

	private Button startButton;
	private Button connectToServerButton;
	private Button clientsButton;
	private Button settingsButton;
	private Button aboutButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initializeViews();
	}
	
	private void initializeViews() {
		startButton = (Button) findViewById(R.id.startButton);
		connectToServerButton = (Button) findViewById(R.id.connectServerButton);
		clientsButton = (Button) findViewById(R.id.clientsButton);
		settingsButton = (Button) findViewById(R.id.settingsButton);
		aboutButton = (Button) findViewById(R.id.aboutButton);
		
		MouseClickListener listener = new MouseClickListener();
		startButton.setOnClickListener(listener);
		connectToServerButton.setOnClickListener(listener);
		clientsButton.setOnClickListener(listener);
		settingsButton.setOnClickListener(listener);
		aboutButton.setOnClickListener(listener);
	}
	
	private void startCameraDialog() {
    	AlertDialog.Builder newGameDialog = new AlertDialog.Builder(this);
    	
    	newGameDialog.setTitle(R.string.startCameraDialogTitle);
    	newGameDialog.setItems(R.array.modes, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int choice) {

		    	Intent startCameraIntent = null;
				switch(choice) {
				case 0:
					startCameraIntent = new Intent(getBaseContext(), Capture.class);
			    	break;
				case 1:
					startCameraIntent = new Intent(getBaseContext(), Capture.class);
			    	break;
				case 2:
					startCameraIntent = new Intent(getBaseContext(), Capture.class);
			    	break;
				}
				if(startCameraIntent != null)
					startActivity(startCameraIntent);
			}
		});
    	newGameDialog.show();
    }
	
	private class MouseClickListener implements OnClickListener {

		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.startButton:
				startCameraDialog();
				break;
			case R.id.connectServerButton:
				break;
			case R.id.clientsButton:
				break;
			case R.id.settingsButton:
				break;
			case R.id.aboutButton:
				break;
			}
		}
		
	}
}
