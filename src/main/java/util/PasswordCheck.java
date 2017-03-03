package util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Created by oblige on 11/3/14.
 */
public abstract class PasswordCheck<E, R> {

    /**
     * Get real repository
     *
     * @return repo
     */
    protected abstract R repo();

    /**
     * at least one digital and one word
     * 6 to 16
     *
     * @param password password
     * @return valid or not
     */
    public boolean checkPassword(String password) {
        // stronger
        // return password.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{6,16}$");
        // weaker
        return password.matches("^[a-zA-Z0-9]{6,16}$");
    }

    /**
     * start with word
     * only digits and words
     * 4 to 16
     *
     * @param username username
     * @return valid or not
     */
    public boolean checkUsername(String username) {
        return username.matches("^[a-zA-Z][a-zA-Z0-9_]{4,16}$");
    }

    /**
     * register a user
     *
     * @param e user info
     * @return true is added, false otherwise
     */
    public boolean register(E e) throws
            InvocationTargetException,
            IllegalAccessException,
            NoSuchFieldException,
            NoSuchMethodException {

        // do some initial check
        Field username = e.getClass().getField("username");
        Field password = e.getClass().getField("password");
        Field id = e.getClass().getField("id");
        Method findByUsername = repo().getClass().getMethod("findByUsername");
        Method save = repo().getClass().getMethod("save");

        if (null == username || null == password || null == findByUsername || null == save) {
            return false;
        }

        // check format of username & password
        if (!checkUsername((String) username.get(e)) || !checkPassword((String) password.get(e))) {
            return false;
        }
        // check unique username
        List<E> list = (List<E>) findByUsername.invoke(repo(), username);
        if (null != list && 0 != list.size()) {
            return false;
        }
        // save user
        try {
            password.set(e, PasswordHash.createHash((String) password.get(e)));
            e = (E) save.invoke(repo(), e);
            // if & only if user is persisted
            if (null != id.get(e)) {
                return true;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * check valid user info
     *
     * @param e user info
     * @return true if valid, false otherwise
     */
    public boolean validate(E e) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        System.out.println(repo().getClass().getName());
        // do some initial check
        Field username = e.getClass().getField("username");
        Field password = e.getClass().getField("password");
//        Field id = e.getClass().getField("id");
        System.out.println(repo().getClass().getName());
        Method findByUsername = repo().getClass().getMethod("findByUsername");
        Method save = repo().getClass().getMethod("save");

        if (null == username || null == password || null == findByUsername || null == save) {
            return false;
        }
        // check format of username & password
        if (!checkUsername((String) username.get(e)) || !checkPassword((String) password.get(e))) {
            return false;
        }
        // check unique username
        List<E> list = (List<E>) findByUsername.invoke(repo(), username);
        if (null == list || 0 == list.size()) {
            return false;
        }
        // check correctness of password
        boolean valid = false;
        try {
            valid = PasswordHash.validatePassword((String) password.get(e), (String) password.get(list.get(0)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
            e1.printStackTrace();
        }

        return valid;
    }
//driver use phone to validate
    public boolean validateByPhone(E e) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        System.out.println(repo().getClass().getName());
        // do some initial check
        Field username = e.getClass().getField("phone");//phone as username
        Field password = e.getClass().getField("password");
//        Field id = e.getClass().getField("id");
        System.out.println(repo().getClass().getName());
        Method findByPhone = repo().getClass().getMethod("findByPhone");
        Method save = repo().getClass().getMethod("save");

        if (null == username || null == password || null == findByPhone || null == save) {
            return false;
        }
        // check format of username & password
        if (!checkUsername((String) username.get(e)) || !checkPassword((String) password.get(e))) {
            return false;
        }
        // check unique username
        List<E> list = (List<E>) findByPhone.invoke(repo(), username);
        if (null == list || 0 == list.size()) {
            return false;
        }
        // check correctness of password
        boolean valid = false;
        try {
            valid = PasswordHash.validatePassword((String) password.get(e), (String) password.get(list.get(0)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
            e1.printStackTrace();
        }

        return valid;
    }
}
