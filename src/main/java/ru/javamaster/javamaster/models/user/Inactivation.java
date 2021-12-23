package ru.javamaster.javamaster.models.user;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
public class Inactivation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InactivationReason reason;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

}
