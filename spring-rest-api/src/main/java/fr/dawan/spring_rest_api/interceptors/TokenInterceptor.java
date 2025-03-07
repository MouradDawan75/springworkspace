package fr.dawan.spring_rest_api.interceptors;

import fr.dawan.spring_rest_api.services.JwtService;
import fr.dawan.spring_rest_api.tools.TokenSaver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    private static List<String> allowedUrls = List.of(
            "/api/users/create-account",
            "/login",
            "/api-docs",
            "/api/salaries"
                );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if(!allowedUrls.contains(request.getRequestURI()) && !request.getRequestURI().contains("/swagger")
                && !request.getRequestURI().contains("/api/salaries")){
            //Vérifier le Token

            String header = request.getHeader("Authorization");
            if(header == null || header.length() < 7){

                //throw new Exception("Token invalide.....");
                response.setStatus(403);
                response.getWriter().write("Token invalide....");
                return false;
            }

            String token = header.substring(7); //suppression du Bearer et de l'espace

            //Vérifier si Token à expiré

            if(jwtService.isTokenExpired(token)){
                response.setStatus(403);
                response.getWriter().write("Token invalide....");
                return false;
            }

            //Comparer ce Token à celui conservé dans la Map côté serveur
            String email = jwtService.getUsernameFromToken(token);

            if(!TokenSaver.mapTokenByEmail.containsKey(email) || !TokenSaver.mapTokenByEmail.get(email).equals(token)){
                response.setStatus(403);
                response.getWriter().write("Token invalide....");
                return false;
            }

        }

        return true;
    }
}
