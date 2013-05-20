package co.uk.planetjones;


import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;

public class SimpleBatchlet implements javax.batch.api.Batchlet {

    @Inject JobContext jobContext;
    @Inject StepContext stepContext;


    @Override
    public String process() throws Exception {

        // here I could copy a file as a precursor to processing it

       long executionId = jobContext.getExecutionId();

        if(isEven(executionId))   {
            throw new NullPointerException("Deliberate fail of job.  I don't like even numbers :)");
        }

        return "SUCCESS";

    }


    private boolean isEven(long num) {
        return (num % 2 == 0);
    }

    @Override
    public void stop() throws Exception {

    }


}
