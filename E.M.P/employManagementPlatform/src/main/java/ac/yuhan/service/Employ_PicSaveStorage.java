package ac.yuhan.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Employ_PicSaveStorage {
    @Value("${upload.directory}")
    private String uploadDirectory;

    public void saveFile(MultipartFile uploadFile) {
        try {
            // 현재 실행 중인 위치에 파일 저장
        	String filePath = uploadDirectory + uploadFile.getOriginalFilename();

    		uploadFile.transferTo(new File(filePath));
    		
        } catch (Exception e) {
            e.printStackTrace();
            
            // 예외 처리: 파일 저장 중 에러 발생
        }
        
    }
}
