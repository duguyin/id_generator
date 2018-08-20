package com.duguyin.id_generator.impl.populater;


import com.duguyin.id_generator.factory.IdMeta;
import com.duguyin.id_generator.impl.bean.Id;

public interface IdPopulator {

    void populateId(Id id, IdMeta idMeta);

}
