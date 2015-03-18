package com.example.activitydata;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class ContactInfoService {

	static List<ContactInfo> contactInfos = new ArrayList<ContactInfo>();

	/**
	 * 系统的所有的联系人信息
	 * 
	 * @param context
	 * @return
	 */
	public static List<ContactInfo> readContactInfos(Context context) {
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri datauri = Uri.parse("content://com.android.contacts/data");
		Cursor cursor = resolver.query(uri, new String[] { "contact_id" },
				null, null, null);

		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("contact_id"));
			System.out.println("id:" + id);
			if (id != null) {
				Cursor dataCursor = resolver.query(datauri, new String[] {
						"data1", "mimetype" }, "raw_contact_id=?",
						new String[] { id }, null);
				ContactInfo contactInfo = new ContactInfo();

				while (dataCursor.moveToNext()) {
					String data = dataCursor.getString(dataCursor
							.getColumnIndex("data1"));
					String mimetype = dataCursor.getString(dataCursor
							.getColumnIndex("mimetype"));
					System.out.println("data:" + data);
					System.out.println("mimetype:" + mimetype);
					if ("vnd.android.cursor.item/name".equals(mimetype)) {
						contactInfo.setName(data);
					} else if ("vnd.android.cursor.item/phone_v2"
							.equals(mimetype)) {
						contactInfo.setTel(data);
					}
				}
				contactInfos.add(contactInfo);
				dataCursor.close();
			}
		}
		cursor.close();
		return contactInfos;

	}
}
