package fr.dawan.spring_rest_api.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

    @Async
    public void methodVoid() throws Exception{

        Thread.sleep(2000);
        System.out.println(">>>> méthode void......");
    }

    @Async
    public CompletableFuture<String> methodWithReturn() throws Exception{

        Thread.sleep(2000);
        CompletableFuture<String> resultat = CompletableFuture.completedFuture("résultat final.....");
        return resultat;

    }
}
