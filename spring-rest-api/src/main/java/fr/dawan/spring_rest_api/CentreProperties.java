package fr.dawan.spring_rest_api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "centre")
public class CentreProperties {

    private String name;
    private String url;
}
