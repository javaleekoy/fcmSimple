package com.peramdy.mode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by peramdy on 2017/7/5.
 * https://firebase.google.com/docs/cloud-messaging/http-server-ref
 */
public class Notification {


    private Collection<String> multicast;
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, Object> notificationAttributes;



    public Notification() {
        clear();
    }

    /**
     * Convert this object into JSON.
     *
     * @return JSON object adhering to the FCM format.
     */
    public String toJson() {
        JSONObject message = new JSONObject();
        JSONObject na = new JSONObject();
        na.putAll(notificationAttributes);
        message.put("notification", na);
        message.putAll(requestAttributes);

        if (!multicast.isEmpty()) {
            JSONArray ja = new JSONArray();
            ja.addAll(multicast);
            message.put("registration_ids", ja);
        }
        return message.toString();
    }


    /**
     * Clear both multicast and attributes from Notification
     *
     * @return Itself as part of the  Builder Patten
     * @see #clearTargets() and #clearAttributes()
     */
    public Notification clear() {
        clearTargets();
        clearAttributes();
        return this;
    }

    /**
     * Clear all targets from Notification
     *
     * @return Itself as part of the Builder Pattern
     * @see #clear()
     */
    public Notification clearTargets() {
        multicast = new ArrayList<String>();
        return this;
    }

    /**
     * Clear all attributes from Notification
     *
     * @return Itself as part of the Builder Pattern
     * @see #clear()
     */
    public Notification clearAttributes() {
        requestAttributes = new HashMap<String, Object>();
        notificationAttributes = new HashMap<String, Object>();
        return this;
    }

    public Notification addNotificationAttribute(String key, Object value) {
        notificationAttributes.put(key, value);
        return this;
    }

    public Notification addRequestAttribute(String key, Object value) {
        requestAttributes.put(key, value);
        return this;
    }


    /* *********************** */
    /* ** REQUEST ATTRIBUTES * */
    /* *********************** */

    /**
     * 可选，字符串
     * 此参数用于指定消息的接收者
     *
     * @param to Target id
     * @return
     */
    public Notification to(String to) {
        return addRequestAttribute("to", to);
    }

    /**
     * 字符串
     * 数组
     * 此参数用于指定一系列接收多播消息的设备（注册令牌或 ID）。其中必须包含至少 1 个、最多 1000 个注册令牌。
     * 仅可以将此参数用于多播消息传递，而不可以用于单一接收者。仅允许使用 HTTP JSON 格式进行多播消息传递（发送至 1 个以上的注册令牌）。
     *
     * @param registrationIds
     * @return
     */
    public Notification registrationIds(Collection<String> registrationIds) {
        this.multicast = registrationIds;
        return this;
    }


    public Notification addAllMulticasts(Collection<String> targets) {
        this.multicast.addAll(targets);
        return this;
    }

    public Notification addMulticast(String target) {
        this.multicast.add(target);
        return this;
    }

    /**
     * 可选，
     * 字符串
     * 此参数用于指定确定消息目标条件的逻辑表达式。
     * 支持的条件：主题，采用"‘您的主题’在主题中"格式。此值不区分大小写。
     * 支持的运算符：&&、||。每个主题消息最多支持两个运算符
     *
     * @param cond string
     * @return
     */
    public Notification condition(String cond) {
        return addRequestAttribute("condition", cond);
    }

    /**
     * 可选，
     * 字符串
     * 此参数用于指定一组可折叠的消息（例如含有 collapse_key: "Updates Available"），因此当恢复传递时只有最后一条消息被送出。
     * 这是为了避免当设备重新上线或活动时重复发送过多相同的消息（请参阅 delay_while_idle）。
     * 请注意，消息发送顺序并不固定。
     * 注：在任意指定时间内最多允许 4 个不同的折叠密钥。这意味着FCM连接服务器可以为每个客户端应用同时存储 4 条不同的"发送至同步"消息。
     * 如果超出此限值，FCM连接服务器将无法保证保留哪 4 个折叠密钥。
     *
     * @param key
     * @return
     */
    public Notification collapse_key(String key) {
        return addRequestAttribute("collapse_key", key);
    }


