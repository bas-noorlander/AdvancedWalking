package scripts.advancedwalking.core.io;

import scripts.advancedwalking.core.logging.LogProxy;
import scripts.advancedwalking.network.CommonFiles;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Laniax
 */
public class IOExtensions {

    static LogProxy log = new LogProxy("IOExtensions");

    /**
     * Gets a SHA-1 hash of the file.
     * @param file the file to get the hash from.
     * @return the hash of the file, or an empty string if unsuccessful.
     */
    public static String getSHA1Hash(File file) {

        String result = "";

        try {
            MessageDigest dg = MessageDigest.getInstance("SHA-1");
            InputStream fis = new FileInputStream(file);
            int n = 0;
            byte[] buffer = new byte[8192];
            while (n != -1) {
                n = fis.read(buffer);

                if (n > 0)
                    dg.update(buffer, 0, n);
            }

            result = DatatypeConverter.printHexBinary(dg.digest());
            fis.close();
        } catch(NoSuchAlgorithmException | IOException e) {
            log.error("Failed to generate SHA1 for file.");
        }

        return result;
    }

    /**
     * serialize the object into the file.
     * @param obj the object to serialize
     * @param outputFile the file where the data will be written in
     */
    public static boolean serialize(Object obj, File outputFile) {

        if (!CommonFiles.local_root_dir.isDirectory() && !CommonFiles.local_root_dir.mkdirs()) {
            log.error("Couldnt make directories!");
            return false;
        }

        try {
            OutputStream fos = new FileOutputStream(outputFile);
            OutputStream bos = new BufferedOutputStream(fos);
            ObjectOutput oos = new ObjectOutputStream(bos);

            try {
                oos.writeObject(obj);

                oos.flush();
            } finally {
                oos.close();
                bos.close();
                fos.close();

                return true;
            }

        } catch (IOException e) {
            log.error("Error serializing object to file: %s.", e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deserializes an object from the file.
     * @param inputFile the file to deserialize
     * @return the object which was deserialized
     */
    public static Object deserialize(File inputFile) {

        try {
            InputStream fis = new FileInputStream(inputFile);
            InputStream bis = new BufferedInputStream(fis);
            ObjectInput ois = new ObjectInputStream(bis);

            try {
                return ois.readObject();
            } finally {
                fis.close();
                bis.close();
                ois.close();
            }

        } catch (ClassNotFoundException | IOException | StackOverflowError e) {
            log.error("Error deserializing  object to file: %s.", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


}
