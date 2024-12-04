package hu.nye.util;

import javax.xml.transform.OutputKeys;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.FileNotFoundException;

import hu.nye.board.Board;

public class GameSaver {

    public static void saveBoardToFile(Board board, String fileName) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Root element
            Element rootElement = doc.createElement("board");
            doc.appendChild(rootElement);

            // Add rows to the XML
            char[][] boardState = board.getBoardState();
            for (int i = 0; i < board.getRows(); i++) {
                Element rowElement = doc.createElement("row");
                rowElement.appendChild(doc.createTextNode(new String(boardState[i])));
                rootElement.appendChild(rowElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

            System.out.println("Játékállás elmentve: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Board loadBoardFromFile(String fileName) {
        try {
            File file = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            int[] dimensions = getBoardDimensions(doc);
            int rows = dimensions[0];
            int columns = dimensions[1];

            Element rootElement = doc.getDocumentElement();
            NodeList rowNodes = rootElement.getElementsByTagName("row");
            String[] boardState = new String[rowNodes.getLength()];
            for (int i = 0; i < rowNodes.getLength(); i++) {
                boardState[i] = rowNodes.item(i).getTextContent();
            }

            Board board = new Board(rows, columns);
            board.loadFromStringArray(boardState);
            return board;
        }catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int[] getBoardDimensions(Document doc) {
        Element rootElement = doc.getDocumentElement();
        NodeList rowNodes = rootElement.getElementsByTagName("row");
        int rows = rowNodes.getLength();
        int columns = 0;
        if (rows > 0) {
            columns = rowNodes.item(0).getTextContent().length();
        }
        return new int[] { rows, columns };
    }

    public static void deleteHighScoreTable(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("High score table deleted: " + fileName);
            } else {
                System.out.println("Failed to delete high score table: " + fileName);
            }
        } else {
            System.out.println("High score table not found: " + fileName);
        }
    }
}