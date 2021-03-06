package pd.asystentgitarzysty.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Chord {

    private static final List<String> CHORD_TYPES = Arrays.asList("maj", "M", "m", "min",
            "aug", "dim", "add", "sus");
    public static Map<String, String> KEYS = createMap();
    private static final String REGEX = createRegex();

    private String key;
    private String type;
    private String extensions;

    private Chord(String key, String type) {
        this.key = key;
        this.type = type;
        this.extensions = "";
    }

    private Chord(String key, String type, String extensions) {
        this.key = key;
        this.type = type;
        this.extensions = extensions;
    }

    public String getKey() {
        return key;
    }

    private static Map<String, String> createMap(){
        Map<String, String> map = new HashMap<>();
        map.put("A#", "Bb");
        map.put("Bb", "A#");
        map.put("C#", "Db");
        map.put("Db", "C#");
        map.put("D#", "Eb");
        map.put("Eb", "D#");
        map.put("F#", "Gb");
        map.put("Gb", "F#");
        map.put("G#", "Ab");
        map.put("Ab", "G#");
        return map;
    }

    private static String createRegex(){
        StringBuilder regexBuilder = new StringBuilder("[A-H][#b]?(");
        for (int i = 0; i < CHORD_TYPES.size(); i++) {
            regexBuilder.append(CHORD_TYPES.get(i));
            if (i < CHORD_TYPES.size() - 1)
                regexBuilder.append("|");
        }
        regexBuilder.append(")?[1-9#b\\/()]*(\\/[A-H][#b]?)?");
        return regexBuilder.toString();
    }

    static boolean match(String notation){
        return notation.matches(REGEX);
    }

    static Chord parse(String notation){
        if (!match(notation))
            throw new IllegalArgumentException();
        if (notation.length() == 1)
            return new Chord(notation, "");
        String key = notation.substring(0, 2);
        if (!key.contains("#") && !key.contains("b"))
            key = key.substring(0, 1);
        String type = "";
        for (String t : CHORD_TYPES)
            if (notation.contains(t))
                type = t;
        String extensions = notation.substring(key.length() + type.length(), notation.length());
        return new Chord(key, type, extensions);
    }

    @Override
    public String toString(){
        return key + type + extensions;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Chord))
            return false;
        else {
            Chord ch = (Chord) o;
            return this.key.equals(ch.key) && this.type.equals(ch.type)
                    && this.extensions.equals(ch.extensions);
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(key, type, extensions);
    }
}
