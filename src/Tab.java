import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public abstract class Tab implements Panelled {
    JPanel upPanel = new JPanel();
    JPanel midPanel = new JPanel();
    JPanel searchPanel = new JPanel();
    JPanel downPanel = new JPanel();

    JButton addBtn = new JButton("Add");
    JButton updateBtn = new JButton("Update");
    JButton delBtn = new JButton("Delete");
    JButton searchBtn = new JButton("Search");

    JLabel searchLabel = new JLabel("Search:");
    JTextField searchTField = new JTextField();
    JTable table = new JTable();
    JScrollPane scroller = new JScrollPane(table);

    public Tab() {
        upPanel.setLayout(new GridLayout(5, 2));
        upPanel.setBorder(new EmptyBorder(50, 50, 0, 50));
        //midPanel
        midPanel.add(addBtn);
        midPanel.add(updateBtn);
        midPanel.add(delBtn);
        searchPanel.setLayout(new GridLayout(4, 2));
        searchPanel.add(searchLabel);
        searchPanel.add(searchTField);
        searchPanel.add(searchBtn);
        downPanel.add(scroller);
        scroller.setPreferredSize(new Dimension(450, 100));

    }

    protected void hideIdColumn() {
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
    }

    public ArrayList<JPanel> getPanels() {
        return new ArrayList<>(Arrays.asList(upPanel, midPanel, searchPanel, downPanel));
    }

    abstract void updateCombos();
    abstract void updateTable();
    abstract void clearForm();
}
