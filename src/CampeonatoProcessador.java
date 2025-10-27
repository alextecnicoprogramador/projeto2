public abstract class CampeonatoProcessador {

    public static String escapeCsv(String s) {
        if(s == null) return "";
        if(s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r")) {
            return "\"" + s.replace("\"","\"\"") + "\"";
        }
        return s;
    }

    public static String unquote(String s) {
        if(s == null) return "";
        s = s.trim();
        if(s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1).replace("\"\"", "\"");
        }
        return s;
    }
}
