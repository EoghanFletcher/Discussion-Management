package com.middleware.middlewarediscussionmanagement;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import dao.CommunicationDao;
import dao.Dao;
import dao.UserDao;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

public class CommunicationDaoTest {

    private static NetHttpTransport HTTP_TRANSPORT = null;
    private static CommunicationDao communicationDao  = null;

    @BeforeClass
    public static void setupData() {
        System.out.println("setupData");

        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            communicationDao = new CommunicationDao();

        } catch (Exception ex) {
            System.out.println("An error occurred [setupData], ex: " + ex);
            ex.printStackTrace();
        }
    }


        @Test
        public void getCredential() {
            System.out.println("getCredential");

            try {
                Assert.assertNotNull(communicationDao.getCredentials(HTTP_TRANSPORT));
            }
            catch(Exception ex) {
                System.out.println("An error occurred [getCredential[Test]], ex: " + ex);
                ex.printStackTrace();
            }
        }

        @Test
        public void getDrafts() {
            System.out.println("getDrafts");

            try {
//                Assert.assertNotNull(communicationDao.getEmails(HTTP_TRANSPORT));
                Assert.assertNotNull(communicationDao.getDrafts(HTTP_TRANSPORT));
            }
            catch(Exception ex) {
                System.out.println("An error occurred [getDrafts[Test]], ex: " + ex);
                ex.printStackTrace();
            }
        }

        @AfterClass
        public static void removeTestData() {
            System.out.println("removeTestData");
            try {

            }
            catch(Exception ex) {
                System.out.println("An error occurred [removeTestData], ex: " + ex);
                ex.printStackTrace();
            }
        }
}
