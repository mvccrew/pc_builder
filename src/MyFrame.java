import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MyFrame extends JFrame{
	
	Connection conn = null;
	PreparedStatement state = null;
	int id = -1;
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JButton addBtn = new JButton("Add");
	JButton delBtn = new JButton("Delete");
	JTable table = new JTable();
	JScrollPane scroller = new JScrollPane(table);
	
	JLabel nameLabel = new JLabel("Name:");
	JLabel priceLabel = new JLabel("Price:");
	JLabel typeLabel = new JLabel("Type:");
	
	JTextField nameTField = new JTextField();
	JTextField priceTField = new JTextField();
	String[] content = {"","CPU","GPU","RAM", "HDD", "Cooling"};
	JComboBox<String> typeCombo = new JComboBox<>(content);
	
	public MyFrame() {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setLayout(new GridLayout(3, 1));
		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);
		
		//upPanel
		upPanel.setLayout(new GridLayout(5, 2));
		upPanel.add(nameLabel);
		upPanel.add(nameTField);
		upPanel.add(priceLabel);
		upPanel.add(priceTField);
		upPanel.add(typeLabel);
		upPanel.add(typeCombo);
		upPanel.setBorder(new EmptyBorder(50, 50, 0, 50));
		//midPanel
		midPanel.add(addBtn);
		midPanel.add(delBtn);
		addBtn.addActionListener(new AddAction());
		delBtn.addActionListener(new DeleteAction());
		//downPanel
		downPanel.add(scroller);
		scroller.setPreferredSize(new Dimension(450, 100));
		table.setModel(DBConnector.getAllModel());
		table.addMouseListener(new MouseTableAction());
	}//end constructor
	
	class MouseTableAction implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			id = Integer.parseInt(table.getValueAt(row, 0).toString());
			if(e.getClickCount() > 1) {
				nameTField.setText(table.getValueAt(row, 1).toString());
				String type = table.getValueAt(row, 5).toString();
				typeCombo.setSelectedItem(type);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class DeleteAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String sql = "delete from staff where id=?";
			conn = DBConnector.getConnection();
			try {
				state = conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				id = -1;
				table.setModel(DBConnector.getAllModel());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		
	}
	
	class AddAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = nameTField.getText();
			int price = Integer.parseInt(priceTField.getText());
			String type = typeCombo.getSelectedItem().toString();
			String sql = "insert into parts values (null,?,?,?);";
			
			conn = DBConnector.getConnection();
			try {
				state = conn.prepareStatement(sql);
				state.setString(1, name);
				state.setInt(2, price);
				state.setString(3, type);
				state.execute();
				table.setModel(DBConnector.getAllModel());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			clearForm();
			
		}
		
	}//end AddAction
	
	private void clearForm() {
		nameTField.setText("");
		priceTField.setText("");
		typeCombo.setSelectedIndex(0);
	}

}//end class MyFrame
