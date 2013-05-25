package co.uk.planetjones;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.ejb.Stateless;
import java.util.Properties;

/**
 * Simple EJB to allow JSR-352 batch jobs to be created, restarted and details viewed
 * Complement to blogpost at www.planetjones.co.uk
 */
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
