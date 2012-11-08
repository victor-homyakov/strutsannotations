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
     * @return true for method with signature
     *         <code>public ActionForward name(ActionMapping, ActionForm,
     *         HttpServletRequest, HttpServletResponse)</code>
     */
    public boolean methodMatches(Method method) {
        return checkVisibility(method) && checkReturnType(method) && checkParameters(method);
    }

    /**
     * @return true for public method: <code>public {type} {name}({params})</code>
     */
    private static boolean checkVisibility(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    /**
     * @return true for method with {@link ActionForward} return type (method may actually return ?
     *         extends ActionForward): <code>{visibility} ActionForward {name}({params})</code>
     */
    private static boolean checkReturnType(Method method) {
        return ActionForward.class.isAssignableFrom(method.getReturnType());
    }

    /**
     * @return true for method with 4 desired parameters: <code>{visibility} {type} {name}
     *         (ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)</code>
     */
    private static boolean checkParameters(Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != PARAMETER_TYPES.length) {
            return false;
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!parameterTypes[i].isAssignableFrom(PARAMETER_TYPES[i])) {
                return false;
            }
        }
        return true;
    }

}
