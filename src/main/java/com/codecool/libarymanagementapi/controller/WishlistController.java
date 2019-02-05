package com.codecool.libarymanagementapi.controller;


import com.codecool.libarymanagementapi.model.Wishlist;
import com.codecool.libarymanagementapi.repository.WishlistRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/wishlist")
public class WishlistController {


    @Autowired
    WishlistRepository wrep;


    @PostMapping("/onwishlist")
    public JSONObject onwishlist(@RequestParam("OLID") String olId) {
        Long user = 0L;
        Wishlist wishlist = wrep.findByOlId(olId);

        System.out.println("wishlist: " + wishlist);

        boolean found = false;
        if (wishlist != null){
            found = true;
        }

        JSONObject obj = new JSONObject();
        obj.put("onwishlist", found);
        return obj;
    }


    @PostMapping("/add")
    public JSONObject greeting(@RequestParam("OLID") String olId) {
        Long user = 0L;

        Wishlist book = wrep.findByUserIdAndOlId(user, olId);
        JSONObject obj = new JSONObject();
        if (book == null) {
            Wishlist newWish = new Wishlist();
            newWish.setUserId(user);
            newWish.setOlId(olId);
            wrep.save(newWish);
            obj.put("success", true);
        } else {
            obj.put("success", false);
            System.out.println("shit was already in db");
        }
        return obj;
    }

    @PostMapping("/remove")
    public JSONObject remove(@RequestParam("OLID") String olId) {
        Long user = 0L;
        Set<Wishlist> wishlist = wrep.findAllByUserId(user);
        System.out.println("olId: " + olId);


        Long book = wrep.deleteWishlistByUserIdAndOlId(user, olId);
        System.out.println("book: " + book);

        JSONObject obj = new JSONObject();
        if (book == 1){
            obj.put("success", true);
        } else {
            obj.put("success", false);
        }
        return obj;
    }

    @PostMapping("/getwishlist")
    public JSONObject getwishlist() {
        Long user = 0L;
        Set<Wishlist> wishlistSet = wrep.findAllByUserId(user);
        JSONObject obj = new JSONObject();

        for (Wishlist book : wishlistSet){
            obj.put("book_" + book.getId(), book.getOlId());

        }
        System.out.println(obj.toString());
        return obj;
    }
}
