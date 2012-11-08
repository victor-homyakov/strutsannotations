package by.shade.strutsannotations.scanner.classes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
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
    private final File classPath = getResourceFile("/");

    private Collection<Class<?>> list;

    public File getResourceFile(String resource) {
        URL url = getClass().getResource(resource);
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e1) {
            // e.printStackTrace();
            try {
                return new File(URLDecoder.decode(url.getPath(), "UTF-8"));
            } catch (UnsupportedEncodingException e2) {
                // this should not happen
                throw new RuntimeException(e2);
            }
        }
    }

    @Before
    public void setUp() throws ClassNotFoundException {
        list = new ClassScanner(classPath, ClassAction.class.getPackage().getName())
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
