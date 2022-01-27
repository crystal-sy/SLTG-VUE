package com.sltg.common.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sltg.common.config.SltgSysConfig;
import org.apache.commons.lang3.ArrayUtils;
import com.sltg.common.utils.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件处理工具类
 * 
 * @author styra~
 */
public class FileUtils {
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 输出指定文件的byte数组
     * 
     * @param filePath 文件路径
     * @param os 输出流
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     * 
     * @param filePath 文件
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    /**
     * 文件名称验证
     * 
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 检查文件是否可下载
     * 
     * @param resource 需要下载的文件
     * @return true 正常 false 非法
     */
    public static boolean checkAllowDownload(String resource) {
        // 禁止目录上跳级别
        if (StringUtils.contains(resource, "..")) {
            return false;
        }

        // 检查允许下载的文件规则
        return ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, FileTypeUtils.getFileType(resource));
    }

    /**
     * 下载文件名重新编码
     * 
     * @param request 请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
        throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 下载文件名重新编码
     *
     * @param response 响应对象
     * @param realFileName 真实文件名
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName)
        throws UnsupportedEncodingException {
        String percentEncodedFileName = percentEncode(realFileName);

        String contentDispositionValue = "attachment; filename=" + percentEncodedFileName + ";"
            + "filename*=" + "utf-8''" + percentEncodedFileName;
        response.setHeader("Content-disposition", contentDispositionValue);
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }

    /**
     * 获取上传文件名后缀
     *
     * @param file 文件
     * @return 返回文件后缀
     */
    public static String getFileExtension(MultipartFile file) {
        if (file == null) {
            return "";
        }

        String fileName = file.getOriginalFilename();
        if(fileName == null || fileName.lastIndexOf(".") == -1){
            return "";
        }

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 从上传临时文件夹中获取文件到用户目录，并返回文件名称
     *
     * @param fileId 文件id
     * @param targetDir 目标文件夹
     * @return 返回文件名称
     */
    public static String saveFile(String fileId, File targetDir) {
        if (!Strings.isNotBlank(fileId) || targetDir == null) {
            return "";
        }

        String uploadPath = SltgSysConfig.getUploadPath();
        File uploadDir = new File(uploadPath + File.separator + fileId);
        if (!uploadDir.exists() || !uploadDir.isDirectory()) {
            LOGGER.error("The dir is error. fileId:{}", fileId);
            return "";
        }

        File[] files = uploadDir.listFiles();
        File uploadFile = files != null && files.length > 0 ? files[0] : null;
        if (uploadFile == null) {
            LOGGER.error("The dir is empty. fileId:{}", fileId);
            return "";
        }

        // 先删除再创建，保证只有一份文件
        if (targetDir.exists()) {
            targetDir.delete(); // TODO 删除文件夹失败，必须是空文件夹才能删除
        }
        targetDir.mkdirs();

        String uploadFileName = uploadFile.getName();
        File targetFile = new File(targetDir + File.separator + uploadFileName);
        if (uploadFile.renameTo(targetFile)) {
            uploadDir.delete();
            return uploadFileName;
        } else {
            LOGGER.error("remove file is error. fileId:{}", fileId);
            return "";
        }
    }
}
