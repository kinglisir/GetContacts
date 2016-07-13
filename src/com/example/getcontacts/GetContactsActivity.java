package com.example.getcontacts;

import java.util.ArrayList;







import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GetContactsActivity extends Activity {
	private ListView lv_getcontact;
	private ArrayList<PhoneContact> list = new ArrayList<PhoneContact>();//存放联系人对象的集合
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getcontacts);
		initView();
	}
	//初始化布局
	private void initView() {
		lv_getcontact = (ListView) findViewById(R.id.lv_getcontact);
		//获取手机联系人
		queryPhoneContact();
		//listview 设置适配器
		MyAdapter adapter = new MyAdapter();
		lv_getcontact.setAdapter(adapter);
	}
	
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder ;
			if(convertView==null){
				holder = new ViewHolder();
				convertView = View.inflate(getApplicationContext(), R.layout.listview_getcontacts, null);
				holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
				holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
				holder.iv_phone = (ImageView) convertView.findViewById(R.id.iv_phone);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			//填充数据
			fillDataToView(holder,position);
			return convertView;
		}
		
		private void fillDataToView(ViewHolder holder,int position) {
			 holder.tv_name.setText(list.get(position).getName());
			 holder.tv_phone.setText(list.get(position).getPhone());
			 holder.iv_phone.setImageBitmap(list.get(position).getContactPhoto());
			 
		}

		class ViewHolder{
			TextView tv_name,tv_phone;
			ImageView iv_phone;
		}
		
	}
	
	 /**
	    * 查询默认手机联系人
	    */
	   public void queryPhoneContact(){
		   /**得到手机通讯录联系人信息**/
			try {
				PackageManager pm = getPackageManager();  
				//boolean permission = (PackageManager.PERMISSION_GRANTED ==pm.checkPermission("android.permission.READ_CONTACTS ", "com.free.shishi")); 
				//if(permission){
				list=(ArrayList<PhoneContact>) PhoneContactDao.getPhoneContacts(GetContactsActivity.this);
				//}else{
				//	ToastHelper.shortShow(activity, "您禁止了读取联系人权限，请到设置中打开");
					//finish();
				//}
				
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"您禁止了读取联系人权限，请到设置中打开" , 0).show();
				finish();
			}
	   }
	
}
