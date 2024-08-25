package MainLens.FrameToolkit;

import javax.swing.*;
import java.awt.*;

public class OptionContainerPanel extends JPanel{
	public static final String NO_TEXTFIELD = "NoTextField";
    public DetailLabel label;
    public DetailTextField textField;
    public static final Dimension size = new Dimension(OptionContainer.size.width - 10,30);
    public OptionContainerPanel(){
        this.setLayout(null);
        this.setSize(size);
        this.setBackground(Color.white);
        label = new DetailLabel();
        label.setFont(new Font("Sarif",Font.BOLD,20));
        textField = new DetailTextField();
        label.setBounds(5,5,size.width-DetailTextField.size.width,size.height - 5);
        textField.setBounds(size.width-DetailTextField.size.width-5,5,100,size.height-5);
        this.add(label);
        this.add(textField);
    }
    public OptionContainerPanel(String labelText){
        this();
        label.setText(labelText);
    }
    public OptionContainerPanel(String labelText,String textF){
        this();
        if(textF==NO_TEXTFIELD){
            textField.setVisible(false);
            label.setText(labelText);
        }else {
            label.setText(labelText);
            textField.setText(textF);
        }
    }
    public void setLabelText(String s){
        label.setText(s);
    }
    public void setTextFieldText(String s){
        textField.setText(s);
    }

}
