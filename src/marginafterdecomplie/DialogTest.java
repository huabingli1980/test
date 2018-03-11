/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marginafterdecomplie;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author huabing li
 */
public class DialogTest {  
    public static void main(String[] args) {  
        JFrame frame = new JFrame();  
        JButton button = new JButton("button");  
          
        button.addMouseListener((MouseListener) new ShowDialogLintener(frame));  
        frame.add(button,BorderLayout.CENTER);  
        frame.setVisible(true);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.pack();  
    }  
}  
class ShowDialogLintener extends MouseAdapter{  
    JFrame frame;  
    public ShowDialogLintener(JFrame frame) {  
        this.frame = frame;  
    }  
    @Override  
    public void mouseClicked(MouseEvent arg0) {  
        super.mouseClicked(arg0);  
        JFileChooser chooser = new JFileChooser(".");  
        chooser.showOpenDialog(frame);  
        String filePath = chooser.getSelectedFile().getAbsolutePath();  
        System.out.println(filePath);  
    }  
} 
