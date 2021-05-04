package com.twitterapi.twitterapi.controller.twitter;

import com.twitterapi.twitterapi.entity.twitter.TwitterInformation;
import com.twitterapi.twitterapi.service.twitter.TwitterService;
import com.twitterapi.twitterapi.util.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import twitter4j.*;

import java.net.URI;
import java.util.List;


@RestController
public class TwitterController {

    @Autowired
    TwitterService twitterService;

    @RequestMapping("checkTweets")
    public ResponseEntity<JSONResponse> checkTweets(@RequestParam(value="search", defaultValue="Test") String search) throws TwitterException {
        List<Status> statusTwitter = this.twitterService.getTwitterSearch(search);

        JSONResponse response = new JSONResponse(200,"Ok",statusTwitter);

        return ResponseEntity.ok()
                .body(response);
    }

    @RequestMapping("markTweet/{id}")
    public String markTweet(@PathVariable Long id) throws TwitterException {
       this.twitterService.validateTwitterInformation(id);


        return "Hello !!";
    }

    @RequestMapping("checkTweetsByUser")
    public ResponseEntity<JSONResponse> checkTweetsByUser(@RequestParam(value="user", defaultValue="Test") String user) throws TwitterException {
        List<TwitterInformation> result = this.twitterService.getTwitterInformationByUser(user);

        JSONResponse response = new JSONResponse(200,"Ok",result);

        return ResponseEntity.ok()
                .body(response);

    }

    @RequestMapping("checkHashtag")
    public ResponseEntity<JSONResponse> checkHashtag(@RequestParam(value="hashtag", defaultValue="Test") String hashtag) throws TwitterException {
        List<Trends> result = this.twitterService.getTwitterByHashtag();

        JSONResponse response = new JSONResponse(200,"Ok",result);

        return ResponseEntity.ok()
                .body(response);
    }
}
