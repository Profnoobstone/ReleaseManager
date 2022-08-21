import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        String location = new File("").getAbsolutePath();
        File folder = new File(location);
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for(File file: listOfFiles){
            if(file.getName().endsWith(".jar"))
                procesFile(file);
        }
    }

    //copies file / gets information for copying file
    public static void procesFile(File file){
        String location = file.getAbsolutePath();
        location = location.substring(0, location.lastIndexOf("\\")) + getDate() + file.getName();
        location = String.format("%s%s.jar",location.substring(0,location.lastIndexOf(".jar")),getVersion());
        Path newLocation = Path.of(location);
        new File(location).mkdirs();
        try {
            Files.copy(file.getAbsoluteFile().toPath(), newLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //returns current data
    public static String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("\\dd-MM-yyyy\\");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    //returns the user input
    public static String getVersion(){
        String version = "";
        try {
            System.console();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Please insert new version: ");
            version = "" + br.readLine();
            if (version.equals("")) return getVersion();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return version;
    }
}
