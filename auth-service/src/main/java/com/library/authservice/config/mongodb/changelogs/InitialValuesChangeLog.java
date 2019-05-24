package com.library.authservice.config.mongodb.changelogs;

import java.util.HashSet;
import java.util.Set;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.library.authservice.domain.AuthClientDetails;
import com.library.authservice.domain.User;
import com.library.authservice.enums.Authorities;

import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeLog
public class InitialValuesChangeLog {

    @ChangeSet(order = "001", id = "001_insertBrowserClientDetails", author = "Ythalo Rossy Saldanha Lira <ythalorossy@gmail.com>")
    public void insertBrowserClientDetails(MongoTemplate mongoTemplate) {
        
        AuthClientDetails browserClientDetails = new AuthClientDetails();
        browserClientDetails.setClientId("browser");
        browserClientDetails.setClientSecret("$2a$10$fWNTd3H.u7G/aNROVQSifebOkZ2xzU5nUPOCI2Ld42M8E25/ljJqK");   //1234
        browserClientDetails.setScopes("ui");
        browserClientDetails.setGrantTypes("refresh_token,password");

        mongoTemplate.save(browserClientDetails);
    }

    @ChangeSet(order = "002", id = "002_insertUserToTestAuthentication", author = "Ythalo Rossy Saldanha Lira <ythalorossy@gmail.com>")
    public void insertUserToTestAuthentication(MongoTemplate mongoTemplate) {
        
        Set<Authorities> authorities = new HashSet<>();
        authorities.add(Authorities.ROLE_USER);

        User user = new User();
        user.setActivated(true);
        user.setAuthorities(authorities);
        user.setPassword("$2a$10$fWNTd3H.u7G/aNROVQSifebOkZ2xzU5nUPOCI2Ld42M8E25/ljJqK");   // 1234
        user.setUsername("randomuser");

        mongoTemplate.save(user);
    }

    @ChangeSet(order = "003", id = "003_insertAccountServiceClientDetails", author = "Ythalo Rossy Saldanha Lira <ythalorossy@gmail.com>")
    public void insertAccountServiceClientDetails(MongoTemplate mongoTemplate) {
        
        AuthClientDetails accountServiceClientDetails = new AuthClientDetails();
        accountServiceClientDetails.setClientId("account-service");
        accountServiceClientDetails.setClientSecret("$2a$10$fWNTd3H.u7G/aNROVQSifebOkZ2xzU5nUPOCI2Ld42M8E25/ljJqK");    //1234
        accountServiceClientDetails.setScopes("server");
        accountServiceClientDetails.setGrantTypes("refresh_token,client_credentials");

        mongoTemplate.save(accountServiceClientDetails);
    }

    @ChangeSet(order = "004", id = "004_insertCategoriesClientDetails", author = "Ythalo Rossy Saldanha Lira <ythalorossy@gmail.com>")
    public void insertCategoriesClientDetails(MongoTemplate mongoTemplate) {
        
        AuthClientDetails accountServiceClientDetails = new AuthClientDetails();
        accountServiceClientDetails.setClientId("categories-service");
        accountServiceClientDetails.setClientSecret("$2a$10$fWNTd3H.u7G/aNROVQSifebOkZ2xzU5nUPOCI2Ld42M8E25/ljJqK");    //1234
        accountServiceClientDetails.setScopes("server");
        accountServiceClientDetails.setGrantTypes("refresh_token,client_credentials");

        mongoTemplate.save(accountServiceClientDetails);
    }

}