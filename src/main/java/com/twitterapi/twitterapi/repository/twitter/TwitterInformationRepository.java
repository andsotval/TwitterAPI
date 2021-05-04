package com.twitterapi.twitterapi.repository.twitter;

import com.twitterapi.twitterapi.entity.twitter.TwitterInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TwitterInformationRepository extends CrudRepository<TwitterInformation,Long> {

    List<TwitterInformation> findAllByUser(String user);
}
