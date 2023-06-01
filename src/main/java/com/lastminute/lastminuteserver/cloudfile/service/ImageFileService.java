package com.lastminute.lastminuteserver.cloudfile.service;

import com.lastminute.lastminuteserver.cloudfile.domain.CloudFile;
import com.lastminute.lastminuteserver.cloudfile.dto.UploadFileDto;
import com.lastminute.lastminuteserver.cloudfile.repository.AmazonS3Repository;
import com.lastminute.lastminuteserver.exceptions.S3FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageFileService {

    private final AmazonS3Repository amazonS3Repository;

    public UploadFileDto save(UploadFileDto fileDto) {
        MultipartFile multipart = fileDto.getMultipartFile();
        try {
            CloudFile file = new CloudFile(multipart.getResource().getFile(), fileDto.getId());
            CloudFile savedFile = amazonS3Repository.save(file);
//            return UploadFileDto.of(savedFile.getFile());
        } catch (IOException e) {
            throw new S3FileUploadException("파일이 손상되었습니다.");
        }

        return null;
    }

    public void delete(UploadFileDto fileDto) {
//        fileDto.getId()
//
//        amazonS3Repository.save()
    }
}
