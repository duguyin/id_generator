package com.duguyin.id_generator.impl;

import com.duguyin.id_generator.factory.IdMeta;
import com.duguyin.id_generator.factory.IdMetaFactory;
import com.duguyin.id_generator.impl.bean.Id;
import com.duguyin.id_generator.impl.bean.IdType;
import com.duguyin.id_generator.impl.converter.IdConverter;
import com.duguyin.id_generator.impl.converter.IdConverterImpl;
import com.duguyin.id_generator.impl.provider.MachineIdProvider;
import com.duguyin.id_generator.service.IdService;
import com.duguyin.id_generator.util.TimeUtils;

import java.util.Date;

public abstract class AbstractIdServiceImpl implements IdService {


    protected long machineId = -1;
    protected long genMethod = 0;
    protected long type = 0;
    protected long version = 0;

    protected IdType idType;
    protected IdMeta idMeta;

    protected IdConverter idConverter;

    protected MachineIdProvider machineIdProvider;

    public AbstractIdServiceImpl() {
        idType = IdType.MAX_PEAK;
    }

    public AbstractIdServiceImpl(String type) {
        idType = IdType.parse(type);
    }

    public AbstractIdServiceImpl(IdType type) {
        idType = type;
    }

    public void init() {
        this.machineId = machineIdProvider.getMachineId();

        if (machineId < 0) {


            throw new IllegalStateException(
                    "The machine ID is not configured properly so that Vesta Service refuses to start.");

        }
        if(this.idMeta == null){
            setIdMeta(IdMetaFactory.getIdMeta(idType));
            setType(idType.value());
        } else {
            if(this.idMeta.getTimeBits() == 30){
                setType(0);
            } else if(this.idMeta.getTimeBits() == 40){
                setType(1);
            } else {
                throw new RuntimeException("Init Error. The time bits in IdMeta should be set to 30 or 40!");
            }
        }
        setIdConverter(new IdConverterImpl(this.idMeta));
    }

    public long genId() {
        Id id = new Id();

        id.setMachine(machineId);
        id.setGenMethod(genMethod);
        id.setType(type);
        id.setVersion(version);

        populateId(id);

        long ret = idConverter.convert(id);

        // Use trace because it cause low performance

        return ret;
    }

    protected abstract void populateId(Id id);

    public Date transTime(final long time) {
        if (idType == IdType.MAX_PEAK) {
            return new Date(time * 1000 + TimeUtils.EPOCH);
        } else if (idType == IdType.MIN_GRANULARITY) {
            return new Date(time + TimeUtils.EPOCH);
        }

        return null;
    }


    public Id expId(long id) {
        return idConverter.convert(id);
    }

    public long makeId(long time, long seq) {
        return makeId(time, seq, machineId);
    }

    public long makeId(long time, long seq, long machine) {
        return makeId(genMethod, time, seq, machine);
    }

    public long makeId(long genMethod, long time, long seq, long machine) {
        return makeId(type, genMethod, time, seq, machine);
    }

    public long makeId(long type, long genMethod, long time,
                       long seq, long machine) {
        return makeId(version, type, genMethod, time, seq, machine);
    }

    public long makeId(long version, long type, long genMethod,
                       long time, long seq, long machine) {
        IdType idType = IdType.parse(type);

        Id id = new Id(machine, seq, time, genMethod, type, version);
        IdConverter idConverter = new IdConverterImpl(idType);

        return idConverter.convert(id);
    }


    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public void setGenMethod(long genMethod) {
        this.genMethod = genMethod;
    }

    public void setType(long type) {
        this.type = type;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setIdConverter(IdConverter idConverter) {
        this.idConverter = idConverter;
    }

    public void setIdMeta(IdMeta idMeta) {
        this.idMeta = idMeta;
    }

    public void setMachineIdProvider(MachineIdProvider machineIdProvider) {
        this.machineIdProvider = machineIdProvider;
    }
}