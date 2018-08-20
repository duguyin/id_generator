package com.duguyin.id_generator.impl;

import com.duguyin.id_generator.impl.bean.Id;
import com.duguyin.id_generator.impl.bean.IdType;
import com.duguyin.id_generator.impl.populater.IdPopulator;
import com.duguyin.id_generator.impl.populater.LockIdPopulator;

public class IdServiceImpl extends AbstractIdServiceImpl {

    private static final String SYNC_LOCK_IMPL_KEY = "vesta.sync.lock.impl.key";

    private static final String ATOMIC_IMPL_KEY = "vesta.atomic.impl.key";

    protected IdPopulator idPopulator;

    public IdServiceImpl() {
        super();

        initPopulator();
    }

    public IdServiceImpl(String type) {
        super(type);

        initPopulator();
    }

    public IdServiceImpl(IdType type) {
        super(type);

        initPopulator();
    }

    public void initPopulator() {
        if(idPopulator != null){
            System.out.println("The " + idPopulator.getClass().getCanonicalName() + " is used.");
//        } else if (CommonUtils.isPropKeyOn(SYNC_LOCK_IMPL_KEY)) {
//            System.out.println("The SyncIdPopulator is used.");
//            idPopulator = new SyncIdPopulator();
//        } else if (CommonUtils.isPropKeyOn(ATOMIC_IMPL_KEY)) {
//            System.out.println("The AtomicIdPopulator is used.");
//            idPopulator = new AtomicIdPopulator();
        } else {
            System.out.println("The default LockIdPopulator is used.");
            idPopulator = new LockIdPopulator();
        }
    }

    protected void populateId(Id id) {
        idPopulator.populateId(id, this.idMeta);
    }

    public void setIdPopulator(IdPopulator idPopulator) {
        this.idPopulator = idPopulator;
    }
}