package com.codecool.libarymanagementapi.repository;

import com.codecool.libarymanagementapi.model.User;
import com.codecool.libarymanagementapi.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    Set<Wishlist> findAllByUser(User userid);
    Wishlist findByOlId(String olID);
    Wishlist findByUserAndOlId(User userId, String olId);

    @Transactional
    Long deleteWishlistByUserAndOlId(User userId, String olId);
}
