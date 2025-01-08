package com.busanit501.bootproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "matching_rooms")
public class MatchingRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "host_id", nullable = false)
//    private User host;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private LocalDate meetingDate;

    @Column(nullable = false)
    private LocalTime meetingTime;

    @Column(nullable = false)
    private Long maxParticipants;

    private String imageUrl;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<RoomParticipant> participants = new ArrayList<>();
//
//    public Long getCurrentParticipants() {
//        return participants.stream()
//                .filter(p -> p.getStatus() == RoomParticipant.ParticipantStatus.Accepted)
//                .map(RoomParticipant::getUser)
//                .distinct()
//                .count();
//    }
}