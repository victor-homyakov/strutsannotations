package by.shade.strutsannotations.scanner;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.struts.action.Action;

import by.shade.strutsannotations.scanner.classes.ActionClassFilter;
import by.shade.strutsannotations.scanner.classes.ClassScanner;
import by.shade.strutsannotations.scanner.fields.FieldScanner;
import by.shade.strutsannotations.scanner.fields.ForwardFieldFilter;

/**
 * @author homyakov
 * @version $Id$
 */
public final class StrutsAnnotationsScanner {

    private StrutsAnnotationsScanner() {
        // Utility class.
    }

    /**
     * Search for annotated actions.
     *
     * @param classPath
     *            class path
     * @return list of annotated actions found in class loader
     * @throws ClassNotFoundException
     *             on class loader errors
     */
    public static Collection<Class<?>> findActions(final String classPath)
            throws ClassNotFoundException {
        return new ClassScanner(classPath, Thread.currentThread().getContextClassLoader(), "")
                .list(new ActionClassFilter());
    }

    /**
     * Search for annotated fields.
     *
     * @param clazz
     *            class to search in
     * @return list of annotated fields found in class
     */
    public static Collection<Field> findFields(final Class<? extends Action> clazz) {
        return new FieldScanner(clazz).list(new ForwardFieldFilter());
    }

}
