//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.LineNumberReader;
//import java.util.Map;
//import java.util.Properties;
//
//public class Test {
//
//    public static void main(String []args) {
//        System.out.println(System.getProperty("os.name"));
//
//        System.out.println("System.getenv():");
//        Map<String, String> map = System.getenv();
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }
//
//        System.out.println("System.getProperties():");
//        Properties properties = System.getProperties();
//        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }
//
//
//        System.out.println("--------------");
//        System.out.println("exec start ...");
////        String end1 = exceWin("").toString();
////        String end2 = exec("wget 'http://10.33.119.31/cgi/downlog?warname=ehuodiKA&logname=out.log&date=20180518'").toString();
//        String end3 = exceWin("ls ").toString();
//        System.out.println("返回值~~~~~~~~~~~");
////        System.out.println(end1);
////        System.out.println(end2);
//        System.out.println(end3);
//        System.out.println("exec end ...");
//
//    }
//
//    private static String exec(String cmd) {
//
//        return null;
//    }
//
//
//    public static Object execLin(String cmd) {
//        try {
//            String[] cmdA = { "/bin/sh", "-c", cmd };
//            Process process = Runtime.getRuntime().exec(cmdA);
//            LineNumberReader br = new LineNumberReader(new InputStreamReader(
//                    process.getInputStream()));
//            StringBuffer sb = new StringBuffer();
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//                sb.append(line).append("\n");
//            }
//            return sb.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static String exceWin(String cmd) {
//        try {
//            Process process = Runtime.getRuntime().exec(cmd);
//            LineNumberReader br = new LineNumberReader(new InputStreamReader(
//                    process.getInputStream()));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//                sb.append(line).append("\n");
//            }
//            return sb.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
