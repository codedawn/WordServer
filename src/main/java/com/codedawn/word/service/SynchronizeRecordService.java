package com.codedawn.word.service;

import com.codedawn.word.entity.SynchronizeRecord;
import com.codedawn.word.mapper.SynchronizeRecordMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SynchronizeRecordService {

    @Autowired
    private SynchronizeRecordMapper synchronizeRecordMapper;

    public SynchronizeRecord selectSynchronizeRecordByOpenId(String userId){
        return synchronizeRecordMapper.selectSynchronizeRecordByOpenId(userId);
    }

    public void insertSynchronizeRecord(SynchronizeRecord synchronizeRecord){
        synchronizeRecordMapper.insertSynchronizeRecord(synchronizeRecord);
    }

    public void updateSynchronizeRecord(SynchronizeRecord synchronizeRecord){
        synchronizeRecordMapper.updateSynchronizeRecord(synchronizeRecord);
    }
}
