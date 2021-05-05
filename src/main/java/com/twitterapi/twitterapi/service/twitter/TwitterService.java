package com.twitterapi.twitterapi.service.twitter;

import com.twitterapi.twitterapi.entity.twitter.Location;
import com.twitterapi.twitterapi.entity.twitter.TwitterInformation;
import com.twitterapi.twitterapi.repository.twitter.TwitterInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.api.TrendsResources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TwitterService {

    static final Integer NUM_TWEETS_FOR_PERSIST = 15000;
    static final String LANGUAGE_AVAILABLES_TWEET_FOR_PERSIST = "en|es|it";
    static final Integer LIMIT_HASHTAG = 10;
    @Autowired
    TwitterInformationRepository twitterInformationRepository;

    public List<Status> getTwitterSearch(String text) throws TwitterException {
        List<Status> resultStatus = new ArrayList<Status>();

        // The factory instance is re-useable and thread safe.
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query(text);
        QueryResult result = twitter.search(query);

        for (Status status : result.getTweets()) {
            if(status.getUser().getFollowersCount()>NUM_TWEETS_FOR_PERSIST && status.getLang().matches(LANGUAGE_AVAILABLES_TWEET_FOR_PERSIST)){
                System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
                this.persistTwitter(status);
            }
            resultStatus.add(status);
        }
        return resultStatus;
    }

    public void persistTwitter(Status status){
        TwitterInformation twitterModel = new TwitterInformation();

        twitterModel.setUser(status.getUser().getScreenName());
        twitterModel.setText(status.getText());
        Location location = new Location();

        if(status.getGeoLocation() != null){

            location.setLongitude(status.getGeoLocation().getLongitude());
            location.setLatitude(status.getGeoLocation().getLatitude());
        }
        twitterModel.setLocation(location);

        this.twitterInformationRepository.save(twitterModel);

    }

    public TwitterInformation validateTwitterInformation(Long id) {
        Optional<TwitterInformation> entity = this.twitterInformationRepository.findById(id);
        TwitterInformation result = null;
        if(!entity.isEmpty()){
            result = entity.get();
            result.setValidation(true);
            this.twitterInformationRepository.save(result);
        }
        return result;
    }

    public List<TwitterInformation> getTwitterInformationByUser(String user) {
        return this.twitterInformationRepository.findAllByUser(user);

    }

    public List<Trend> getTwitterByHashtag() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        TrendsResources result = twitter.trends();
        return Arrays.stream(result.getPlaceTrends(1).getTrends()).collect(Collectors.toList()).subList(0,LIMIT_HASHTAG);
    }
}
