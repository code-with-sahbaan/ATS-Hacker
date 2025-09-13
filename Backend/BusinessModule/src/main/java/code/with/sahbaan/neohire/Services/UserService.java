package code.with.sahbaan.neohire.Services;

import code.with.sahbaan.neohire.Entities.Users;

import java.util.Optional;

public interface UserService {

    public Optional<Users> findByEmail(String email);

    public void saveOrUpdate(Users user);
}
