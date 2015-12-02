import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ALEX on 24/11/2015.
 */
public class Window extends JDialog{
    private JPanel contentPane;
    private JButton openFileBtn;
    private JButton newExpressionBtn;
    private JButton executeButton;
    private JPanel resultsFrame;
    private JTextArea results;
    private JFormattedTextField actualExpression;
    private Expression regularExpression;
    FileNameExtensionFilter extensionFilter;
    JFileChooser jFileChooser;
    long cuantity;
    TextExpression textExpression;

    public Window() {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        setLocation(100,100);
        openFileBtn.setEnabled(false);
        cuantity = 0;
        regularExpression=null;
        textExpression = new TextExpression();
        extensionFilter = new FileNameExtensionFilter("TEXT FILES","txt","text");
        actualExpression.setText("r = \"no definida\"");

        newExpressionBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textExpression.after="";
                textExpression.before="";
                regularExpression = getNewExpression(textExpression,true);
                actualExpression.setText("r = " + textExpression.before + textExpression.after);
                openFileBtn.setEnabled(true);
                regularExpression.buildAFN(true);
            }
        });
        openFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(extensionFilter);

                int returnValue = jFileChooser.showOpenDialog(getParent());
                if(returnValue == JFileChooser.APPROVE_OPTION){
                    executeButton.setEnabled(true);
                    cuantity = 0;
                }
                else
                    executeButton.setEnabled(false);
            }
        });
        executeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File file = jFileChooser.getSelectedFile();
                results.setText(null);

                try{
                    BufferedReader in = new BufferedReader(new FileReader(file));
                    String line = null;
                    while ((line = in.readLine()) != null) {
                        for(String part:line.split("\\s+")){
                            String temp = removeStrangeCharacters(part).toLowerCase();
                            temp = temp.replaceAll("\\s+","");
                            if(temp.length()!=0&&regularExpression.start.goNextState(temp)) {
                                results.append(part+"\n");
                                cuantity++;
                            }
                        }
                    }
                    in.close();

                    TitledBorder titledBorder =
                            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(61,59,62)), "Cadenas encontradas " + cuantity, TitledBorder.LEFT,
                                    TitledBorder.DEFAULT_POSITION, actualExpression.getFont(), new Color(61,59,62));
                    resultsFrame.setBorder(titledBorder);
                    cuantity = 0;
                }
                catch(IOException ex)
                {
                    System.err.println("Open plaintext error: "+ex);
                }
            }
        });
    }

    public static String removeStrangeCharacters(String input) {
        String original = "·‡‰ÈËÎÌÏÔÛÚˆ˙˘uÒ¡¿ƒ…»ÀÕÃœ”“÷⁄Ÿ‹—Á«";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        String empty = " ";
        String notWanted = "\".,|?ø!°\\/:;-_<>¥'";
        for (int i=0; i<original.length(); i++)
            output = output.replace(original.charAt(i), ascii.charAt(i));
        for (int i=0; i<notWanted.length();i++)
            output = output.replace(notWanted.charAt(i),empty.charAt(0));
        return output;
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.setSize(590, 750);
        window.setVisible(true);

        System.exit(0);
    }

    public boolean isKleene(Expression expression){
        if(expression.type=='*')
            return false;
        if(expression.type=='p')
            return true;
        if(expression.type=='.')
            return isKleene(expression.expression1)||isKleene(expression.expression2);
        return isKleene(expression.expression1)&&isKleene(expression.expression2);
    }

    public Expression getNewExpression(TextExpression textExpression,boolean kleene){

        char data,type;

        InsertExpressionWindow insertexpressionWindow = new InsertExpressionWindow(textExpression.before,textExpression.after,kleene);
        insertexpressionWindow.setSize(250,210);
        insertexpressionWindow.setVisible(true);

        type=insertexpressionWindow.type;
        insertexpressionWindow.dispose();

        Expression expression;
        int auxiliar=1;

        switch (type){
            case '*':
                expression = new KleeneLock();
                textExpression.before = textExpression.before+"(";
                textExpression.after = ")*"+textExpression.after;
                expression.expression1 = getNewExpression(textExpression,false);
                break;
            case '.':
                expression = new Concatenation();
                textExpression.before = textExpression.before+"(";
                textExpression.after = ").(_)"+textExpression.after;
                expression.expression1 = getNewExpression(textExpression,true);
                while(auxiliar !=0) {
                    if(textExpression.after.charAt(auxiliar)==')') {
                        textExpression.after = textExpression.after.substring(1);
                        textExpression.before += ")";
                    }
                    else if(textExpression.after.charAt(auxiliar)=='*'){
                        textExpression.after = textExpression.after.substring(2);
                        textExpression.before += ")*";
                    }
                    else
                        auxiliar=0;
                }
                textExpression.after=textExpression.after.substring(4);
                textExpression.before = textExpression.before+").(";
                expression.expression2 = getNewExpression(textExpression,isKleene(expression.expression1)||kleene);
                break;
            case '+':
                expression = new Join();
                textExpression.before = textExpression.before+"(";
                textExpression.after = ")+(_)"+textExpression.after;
                expression.expression1 = getNewExpression(textExpression,kleene);
                while(auxiliar !=0) {
                    if(textExpression.after.charAt(auxiliar)==')') {
                        textExpression.after = textExpression.after.substring(1);
                        textExpression.before += ")";
                    }
                    else if(textExpression.after.charAt(auxiliar)=='*'){
                        textExpression.after = textExpression.after.substring(2);
                        textExpression.before += ")*";
                    }
                    else
                        auxiliar=0;
                }
                textExpression.after=textExpression.after.substring(4);
                textExpression.before = textExpression.before+")+(";
                expression.expression2 = getNewExpression(textExpression,kleene);
                break;
            case 'p':
                int temporal=0;
                String temp = JOptionPane.showInputDialog("Inserte un sÌmbolo");
                if(temp!=null){
                    temp = temp.replaceAll("\\s+","");
                    temporal= temp.length();
                    temp = removeStrangeCharacters(temp);
                    temp = temp.replaceAll("\\s+","");
                }

                while(temp==null||temp.length()==0||temp.length()!=temporal){
                    temp = JOptionPane.showInputDialog("Inserte un sÌmbolo");
                    if(temp!=null){
                        temp = temp.replaceAll("\\s+","");
                        temporal= temp.length();
                        temp = removeStrangeCharacters(temp);
                        temp = temp.replaceAll("\\s+","");
                    }
                }
                temp=temp.toLowerCase();
                data=temp.charAt(0);
                textExpression.before = textExpression.before+(data);
                expression = new Primitive(data);
                break;
            default:
                expression = null;
        }
        return expression;
    }
}
