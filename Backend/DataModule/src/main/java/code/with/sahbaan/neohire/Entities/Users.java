package code.with.sahbaan.neohire.Entities;

import code.with.sahbaan.neohire.Entities.Candidate.Resume;
import code.with.sahbaan.neohire.Entities.Recruiter.Job;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USERS")
public class Users {

    /*
    * GENERAL FIELDS FOR ALL TYPE OF USERS
    * */

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

    @Column(name = "CITY")
    private String city;

    @Column(name = "COUNTRY")
    private String country;

    /*
     * CANDIDATE SPECIFIC FIELDS
     * */

    @OneToOne(mappedBy = "candidate", cascade = CascadeType.ALL)
    private Resume resume;

    /*
     * RECRUITER SPECIFIC FIELDS
     * */

    @OneToMany(mappedBy = "recruiter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Job> jobs = new HashSet<>();
}
