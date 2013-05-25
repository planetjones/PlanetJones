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

/**
 * Simple servlet to start, restart and view details of JSR-352 Batch Job
 * Complement to blogpost at www.planetjones.co.uk
 */
@WebServlet(name = "BatchJobStartServlet", urlPatterns = BatchJobStartServlet.SERVLET_MAPPING)
public class BatchJobStartServlet extends HttpServlet {

    enum OperatorAction {
        START, RESTART, VIEW;
    }

    final static String SERVLET_MAPPING = "/run_batch_job";

    @EJB
    private BatchExecutionBean batchExecutor;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        OperatorAction action = OperatorAction.valueOf(req.getParameter("action"));

        long executionId = -1;

        if(req.getParameter("executionId") != null) {executionId = Long.valueOf(req.getParameter("executionId"));}

        switch (action) {

            case START:
                executionId = batchExecutor.submitJob();
                this.write(String.format("Batch execution %d is running", executionId), res);
                break;
            case RESTART:
                executionId = batchExecutor.restartJob(executionId);
                this.write(String.format("Batch execution %d is the result of a restart", executionId), res);
                break;
            case VIEW:
                JobExecution execution = batchExecutor.getJobExecutionDetails(executionId);
                this.write("Execution Id \n " + execution, res);
                break;

        }

        String contextPath = req.getContextPath();

        this.write("<h2>Options</h2>", res);
        this.writeLink(OperatorAction.VIEW, "View details for Execution Id " + executionId, executionId, req, res);
        this.writeLink(OperatorAction.RESTART, "Restart Execution Id " + executionId, executionId, req, res);
        this.writeLink(OperatorAction.START, "Start a new job", null, req, res);
    }

    private void write(String message, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println(message);
    }

    private void writeLink(OperatorAction action, String text, Long executionId, HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"").append(req.getContextPath()).append(SERVLET_MAPPING).append("?action=").append(action.name());
        if(executionId != null) {sb.append("&executionId=").append(executionId);}
        sb.append("\">").append(text).append("</a>");
        out.println(sb.toString());
        out.println("<hr/>");
    }
}