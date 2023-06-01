package com.lastminute.lastminuteserver.user.service;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.user.domain.ForbiddenName;
import com.lastminute.lastminuteserver.user.dto.ForbiddenNameDto;
import com.lastminute.lastminuteserver.user.repository.ForbiddenNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForbiddenNameService {

    private final ForbiddenNameRepository forbiddenNameRepository;

    public ForbiddenNameDto createForbiddenName(ForbiddenNameDto request) {
        if (isForbiddenName(request.name())) {
            throw RequestException.of(RequestExceptionCode.FORBIDDEN_NAME_ALREADY_EXIST);
        }

        ForbiddenName forbiddenName = request.toEntity();
        forbiddenName = forbiddenNameRepository.save(forbiddenName);

        return ForbiddenNameDto.of(forbiddenName);
    }

    public void deleteForbiddenName(String name) {
        ForbiddenName forbiddenName = forbiddenNameRepository.findById(name)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.FORBIDDEN_NAME_NOT_FOUND));

        forbiddenNameRepository.delete(forbiddenName);
    }

    public boolean isForbiddenName(String name) {
        return forbiddenNameRepository.findById(name).isPresent();
    }

}
