package fr.dawan.spring_rest_api.tools;

import org.springdoc.core.converters.SchemaPropertyDeprecatingConverter;

import java.util.HashMap;
import java.util.Map;

public class TokenSaver {

    /*
    Map utilisé côté serveur pour conserver le token
    Clé: email
    Valeur: Token
     */
    public static Map<String, String> mapTokenByEmail = new HashMap<>();
}
