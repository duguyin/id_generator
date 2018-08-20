package com.duguyin.id_generator.impl.converter;

import com.duguyin.id_generator.impl.bean.Id;

public interface IdConverter {

    public long convert(Id id);

    public Id convert(long id);

}