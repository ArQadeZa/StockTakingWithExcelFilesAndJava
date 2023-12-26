package swing.datePicker;

import runner.Runner;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DatePicker extends JPanel {

    private final JSpinner spinner;
    private final JLabel label;

    public DatePicker() {
        setLayout(new GridBagLayout());

        label = new JLabel("Date: ");
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        add(label, constraints);

        SpinnerDateModel model = new SpinnerDateModel();
        spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(editor);
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(spinner, constraints);
    }

    public LocalDate getSelectedDate() {
        return ((Date) spinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 150);
    }
}