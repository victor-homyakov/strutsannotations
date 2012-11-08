package by.shade.strutsannotations.scanner.classes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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
                throw new RuntimeException(e2); // NOPMD
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
        assertEquals("Should find 2 annotated classes", 2, list.size());

        assertPassed(ClassAction.class);
        assertPassed(ClassActions.class);

        assertRejected(ClassNoAction.class);
        assertRejected(ClassActionWithoutAnnotation.class);
        assertRejected(ClassWithAnnotation.class);
    }

    private void assertPassed(Class<?> clazz) {
        assertTrue("List of annotated classes should contain class " + clazz, list.contains(clazz));
    }

    private void assertRejected(Class<?> clazz) {
        assertFalse("List of annotated classes should not contain class " + clazz,
                list.contains(clazz));
    }

}