    /**
     * 可选，
     * 字符串
     * 设置消息的优先级。有效值为"普通"和"高"。在 iOS 上，这些值相当于 APNs 的 5 至 10 级优先级。
     * 默认以普通优先级发送消息。普通优先级可优化客户端应用的电池消耗，除非需要立即传递，否则应使用普通优先级。
     * 对于普通优先级消息，应用可以接收未指定延迟时间的消息。
     * 当以高优先级发送消息时，将会立即发送消息，应用可能会唤醒睡眠中的设备并打开服务器网络连接。
     *
     * @param p integer
     * @return
     */
    public Notification priority(Integer p) {
        p = Math.min(p, 10);
        p = Math.max(p, 0);
        return addRequestAttribute("priority", p);
    }


    /**
     * 可选，
     * JSON
     * 布尔值	当此参数设置为 true 时，表示只能在设备变为活动状态后发送消息。
     * 默认值为 false
     *
     * @param b boolean
     * @return
     */
    public Notification delay_while_idle(Boolean b) {
        return addRequestAttribute("delay_while_idle", b);
    }


    /**
     * 可选，
     * JSON
     * 布尔值	在
     * iOS 上，使用此字段代表 APNs 负载中的 content-available。
     * 当发送通知或消息且此字段设置为 true 时，将会唤醒处于非活动状态的客户端应用。在 Android 上，
     * 默认由数据消息唤醒应用。Chrome 目前不支持此功能。
     *
     * @param b boolean
     * @return
     */
    public Notification content_available(Boolean b) {
        return addRequestAttribute("content_available", b);
    }

    /**
     * 可选
     * 此参数用于指定当设备离线时消息在FCM存储中保留的时长（单位：秒）。
     * 受支持的最长生存时间为 4 周，默认值为 4 周。如需了解详细信息，请参阅设置消息寿命。
     *
     * @param t
     * @return
     */
    public Notification time_to_live(Integer t) {
        return addRequestAttribute("time_to_live", t);
    }

    /**
     * 可选，
     * 字符串
     * 此参数用于指定应用的数据包名称，其注册令牌必须匹配才能接收消息。
     *
     * @param name string
     * @return
     */
    public Notification restricted_package_name(String name) {
        return addRequestAttribute("restricted_package_name", name);
    }

    /**
     * 可选，
     * JSON
     * 布尔值
     * 此参数设置为 true 时，允许开发者测试请求，无需实际发送消息。
     * 默认值为 false。
     *
     * @param b
     * @return
     */
    public Notification dry_run(Boolean b) {
        return addRequestAttribute("dry_run", b);
    }


    /**
     * 可选，
     * JSON
     * 对象
     * 此参数用于指定消息负载的自定义键值对。
     * （例如，使用 data:{"score":"3x1"}:）
     * 在 iOS 上，如果通过 APNs 发送消息，它代表自定义数据字段。如果通过FCM连接服务器发送，
     * 其在 AppDelegate application:didReceiveRemoteNotification: 中将表示为键值字典。
     * 在 Android 中上，这会产生一个名为 score、字符串值为 3x1 的 Intent extra。
     * 键不能是保留字（"from"或以"google"或"gcm"开头的任何字）。不要使用该表中已确定的任何字（例如 collapse_key）。
     * 推荐使用字符串类型中的值。您必须将对象中的值或其他非字符串数据类型（例如整数或布尔值）转换成字符串。
     *
     * @param data All key-value pairs to be sent in the message
     * @return
     */
    public Notification data(Map<String, Object> data) {
        JSONObject obj = new JSONObject();
        obj.putAll(data);
        return addRequestAttribute("data", obj);
    }


    /**
     * 可选，JSON 对象
     * 此参数用于指定通知负载的用户可见预定义键值对。有关详情，请参阅通知负载支持。有关通知消息和数据消息选项的详细信息。
     *
     * @param map
     * @return
     */
    public Notification notification(Map<String, Object> map) {
        JSONObject obj = new JSONObject();
        obj.putAll(map);
        return addRequestAttribute("notification", obj);
    }


    /* *********************** */
    /* NOTIFICATION ATTRIBUTES */
    /* *********************** */

    /**
     * iOS (watch)、Android
     * 必选(Android)，可选 (iOS)，字符串
     * 指示通知标题。该字段在 iOS 手机和平板电脑上不可见。
     *
     * @param title
     * @return
     */
    public Notification title(String title) {
        return addNotificationAttribute("title", title);
    }

    /**
     * iOS、Android
     * 可选，字符串
     * 指示通知正文
     *
     * @param body
     * @return
     */
    public Notification body(String body) {
        return addNotificationAttribute("text", body);
    }

