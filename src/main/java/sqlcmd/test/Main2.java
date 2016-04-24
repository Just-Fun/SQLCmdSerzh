package sqlcmd.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by serzh on 2/1/16.
 */
public class Main2 {

// не помню что это за...
    public static void main(String[] args) throws Exception {
        String cmd = "/usr/bin/pgadmin3";
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(cmd);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));

        String line;
        while ((line = buf.readLine()) != null) {
            System.out.println(line);
        }
    }
}
