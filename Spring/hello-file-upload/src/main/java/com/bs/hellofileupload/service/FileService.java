package com.bs.hellofileupload.service;

import com.bs.hellofileupload.domain.board.Board;
import com.bs.hellofileupload.domain.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    @Value("${resources.location}")
    private String resources_location;

    @Transactional
    public Long save(MultipartFile multipartFile, Board board) throws IOException {
        log.info("type={}",multipartFile.getContentType());
        if(!checkContentType(multipartFile.getContentType())){
            return null;
        }
        String filePath = genreateFilePath(multipartFile);
        com.bs.hellofileupload.domain.file.File imageFile = com.bs.hellofileupload.domain.file.File.builder()
                .originalName(multipartFile.getOriginalFilename())
                .filePath("images/"+filePath)
                .board(board)
                .build();
//        File file = new File( resources_location + filePath);
        return fileRepository.save(imageFile).getId();
    }

    @Transactional
    public Long saveOrUpdate(MultipartFile multipartFile, Board board) throws IOException {
        String filePath  = genreateFilePath(multipartFile);
        com.bs.hellofileupload.domain.file.File findFile = fileRepository.findByBoard_Id(board.getId())
                .map(file -> file.update("images/" + filePath, multipartFile.getOriginalFilename()))
                .orElse(com.bs.hellofileupload.domain.file.File.builder()
                        .originalName(multipartFile.getOriginalFilename())
                        .filePath("images/" + filePath)
                        .board(board)
                        .build());
        return fileRepository.save(findFile).getId();
    }


    private String genreateFilePath(MultipartFile multipartFile) throws IOException {
        String extension = assignmentExtensionType(multipartFile.getContentType());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyyMMdd");
        String current_date = now.format(dateTimeFormatter);
        String uuid = UUID.randomUUID().toString();
        File file = new File(resources_location+current_date);

        if(!file.exists()){
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }
        String new_file_name = uuid+extension;
        file = new File( resources_location+current_date + "/" + new_file_name);
        multipartFile.transferTo(file);
        log.info("file={}", file.getAbsolutePath());
        return current_date + "/" + new_file_name;
    }

    private String assignmentExtensionType(String contentType){
        if(contentType.contains("image/png")){
            return ".png";
        }
        if(contentType.contains("image/gif")){
            return ".gif";
        }
        return ".jpg";
    }

    private Boolean checkContentType(String contentType){
        if(contentType.contains("image/jpeg")||contentType.contains("image/png")||contentType.contains("image/gif")){
            return true;
        }
        return false;
    }



}
