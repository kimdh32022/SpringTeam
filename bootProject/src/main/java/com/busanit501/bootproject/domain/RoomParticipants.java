package com.busanit501.bootproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@Entity
@Table(name = "RoomParticipants")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomParticipants extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomParticipantsId;

    @ManyToOne
    @JoinColumn(name = "chatRoomId")
    private MatchingRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senderId")
    private User sender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('Pending', 'Accepted','Rejected') DEFAULT 'Pending'")
    private RoomParticipantsStatus status;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
}
