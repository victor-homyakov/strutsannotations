package by.shade.strutsannotations.scanner.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Filters methods suitable to call in EventDispatchAction.
 *
 * @author homyakov
 * @version $Id$
 */
public class EventDispatchMethodFilter implements IMethodFilter {

    /**
     * The set of argument type classes for the reflected method call in DispatchAction. These are
     * the same for all calls, so calculate them only once.
     */
    private static final Class<?>[] PARAMETER_TYPES = { ActionMapping.class, ActionForm.class,
            HttpServletRequest.class, HttpServletResponse.class };

    /**
     * @param method
     *            method to test
     * @return true for method with signature public ActionForward name(ActionMapping, ActionForm,
     *         HttpServletRequest, HttpServletResponse)
     */
    public boolean methodMatches(final Method method) {
        // test for "public ActionForward method(...)"
        // (method may actually return ? extends ActionForward)
        if (!Modifier.isPublic(method.getModifiers())
                || !ActionForward.class.isAssignableFrom(method.getReturnType())) {
            return false; // NOPMD
        }

        // test for "method(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)"
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != PARAMETER_TYPES.length) {
            return false; // NOPMD
        }

        for (int i = 0; i < parameterTypes.length; i++) {
            if (!parameterTypes[i].isAssignableFrom(PARAMETER_TYPES[i])) {
                return false; // NOPMD
            }
        }

        return true;
    }

}
