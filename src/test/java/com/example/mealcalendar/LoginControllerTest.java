package com.example.mealcalendar;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginControllerTest {

    private LoginController loginController;
    private static final String PASSWORD = "password";

    @Test
    void test() throws IOException {
        loginController = new LoginController(new TestUserDao(false));
        LoginBean bean = new LoginBean("", "");
        assertFalse(loginController.login(bean));//Luca
        loginController = new LoginController(new TestUserDao(true));
        bean = new LoginBean("", PASSWORD);
        assertTrue(loginController.login(bean));//Luca
    }

    private class TestUserDao extends UserDao {
        boolean good;


        public TestUserDao(boolean good) {
            super(USE_DATABASE, false);
            this.good = good;
        }

        @Override
        public UserEntity getUserByUsername(String username) throws IOException {
            if (good) {
                return new UserEntity("", "", BCrypt.hashpw(PASSWORD, BCrypt.gensalt()));
            }else {
                return null;
            }
        }
    }
}
