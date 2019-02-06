package com.codecool.libarymanagementapi.controller;


import com.codecool.libarymanagementapi.message.request.LoginForm;
import com.codecool.libarymanagementapi.message.response.JwtResponse;
import com.codecool.libarymanagementapi.message.response.ResponseMessage;
import com.codecool.libarymanagementapi.model.User;
import com.codecool.libarymanagementapi.model.Wishlist;
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

    @PostMapping("/onwishlist")
    public String onwishlist(@RequestParam("OLID") String olId, @RequestBody String req) {
        Long user = 0L;
        Wishlist wishlist = wrep.findByOlId(olId);

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
    public String greeting(@RequestParam("OLID") String olId, @RequestBody String req) {
        System.out.println("REQ: " + req);
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
        return obj.toString();
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("OLID") String olId) {
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
        return obj.toString();
    }

    @PostMapping("/getwishlist")
    public String getwishlist(HttpServletRequest request, Authentication authentication) {
        UserPrinciple user2 = (UserPrinciple) authentication.getPrincipal();
        System.out.println(user2);
        System.out.println("auth: " + authentication.getName());
//        String jwt = getJwtFromRequest(request);
//        System.out.println("AUTH NAME: " + jwt);
//        User user2 = (User) authentication.getPrincipal();

//        System.out.println("username " + user2.getName());
        Long user = 0L;
        Set<Wishlist> wishlistSet = wrep.findAllByUserId(user);
        JSONObject obj = new JSONObject();

        for (Wishlist book : wishlistSet){
            obj.put("book_" + book.getId(), book.getOlId());
        }
        System.out.println(obj.toString());
        return obj.toString();
    }

    private String getJwtFromRequest(HttpServletRequest request) {

//        logger.debug("Attempting to get token from request header");

        String bearerToken = request.getHeader("Authorization");
        System.out.println("bearerToken: " + bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
