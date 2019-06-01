import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class SearchByType implements Panelled {

    JPanel upPanel = new JPanel();
    JPanel midPanel = new JPanel();
    JPanel downPanel = new JPanel();

    JButton searchBtn = new JButton("search");

    JLabel partLabel = new JLabel("Part type");
    JLabel compLabel = new JLabel("Computer type");
    String[] partContent = {"","CPU","GPU","RAM", "HDD", "Cooling"};
    String[] compContent = {"","Gaming PC","2 in 1","Super computer"};
    JComboBox<String> partComboBox = new JComboBox<>(partContent);
    JComboBox<String> compComboBox = new JComboBox<>(compContent);

    JTable table = new JTable();
    JScrollPane scroller = new JScrollPane(table);

    public SearchByType() {
        upPanel.setLayout(new GridLayout(4, 2));
        upPanel.setBorder(new EmptyBorder(50, 50, 0, 50));
        upPanel.add(partLabel);
        upPanel.add(partComboBox);
        upPanel.add(compLabel);
        upPanel.add(compComboBox);

        midPanel.add(searchBtn);

        downPanel.add(scroller);
        scroller.setPreferredSize(new Dimension(450, 100));
    }

    public ArrayList<JPanel> getPanels() {
        return new ArrayList<>(Arrays.asList(upPanel, midPanel, downPanel));
    }

    public void updateTable(String partType, String compType) {
        table.setModel(DBHelper.searchByTypeModel(partType, compType));
        table.getColumnModel().getColumn(0).setHeaderValue("Part name");
        table.getColumnModel().getColumn(1).setHeaderValue("Computer name");
    }

    void clearForm() {
        partComboBox.setSelectedIndex(0);
        compComboBox.setSelectedIndex(0);
    }

}
