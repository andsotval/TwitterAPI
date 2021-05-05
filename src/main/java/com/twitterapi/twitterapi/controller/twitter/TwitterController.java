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

import twitter4j.*;

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
    public ResponseEntity<JSONResponse> markTweet(@PathVariable Long id) throws TwitterException {
        TwitterInformation entity = this.twitterService.validateTwitterInformation(id);
        JSONResponse response = new JSONResponse(200,"Ok",entity);

        return ResponseEntity.ok()
                .body(response);
    }

    @RequestMapping("checkTweetsByUser")
    public ResponseEntity<JSONResponse> checkTweetsByUser(@RequestParam(value="user", defaultValue="Test") String user) throws TwitterException {
        List<TwitterInformation> result = this.twitterService.getTwitterInformationByUser(user);

        JSONResponse response = new JSONResponse(200,"Ok",result);

        return ResponseEntity.ok()
                .body(response);

    }

    @RequestMapping("checkHashtag")
    public ResponseEntity<JSONResponse> checkHashtag() throws TwitterException {
        List<Trend> result = this.twitterService.getTwitterByHashtag();

        JSONResponse response = new JSONResponse(200,"Ok",result);

        return ResponseEntity.ok()
                .body(response);
    }
}
