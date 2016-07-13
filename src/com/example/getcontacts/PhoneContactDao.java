package com.example.getcontacts;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.TextUtils;

/**
 * ��ȡ�ֻ���ϵ��dao
 * @author oceangray
 *
 */
public class PhoneContactDao {
	/**��ȡ��Phon���ֶ�**/
    private static final String[] PHONES_PROJECTION = new String[] {
	    Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };
    /**��ϵ����ʾ����**/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
 
    /**�绰����**/
    private static final int PHONES_NUMBER_INDEX = 1;
 
    /**ͷ��ID**/
    private static final int PHONES_PHOTO_ID_INDEX = 2;
 
    /**��ϵ�˵�ID**/
    private static final int PHONES_CONTACT_ID_INDEX = 3;
    /**�õ��ֻ�ͨѶ¼��ϵ����Ϣ**/
    public static List<PhoneContact> getPhoneContacts(Activity activity) {
    	List<PhoneContact> contacts=new ArrayList<PhoneContact>();
		ContentResolver resolver = activity.getContentResolver();
		// ��ȡ�ֻ���ϵ��
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			    while (phoneCursor.moveToNext()) {
			    	PhoneContact contact=new PhoneContact();
					//�õ��ֻ�����
					String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
					//���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��
					if (TextUtils.isEmpty(phoneNumber))
					    continue;
					//�õ���ϵ������
					String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
					contact.setName(contactName);
					//�õ���ϵ��ID
					Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
					contact.setPhone(phoneNumber);
					//�õ���ϵ��ͷ��ID
					Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
					//�õ���ϵ��ͷ��Bitamp
					Bitmap contactPhoto = null;
					//photoid ����0 ��ʾ��ϵ����ͷ�� ���û�и���������ͷ�������һ��Ĭ�ϵ�
					if(photoid > 0 ) {
					    Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);
					    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
					    contactPhoto = BitmapFactory.decodeStream(input);
					   contact.setContactPhoto(contactPhoto);
					}
					contacts.add(contact);
			    }
			    phoneCursor.close();
			}
		return contacts;
	  }
    /**�õ��ֻ�ͨѶ¼��ϵ����Ϣ**/
    public static List<PhoneContact> searchPhoneContacts(Activity activity,String seachContent) {
    	List<PhoneContact> contacts=new ArrayList<PhoneContact>();
		ContentResolver resolver = activity.getContentResolver();
		String selection=Phone.DISPLAY_NAME+" like '%"+seachContent+"%' or "+ Phone.NUMBER +" like '%"+seachContent+"%'";
		// ��ȡ�ֻ���ϵ��
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, selection, null, null);
		if (phoneCursor != null) {
			    while (phoneCursor.moveToNext()) {
			    	PhoneContact contact=new PhoneContact();
					//�õ��ֻ�����
					String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
					//���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��
					if (TextUtils.isEmpty(phoneNumber))
					    continue;
					//�õ���ϵ������
					String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
					contact.setName(contactName);
					//�õ���ϵ��ID
					Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
					contact.setPhone(phoneNumber);
					//�õ���ϵ��ͷ��ID
					Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
					//�õ���ϵ��ͷ��Bitamp
					Bitmap contactPhoto = null;
					//photoid ����0 ��ʾ��ϵ����ͷ�� ���û�и���������ͷ�������һ��Ĭ�ϵ�
					if(photoid > 0 ) {
					    Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);
					    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
					    contactPhoto = BitmapFactory.decodeStream(input);
					    contact.setContactPhoto(contactPhoto);
					}
					contacts.add(contact);
			    }
			    phoneCursor.close();
			}
		return contacts;
	  }
    /**�õ��ֻ�SIM����ϵ������Ϣ**/
    public  static List<PhoneContact> getSIMContacts(Activity activity) {
    	List<PhoneContact> contacts=new ArrayList<PhoneContact>();
		ContentResolver resolver = activity.getContentResolver();
		// ��ȡSims����ϵ��
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,null);
		if (phoneCursor != null) {
		    while (phoneCursor.moveToNext()) {
		    	PhoneContact contact=new PhoneContact();
				// �õ��ֻ�����
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// ���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��
				if (TextUtils.isEmpty(phoneNumber))
				    continue;
				// �õ���ϵ������
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
				//Sim����û����ϵ��ͷ��
				contact.setName(contactName);
				contact.setPhone(phoneNumber);
				contacts.add(contact);
		    }
		    phoneCursor.close();
		}
		return contacts;
    }
}
