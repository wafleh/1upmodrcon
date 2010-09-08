package modrcon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.*;

public class ServerParser {

	public List serverList;
	public Document dom;

        /**
         * This method wipes the servers.xml file and starts over fresh,
         * adding a new single server to the servers.xml file. This method
         * is primarily used by the ServerSetupWizard the first time the user
         * runs the program to add a default server to the database.
         *
         * @param s The single server to add.
         */
        public static void newServer(Server s) {
            String contents = "<servers>\r\n";
            contents += "\t<server>\r\n";
            contents += "\t\t<name>"+s.getName()+"</name>\r\n";
            contents += "\t\t<ip>"+s.getIP()+"</ip>\r\n";
            contents += "\t\t<port>"+s.getPortAsString()+"</port>\r\n";
            contents += "\t\t<logintype>"+s.getLoginType()+"</logintype>\r\n";
            contents += "\t\t<password>"+s.getEncryptedPassword()+"</password>\r\n";
            contents += "\t</server>\r\n";
            contents += "</servers>\r\n";
            try {
                // Will put the file where the 1upmodrcon.properties file exists.
                // Will also create the file if it does not exist!
                FileOutputStream fos = new FileOutputStream("servers.xml");
                OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
                out.write(contents);
                out.close();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


	public ServerParser() {
		//create a list to hold the server objects
		serverList = new ArrayList();
                if (!this.writeDefaultXmlFile()) {
                    System.out.println("Failed to Create Servers.xml File");
                    System.exit(0);
                }
	}

	public void runExample() {

		//parse the xml file and get the dom object
		parseXmlFile();

		//get each employee element and create a Employee object
		parseDocument();

		//Iterate through the list and print the data
		printData();

	}


	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			// the xml file is located in the same place as the 1upmodrcon.properties file.
                        dom = db.parse("servers.xml");


		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

        /**
         * Creates a default servers.xml file with default values.
         *
         * @param file The file to attempt to create.
         * @return True on Success, otherwise False.
         */
        private boolean writeDefaultXmlFile() {
            Writer writer = null;
            File file = new File("servers.xml");
            if (!file.exists()) {
                try {
                    String text = "<servers>\n</servers>\n";
                    writer = new BufferedWriter(new FileWriter(file));
                    writer.write(text);
                    return true;
                }
                catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
                finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    }
                    catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else {
                return true;
            }
        }

	private void parseDocument(){
		//get the root elememt
		Element docEle = dom.getDocumentElement();

		//get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("server");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {

				//get the employee element
				Element el = (Element)nl.item(i);

				//get the Employee object
				Server s = getServer(el);

				//add it to list
				serverList.add(s);
			}
		}
	}

	private Server getServer(Element e) {
		String name = getTextValue(e,"name");
		String ip = getTextValue(e,"ip");
		String port = getTextValue(e,"port");
                String method = getTextValue(e, "logintype");
                String encryptedPassword = getTextValue(e, "password");
                String decryptedPassword = ModRconUtil.decryptString(encryptedPassword);
		//Create a new Server with the values read from the xml nodes
		Server s = new Server(name,ip,port,method,decryptedPassword);
		return s;
	}

	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is name I will return John
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
                return textVal;
	}


	/**
	 * Calls getTextValue and returns a int value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}

	/**
	 * Iterate through the list and print the
	 * content to console
	 */
	private void printData(){

		System.out.println("No of Employees '" + serverList.size() + "'.");

		Iterator it = serverList.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}

        public List getServers() {
            parseXmlFile();
            parseDocument();
            return serverList;
        }

	public static void main(String[] args){
		//create an instance
		ServerParser dpe = new ServerParser();

		//call run example
		dpe.runExample();
	}

        public boolean createBlankDatabase() {
            Writer writer = null;
            File file = new File("servers.db");
            if (!file.exists()) {
                try {
                    String text = "";
                    writer = new BufferedWriter(new FileWriter(file));
                    writer.write(text);
                    return true;
                }
                catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
                finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    }
                    catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else {
                return true;
            }
        }

}

