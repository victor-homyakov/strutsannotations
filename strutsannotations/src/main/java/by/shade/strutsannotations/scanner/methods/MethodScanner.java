package by.shade.strutsannotations.scanner.methods;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Scans given class for methods matching filter.
 *
 * @author homyakov
 * @version $Id$
 */
public class MethodScanner {

    private final Class<?> clazz;

    /**
     * @param clazz
     *            class to scan
     */
    public MethodScanner(final Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * @param filter
     *            filter
     * @return list of matching methods
     */
    public Collection<Method> list(final IMethodFilter filter) {
        final Method[] methods = clazz.getDeclaredMethods();
        final List<Method> result = new ArrayList<Method>(methods.length);
        for (Method method : methods) {
            if (filter.methodMatches(method)) {
                result.add(method);
            }
        }
        return result;
    }

}
