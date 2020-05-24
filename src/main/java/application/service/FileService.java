package application.service;

import application.domain.File;
import application.domain.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    public void insert(MultipartFile uploadFile, String savedFileName) throws SQLException {
        File file = File.builder()
                .oriName(uploadFile.getOriginalFilename())
                .savedName(savedFileName)
                .size(uploadFile.getSize())
                .build();
        fileRepository.save(file);
    }
}