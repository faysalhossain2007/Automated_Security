package buet.threebyzero.autoSecuritySystem;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns{
	int DATABASE_VERSION = 1;
	
	String TABLE_NAME = "client_info";
	String CLIENT_NAME = "name";
	String PHONE_NO = "number";  
	
	String EMERGENCY = "emergency";
	String WARNING = "warning";

}
