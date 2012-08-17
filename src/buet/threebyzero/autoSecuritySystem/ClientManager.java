package buet.threebyzero.autoSecuritySystem;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ClientManager implements Constants{
	private ClientData clientData;
	
	public ClientManager(Context context) {
		clientData = new ClientData(context);
	}
	
	public void addClient(String name, String phoneNo) {
		SQLiteDatabase database = clientData.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CLIENT_NAME, name);
		values.put(PHONE_NO, phoneNo);
		database.insert(TABLE_NAME, null, values);
	}
	
	public String[] getPhoneNumbers() {
		SQLiteDatabase database = clientData.getReadableDatabase();
		String[] from = {CLIENT_NAME};
		Cursor cursor = database.query(TABLE_NAME, from, null, null, null, null, null);
		
		if(cursor.moveToFirst()) {
			List<String> phoneNumbers = new ArrayList<String>();
			int nameIndex = cursor.getColumnIndex(CLIENT_NAME);
			while(cursor.moveToNext())
				phoneNumbers.add(cursor.getString(nameIndex));
			return phoneNumbers.toArray(new String[phoneNumbers.size()]);
		}
		return null;
	}
	
	public Client[] getClients() {
		SQLiteDatabase database = clientData.getReadableDatabase();
		String[] from = {CLIENT_NAME, PHONE_NO};
		Cursor cursor = database.query(TABLE_NAME, from, null, null, null, null, null);
		
		if(cursor.moveToFirst()) {
			List<Client> phoneNumbers = new ArrayList<Client>();
			int nameIndex = cursor.getColumnIndex(CLIENT_NAME);
			int phoneNoIndex = cursor.getColumnIndex(PHONE_NO);
			while(cursor.moveToNext()) {
				String name = cursor.getString(nameIndex);
				String phoneNo = cursor.getString(phoneNoIndex);
				phoneNumbers.add(new Client(name, phoneNo));
			}
			return phoneNumbers.toArray(new Client[phoneNumbers.size()]);
		}
		return null;
	}
	
	public void deleteClient(int id) {
		SQLiteDatabase database = clientData.getReadableDatabase();
		database.delete(TABLE_NAME, _ID + "='" + id + "'", null);
	}
}
