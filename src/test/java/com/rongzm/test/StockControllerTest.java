package com.rongzm.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by rongzhiming on 2015/5/9.
 */
public class StockControllerTest {
    public static void main(String[] args) throws Exception{
        final String url = "http://localhost:9001/fs/stock/buy/1/1";
        final HttpClientBuilder builder = HttpClientBuilder.create();

        ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 - 1);
        List<Future<String>> results = new ArrayList<Future<String>>();
        long start = System.currentTimeMillis();

        int concurrent = 1000;
        for(int i=0;i<concurrent;i++){
            Future<String> future = exec.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    HttpClient httpClient = builder.build();
                    HttpResponse rsp = httpClient.execute(new HttpGet(url));
                    return EntityUtils.toString(rsp.getEntity());
                }
            });
            results.add(future);
        }
        int suc=0;
        for(Future<String> future: results){
            String result = future.get();
            if(result.equals("true")){
                suc++;
            }
        }
        long cast = System.currentTimeMillis() - start;
        System.out.println("[cast]" + cast);
        System.out.println(suc);
    }
}
