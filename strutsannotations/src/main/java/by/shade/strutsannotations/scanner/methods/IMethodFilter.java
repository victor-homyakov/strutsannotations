package by.shade.strutsannotations.scanner.methods;

import java.lang.reflect.Method;

/**
 * Interface for method filters.
 *
 * @author homyakov
 * @version $Id$
 */
public interface IMethodFilter {

    /**
     * @param method
     *            method
     * @return true if method matches criteria
     */
    boolean methodMatches(Method method);

}
