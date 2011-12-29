package by.shade.strutsannotations.scanner.classes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

/**
 * @author homyakov
 * @version $Id$
 */
public class ClassHandlerTest {

    private ClassHandler classHandler;

    @Before
    public void setUp() {
        classHandler = new ClassHandler("", Thread.currentThread().getContextClassLoader());
    }

    /**
     * Test method for {@link ClassHandler#toClassDefinition(java.io.File)}.
     *
     * @throws ClassNotFoundException
     *             on errors
     */
    @Test
    public void testToClassDefinition() throws ClassNotFoundException {
        Class<?> klass = classHandler.toClassDefinition(new File("java/io/File.class"));
        assertNotNull(klass);
        assertEquals("java.io.File", klass.getCanonicalName());
        assertEquals("File", klass.getSimpleName());
    }

    /**
     * Test method for {@link ClassHandler#toQualifiedClassName(java.io.File)}.
     */
    @Test
    public void testToQualifiedClassName() {
        assertFQCN("File", classHandler, "File.class");
        assertFQCN("package.File", classHandler, "package/File.class");

        classHandler = new ClassHandler("root", null);
        assertFQCN("File", classHandler, "root/File.class");
        assertFQCN("package.File", classHandler, "root/package/File.class");
        assertFQCN("package.subpackage.File", classHandler, "root/package/subpackage/File.class");
    }

    /**
     * Test method for {@link ClassHandler#hasClassExtension(java.io.File)}.
     */
    @Test
    public void testHasClassExtension() {
        assertFalse(classHandler.hasClassExtension(new File("")));
        assertFalse(classHandler.hasClassExtension(new File("File.java")));
        assertTrue(classHandler.hasClassExtension(new File("File.class")));
    }

    private static void assertFQCN(String expected, ClassHandler handler, String path) {
        assertEquals(expected, handler.toQualifiedClassName(new File(path)));
    }

}
