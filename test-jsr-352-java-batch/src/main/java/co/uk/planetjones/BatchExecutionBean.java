package co.uk.planetjones;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.annotation.PostConstruct;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.ejb.Stateless;
import javax.ejb.EJB;

@Stateless
public class BatchExecutionBean {

    public long submitJob() {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties jobProperties = new Properties();
        long executionId = jobOperator.start("planetjones-test-batch-job", jobProperties);
        return executionId;
    }

    public JobExecution getJobExecutionDetails(long executionId) {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        return jobExecution;
    }

    public long restartJob(long executionId) {
        Properties jobProperties = new Properties();
        long newExecutionId = BatchRuntime.getJobOperator().restart(executionId, jobProperties);
        return newExecutionId;
    }

}
