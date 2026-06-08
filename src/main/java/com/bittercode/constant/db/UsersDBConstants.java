package com.bittercode.constant.db;

public interface UsersDBConstants {

	String TABLE_USERS = "users";
	String COLUMN_USERNAME = "username";
	String COLUMN_PASSWORD = "password";
	String COLUMN_FIRSTNAME = "firstname";
	String COLUMN_LASTNAME = "lastname";
	String COLUMN_ADDRESS = "address";
	String COLUMN_PHONE = "phone";
	String COLUMN_MAILID = "mailid";
	String COLUMN_USERTYPE = "usertype";
	String SECRET_REFERENCE = "@Microsoft.KeyVault(SecretUri=${AZURE_KEY_VAULT_SECRET_URI})";
}
