package graphe;

import java.util.ArrayList;
import java.util.HashMap;
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
        reg_match =" *\\("+reg_valideName+"? *"+reg_label+"? *"+reg_properties+"* *\\)", // MATCH (n:Type {key: val})
        reg_return = reg_valideName;

    // ======= METHODS =======
    public static Object query(String q) throws Exception {
        Pattern pattern = Pattern.compile("^ *"+reg_instruction, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(q);
        ArrayList<String> cachedVar = new ArrayList<String>();
        HashMap<String, ArrayList<Element>> cache = new HashMap<String, ArrayList<Element>>();

        while( matcher.find() ) {
            String instruction = matcher.group(1).toUpperCase();

            if( instruction.equals("CREATE")) {
                matcher = Pattern.compile(reg_create).matcher(q);
                create_query(matcher, cachedVar, cache);
            }
            else if( instruction.equals("MATCH") ) {
                matcher = Pattern.compile(reg_match).matcher(q);
                match_query(matcher, cachedVar, cache);
            }
            else if( instruction.equals("RETURN") ) {
                matcher = Pattern.compile(reg_return).matcher(q);
                return return_query(matcher, cachedVar, cache);
            }
        }

        return null;
    }

    public static void match_query(Matcher matcher, ArrayList<String> cachedVar, HashMap<String, ArrayList<Element>> cache){
        System.out.println("=====MATCH");
        ArrayList<String>
        nodeVarNames = new ArrayList<String>(),
        nodeLabels = new ArrayList<String>(),
        nodeProperties = new ArrayList<String>(),
        linkVarNames = new ArrayList<String>(),
        linkLabels = new ArrayList<String>(),
        linkOrientations = new ArrayList<String>(),
        linkProperties = new ArrayList<String>();
                 
        while( matcher.find() ) {
            // init
            for(int i=0; i<3; i++){
                nodeVarNames.add( matcher.group(3 + i*29) );
                nodeLabels.add( matcher.group(5 + i*29) );
                nodeProperties.add( matcher.group(6 + i*29) );

                linkVarNames.add( matcher.group(18 + i*29) );
                linkLabels.add( matcher.group(20 + i*29) );
                linkProperties.add( matcher.group( i*29 ) );
                linkOrientations.add( matcher.group(16 + i*29)+matcher.group(30 + i*29) ); // "<--" "--" "-->"
            }

            for(int i=0; i<3; i++) {
                String s_nodeLabel = nodeLabels.get(i),
                       s_nodeVar = nodeVarNames.get(i);

                if( s_nodeLabel.isEmpty() ) break;

                if( !s_nodeVar.isEmpty() ) {
                    // TODO: replace findNodesByLabel bay Node.findNodes(label, properties) once it is finish
                    ArrayList<Element> arr = Node.findNodesByLabel(s_nodeLabel);

                    if( cache.containsKey(s_nodeLabel) )
                        cache.replace(s_nodeVar, arr);
                    else
                        cache.put(s_nodeVar, arr);
                }
            }
        }
    }

    public static void create_query(Matcher matcher, ArrayList<String> cachedVar, HashMap<String, ArrayList<Element>> cache){
        System.out.println("=====CREATE");
        while( matcher.find() ) {
            //for( int i=0; i<matcher.groupCount(); i++)
                //if(matcher.group(i) != null)
                    //System.out.println(""+i+": "+matcher.group(i));

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

    public static Element[] return_query(Matcher matcher, ArrayList<String> cachedVar, HashMap<String, ArrayList<Element>> cache) {
        System.out.println("=====RETURN");
        ArrayList<Element> elements = new ArrayList<Element>();

        return (Element[]) elements.toArray();
    }
}