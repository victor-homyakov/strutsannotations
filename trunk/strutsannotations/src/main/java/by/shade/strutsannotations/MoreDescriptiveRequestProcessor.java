package by.shade.strutsannotations;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RequestProcessor;

/**
 * Wrapper for RequestProcessor with more descriptive error messages.
 *
 * @author homyakov
 * @version $Id$
 */
public class MoreDescriptiveRequestProcessor extends RequestProcessor {

    @Override
    protected ActionMapping processMapping(HttpServletRequest request,
            HttpServletResponse response, String path) throws IOException {
        ActionMapping result = super.processMapping(request, response, path);
        if (result == null) {
            // params: request.getQueryString()
            /*log.error("Request path: " + request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + request.getContextPath());*/
            log.error("Invalid path was requested. RequestURL: " + request.getRequestURL()
                    + "; RequestURI: " + request.getRequestURI());
        }
        return result;
    }

}
