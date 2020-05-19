package application.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtil {
    public void save(MultipartFile uploadFile) throws IOException {
        String oriFileName = uploadFile.getOriginalFilename();
        String oriFileExt = oriFileName.substring(oriFileName.lastIndexOf("."));

        String savedFileName = UUID.randomUUID().toString().replace("-", "") + oriFileExt;

        File file = new File("C:\\upload\\temp"+savedFileName);

        uploadFile.transferTo(file);
    }
}
