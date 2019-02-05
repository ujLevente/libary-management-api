package com.codecool.libarymanagementapi.repository;

import com.codecool.libarymanagementapi.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    Set<Wishlist> findAllByUserId(Long userid);
    Wishlist findByOlId(String olID);
    Wishlist findByUserIdAndOlId(Long userId, String olId);

    @Transactional
    Long deleteWishlistByUserIdAndOlId(long userId, String olId);
    Set<Wishlist> findDeviceByUserId(long userId);
}
