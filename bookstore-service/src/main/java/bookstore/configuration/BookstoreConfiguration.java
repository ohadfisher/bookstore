package bookstore.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BookstoreConfiguration {

    @Bean
    //@LoadBalanced //@LoadBalanced- (not just LB also ) it will mark the url as hint for the service that need discover
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

}
