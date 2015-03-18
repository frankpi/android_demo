package com.example.activitydata;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectContactActivity extends Activity {


	private ListView lv;
	private List<ContactInfo> contactInfos;
	ContactInfoService contactInfoService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectcontact);
		lv = (ListView) findViewById(R.id.lv_contact);
		contactInfos = ContactInfoService.readContactInfos(this);
		lv.setAdapter(new ContactAdapter());

		lv.setOnItemClickListener(new MyListener());
	}

	public class MyListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			ContactInfo info = contactInfos.get(position);
			String number = info.getTel();
			Intent data = new Intent();
			data.putExtra("number", number);
			setResult(0, data);

			finish();
		}

	}

	public class ContactAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return contactInfos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ContactInfo info = contactInfos.get(position);
			View view = View.inflate(getApplicationContext(),
					R.layout.contact_item, null);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			TextView tv_tel = (TextView) view.findViewById(R.id.tv_tel);
			tv_name.setText(info.getName());
			tv_tel.setText(info.getTel());
			return view;
		}
		
	}
}
