package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by oblige on 11/19/14.
 */
public class ClassScanner {

    public static Set<Class> getClassesInPackage(String packageName) {

        Set<Class> classes = new HashSet<>();
        String packageNameSlashed = packageName.replace(".", "/");
        URL directoryURL = Thread.currentThread().getContextClassLoader().getResource(packageNameSlashed);
        if (directoryURL == null) {
            info("Could not retrieve URL resource: " + packageNameSlashed);
            return classes;
        }

        String directoryString = directoryURL.getFile();
        System.out.println(directoryString);
        if (directoryString == null) {
            info("Could not find directory for URL resource: " + packageNameSlashed);
            return classes;
        }

        File directory = new File(directoryString);
        /*
         * On Linux, directory will exist at the first time. But on windows, we have to
         * do some tweak on the directoryString, replace '/' to '\', replace "%20" to " "
         * So avoid using Chinese chars and blank.
         */
        if (!directory.exists()) {
            // replace some chars
            directory = new File(directoryString.replace("/", "\\").replace("%20", " "));
            // if both are failed, the dir doesnot exist
            if (!directory.exists()) {
                info(packageName + " does not appear to exist as a valid package on the file system.");
                return classes;
            }
        }

        // Get the list of the files contained in the package
        String[] files = directory.list();
        for (String fileName : files) {
            // We are only interested in .class files
            if (fileName.endsWith(".class")) {
                // Remove the .class extension
                fileName = fileName.substring(0, fileName.length() - 6);
                try {
                    classes.add(Class.forName(packageName + "." + fileName));
                } catch (ClassNotFoundException e) {
                    info(packageName + "." + fileName + " does not appear to be a valid class.");
                }
            }
        }

        return classes;
    }

    private static final Log log = LogFactory.getLog(ClassScanner.class);

    private static void info(String message) {
        log.info("[ClassScan]: " + message);
    }
}
