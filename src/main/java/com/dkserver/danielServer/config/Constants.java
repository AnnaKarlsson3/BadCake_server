package com.dkserver.danielServer.config;

public class Constants {

    //Json-token
    public static final long JWT_EXPIRATION = 33600000;

    //create new DB
    public static final String NEW_JDBC_URL ="jdbc:mysql://localhost:3306/";
    public static final String USE_SSL_IN_JDBC_URL = "?useSSL=false";
    public static final String DRIVERCLASS_NAME = "com.mysql.cj.jdbc.Driver";

    //Tenant setup
    public static final String DEFAULT_TENANT ="PUBLIC";

    //Responses



}
