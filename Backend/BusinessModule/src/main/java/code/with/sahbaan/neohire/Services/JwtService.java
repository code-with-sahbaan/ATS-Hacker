package code.with.sahbaan.neohire.Services;

import code.with.sahbaan.neohire.Entities.Users;

public interface JwtService {

    String generateToken(Users user);
}
