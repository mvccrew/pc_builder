import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class PcBuilder extends JFrame{
	
	Connection conn = null;
	PreparedStatement state = null;
	int id = -1;

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	PartsTab parts = new PartsTab();
	ComputersTab computers = new ComputersTab();

	public PcBuilder() {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 1000);

		tabbedPane.add("Parts", getTabContent(parts));
		tabbedPane.add("Computers", getTabContent(computers));
		this.add(tabbedPane);

		parts.addBtn.addActionListener(new AddAction());
		parts.delBtn.addActionListener(new DeleteAction());
		parts.table.addMouseListener(new MouseTableAction());
		parts.searchBtn.addActionListener(new SearchAction());

		computers.addBtn.addActionListener(new AddCompAction());

	}//end constructor

	private static JPanel getTabContent(Tab tab) {
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new GridLayout(4, 1));
		containerPanel.setPreferredSize(new Dimension(1000, 1000));

		for (JPanel panel : tab.getPanels()) {
			containerPanel.add(panel);
		}

		return containerPanel;
	}

	class SearchAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String search = parts.searchTField.getText();
			parts.table.setModel(DBHelper.getFilteredByName(search, "parts"));
			parts.clearForm();
		}
	}
	
	class MouseTableAction implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = parts.table.getSelectedRow();
			id = Integer.parseInt(parts.table.getValueAt(row, 0).toString());
			if(e.getClickCount() > 1) {
				String type = parts.table.getValueAt(row, 3).toString();
				parts.nameTField.setText(parts.table.getValueAt(row, 1).toString());
				parts.priceTField.setText(parts.table.getValueAt(row, 2).toString());
				parts.typeCombo.setSelectedItem(type);
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
			String sql = "delete from parts where id=?";
			conn = DBHelper.getConnection();
			try {
				state = conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				id = -1;
				parts.table.setModel(DBHelper.getAllModel("parts"));
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
			String name = parts.nameTField.getText();
			double price = Double.parseDouble(parts.priceTField.getText());
			String type = parts.typeCombo.getSelectedItem().toString();
			String sql;
			if (DBHelper.getFilteredByName(name, "parts").getRowCount() != 0) {
				sql = "update parts set name=?, price=?, type=? where id=" + id;
			}
			else {
				sql = "insert into parts values (null,?,?,?);";
			}
			
			conn = DBHelper.getConnection();
			try {
				state = conn.prepareStatement(sql);
				state.setString(1, name);
				state.setDouble(2, price);
				state.setString(3, type);
				state.execute();
				id = -1;
				parts.table.setModel(DBHelper.getAllModel("parts"));
				computers.upPanel.revalidate();
				computers.upPanel.repaint();
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
			
			parts.clearForm();
			
		}
		
	}
	class AddCompAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = computers.nameTField.getText();
			int cpuId = parts.getPartId(computers.cpuCombo.getSelectedItem().toString(), "CPU");
			int gpuId = parts.getPartId(computers.gpuCombo.getSelectedItem().toString(), "GPU");
			int ramId = parts.getPartId(computers.ramCombo.getSelectedItem().toString(), "RAM");
			int hddId = parts.getPartId(computers.hddCombo.getSelectedItem().toString(), "HDD");
			int coolingId = parts.getPartId(computers.coolingCombo.getSelectedItem().toString(), "Cooling");
			String type = computers.typeCombo.getSelectedItem().toString();
			double price = computers.getTotalPrice(cpuId, gpuId, ramId, hddId, coolingId);
			/*double price = getPartPrice(computers.cpuCombo.getSelectedItem().toString()) +
					getPartPrice(computers.gpuCombo.getSelectedItem().toString()) + getPartPrice(computers.ramCombo.getSelectedItem().toString()) +
					getPartPrice(computers.hddCombo.getSelectedItem().toString()) + getPartPrice(computers.coolingCombo.getSelectedItem().toString());*/
			String sql = "insert into computers values (null,?,?,?,?,?,?,?,?);";

			conn = DBHelper.getConnection();
			try {
				state = conn.prepareStatement(sql);
				state.setString(1, name);
				state.setDouble(2, price);
				state.setString(3, type);
				state.setInt(4, cpuId);
				state.setInt(5, gpuId);
				state.setInt(6, ramId);
				state.setInt(7, hddId);
				state.setInt(8, coolingId);
				state.execute();
				computers.table.setModel(DBHelper.getAllModel("computers"));
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

			computers.clearForm();

		}

	}

}//end class PcBuilder
