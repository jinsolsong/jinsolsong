package com.spring.restartnewsfeed.mock;

import com.spring.restartnewsfeed.user.domain.User;


public class MockData {

    public final static Long TEST_ID1 = 1L;
    public final static String TEST_NAME1 = "TestName1";
    public final static String TEST_EMAIL1 = "TestUser1@email.com";
    public final static String TEST_NICKNAME1 = "TestUser1";
    public final static String TEST_PASSWORD1 = "Password123!";
    public final static User TEST_USER1 = new User(TEST_NAME1, TEST_EMAIL1, TEST_PASSWORD1);


    public final static Long TEST_ID2 = 2L;
    public final static String TEST_NAME2 = "TestName2";
    public final static String TEST_EMAIL2 = "TestUser2@email.com";
    public final static String TEST_NICKNAME2 = "TestUser2";
    public final static String TEST_PASSWORD2 = "Password1234!";
    public final static User TEST_USER2 = new User(TEST_NAME2, TEST_EMAIL2, TEST_PASSWORD2);
}
