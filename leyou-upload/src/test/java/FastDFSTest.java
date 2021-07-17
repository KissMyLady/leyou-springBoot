import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.upload.UploadApp;

import java.io.File;
import java.io.FileInputStream;


@SpringBootTest(classes = UploadApp.class)
@RunWith(SpringRunner.class)
public class FastDFSTest {

    private static final Logger logger = LoggerFactory.getLogger(FastDFSTest.class);

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 文件上传, 获取资源图片路径
     */
    @Test
    public void testUpload() throws Exception{
        //要上传的文件
        File file = new File("H:\\我的文档\\cut_up\\2021-07-17_141457.png");
        StorePath storePath = this.storageClient.uploadFile(
                new FileInputStream(file), file.length(), "png", null
        );

        //带分组的路径
        System.out.println("带分组的路径: "+ storePath.getFullPath());
        //group1/M00/00/00/CoIFVmDywuiAJ-73AAB5r6bSAng697.png

        //不带分组的路径
        System.out.println("不带分组的路径: "+ storePath.getPath());
        //M00/00/00/CoIFVmDywuiAJ-73AAB5r6bSAng697.png
    }

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Test
    public void testUploadAndCreteThumb() throws Exception{
        //要上传的文件
        File file = new File("H:\\我的文档\\cut_up\\111-1111.jpg");
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                new FileInputStream(file), file.length(), "jpg", null
        );

        //带分组的路径
        System.out.println("带分组的路径: "+ storePath.getFullPath());
        //group1/M00/00/00/CoIFVmDywuiAJ-73AAB5r6bSAng697.png

        //不带分组的路径
        System.out.println("不带分组的路径: "+ storePath.getPath());

        //略缩图
        String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
        System.out.println("打印略缩图路径: "+ path);

        /*
        * 带分组的路径:   group1/M00/00/00/CoIFVmDyxI6AaMq8AAGqlodfWtw052.jpg
        * 不带分组的路径: M00/00/00/CoIFVmDyxI6AaMq8AAGqlodfWtw052.jpg
        * 打印略缩图路径: group1/M00/00/00/CoIFVmDyxI6AaMq8AAGqlodfWtw052_60x60.jpg
        *
        *
        * 带分组的路径: group1/M00/00/00/CoIFVmDy10aANvO5AAvw400AlvU156.jpg
          不带分组的路径: M00/00/00/CoIFVmDy10aANvO5AAvw400AlvU156.jpg
          打印略缩图路径: M00/00/00/CoIFVmDy10aANvO5AAvw400AlvU156_600x600.jpg
        * */
    }

}
