package com.lastminute.lastminuteserver.cloudfile.dto;

import com.lastminute.lastminuteserver.utils.MultipartUtil;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UploadFileDto {
    private final String id;
    private final String name;
    private final String format;
    private final String path;
    private final Long bytes;
    private final MultipartFile multipartFile;
    private String uploadedUrl;

    public static UploadFileDto of(MultipartFile multipartFile) {
        final String fileId = MultipartUtil.createFileId();
        final String format = MultipartUtil.getFormat(multipartFile.getContentType());
        final String path = MultipartUtil.cratePath(fileId, format);

        return UploadFileDto.builder()
                .id(fileId)
                .name(multipartFile.getOriginalFilename())
                .format(format)
                .path(path)
                .bytes(multipartFile.getSize())
                .multipartFile(multipartFile)
                .build();
    }

}
