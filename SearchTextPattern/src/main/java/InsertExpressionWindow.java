import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ALEX on 25/11/2015.
 */
public class InsertExpressionWindow extends JDialog{
    private JPanel contentPane;
    private JRadioButton concatenationRadioButton;
    private JRadioButton primitiveRadioButton;
    private JRadioButton joinRadioButton;
    private JRadioButton kleeneLockRadioButton;
    private JButton insertButton;
    private JFormattedTextField expressionLabel;
    public char type;
    boolean kleene;

    public InsertExpressionWindow(String expressionprev,String expressionpos,boolean kleene) {
        setContentPane(contentPane);
        setModal(true);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(concatenationRadioButton);
        buttonGroup.add(primitiveRadioButton);
        buttonGroup.add(joinRadioButton);
        buttonGroup.add(kleeneLockRadioButton);
        expressionLabel.setText(expressionprev+"  "+expressionpos);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setResizable(false);
        setLocationRelativeTo(null);
        kleeneLockRadioButton.setEnabled(kleene);

        concatenationRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertButton.setEnabled(true);
                type = '.';
            }
        });

        primitiveRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertButton.setEnabled(true);
                type = 'p';
            }
        });

        kleeneLockRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertButton.setEnabled(true);
                type = '*';
            }
        });

        joinRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertButton.setEnabled(true);
                type = '+';
            }
        });

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}
