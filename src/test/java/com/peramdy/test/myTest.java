package com.peramdy.test;

import com.peramdy.mode.Notification;
import com.peramdy.service.FcmMessageService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by peramdy on 2017/7/5.
 */
public class myTest {


    @Test
    public void testOne() {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("Hello", "World!");
        data.put("Marco", "Polo");
        data.put("Foo", "Bar");
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        Notification notification = new Notification();
        notification.registrationIds(list)
                .collapse_key("a_collapse_key")
                .priority(1)
                .delay_while_idle(true)
                .time_to_live(100)
                .restricted_package_name("com.package.name")
                .dry_run(true)
                .data(data)
                .title("Testing")
                .body("Hello World!")
                .color("#ff0000");

        System.out.println(notification.toJson());
    }

    @Test
    public void testTwo(){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("Hello", "World!");
        data.put("Marco", "Polo");
        data.put("Foo", "Bar");
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        Notification notification = new Notification();
        notification.registrationIds(list)
                .collapse_key("a_collapse_key")
                .priority(1)
                .delay_while_idle(true)
                .time_to_live(100)
                .restricted_package_name("com.package.name")
                .dry_run(true)
                .data(data)
                .title("Testing")
                .body("Hello World!")
                .color("#ff0000");

        FcmMessageService.setapiKey("AIzaSyCYmpB1vusSl1GaqxnLujMs3XUqfCCKhIA");
        FcmMessageService.pushMessage(notification);

    }


    @Test
    public void testThree(){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("Hello", "World!");
        data.put("Marco", "Polo");
        data.put("Foo", "Bar");
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        Notification notification = new Notification();
        notification.registrationIds(list)
                .collapse_key("a_collapse_key")
                .priority(1)
                .delay_while_idle(true)
                .time_to_live(100)
                .restricted_package_name("com.package.name")
                .dry_run(true)
                .data(data)
                .title("Testing")
                .body("Hello World!")
                .color("#ff0000");

        FcmMessageService.setNotification(notification);
        FcmMessageService.setapiKey("AIzaSyCYmpB1vusSl1GaqxnLujMs3XUqfCCKhIA");
        FcmMessageService.pushMessage();
    }


}
