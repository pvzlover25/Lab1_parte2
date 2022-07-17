package Auxiliar;
import java.util.*;

public class Extensiones {
    public static final Set<String> extensionesTexto=extensiones();
    private Extensiones(){}
    private static Set<String> extensiones(){
        HashSet<String> ret=new HashSet();
        ret.add("txt");
        ret.add("dic");
        ret.add("exc");
        ret.add("idx");
        ret.add("log");
        ret.add("scp");
        ret.add("wtx");
        ret.add("c");
        ret.add("cpp");
        ret.add("java");
        ret.add("h");
        ret.add("py");
        ret.add("diz");
        return Collections.unmodifiableSet(ret);
    }
}
