package com.addressbook.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("batch")
public class BatchResource {

    private JobLauncher jobLauncher;
    private Job jsonExportJob;

    @Autowired
    public BatchResource(JobLauncher jobLauncher, Job jsonExportJob) {
        this.jobLauncher = jobLauncher;
        this.jsonExportJob = jsonExportJob;
    }

    @PostMapping("export")
    public void invokeContactsExportJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
        jobLauncher.run(jsonExportJob, jobParameters);
    }
}
