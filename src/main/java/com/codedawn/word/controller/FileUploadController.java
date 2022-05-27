package com.codedawn.word.controller;

import com.codedawn.word.entity.SynchronizeRecord;
import com.codedawn.word.mapper.SynchronizeRecordMapper;
import com.codedawn.word.service.SynchronizeRecordService;
import com.codedawn.word.util.ResponseCode;
import com.codedawn.word.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


@RestController
public class FileUploadController {


    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    private static final String filePath="/uploadFile/";


    private SynchronizeRecordService synchronizeRecordService;


    /**
     * 上传文件
     * @param file
     * @param userId
     * @return
     */
    @RequestMapping(value = "/upload")
    public ResponseUtil upload(@RequestParam("file") MultipartFile file, @RequestHeader("userId") String userId) {
        try {
            //文件为空
            if (file.isEmpty()) {
                return ResponseUtil.ensureFailure(ResponseCode.UPLOAD_FAILURE);
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();

            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);
            // 设置文件存储路径
            File f = new File(filePath);
            String path = f.getAbsolutePath() + File.separator + fileName;
            log.info("上传的文件url：" + path);
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            //创建上传记录
            SynchronizeRecord synchronizeRecord = new SynchronizeRecord();
            synchronizeRecord.setTimestamp(System.currentTimeMillis());
            synchronizeRecord.setUserId(userId);
            synchronizeRecord.setUrl(path);
            //写入到数据库中
            SynchronizeRecord record = synchronizeRecordService.selectSynchronizeRecordByOpenId(userId);
            if (record == null) {
                synchronizeRecordService.insertSynchronizeRecord(synchronizeRecord);
            }else {
                record.setUrl(synchronizeRecord.getUrl());
                record.setTimestamp(synchronizeRecord.getTimestamp());
            }
            //上传成功
            return ResponseUtil.ensureSucceed(ResponseCode.UPLOAD_SUCCEED);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseUtil.ensureFailure(ResponseCode.UPLOAD_FAILURE);
    }


    /**
     * 下载文件
     * @param userId
     * @param timestamp
     * @param response
     * @return
     */
    @GetMapping("/download")
    public String downloadFile(@RequestHeader("userId") String userId,@RequestParam("timestamp") Long timestamp,HttpServletResponse response) {


//        if (timestamp == null) {
//            //"没有携带时间戳"
//            return ResponseUtil.ensureFailure(ResponseCode.DOWNLOAD_FAILURE).toJson();
//        }
        //跳过userid查询上传记录
        SynchronizeRecord synchronizeRecord = synchronizeRecordService.selectSynchronizeRecordByOpenId(userId);
        if (synchronizeRecord == null) {
            //"没有上传记录"
            return  ResponseUtil.ensureFailure(ResponseCode.NO_RECORD_DOWNLOAD_FAILURE).toJson();
        }

//        if (synchronizeRecord.getTimestamp() <= timestamp) {
//            return "已经是最新同步";
//        }
//        File f = new File(synchronizeRecord.getUrl());
//        String path = f.getAbsolutePath()+File.separator;
        String fileName = userId+".zip";// 文件名
        //设置文件路径

        File file = new File(synchronizeRecord.getUrl());
        //File file = new File(realPath , fileName);
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("下载成功");
//                return ResponseUtil.ensureSucceed(ResponseCode.DOWNLOAD_SUCCEED).toJson();
//                return "下载成功";
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return "";
        }
        //文件不存在
        return ResponseUtil.ensureFailure(ResponseCode.DOWNLOAD_FAILURE).toJson();
    }
}
