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
        classHandler = new ClassHandler(new File(""), Thread.currentThread()
                .getContextClassLoader());
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
        assertFQCN("File1", classHandler, "File1.class");
        assertFQCN("package1.File1", classHandler, "package1/File1.class");

        classHandler = new ClassHandler(new File("root"), null);
        assertFQCN("File2", classHandler, "root/File2.class");
        assertFQCN("package2.File2", classHandler, "root/package2/File2.class");
        assertFQCN("package2.subpackage2.File2", classHandler,
                "root/package2/subpackage2/File2.class");

        classHandler = new ClassHandler(new File("root/"), null);
        assertFQCN("File3", classHandler, "root/File3.class");
        assertFQCN("package3.File3", classHandler, "root/package3/File3.class");
        assertFQCN("package3.subpackage3.File3", classHandler,
                "root/package3/subpackage3/File3.class");
    }

    /**
     * Test method for {@link ClassHandler#hasClassExtension(java.io.File)}.
     */
    @Test
    public void testHasClassExtension() {
        assertFalse(classHandler.hasClassExtension(new File("")));
        assertFalse(classHandler.hasClassExtension(new File("File.java")));
        assertTrue(classHandler.hasClassExtension(new File("File.class")));
        assertFalse(classHandler.hasClassExtension(new File("File.class.")));
    }

    private static void assertFQCN(String expected, ClassHandler handler, String path) {
        assertEquals(expected, handler.toQualifiedClassName(new File(path)));
    }

}
