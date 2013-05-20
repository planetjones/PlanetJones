package co.uk.planetjones;


import com.sun.org.apache.bcel.internal.classfile.ExceptionTable;

import javax.batch.operations.JobExecutionAlreadyCompleteException;
import javax.batch.runtime.JobExecution;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "BatchJobStartServlet", urlPatterns = BatchJobStartServlet.SERVLET_MAPPING)
public class BatchJobStartServlet extends HttpServlet {

    final static String SERVLET_MAPPING = "/run_batch_job";
    final static String HYPERLINK = "<a href=\"%s\">%s</a>";

    @EJB
    private BatchExecutionBean batchExecutor;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        long executionId;

        if (req.getParameter("detailsForExecutionId") != null) {
            // have a look at what a job is up to
            executionId = Long.valueOf(req.getParameter("detailsForExecutionId"));
            JobExecution execution = batchExecutor.getJobExecutionDetails(executionId);
            this.write("Execution Id \n " + execution, res);

        } else if (req.getParameter("restartExecutionId") != null) {
            // restart a job... ok
            long executionIdToRestart = Long.valueOf(req.getParameter("restartExecutionId"));

                executionId = batchExecutor.restartJob(executionIdToRestart);

                this.write(String.format("Batch execution %d is the result of a restart", executionId), res);

        } else {
            // must be a new job you want
            executionId = batchExecutor.submitJob();
            this.write(String.format("Batch execution %d is running", executionId), res);
        }


        String contextPath = req.getContextPath();

        this.write("<h2>Options</h2>", res);
        this.writeLink(contextPath + SERVLET_MAPPING + "?detailsForExecutionId=" + executionId, "View details for Execution Id " + executionId, res);
        this.writeLink(contextPath + SERVLET_MAPPING + "?restartExecutionId=" + executionId, "Restart Execution Id " + executionId, res);
        this.writeLink(contextPath + SERVLET_MAPPING, "Submit a new job", res);

    }

    private void write(String message, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println(message);
    }


    private void writeLink(String url, String text, HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();
        out.println(String.format(HYPERLINK, url, text));
        out.println("<br/>");
    }
}