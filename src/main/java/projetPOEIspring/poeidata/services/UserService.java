package projetPOEIspring.poeidata.services;

import projetPOEIspring.poeidata.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Integer id);

    User getByUsername (String username);

    User createUser (User user);

    User getByUsernameAndPassword (String username, String password);

    void deleteUser (Integer id);

    User updateUser (User user);
}
