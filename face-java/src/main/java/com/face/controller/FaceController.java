package com.face.controller;

import com.alibaba.fastjson.JSONObject;
import com.face.WebConstants;
import com.face.config.BaiduConfig;
import com.face.service.IFaceService;
import com.face.service.IUserService;
import com.face.utils.baidu.FaceAuth;
import com.face.utils.baidu.FaceUtils;
import com.face.vo.ReturnData;
import com.face.vo.StatusCode;
import com.face.vo.UserVo;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
@RequestMapping("/api/face")
public class FaceController {

    @Resource
    private IFaceService faceService;

    @Value("${image.dir}")
    public String IMAGE_DIR;

    @Value("${upload.dir}")
    public String UPLOAD_URL;

    @Value("${image.url}")
    public String IMAGE_URL;

    @Autowired
    private BaiduConfig baiduConfig;

    private static String GROUP_ID = "repository";

    @Autowired
    private IUserService userService;

    public static long getFileSize(File file) {
        if (file.exists() && file.isFile()) {
            String fileName = file.getName();
            return file.length()/1024;
        }
        return 0;
    }

    @RequestMapping("/detect")
    @ResponseBody
    public ReturnData detect(@RequestParam("imgFile") MultipartFile multipartFile, HttpSession session) throws Exception {
        String tokon = FaceAuth.getAuth(baiduConfig.APP_KEY, baiduConfig.APP_SERCET_KEY);
        UserVo userVo = (UserVo) session.getAttribute("user");
        String fileDir = UPLOAD_URL + "face/" + userVo.getUserId();
        File imgDir = new File(fileDir);
        if (!imgDir.exists()) imgDir.mkdir();
        File file = new File(fileDir + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        File fromPic=file;
        String toFileName=UPLOAD_URL + "face/"+multipartFile.getOriginalFilename();
        if(toFileName.endsWith("."))toFileName=toFileName.substring(0,toFileName.length()-1);
        File toPic=new File(toFileName);
        if (getFileSize(fromPic)>500){
            Thumbnails.of(fromPic).scale(0.2f).toFile(toPic);//按比例缩小
        }else {
            Thumbnails.of(fromPic).scale(0.9f).toFile(toPic);
        }
        String res = FaceUtils.detect(tokon, toPic);
        JSONObject json = JSONObject.parseObject(res);
        return new ReturnData(StatusCode.SUCCESS, "", json);
    }


    @RequestMapping("/add")
    @ResponseBody
    public ReturnData add(@RequestParam("imgFile") MultipartFile multipartFile, HttpSession session) throws Exception {
        String tokon = FaceAuth.getAuth(baiduConfig.APP_KEY, baiduConfig.APP_SERCET_KEY);
        UserVo userVo = (UserVo) session.getAttribute("user");
        String fileDir = UPLOAD_URL + "face/" + userVo.getUserId();
        File imgDir = new File(fileDir);
        if (!imgDir.exists()) imgDir.mkdir();
        File file = new File(fileDir + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        File fromPic=file;
        String toFileName=UPLOAD_URL + "face/"+multipartFile.getOriginalFilename();
        if(toFileName.endsWith("."))toFileName=toFileName.substring(0,toFileName.length()-1);
        File toPic=new File(toFileName);
        if (getFileSize(fromPic)>500){
            Thumbnails.of(fromPic).scale(0.2f).toFile(toPic);//按比例缩小
        }else {
            Thumbnails.of(fromPic).scale(0.9f).toFile(toPic);
        }
        String res = FaceUtils.add(tokon, toPic, GROUP_ID, userVo.getUserId().toString());
        JSONObject json = JSONObject.parseObject(res);
        return new ReturnData(StatusCode.SUCCESS, "", json);
    }

    @RequestMapping("/search")
    @ResponseBody
    public UserVo search(@RequestParam("imgFile") MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tokon = FaceAuth.getAuth(baiduConfig.APP_KEY, baiduConfig.APP_SERCET_KEY);
        String fileDir = UPLOAD_URL + "face/search";
        File imgDir = new File(fileDir);
        if (!imgDir.exists()) imgDir.mkdir();
        File file = new File(fileDir + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        File fromPic=file;
        String toFileName=UPLOAD_URL + "face/"+multipartFile.getOriginalFilename();
        if(toFileName.endsWith("."))toFileName=toFileName.substring(0,toFileName.length()-1);
        File toPic=new File(toFileName);
        if (getFileSize(fromPic)>500){
            Thumbnails.of(fromPic).scale(0.2f).toFile(toPic);//按比例缩小
        }else {
            Thumbnails.of(fromPic).scale(0.9f).toFile(toPic);
        }
        String res = FaceUtils.search(tokon, toPic, GROUP_ID, null);
        JSONObject json = JSONObject.parseObject(res);
        if (json.containsKey("result")) {
            json = json.getJSONObject("result");
            if (json.containsKey("user_list")) {
                String userId = "";
                if (((JSONObject) (json.getJSONArray("user_list").get(0))).containsKey("user_id"))
                    userId = ((JSONObject) (json.getJSONArray("user_list").get(0))).getString("user_id");
                UserVo userVo = new UserVo();
                userVo.setUserId(Long.parseLong(userId));
                UserVo realUser = userService.qryUserByUserId(userVo);
                HttpSession session = request.getSession();
                String sessionId = session.getId();
                userService.loginRedisOps(realUser, sessionId);
                response.addHeader(WebConstants.USER_INFO_KEY, "sessionId=" + session.getId());
                return realUser;
            }
            return null;
        } else {
            return null;
        }
    }
}