    /**
     * Android
     * 必选，字符串
     * 指示通知图标。将可绘制资源 myicon 的值设为 myicon。
     *
     * @param ic
     * @return
     */
    public Notification icon(String ic) {
        return addNotificationAttribute("icon", ic);
    }


    /**
     * OS、Android
     * 可选，字符串
     * 指示设备收到通知时要播放的声音。支持 default 或应用中捆绑的声音资源的文件名。
     * Android 声音文件必须位于 /res/raw/ 中，而 iOS 声音文件既可以位于客户端应用的主捆绑包中，也可以位于应用数据容器的 Library/Sounds 文件夹中。如需了解详细信息，请参阅 iOS 开发者内容库
     *
     * @param sound
     * @return
     */
    public Notification sound(String sound) {
        return addNotificationAttribute("sound", sound);
    }

    /**
     * iOS
     * 可选，字符串
     * 指示客户端应用首页图标上的标志。
     *
     * @param badge
     * @return
     */
    public Notification badge(String badge) {
        return addNotificationAttribute("badge", badge);
    }


    /**
     * Android
     * 可选，字符串
     * 指示每一条通知是否会导致在 Android 上的通知抽屉式导航栏中产生新条目。
     * 如未设置此参数，每次请求时将创建一个新的通知。
     * 如果已设置此参数，且已显示带有相同标记的通知，则新通知将替换通知抽屉式导航栏中的现有通知。
     *
     * @param tag
     * @return
     */
    public Notification tag(String tag) {
        return addNotificationAttribute("tag", tag);
    }

    /**
     * Android
     * 可选，字符串
     * 指示图标颜色，以 #rrggbb 格式表示
     *
     * @param color
     * @return
     */
    public Notification color(String color) {
        return addNotificationAttribute("color", color);
    }

    /**
     * iOS、Android
     * 可选，字符串
     * 指示与用户点击通知相关的操作。
     * 如果在 iOS 中设置此参数，则它对应于 APNs 负载中的 category。
     * 在 Android 上，如果设置此参数，则当用户点击通知时，将会启动带有匹配 Intent 过滤器的 Activity。
     *
     * @param click_action
     * @return
     */
    public Notification click_action(String click_action) {
        return addNotificationAttribute("click_action", click_action);
    }

    /**
     * iOS、Android	可选，字符串
     * 指示待本地化的正文字符串对应的键。
     * 在 iOS 中，这对应于 APNs 负载中的"loc-key"。
     * 在 Android 中，当填充此值时将使用应用字符串资源中的键
     *
     * @param body_loc_key
     * @return
     */
    public Notification body_loc_key(String body_loc_key) {
        return addNotificationAttribute("body_loc_key", body_loc_key);
    }


    /**
     * iOS、Android
     * 可选，字符串形式的 JSON 数组
     * 指示要替换待本地化的正文字符串中的格式说明符的字符串值。
     * 在 iOS 中，这对应于 APNs 负载中的"loc-args"。
     * 在 Android 中，这些是字符串资源的格式参数。如需了解详细信息，请参阅格式化和样式设置。
     *
     * @param args
     * @return
     */
    public Notification body_loc_args(Collection<String> args) {
        JSONArray arr = new JSONArray();
        arr.addAll(args);
        return addNotificationAttribute("body_loc_args", arr);
    }

    /**
     * iOS、Android
     * 可选，字符串
     * 指示待本地化的标题字符串对应的键。
     * 在 iOS 中，这对应于 APNs 负载中的"title_loc_key"。
     * 在 Android 中，当填充此值时将使用应用字符串资源中的键。
     *
     * @param key
     * @return
     */
    public Notification title_loc_key(String key) {
        return addNotificationAttribute("title_loc_key", key);
    }

    /**
     * iOS、Android
     * 可选，字符串形式的 JSON 数组
     * 指示要替换待本地化的标题字符串中的格式说明符的字符串值。
     * 在 iOS 中，这对应于 APNs 负载中的"title_loc_args"。
     * 在 Android 中，这些是字符串资源的格式参数。如需了解详细信息，请参阅格式化字符串。
     *
     * @param args
     * @return
     */
    public Notification title_loc_args(Collection<String> args) {
        JSONArray arr = new JSONArray();
        arr.addAll(args);
        return addNotificationAttribute("title_loc_args", arr);
    }


}
