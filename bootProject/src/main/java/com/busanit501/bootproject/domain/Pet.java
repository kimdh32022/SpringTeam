package com.busanit501.bootproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude ="imageSet")
public class Pet extends BaseEntity {

    @Id // PK, 기본키,
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    private Long userId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String personality;
    
    private Float weight;

    private String profile_picture;

    @Builder.Default
    private boolean verified = false;


    public enum Type {
        // 작은 견종
        CHIHUAHUA,
        POMERANIAN,
        YORKSHIRE_TERRIER,
        DACHSHUND,
        MALTESE,
        SHIH_TZU,
        TOY_POODLE,

        // 중형 견종
        BEAGLE,
        BORDER_COLLIE,
        BULLDOG,
        COCKER_SPANIEL,
        SHIBA_INU,
        BOSTON_TERRIER,
        MINIATURE_SCHNAUZER,

        // 대형 견종
        LABRADOR_RETRIEVER,
        GOLDEN_RETRIEVER,
        SIBERIAN_HUSKY,
        GERMAN_SHEPHERD,
        DOBERMAN,
        BOXER,
        ROTTWEILER,

        // 초대형 견종
        SAINT_BERNARD,
        GREAT_DANE,
        MASTIFF,
        NEWFOUNDLAND,

        // 특정 작업견
        AKITA,
        AUSTRALIAN_SHEPHERD,
        BELGIAN_MALINOIS,
        ALASKAN_MALAMUTE,
        BERNESE_MOUNTAIN_DOG,

        // 사냥견
        BASSET_HOUND,
        BLOODHOUND,
        POINTER,
        IRISH_SETTER,

        // 기타
        MIXED_BREED // 믹스견 (잡종견)
    }

}
