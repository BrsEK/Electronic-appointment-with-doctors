package ru.krinitsky.registratura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.krinitsky.registratura.domain.Role;
import ru.krinitsky.registratura.domain.User;
import ru.krinitsky.registratura.reposytory.RoleRepository;
import ru.krinitsky.registratura.reposytory.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder encoder;

    private final static int ROLE_ID_ADMIN = 1;
    private final static int ROLE_ID_DOCTOR = 2;
    private final static int ROLE_ID_RECEPTIONIST = 3;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user;
    }


    public boolean saveUser(User user, int roleId) {
        User userFromDb =
                userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        Role role = roleRepository.findById(roleId).get();
        user.getRoles().add(role);
        user.setPassword(encoder.encode(user.getPassword()));
        role.getUsers().add(user);
        userRepository.save(user);
        return true;
    }


    //Удалить пользователя
    public void deleteUser(String username) {
        userRepository.findByUsername(username);
    }
}
