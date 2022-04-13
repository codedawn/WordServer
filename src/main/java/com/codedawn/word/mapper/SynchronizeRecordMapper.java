package com.codedawn.word.mapper;

import com.codedawn.word.entity.SynchronizeRecord;
import org.apache.ibatis.annotations.Param;

public interface SynchronizeRecordMapper {

    public SynchronizeRecord selectSynchronizeRecordByOpenId(String userId);

    public void insertSynchronizeRecord(@Param("synchronizeRecord") SynchronizeRecord synchronizeRecord);

    public void updateSynchronizeRecord(@Param("synchronizeRecord")SynchronizeRecord synchronizeRecord);
}
