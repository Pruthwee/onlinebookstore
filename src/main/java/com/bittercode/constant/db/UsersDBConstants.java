package com.bittercode.constant.db;

/**
 * Database constants for Users table
 * Cloud-ready: No hard-coded credentials, only table/column names
 * Credentials should be managed via Azure Key Vault with Managed Identity
 */
public interface UsersDBConstants {

	public static String TABLE_USERS = "users";
	
	public static String COLUMN_USERNAME = "username";
	// Note: Passwords should be hashed and never stored in plain text
	// Consider using Azure AD B2C or Azure Key Vault for credential management
	public static String COLUMN_PASSWORD = "password";
	public static String COLUMN_FIRSTNAME = "firstname";
	public static String COLUMN_LASTNAME = "lastname";
	public static String COLUMN_ADDRESS = "address";
	public static String COLUMN_PHONE = "phone";
	public static String COLUMN_MAILID = "mailid";
	public static String COLUMN_USERTYPE = "usertype";
}
