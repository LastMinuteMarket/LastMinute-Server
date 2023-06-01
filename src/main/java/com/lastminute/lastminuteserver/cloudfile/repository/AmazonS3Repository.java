package com.lastminute.lastminuteserver.cloudfile.repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.lastminute.lastminuteserver.cloudfile.domain.CloudFile;
import com.lastminute.lastminuteserver.exceptions.S3FileException;
import com.lastminute.lastminuteserver.exceptions.S3FileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * ID 값으로 CloudFile.id 를 사용합니다.
 * 즉, S3 resource의 full url이 아니므로 혼동에 주의하세요.
 * full url은 baseUrl/id 입니다.
 * 아직 잘돌아가는지 확인은 못했습니다.. :(
 * @version 1.0
 * @author kimhyeongki
 */
@Repository
@Slf4j
@RequiredArgsConstructor
public class AmazonS3Repository {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.base-url}")
    private String baseUrl;

    private <S extends File> PutObjectRequest createPutObjectRequest(S file) {
        return new PutObjectRequest(bucket, file.getPath(), file)
                .withCannedAcl(CannedAccessControlList.PublicRead);
    }

    private static void makeFileDir(File file) {
        if (!file.exists() && !file.mkdirs()) {
            throw new S3FileException(String.format("file is not exist : %s", file));
        }
    }

    private static void deleteFile(File file) {
        if (!file.exists() && !file.delete()) {
            log.warn(String.format("file is not deleted : %s", file));
        }
    }

    @NonNull
    public <S extends CloudFile> S save(S entity) {
        final File file = entity.getFile();
        makeFileDir(file);

        try {
            amazonS3.putObject(createPutObjectRequest(file));
            entity.setUrl(baseUrl + entity.getId());
            file.deleteOnExit();

            return entity;
        } catch (Exception e) {
            throw new S3FileUploadException(String.format("file [%s] upload to s3 storage failed. nested exception is %s.", file.getName(), e));
        } finally {
            deleteFile(file);
        }
    }

    @NonNull
    public <S extends CloudFile> Iterable<S> saveAll(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), true)
                .parallel()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @NonNull
    public Optional<CloudFile> findById(@NonNull String id) {
        final File file = new File("read", id);
        makeFileDir(file);

        try(OutputStream out = new FileOutputStream(file)) {
            final S3Object s3Object = amazonS3.getObject(bucket, baseUrl + id);
            byte[] bytes = IOUtils.toByteArray(s3Object.getObjectContent());

            if (bytes.length == 0) return Optional.empty();

            out.write(bytes);

            CloudFile cloudFile = new CloudFile(file, id);
            cloudFile.setUrl(baseUrl + id);

            return Optional.of(cloudFile);
        } catch (Exception e) {
            throw new S3FileException(String.format("file can not load for id : %s", id));
        } finally {
            deleteFile(file);
        }
    }

    public boolean existsById(@NonNull String id) {
        return amazonS3.doesObjectExist(bucket, baseUrl + id);
    }

    public void deleteById(@NonNull String key) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));
    }

    public void delete(CloudFile entity) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, entity.getId()));
    }

    public void deleteAllById(@NonNull Iterable<? extends String> ids) {
        StreamSupport.stream(ids.spliterator(), true)
                .parallel()
                .forEach(id -> amazonS3.deleteObject(new DeleteObjectRequest(bucket, id)));
    }

    public void deleteAll(@NonNull Iterable<? extends CloudFile> entities) {
        List<String> ids = StreamSupport.stream(entities.spliterator(), false)
                .map(CloudFile::getId).toList();

        deleteAllById(ids);
    }

}
