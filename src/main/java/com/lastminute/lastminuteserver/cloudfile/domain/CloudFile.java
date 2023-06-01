package com.lastminute.lastminuteserver.cloudfile.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.Serializable;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CloudFile implements Serializable {

    public static final long serialVersionUID = 125242L;

    @NotNull
    private final File file;

    @NotNull
    private final String id;

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CloudFile cloudFile = (CloudFile) o;
        return Objects.equals(file, cloudFile.file) && Objects.equals(id, cloudFile.id) && Objects.equals(url, cloudFile.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, id, url);
    }
}
