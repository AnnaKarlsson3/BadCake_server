package com.dkserver.danielServer.utils;

public class Constants {

    //Json-token
    public static final long JWT_EXPIRATION = 33600000;

    //endpoints
    public static final String REST_AUTH = "/rest/auth/";
    public static final String REST = "/rest";

    //create new DB
    public static final String NEW_JDBC_URL ="jdbc:mysql://localhost:3306/";
    public static final String USE_SSL_IN_JDBC_URL = "?useSSL=false";
    public static final String DRIVERCLASS_NAME = "com.mysql.cj.jdbc.Driver";

    //Tenant setup
    public static final String DEFAULT_TENANT ="PUBLIC";
    public static final String HEADER_TENANT ="X-TenantID";
    public static final String NO_HEADER_TENANT = "X-TenantID not present in the Request Header";

    //CORS
    public static final String CORS_URL = "http://localhost:5173";

    //Responses
    public static final String SHORTNOTE_RESPONSE_DELETE_OK ="Shortnote post deleted successful!";
    public static final String SHORTNOTE_RESPONSE_NO_POST_FOUND = "No shortNotes posts found!";
    public static final String SHORTNOTE_RESPONSE_NO_POST_FOUND_ID = "No shortnote posts found with that id!";

    public static final String CUSTOMER_RESPONSE_NO_FOUND = "No customers found!";
    public static final String CUSTOMER_RESPONSE_UPLOAD_SUCCESS = "Customer post uploaded successful!";

    public static final String LINK_RESPONSE_DELETE_OK = "Linkpost deleted successful!";
    public static final String LINK_RESPONSE_UPLOAD_SUCCESS = "Linkpost uploaded successful!";
    public static final String LINK_RESPONSE_NO_FOUND = "No Linkposts found!";
    public static final String LINK_RESPONSE_NO_FOUND_ID = "No Linkpost found with that id!";

    public static final String AUTH_RESPONSE_NO_FOUND = "Wrong username or password!";
    public static final String AUTH_RESPONSE_TAKEN = "Username is taken or Email is used!";
    public static final String AUTH_RESPONSE_UPLOAD_SUCCESS = "User registered success!";
    public static final String AUTH_RESPONSE_PASSWORD_CHANGE_SUCCESS = "Password reset successfully.";
    public static final String AUTH_RESPONSE_PASSWORD_INVALID_TOKEN = "Invalid or expired token.";
    public static final String AUTH_RESPONSE_PASSWORD_EMAIL_SENT = "ResetPassword email sent!";

    public static final String AUTH_EMAIL_MESSAGE_FROM = "dev.badcake@gmail.com";
    public static final String AUTH_EMAIL_MESSAGE_SUBJECT = "Återställ lösenord";
    public static final String AUTH_EMAIL_MESSAGE_BODY = "För att återställa ditt lösenord hos BadCake, klicka på länken: "
            + "http://localhost:8080/rest/auth/resetpassword?token=";



    public static final String ERROR_RESPONSE_SOMETHING_WRONG = "Something went wrong";


}
