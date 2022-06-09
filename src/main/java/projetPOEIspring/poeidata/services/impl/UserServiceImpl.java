package projetPOEIspring.poeidata.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projetPOEIspring.poeidata.exceptions.NameException;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.models.User;
import projetPOEIspring.poeidata.repositories.UserRepository;
import projetPOEIspring.poeidata.services.UserService;

import java.util.List;

@Service
@Qualifier("userService")
public class UserServiceImpl implements UserService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll(Sort.by("username").ascending());
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UnknownResourceException("No user found for the given ID.")
        );
    }

    @Override
    public User getByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(
                () -> new UnknownResourceException("No user found for this username.")
        );
    }

    @Override
    public User createUser(User user) {
        log.debug("Attempting to save in DB...");

        String passwordEncoded = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(passwordEncoded);
        if (user.getUsername().length() > 30) {
            throw new NameException("Username cannot have more than 30 characters.");
        } else {
            return this.userRepository.save(user);
        }
    }

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        User user = this.userRepository.findByUsername(username).get();
        if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return user;
        }
        throw  new UnknownResourceException("No user found for the given user/password.");
    }

    @Override
    public void deleteUser(Integer id) {
        User userToDelete = this.getById(id);
        this.userRepository.delete(userToDelete);
    }

    @Override
    public User updateUser(User user) {
        User existingUser = this.getById(user.getId());
        existingUser.setUsername(user.getUsername());
        existingUser.setGrants(user.getGrants());
        String passwordEncoded = new BCryptPasswordEncoder().encode(user.getPassword());
        existingUser.setPassword(passwordEncoded);
        if (user.getUsername().length() > 30) {
            throw new NameException("Username cannot have more than 30 characters.");
        } else {
            return this.userRepository.save(existingUser);
        }
    }
}
