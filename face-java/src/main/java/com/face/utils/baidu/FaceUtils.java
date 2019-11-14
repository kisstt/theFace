package com.face.utils.baidu;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FaceUtils {

    public static String detect(String token, File file) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", Base64Util.encode(FileUtil.readFileByBytes(file)));
            map.put("face_field", "faceshape,facetype,gender,age,beauty,emotion,glasses");
            map.put("image_type", "BASE64");
            String param = GsonUtils.toJson(map);
            String accessToken = token;
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增人脸
     * @param token
     * @param file
     * @param groupId
     * @param userId
     * @return
     */
    public static String add(String token, File file,String groupId,String userId) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image",  Base64Util.encode(FileUtil.readFileByBytes(file)));
            map.put("group_id", groupId);
            map.put("user_id", userId);
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");
            String param = GsonUtils.toJson(map);
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = token;
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String search(String token, File file,String groupIdList,String userId) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", Base64Util.encode(FileUtil.readFileByBytes(file)));
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", groupIdList);
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");
            String param = GsonUtils.toJson(map);
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = token;

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
