package by.shade.strutsannotations.scanner.classes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import by.shade.strutsannotations.scanner.classes.resources.ClassAction;
import by.shade.strutsannotations.scanner.classes.resources.ClassActionWithoutAnnotation;
import by.shade.strutsannotations.scanner.classes.resources.ClassActions;
import by.shade.strutsannotations.scanner.classes.resources.ClassNoAction;
import by.shade.strutsannotations.scanner.classes.resources.ClassWithAnnotation;

/**
 * @author serafimovich
 * @version $Id$
 */
public class ActionClassFilterTest {
    private final String classPath = getClass().getResource("/").getPath();
    private final String classPathWithoutSlash = classPath.substring(0, classPath.lastIndexOf("/"));
    private Collection<Class<?>> list;

    @Before
    public void setUp() throws ClassNotFoundException {
        list = new ClassScanner(classPathWithoutSlash, Thread.currentThread()
                .getContextClassLoader(), ClassAction.class.getPackage().getName())
                .list(new ActionClassFilter());
    }

    @Test
    public void testFilter() {
        assertTrue(list.size() == 2);

        assertPassed(ClassAction.class);
        assertPassed(ClassActions.class);

        assertRejected(ClassNoAction.class);
        assertRejected(ClassActionWithoutAnnotation.class);
        assertRejected(ClassWithAnnotation.class);
    }

    private void assertPassed(Class<?> annotation) {
        assertTrue(list.contains(annotation));
    }

    private void assertRejected(Class<?> annotation) {
        assertFalse(list.contains(annotation));
    }

}
