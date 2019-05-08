import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
        table.setModel(DBHelper.getAllModel("parts"));
    }

    public void clearForm() {
        nameTField.setText("");
        priceTField.setText("");
        typeCombo.setSelectedIndex(0);
    }
}
