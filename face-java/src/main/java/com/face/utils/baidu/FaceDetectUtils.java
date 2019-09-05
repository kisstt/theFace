package com.face.utils.baidu;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class FaceDetectUtils {


    /**
     * 对传入的base64字符串处理
     *
     * @param base64
     */
    public void detect(String base64) {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        Map<String, Object> map = new HashMap<>();
        map.put("image", "");
        map.put("face_field", "faceshape,facetype");
        map.put("image_type", "FACE_TOKEN");
        String param = (String) JSON.toJSON(map);
        String accessToken = BaiduAuthUtils.getAuth();

    }


    /**
     * 对传入的文件处理
     *
     * @param file
     */
    public void detect(File file) {


    }
}
