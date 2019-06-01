import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SearchByDepAndPrice implements Panelled {

	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JButton searchBtn = new JButton("search");
	
	JLabel depLabel = new JLabel("Department");
    String[] departmentContent = {"","Production","Marketing","HR Management", "Accounting and Finance", "Research and Development"};
	JComboBox<String> depComboBox = new JComboBox<>(departmentContent);
	JLabel priceLabel = new JLabel("Price");
	JTextField priceTextField = new JTextField();
	
	JTable table = new JTable();
	JScrollPane scroller = new JScrollPane(table);
	
	public SearchByDepAndPrice() {
		upPanel.setLayout(new GridLayout(4, 2));
        upPanel.setBorder(new EmptyBorder(50, 50, 0, 50));
        upPanel.add(depLabel);
        upPanel.add(depComboBox);
        upPanel.add(priceLabel);
        upPanel.add(priceTextField);
        
        midPanel.add(searchBtn);
        
        downPanel.add(scroller);
        scroller.setPreferredSize(new Dimension(450, 100));
	}
	
    public ArrayList<JPanel> getPanels() {
        return new ArrayList<>(Arrays.asList(upPanel, midPanel, downPanel));
    }
	
	void clearForm() {
		priceTextField.setText("");
		depComboBox.setSelectedIndex(0);
	}

}
