package graphe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cypher {
    public static final String
        // Cypher Query Block
        reg_instruction = "(CREATE|MATCH|RETURN|DELETE|DETACH)", reg_valideName = "([A-Za-z]+[0-9]*)",
        reg_key = reg_valideName, reg_value = reg_valideName, reg_node = reg_valideName, // (N...)
        reg_label = "(: *" + reg_valideName + ")",                                       // (...:TYPE...)
        reg_property = "(" + reg_key + " *: *(" + reg_value + "))",                      // key: value
        reg_properties = "(\\{ *" + reg_property + "(?:, "+reg_property+")* *\\})",      // {key: value, key: value, ..., key: value

        // Cypher Possible Query
        reg_create=" *\\("+reg_node+"? *"+reg_label+" *"+reg_properties+"* *\\)", // CREATE (n:Type {key: val})
        reg_match =" *\\("+reg_node+"? *"+reg_label+"? *"+reg_properties+"* *\\)"; // MATCH (n:Type {key: val})

    public static boolean query(String q) {
        Pattern pattern = Pattern.compile("^ *"+reg_instruction, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(q);
        System.out.println(q);

        if( matcher.find() ) {
            String instruction = matcher.group(1).toUpperCase();

            if( instruction.equals("CREATE")) {
                matcher = Pattern.compile(reg_create).matcher(q);

                if( matcher.find() ) {
                    String label = matcher.group(3),
                           properties = matcher.group(4);

                    // for( int i=0; i<matcher.groupCount(); i++)
                        // System.out.println(""+ i + ": " + matcher.group(i));

                    createNode(label, properties);
                    return true;
                }

                return false;
            }
            else if( instruction.equals("MATCH") ) {
                matcher = Pattern.compile(reg_match).matcher(q);

                if( matcher.find() ) {
                    return true;
                }

                return false;
            } return false;
        } return false;       
    }

    public static Node createNode(String label, String properties) {
        String[] paires = properties.subSequence(1, properties.length()-1)
                          .toString()
                          .replace(" ", "")
                          .split(",");

        Node n = new Node(label);

        for( String paire: paires) {
            String key = paire.split(":")[0],
                   val = paire.split(":")[1];

            n.addProperty(key, val);
        }

        return n;
    }

}