package org.hswebframework.web.bean;

import lombok.Getter;
import org.hswebframework.web.dict.EnumDict;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Getter
public class ClassDescription {
    private final Class<?> type;

    private final boolean collectionType;
    private final boolean arrayType;
    private final boolean enumType;
    private final boolean enumDict;
    private final int fieldSize;
    private final boolean number;

    private final Object[] enums;
    private final Map<String, Field> fields;

    public ClassDescription(Class<?> type) {
        this.type = type;

        collectionType = Collection.class.isAssignableFrom(type);
        enumDict = EnumDict.class.isAssignableFrom(type);
        arrayType = type.isArray();
        enumType = type.isEnum();

        number = Number.class.isAssignableFrom(type);
        if (enumType) {
            enums = type.getEnumConstants();
        } else {
            enums = null;
        }
        Map<String, Field> f = new HashMap<>();
        ReflectionUtils.doWithFields(type, field -> {
            f.put(field.getName(), field);
        });
        fields = Collections.unmodifiableMap(f);
        fieldSize = fields.size();
    }

}
