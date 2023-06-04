package com.lastminute.lastminuteserver.common;

import com.lastminute.lastminuteserver.exceptions.EnumNotFoundException;

public interface Convertable {
    Object getConvertKey();

    static <E extends Convertable> E findByKey(Object key, Class<E> enumClass) {
        if (key == null) {
            return null;
        }
        for (E enumValue : enumClass.getEnumConstants()) {
            if (key.equals(enumValue.getConvertKey())) {
                return enumValue;
            }
        }
        throw new EnumNotFoundException("key에 해당하는 enum을 찾을 수 없습니다. : " + key);
    }
}
