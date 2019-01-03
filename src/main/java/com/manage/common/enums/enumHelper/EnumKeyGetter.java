package com.manage.common.enums.enumHelper;

public interface EnumKeyGetter<T extends Enum<T>, k> {

    k getKey(T value);

}
