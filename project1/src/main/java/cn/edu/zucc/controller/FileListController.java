package cn.edu.zucc.controller;


import cn.edu.zucc.common.R;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.edu.zucc.common.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/files")
public class FileListController {

    private static final Logger log = LoggerFactory.getLogger(FileListController.class);

    @PostMapping("/upload")
    @ResponseBody
    public Map<String,String> uoload(@RequestParam("file") MultipartFile file) throws IOException{
        log.info("[文件类型]-[{}]",file.getContentType());
        log.info("[文件名称]-[{}]",file.getOriginalFilename());
        log.info("[文件大小]-[{}]",file.getSize());
        //文件上传的目录
        file.transferTo(new File("C:\\Users\\cqn\\Desktop\\Java EE\\J2EE信管实验\\实验七\\FileUpload"+file.getOriginalFilename()));
        Map<String, String> result = new HashMap<>(16);
        result.put("contentType",file.getContentType());
        result.put("fileName",file.getOriginalFilename());
        result.put("fileSize",file.getSize()+"");
        return  result;

        //Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);

    }

    @PostMapping("/getFileList")
    @ResponseBody
    public List<String> getFileList(){
        File file  = new File("C:\\Users\\cqn\\Desktop\\Java EE\\J2EE信管实验\\实验七");
        List<String> list = new ArrayList<String>();
        for(int i = 0; i<file.list().length; i++ ){
            list.add(file.list()[i]);
        }
        return list;
    }

    @PostMapping("/deleteFileByName")
    @ResponseBody
    public R<String> deleteFileByName(){
        File file = new File("C:\\Users\\cqn\\Desktop\\Java EE\\J2EE信管实验\\实验七\\FileUpload");
        if(!file.exists()){
            return R.fail("文件不存在");
        }else {
            file.delete();
            return R.success("删除成功");
        }
    }


}
