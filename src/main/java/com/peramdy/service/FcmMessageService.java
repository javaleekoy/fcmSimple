package com.peramdy.service;

import com.peramdy.mode.Notification;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Created by peramdy on 2017/7/5.
 */
public class FcmMessageService {

    private final static String API_URL = "https://fcm.googleapis.com/fcm/send";
    private static String FIREBASE_SERVER_KEY;
    public static Notification notification;

    static {
        notification = new Notification();
    }

    public static void setapiKey(String apiKey){
        FIREBASE_SERVER_KEY = apiKey;
    }

    public static void setNotification(Notification notification){
        FcmMessageService.notification=notification;
    }

    public static void pushMessage(Notification notification){
        if(FIREBASE_SERVER_KEY == null){
            System.err.println("No Server-Key has been defined !");
            return ;
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost=new HttpPost(API_URL);

        // set header
        httpPost.setHeader("Authorization","key="+FIREBASE_SERVER_KEY);
        httpPost.setHeader("Content-Type","application/json;charset=utf-8");

        //set body
        StringEntity stringEntity=new StringEntity(notification.toJson(),"utf-8");
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response=null;
        try {
            response=httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(response!=null)
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    public static void pushMessage(){
        pushMessage(notification);
    }



}
