package com.example.mealcalendar;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterControllerTest {


    private RegisterController registerController;
    private static final String PASSWORD = "1";
    private static final String USERNAME = "luca";

    @Test
    void test() throws IOException{


        registerController = new RegisterController(new TestUserDao(false));
        UserBean bean = new UserBean(USERNAME,"",PASSWORD);
        assertFalse(registerController.register(bean));
        registerController = new RegisterController(new TestUserDao(true));
        bean = new UserBean("new","new","new");
        assertTrue(registerController.register(bean));

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
