package fr.dawan.spring_rest_api.controllers;

import fr.dawan.spring_rest_api.CentreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.util.Properties;


@RestController
@RequestMapping("/api/properties")
public class PropertiesController {

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private CentreProperties centreProperties;

    @PostMapping(value = "/update/{key}/{value}", produces = "text/plain")
    public String updateProperty(@PathVariable("key") String key, @PathVariable("value") String value) throws Exception{
        MutablePropertySources propertySources = environment.getPropertySources();
        Properties props = new Properties();
        props.put(key,value);

        PropertiesPropertySource pSource = new PropertiesPropertySource("dynamicProps", props);
        propertySources.addFirst(pSource);
        return "Propriété MAJ: "+key+" = "+value;
    }

    @GetMapping(value = "/read/{key}", produces = "text/plain")
    public String getProperty(@PathVariable("key") String key) throws Exception{
        Properties p = new Properties();
        FileInputStream fis = new FileInputStream("login.properties");
        p.load(fis);
        System.out.println(p.getProperty("user"));
        System.out.println(p.getProperty("password"));
        fis.close();
        return key+" = "+environment.getProperty(key);
    }

    @GetMapping("/centre")
    public CentreProperties getCentreProperties() throws Exception{
        return centreProperties;
    }
}
