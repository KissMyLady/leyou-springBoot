package top.mylady.upload.ctrl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.mylady.upload.service.UploadService;
import top.mylady.utils.dtos.AppHttpCodeEnum;
import top.mylady.utils.dtos.ResponseResult;


@RestController
@RequestMapping("/upload")
public class UpLoadService {

    @Autowired
    private UploadService uploadService;

    /**
     * 图片上传
     */
    @PostMapping("/image")
    public ResponseResult<String> uploadImage(@RequestParam("file") MultipartFile file){
        String url = this.uploadService.upload(file);

        if(StringUtils.isBlank(url)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        return ResponseResult.okResult(url);
    }
}
