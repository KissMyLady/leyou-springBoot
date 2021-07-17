package top.mylady.upload.service;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;


@Service
public class UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    private static final List<String> CONTENT_TYPES = Arrays.asList(
            "image/jpeg", "image/gif", "image/png", "image/jpg");

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Autowired
    private FastFileStorageClient storageClient;

    public String upload(MultipartFile file){

        String originalFilename = file.getOriginalFilename();

        //校验文件类型
        String contentType = file.getContentType();

        if (!CONTENT_TYPES.contains(contentType)){
            //不合法的文件类型
            logger.warn("警告, 上传的文件类型不合法, 返回null");
            return null;
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage == null){
                logger.warn("警告, 文件内容不合法: "+ originalFilename);
                return null;
            }

            //保存
            String ext = StringUtils.substringAfterLast(originalFilename, ".");

            //上传
            StorePath storePath = this.storageClient.uploadFile(
                    file.getInputStream(), file.getSize(), ext, null);

            //略缩图
            String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
            System.out.println("打印略缩图路径: "+ path);

            //生成url地址
            return "http://139.198.178.12:8888/"+ storePath.getFullPath();

        }
        catch (Exception e){
            System.out.println("程序错误, 原因e: "+ e);
        }
        return null;

    }
}
