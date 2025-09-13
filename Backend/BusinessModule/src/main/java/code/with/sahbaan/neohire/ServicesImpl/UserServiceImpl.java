package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Entities.Users;
import code.with.sahbaan.neohire.Repositories.UserRepository;
import code.with.sahbaan.neohire.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveOrUpdate(Users user) {
        userRepository.save(user);
    }
}
