package graphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Element {
    private static ArrayList<Element> instances = new ArrayList<Element>();
    private static int nbInstances = 0;

    private String label;
    private HashMap<String, String> properties = new HashMap<String, String>();
    private int id;

    public Element(String label) {
        this.label = label;
        this.id = nbInstances;

        instances.add(this);
        nbInstances++;
    }

    public static boolean query(String q) {
        String reg_instruction = "(CREATE|MATCH|RETURN|DELETE|DETACH)",
               reg_valideName  = "([A-Za-z]+[0-9]*)",
               reg_key         = reg_valideName,
               reg_value       = reg_valideName,
               reg_node        = reg_valideName,                      // (N...)
               reg_label       = "(: *"+reg_valideName+")",           // (...:TYPE...)
               reg_property    = "("+reg_key+" *: *("+reg_value+"))", // key: value
               reg_properties  = "(\\{ *"+reg_property+"* *\\})";     // {key: value, key: value, ..., key: value}

               // CREATE (n:Type {key: val})
        String reg_create=" *\\("+reg_node+"? *"+reg_label+" *"+reg_properties+"* *\\)",
               // MATCH (n:Type {key: val})
               reg_match =" *\\("+reg_node+"? *"+reg_label+"? *"+reg_properties+"* *\\)";

        Pattern pattern = Pattern.compile("^ *"+reg_instruction, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(q);

        System.out.println(q);
        if( matcher.find() ) {
            String instruction = matcher.group(1).toUpperCase();

            if( instruction.equals("CREATE")) {
                matcher = Pattern.compile(reg_create).matcher(q);

                if( matcher.find() ) {
                    System.out.println("CREATE QUERY");
                    for( int i=0; i<matcher.groupCount(); i++)
                        System.out.println(""+ i + ": " + matcher.group(i));

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

    public void setLabel(String label) {
        this.label = label;
    }

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public int getId() {
        return id;
    }

    public String getLabel(){
        return label;
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public HashMap<String, String> getPorperties(){
        return properties;
    }

    public static ArrayList<Element> getIntances(){
        return instances;
    }
}