package com.codecool.libarymanagementapi.controller;


import com.codecool.libarymanagementapi.message.request.LoginForm;
import com.codecool.libarymanagementapi.message.response.JwtResponse;
import com.codecool.libarymanagementapi.message.response.ResponseMessage;
import com.codecool.libarymanagementapi.model.User;
import com.codecool.libarymanagementapi.model.Wishlist;
import com.codecool.libarymanagementapi.repository.UserRepository;
import com.codecool.libarymanagementapi.repository.WishlistRepository;
import com.codecool.libarymanagementapi.security.jwt.JwtAuthTokenFilter;
import com.codecool.libarymanagementapi.security.services.UserDetailsServiceImpl;
import com.codecool.libarymanagementapi.security.services.UserPrinciple;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    WishlistRepository wrep;

    @Autowired
    UserRepository ur;

    @PostMapping("/onwishlist")
    public String onwishlist(@RequestParam("OLID") String olId, Authentication authentication) {
        User user = ur.findByUsername(authentication.getName()).orElse(null);
        Wishlist wishlist = wrep.findByUserAndOlId(user, olId);

        System.out.println("wishlist: " + wishlist);

        boolean found = false;
        if (wishlist != null){
            found = true;
        }

        JSONObject obj = new JSONObject();
        obj.put("onwishlist", found);
        return obj.toString();
    }


    @PostMapping("/add")
    public String greeting(@RequestParam Map<String,String> allRequestParams, @RequestParam("OLID") String olId, Authentication authentication) {
        System.out.println("WISHLIST ADD " + olId);
        System.out.println("AllrequestParams: " + allRequestParams);
        User user = ur.findByUsername(authentication.getName()).orElse(null);
//        System.out.println("REQ: " + req);

        Wishlist book = wrep.findByUserAndOlId(user, olId);
        JSONObject obj = new JSONObject();
        if (book == null) {
            Wishlist newWish = new Wishlist();
            newWish.setUser(user);
            newWish.setOlId(olId);
            wrep.save(newWish);
            obj.put("success", true);
        } else {
            obj.put("success", false);
            System.out.println("shit was already in db");
        }
        return obj.toString();
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("OLID") String olId, Authentication authentication) {
        User user = ur.findByUsername(authentication.getName()).orElse(null);
        System.out.println("OLID:" + olId);
        System.out.println("USER: " + user.getName());

        Long book = wrep.deleteWishlistByUserAndOlId(user, olId);
        System.out.println("book: " + book);

        JSONObject obj = new JSONObject();
        if (book == 1){
            obj.put("success", true);
        } else {
            obj.put("success", false);
        }
        return obj.toString();
    }

    @PostMapping("/getwishlist")
    public String getwishlist(Authentication authentication) {
        User user = ur.findByUsername(authentication.getName()).orElse(null);
        Set<Wishlist> wishlistSet = wrep.findAllByUser(user);
        JSONObject obj = new JSONObject();

        for (Wishlist book : wishlistSet){
            obj.put("book_" + book.getId(), book.getOlId());
        }
        System.out.println(obj.toString());
        return obj.toString();
    }
}
