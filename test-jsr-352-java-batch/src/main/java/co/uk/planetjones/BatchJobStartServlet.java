package co.uk.planetjones;


import com.sun.org.apache.bcel.internal.classfile.ExceptionTable;

import javax.batch.runtime.JobExecution;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet (name="BatchJobStartServlet", urlPatterns="/run_batch_job")
public class BatchJobStartServlet extends HttpServlet {

    @EJB
    private BatchExecutionBean batchExecutor;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {


        if(req.getParameter("executionId") != null) {

            // have a look at what a job is up to
            long executionId = Long.valueOf(req.getParameter("executionId"));
            JobExecution execution = batchExecutor.getJobExecutionDetails(executionId);
            this.write("Execution Id \n " + execution, res);

        }  else if (req.getParameter("executionIdToRestart") != null) {
             // restart a job... ok
            long executionIdToRestart = Long.valueOf(req.getParameter("executionIdToRestart"));
            long newExecutionId = batchExecutor.restartJob(executionIdToRestart);
            this.write(String.format("Batch execution %d is the result of a restart", newExecutionId), res);
        } else {
            // must be a new job you want
            long executionId = batchExecutor.submitJob();
            this.write(String.format("Batch execution %d is running", executionId), res);
        }

    }

    private void write(String message, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out= res.getWriter();
        out.write(message);


    }
}