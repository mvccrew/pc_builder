import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartsTab extends Tab {
    JLabel nameLabel = new JLabel("Name:");
    JLabel priceLabel = new JLabel("Price:");
    JLabel typeLabel = new JLabel("Type:");

    JTextField nameTField = new JTextField();
    JTextField priceTField = new JTextField();
    String[] content = {"","CPU","GPU","RAM", "HDD", "Cooling"};
    JComboBox<String> typeCombo = new JComboBox<>(content);

    public PartsTab() {
        upPanel.add(nameLabel);
        upPanel.add(nameTField);
        upPanel.add(priceLabel);
        upPanel.add(priceTField);
        upPanel.add(typeLabel);
        upPanel.add(typeCombo);
        updateTable();
    }

    public int getPartId(String name, String type) {
        int id = -1;
        String sql = "select id from parts where name=? and type=?";
        PreparedStatement state = null;
        Connection conn = DBHelper.getConnection();
        try {
            state = conn.prepareStatement(sql);
            state.setString(1, name);
            state.setString(2, type);
            ResultSet result = state.executeQuery();

            while (result.next()) {
                id = result.getInt("id");
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                state.close();
                conn.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return id;
    }

    public void updateCombos() {} // Няма нужда от тоя

    public void updateTable() {
        table.setModel(DBHelper.getAllModel("parts"));
        hideIdColumn();
    }

    public void clearForm() {
        nameTField.setText("");
        priceTField.setText("");
        typeCombo.setSelectedIndex(0);
    }
}
