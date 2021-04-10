package graphe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cypher {
    public static final Error invalidQuery= new Error("invalid query");

    public static final String
        // Cypher Query Block
        reg_instruction = "(CREATE|MATCH|RETURN|DELETE|DETACH)", reg_valideName = "([A-Za-z]+[0-9]*)",
        reg_key = reg_valideName, reg_value = reg_valideName,                           // (N...)
        reg_leftLink = "(-|<-)",
        reg_rightLink = "(-|->)",
        reg_label = "(: *" + reg_valideName + ")",                                       // (...:TYPE...)
        reg_property = "(" + reg_key + " *: *(" + reg_value + "))",                      // key: value
        reg_properties = "(\\{ *" + reg_property + "(?:, "+reg_property+")* *\\})",      // {key: value, key: value, ..., key: value
        reg_node = "( *\\( *"+reg_valideName+"? *"+reg_label+" *"+reg_properties+"* *\\))",
        reg_rel = "( *\\[ *"+reg_valideName+"? *"+reg_label+" *"+reg_properties+"* *\\])",

        // Cypher Possible Query
        reg_create="("+reg_node+"("+reg_leftLink+reg_rel+reg_rightLink+reg_node+"("+reg_leftLink+reg_rel+reg_rightLink+reg_node+")?"+")?"+")", // CREATE (n:Type {key: val})
        reg_match =" *\\("+reg_valideName+"? *"+reg_label+"? *"+reg_properties+"* *\\)"; // MATCH (n:Type {key: val})

    // ======= METHODS =======
    public static Object query(String q) throws Exception {
        Pattern pattern = Pattern.compile("^ *"+reg_instruction, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(q);

        while( matcher.find() ) {
            String instruction = matcher.group(1).toUpperCase();

            if( instruction.equals("CREATE")) {
                matcher = Pattern.compile(reg_create).matcher(q);

                while( matcher.find() ) {
                    for( int i=0; i<matcher.groupCount(); i++)
                        if(matcher.group(i) != null)
                            System.out.println(""+i+": "+matcher.group(i));

                    String varName_Node1 = matcher.group(3),
                           label_1Node = matcher.group(5),
                           properties_1Node = matcher.group(6),

                           linkVarName = matcher.group(18),
                           leftLink = matcher.group(16),
                           rightLink = matcher.group(30),
                           linkLabel = matcher.group(20),

                           varName_Node2 = matcher.group(32),
                           label_2Node = matcher.group(34),
                           properties_2Node = matcher.group(35),

                           linkVarName2 = matcher.group(47),
                           leftLink2 = matcher.group(45),
                           rightLink2 = matcher.group(59),
                           linkLabel2 = matcher.group(49),

                           varName_Node3 = matcher.group(61),
                           label_3Node = matcher.group(63),
                           properties_3Node = matcher.group(64);

                    Node node1 = new Node(label_1Node, properties_1Node);

                    if( label_2Node != null ){
                        Node node2 = new Node(label_2Node, properties_2Node);
                        int sens;

                        if( leftLink.equals("-") && rightLink.equals("-"))          sens = Link.DUAL;
                        else if( leftLink.equals("<-") && rightLink.equals("-") )   sens = Link.BTOA;
                        else if( leftLink.equals("-") && rightLink.equals("->"))    sens = Link.ATOB;
                        else                                                        throw invalidQuery;

                        node1.link(node2, linkLabel, sens);

                        if( label_3Node != null ) {
                            Node node3 = new Node(label_3Node, properties_3Node);

                            if( leftLink2.equals("-") && rightLink2.equals("-"))          sens = Link.DUAL;
                            else if( leftLink2.equals("<-") && rightLink2.equals("-") )   sens = Link.BTOA;
                            else if( leftLink2.equals("-") && rightLink2.equals("->"))    sens = Link.ATOB;
                            else                                                        throw invalidQuery;

                            node2.link(node3, linkLabel2, sens);
                        }
                    }
                }

            }
            else if( instruction.equals("MATCH") ) {
                matcher = Pattern.compile(reg_match).matcher(q);

                if( matcher.find() ) {
                }
            }
        }

        return null;
    }

}