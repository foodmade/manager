package com.manage;

import com.manage.job.AutoAccountingJob;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JobTest extends BaseTest {

    @Autowired
    AutoAccountingJob autoAccountingJob;

    @Test
    public void testAutoAccountJob(){
        autoAccountingJob.execute();
    }
}
