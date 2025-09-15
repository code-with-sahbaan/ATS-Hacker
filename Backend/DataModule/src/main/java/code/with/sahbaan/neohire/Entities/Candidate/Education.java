package code.with.sahbaan.neohire.Entities.Candidate;

import code.with.sahbaan.neohire.Entities.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EDUCATION")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EDUCATION_ID")
    private long educationId;

    @Column(name = "SCHOOL_NAME")
    private String schoolName;

    @Column(name = "LEVEL")
    private String level;

    @Column(name = "FROM")
    private LocalDate from;

    @Column(name = "TO")
    private LocalDate to;

    @Column(name = "CURRENTLY_ATTENDING")
    private Boolean currentlyAttending = false;

    @ManyToOne
    private Users candidate;

}
