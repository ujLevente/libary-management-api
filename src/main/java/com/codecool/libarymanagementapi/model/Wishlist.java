package com.codecool.libarymanagementapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "wishlist", schema = "public")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "olId")
    private String olId;

//    public Wishlist(CustomUserDetails user, String olId) {
////        this.user = user;
//        this.olId = olId;
//    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                ", olId='" + olId + '\'' +
//                ", user=" + user +
                '}';
    }
}
