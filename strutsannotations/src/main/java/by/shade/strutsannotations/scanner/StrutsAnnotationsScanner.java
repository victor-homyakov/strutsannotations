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
     * Searches for annotated actions.
     *
     * @param classPath
     *            class path
     * @return list of actions found
     * @throws ClassNotFoundException
     *             on class loader errors
     */
    public static Collection<Class<?>> findActions(final String classPath)
            throws ClassNotFoundException {
        return new ClassScanner(classPath, Thread.currentThread().getContextClassLoader(), "")
                .list(new ActionClassFilter());
    }

    /**
     * Searches for annotated fields.
     *
     * @param clazz
     *            class to search in
     * @return list of fields found
     */
    public static Collection<Field> findFields(final Class<? extends Action> clazz) {
        return new FieldScanner(clazz).list(new ForwardFieldFilter());
    }

}
