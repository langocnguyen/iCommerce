package com.nabtest.icommerce.utils;

import com.google.i18n.phonenumbers.PhoneNumberUtil;

public class Utils {
	private Utils() {}

	public static boolean isValidVietNamPhoneNumber(String phoneNumber) {
		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		return phoneNumberUtil.isPossibleNumber(phoneNumber, "VN");
	}
}
