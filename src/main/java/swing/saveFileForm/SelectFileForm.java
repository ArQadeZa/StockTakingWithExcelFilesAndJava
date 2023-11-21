package swing.saveFileForm;

import lombok.Getter;
import utils.saveFileData.SaveFileUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@Getter
public class SelectFileForm extends JFrame {
    private JButton selectExcelFileButton;
    private JPanel panel1;
    private File fileLocation;

    public SelectFileForm() {
        selectExcelFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open file picker
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showSaveDialog(null);

                //set fileLocation to selected file
                fileLocation = jFileChooser.getSelectedFile();

                //recursive method to force file selection
                if (!SaveFileUtilities.setFileLocation(fileLocation)) {
                    actionPerformed(e);
                }
                panel1.setVisible(false);
                System.out.println(SaveFileUtilities.getFileLocation());
            }
        });
    }
}
