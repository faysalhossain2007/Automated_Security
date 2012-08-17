package buet.threebyzero.autoSecuritySystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClientData extends SQLiteOpenHelper implements Constants {
	
	private static final String DATABASE_NAME = "clients.db";
	
	public ClientData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ CLIENT_NAME + " VARCHAR(25), " + PHONE_NO + " CHAR(15) NOT NULL);");
	};
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
