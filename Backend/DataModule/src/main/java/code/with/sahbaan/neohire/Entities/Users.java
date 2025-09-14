package code.with.sahbaan.neohire.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USERS")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PICTURE_URL")
    private String pictureUrl;

    @Column(name = "PROVIDER")
    private String provider; // GOOGLE, GITHUB, etc.

    @Column(name = "ROLE")
    private String role;
}
