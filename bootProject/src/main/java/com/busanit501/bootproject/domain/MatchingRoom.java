package com.busanit501.bootproject.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "matching_rooms")
public class MatchingRoom extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "calendar_id")
//    private Calendar calendar;
//

//    public User getOtherUser(User currentUser) {
//        if (host.equals(currentUser)) {
//            // 현재 사용자가 host인 경우, user 정보를 반환
//            return user;
//        } else if (user.equals(currentUser)) {
//            // 현재 사용자가 user인 경우, host 정보를 반환
//            return host;
//        }
//        return null;  // 매칭룸에 속한 사용자가 아닌 경우
//    }


}
