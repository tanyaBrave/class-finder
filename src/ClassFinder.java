import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassFinder {
    private static List<Object> objects = new ArrayList<>();

    public static List<Object> getObject (String packageName) {

        findClass(packageName.replace(".","/"));
        return objects;

    }

    private static void findClass(String packageName) {

        String packagePath = "./src/main/java/"+ packageName;
        File dir = new File(packagePath);
        File[] files = dir.listFiles();
        for (File file: files){
            if (file.isDirectory()) {
                findClass(packageName +"/"+ file.getName());
            }
            if (file.isFile()) {
                createObject(packageName, file.getName());
            }

        }
    }

    private static void createObject(String packageName, String fileName){

        Object object = null;
        String className = packageName.replace("/",".") + "." + fileName.substring(0,fileName.length()-5);
        try {
            Class<?> myClass = Class.forName(className);
            object = myClass.newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        objects.add(object);
    }

}
