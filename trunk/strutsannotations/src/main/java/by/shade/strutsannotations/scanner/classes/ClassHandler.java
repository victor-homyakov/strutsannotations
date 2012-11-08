package by.shade.strutsannotations.scanner.classes;

import java.io.File;

/**
 * Incapsulates methods working with classPath and ClassLoader.
 *
 * @author homyakov
 * @version $Id$
 */
public class ClassHandler {

    private static final String DOT_CLASS = ".class";

    private final String classPath;
    private final ClassLoader classLoader;

    public ClassHandler(File classPath, ClassLoader classLoader) {
        this.classPath = classPath.getPath() + File.separator;
        this.classLoader = classLoader;
    }

    public Class<?> toClassDefinition(File file) throws ClassNotFoundException {
        final String name = toQualifiedClassName(file);
        return classLoader.loadClass(name);
    }

    public String toQualifiedClassName(File file) {
        String name = file.getPath();
        int begin = name.startsWith(classPath) ? classPath.length() : 0;
        int end = name.length() - DOT_CLASS.length();
        return name.substring(begin, end).replace(File.separatorChar, '.');
        // if (LOG.isDebugEnabled()) {LOG.debug("File " + name);}
    }

    public boolean hasClassExtension(File file) {
        return file.getName().endsWith(DOT_CLASS);
    }

}
