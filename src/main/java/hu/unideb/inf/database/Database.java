package hu.unideb.inf.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "Results")
public class Database {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long pk;
    private Integer numOfRounds;
    private String winner;
    private LocalDate date;
}

