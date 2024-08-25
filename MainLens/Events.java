package MainLens;

import MainLens.Exceptions.NegativesNotAllowedException;
import MainLens.Exceptions.OutOfRangeException;
import MainLens.GeometricalOptics.Lens.Lens;
import MainLens.GeometricalOptics.Specimen.ObjectSpecimen;
import MainMirror.MainFrame;
import MainMirror.MyException1;
import MainMirror.Thraed;
import MainMirror.Thraed1;
import Run.FrontPage;

//import javax.lang.model.util.ElementScanner14;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class Events implements ActionListener, ChangeListener, KeyListener, MouseListener,MouseMotionListener {
    Visualizer v;
    Info in;
    double temp1;
    double new_dis;
    double diff=0;
    MyException1 me = new MyException1();
    static ChangeSlide cs;
//    public boolean flag = true;
    public Events(Visualizer visualizer){
        this.v = visualizer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==v.concaveButton){
        	v.titlePanel.label.setText("Concave Lens");
            v.isConcaveRunning = true;
            v.concaveButton.setVisible(false);
            v.concaveButton.setEnabled(false);
            v.convexButton.setEnabled(true);
            v.convexButton.setVisible(true);

            v.p.setLens(new Lens(-30));
            v.p.getLens().setR((-1)*(v.p.getLens().getR()));

            v.frame.panel.cur_slider.setMinimum(-100);
            v.frame.panel.cur_slider.setMaximum(0);
            v.frame.panel.cur_slider.setValue(-30);
            
            v.calculationPanel.focalLengthSlider.removeChangeListener(this);
            v.calculationPanel.focalLengthSlider.setMinimum(-50);
            v.calculationPanel.focalLengthSlider.setMaximum(0);
            v.calculationPanel.focalLengthSlider.setValue(-30);
            v.calculationPanel.focalLengthSlider.addChangeListener(this); 
            v.calculationPanel.setDetails();
        }
        else if(e.getSource()==v.infoButton){
            in=new Info();
            cs=new ChangeSlide(this);
            cs.setLocation(0,in.getHeight());
            in.job();
        }
        else if(e.getSource()==v.convexButton){
        	v.titlePanel.label.setText("Convex Lens");
            v.isConcaveRunning = false;
            v.convexButton.setVisible(false);
            v.convexButton.setEnabled(false);
            v.concaveButton.setEnabled(true);
            v.concaveButton.setVisible(true);

            v.p.setLens(new Lens(30));
            v.p.getLens().setR((-1)*(v.p.getLens().getR()));

            v.frame.panel.cur_slider.setMinimum(0);
            v.frame.panel.cur_slider.setMaximum(100);
            v.frame.panel.cur_slider.setValue(30);

            v.calculationPanel.focalLengthSlider.setMinimum(0);
            v.calculationPanel.focalLengthSlider.setMaximum(50);
            v.calculationPanel.focalLengthSlider.setValue(30);
            v.calculationPanel.setDetails();
        }
        else if(e.getSource()==v.calculationPanel.objectDistance.textField){
            try {
                double temp = Double.parseDouble(v.calculationPanel.objectDistance.textField.getText());
                if(Math.abs(temp)>v.limitOfScale ){
                    JOptionPane.showMessageDialog(null,"This Application has some limitations.\nSo beyond 10000 limit Image transformation can't be shown\n but calculations would be available","Limitation",JOptionPane.PLAIN_MESSAGE);
                }
                v.p.setObjectDistance(temp);
                v.g_panel.setScale();
   
            }catch (NumberFormatException exc){
                me.numberOnly();
            }catch (Exception exc){
                System.out.println(exc);
            }
            v.calculationPanel.setDetails();
        }
        else if(e.getSource()==v.calculationPanel.objectHeight.textField){
            try {
                double temp = Double.parseDouble(v.calculationPanel.objectHeight.textField.getText());
                if(Math.abs(temp)>v.limitOfScale){
                    JOptionPane.showMessageDialog(null,"This Application has some limitations.\nSo beyond 10000 limit Image transformation can't be shown\n but calculations would be available","Limitation",JOptionPane.PLAIN_MESSAGE);
                }
                v.p.setObjectHeight(temp);
            }catch (NumberFormatException exc){
                me.numberOnly();
            }catch (Exception exc){
                System.out.println(exc);
            }
            v.g_panel.setScale();
            v.calculationPanel.setDetails();
        }
        else if(e.getSource()==v.calculationPanel.focalLength.textField){
            try {
                double temp = Double.parseDouble(v.calculationPanel.focalLength.textField.getText());
                if(Math.abs(temp)>v.limitOfScale ){
                    JOptionPane.showMessageDialog(null,"This Application has some limitations.\nSo beyond 10000 limit Image transformation can't be shown\n but calculations would be available","Limitation",JOptionPane.PLAIN_MESSAGE);
                }
                v.p.setLens(new Lens(temp));
            }catch (NumberFormatException exc){
                me.numberOnly();
            }catch (Exception exc){
                System.out.println(exc);
            }
            v.calculationPanel.setDetails();
        }else if(e.getSource() == v.frame.panel.ref_index_lens.textField) {
            /*try {
                double temp[]={Double.parseDouble(v.frame.panel.radius_of_cur.textField.getText()), Double.parseDouble(v.frame.panel.ref_index_lens.textField.getText()), Double.parseDouble(v.frame.panel.ref_index_medium.textField.getText())};
                if(temp[1]<0 || temp[2]<0){
                    throw new NegativesNotAllowedException();
                }
                v.p.setLens(new Lens(temp[0],temp[1],temp[2]));
            }catch (NegativesNotAllowedException exc){
                JOptionPane.showMessageDialog(null,"Negative Number is not allowed","Warning",JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException exc){
                me.numberOnly();
            }catch (Exception exc){
                System.out.println(exc);
            }
            v.calculationPanel.setDetails();*/
        }
        else if(e.getSource() == v.frame.panel.ref_index_medium.textField) {
            /*try {
                double temp[]={Double.parseDouble(v.frame.panel.radius_of_cur.textField.getText()), Double.parseDouble(v.frame.panel.ref_index_lens.textField.getText()), Double.parseDouble(v.frame.panel.ref_index_medium.textField.getText())};
                if(temp[1]<0 || temp[2]<0){
                    throw new NegativesNotAllowedException();
                }
                v.p.setLens(new Lens(temp[0],temp[1],temp[2]));
            }catch (NegativesNotAllowedException exc){
                JOptionPane.showMessageDialog(null,"Negative Number is not allowed","Warning",JOptionPane.WARNING_MESSAGE);
            }catch (NumberFormatException exc){
                me.numberOnly();
            }catch (Exception exc){
                System.out.println(exc);
            }
            v.calculationPanel.setDetails();*/
        }
        else if(e.getSource() == v.frame.panel.radius_of_cur.textField) {
            /*try {
                v.frame.panel.cur_slider.setValue((int) Double.parseDouble((v.frame.panel.radius_of_cur.textField.getText())));
                double temp[]={Double.parseDouble(v.frame.panel.radius_of_cur.textField.getText()), Double.parseDouble(v.frame.panel.ref_index_lens.textField.getText()), Double.parseDouble(v.frame.panel.ref_index_medium.textField.getText())};
                if(temp[1]<0 || temp[2]<0){
                    throw new NegativesNotAllowedException();
                }
                v.p.setLens(new Lens(temp[0],temp[1],temp[2]));
            }catch (NegativesNotAllowedException exc){
                JOptionPane.showMessageDialog(null,"Negative Number is not allowed","Warning",JOptionPane.WARNING_MESSAGE);
            }catch (NumberFormatException exc){
                me.numberOnly();
            }catch (Exception exc){
                System.out.println(exc);
            }
            v.calculationPanel.setDetails();*/
        }
        else if(e.getSource()==v.frame.panel.button){
            try {
                v.frame.panel.cur_slider.setValue((int) Double.parseDouble((v.frame.panel.radius_of_cur.textField.getText())));
                double temp[]={Double.parseDouble(v.frame.panel.radius_of_cur.textField.getText()), Double.parseDouble(v.frame.panel.ref_index_lens.textField.getText()), Double.parseDouble(v.frame.panel.ref_index_medium.textField.getText())};
                if(temp[1]<0 || temp[2]<0){
                    throw new NegativesNotAllowedException();
                }
                v.p.setLens(new Lens(temp[0],temp[1],temp[2]));
                v.p.display();
                v.frame.setVisible(false);
            }catch (NegativesNotAllowedException exc){
                JOptionPane.showMessageDialog(null,"Negative Number is not allowed","Warning",JOptionPane.WARNING_MESSAGE);
            }catch (NumberFormatException exc){
                me.numberOnly();
            }catch (Exception exc){
                System.out.println(exc);
            }
            v.calculationPanel.setDetails();
        }else if(e.getSource()==v.setScaleButton){
        	if(Math.abs(v.p.getImage().getDistance())>v.limitOfScale | Math.abs(v.p.getImage().getHeight())>v.limitOfScale) {
        		JOptionPane.showMessageDialog(null,"Either Image Distance or Image Height is beyond the scope of scale range","Limitation",JOptionPane.ERROR_MESSAGE);
        	}
        	else {
            v.g_panel.setScale();
            v.setSlider();
            v.g_panel.repaint();
        	}
        } else if (e.getSource()==v.resetButton) {
        	diff=0;
            v.resetScale();
            v.setSliderLabel();
            v.p.setObjectDistance(-50);
            v.p.setObjectHeight(50);
            v.p.setLens(new Lens((v.isConcaveRunning?-30:30)));
            v.calculationPanel.setDetails();
            v.g_panel.repaint();
        } else if(e.getSource() == v.exitButton) {
            System.exit(0);
        } else if (e.getSource()==v.backButton) {
            v.dispose();
            new FrontPage();
        } else if(e.getSource()== v.angleButton) {
        	v.infoButton.setEnabled(false);
        	v.infoButton.setVisible(false);
        	
        	v.velocityButton.setEnabled(false);
        	v.velocityButton.setVisible(false);
        	
        	v.angleButton.setEnabled(false);
        	v.angleButton.setVisible(false);
        	
        	v.resetButton.setEnabled(false);
        	v.resetButton.setVisible(false);
        	
        	v.setScaleButton.setLocation(v.setScaleButton.getX(),v.opBackButton.getY());
        	
        	v.objectAngle.setVisible(true);
        	v.imageAngle.setVisible(true);
        	v.opBackButton.setVisible(true);
        	v.opBackButton.setEnabled(true);
        	
        } else if(e.getSource()==v.opBackButton) {
        	v.objectAngle.setVisible(false);
        	v.imageAngle.setVisible(false);
        	v.opBackButton.setVisible(false);
        	
        	v.infoButton.setEnabled(true);
        	v.infoButton.setVisible(true);
        	
        	v.velocityButton.setEnabled(true);
        	v.velocityButton.setVisible(true);
        	
        	v.angleButton.setEnabled(true);
        	v.angleButton.setVisible(true);
        	
        	v.resetButton.setEnabled(true);
        	v.resetButton.setVisible(true);
        	
        	v.setScaleButton.setLocation(v.setScaleButton.getX(),v.resetButton.getY());
        }
        
        	else{
            in.setVisible(false);
            in.dispose();
            in=new Info();
            in.filename="/resources2/"+e.getActionCommand()+".txt";
            in.job();
        }
        new Thraed1().run();
    }

    public void stateChanged(ChangeEvent e) {
        if(e.getSource()==v.dis_slider) {
        	if(v.p.getObject().getDistance()==0) {
        		v.calculationPanel.imageHeight.label.setForeground(Color.RED);
        		v.calculationPanel.magnificationFactor.label.setForeground(Color.RED);
        	}else {
        		v.calculationPanel.imageHeight.label.setForeground(Color.BLACK);
        		v.calculationPanel.magnificationFactor.label.setForeground(Color.BLACK);
        	}
           
            v.g_panel.dis_slider_value=v.dis_slider.getValue();
            this.new_dis = v.dis_slider.getValue()-this.diff;
            this.temp1 = v.dis_slider_width*this.new_dis/(2*v.scaleX);
            v.p.setObjectDistance(v.dis_slider.getValue(),new_dis);
            v.calculationPanel.setDetails();
//            v.g_panel.repaint();
        }
        else if(e.getSource()==v.height_slider) {
            v.g_panel.height_slider_value=v.height_slider.getValue();
            v.p.setObjectHeight(v.height_slider.getValue());
            v.calculationPanel.setDetails();
//            v.g_panel.repaint();
        }
        else if (e.getSource()==v.calculationPanel.focalLengthSlider){
            v.p.setLens(new Lens(v.calculationPanel.focalLengthSlider.getValue()));
            v.calculationPanel.setDetails();
//            v.g_panel.repaint();
        }else if(e.getSource()==v.frame.panel.cur_slider) {
            v.frame.panel.radius_of_cur.textField.setText(String.valueOf(v.frame.panel.cur_slider.getValue()));
            v.p.setLens(new Lens((double)v.frame.panel.cur_slider.getValue(),Double.parseDouble(v.frame.panel.ref_index_lens.textField.getText()),Double.parseDouble(v.frame.panel.ref_index_medium.textField.getText())));
            v.calculationPanel.setDetails();
        }
        new Thraed().run();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
//        System.out.println(e.getKeyCode());
        if(e.getSource()==v.calculationPanel.objectDistance.textField){
            boolean isChanged = false;
            if(e.getKeyCode()==38){
//                if(v.p.getObject().getDistance()){
                    v.p.setObjectDistance(v.p.getObject().getDistance()+1.0);
                    isChanged = true;
//                }
            }
            else if(e.getKeyCode()==40){
//                if(v.p.getObject().getDistance()){
                    v.p.setObjectDistance(v.p.getObject().getDistance()-1.0);
                    isChanged = true;
//                }
            }
            if(isChanged)
                v.calculationPanel.setDetails();
        }
        else if(e.getSource()==v.calculationPanel.objectHeight.textField){
            boolean isChanged = false;
            if(e.getKeyCode()==38){
//                if(v.p.getObject().getHeight()<v.scaleY){
                    v.p.setObjectHeight(v.p.getObject().getHeight()+1.0);
                    isChanged = true;
//                }
            }
            else if(e.getKeyCode()==40){
//                if(v.p.getObject().getHeight()>-v.scaleY){
                    v.p.setObjectHeight(v.p.getObject().getHeight()-1.0);
                    isChanged = true;
//                }
            }

            if(isChanged)
                v.calculationPanel.setDetails();

        } else if (e.getSource()==v.calculationPanel.focalLength.textField) {
            boolean isChanged = false;
            double f = v.p.getLens().getF();
            if(e.getKeyCode()==38){
                if(v.isConcaveRunning){
                    v.p.setLens(new Lens(f-1));
                    isChanged = true;
                }else {
                    v.p.setLens(new Lens(f+1));
                    isChanged = true;
                }
            }
            else if(e.getKeyCode()==40){
                if(v.isConcaveRunning){
                    if(f+1<=0) {
                        v.p.setLens(new Lens(f + 1));
                        isChanged = true;
                    }
                }else {
                    if(f-1>=0) {
                        v.p.setLens(new Lens(f - 1));
                        isChanged = true;
                    }
                }
            }

            if(isChanged)v.calculationPanel.setDetails();

        }
//        System.out.println("Key Pressed"+e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e){}


    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==v.calculationPanel.focalLength){
            v.calculationPanel.focalLength.label.setForeground(Color.RED);
        }
        if(e.getSource()==v.titlePanel) {
            //            v.titlePanel.setBorder(BorderFactory.createMatteBorder(1,2,1,2,Color.black));
            v.titlePanel.label.setForeground(new Color(0x001BC3));
        }

    }
    public void mouseClicked(MouseEvent e){
        if(e.getSource()==v.calculationPanel.focalLength){
            v.frame.setVisible(true);
        }
        if (e.getSource()==v.titlePanel) {
            v.dispose();
            new MainFrame(-30, -50, 100);
        }
        if(e.getSource()==v.calculationPanel.imageHeight) {
        	JOptionPane.showMessageDialog(null,"As object distance becomes zero, image height becomes undefined",null,JOptionPane.INFORMATION_MESSAGE);
        }
        if(e.getSource()==v.calculationPanel.magnificationFactor) {
        	JOptionPane.showMessageDialog(null,"As obkect distance becomes zero, image height becomes undefined.\nSince image height becomes undefined, magnification factor also becomes undefined.", null,JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void mousePressed(MouseEvent e) {};
    public void mouseReleased(MouseEvent e) {};
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==v.calculationPanel.focalLength){
            v.calculationPanel.focalLength.label.setForeground(Color.BLACK);
        }else if (e.getSource()==v.titlePanel) {
            //            v.titlePanel.setBorder(new EmptyBorder(0,0,0,0));
            v.titlePanel.label.setForeground(new Color(0));
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    	if(v.objectAngle.isVisible()==true) {
    		//if(/*e.getX() == (int)(v.g_panel.getWidth()/2 + (v.g_panel.dis_slider_width*v.g_panel.u)/2*v.scaleX) &&*/
    				//e.getY() == (int)(v.g_panel.getHeight()/2 - (v.g_panel.height_slider_height*v.g_panel.h0)/2*v.scaleY)) {
    		    
    		    double temp = (v.g_panel.getHeight()/2) - e.getY();
    		    double new_height = (2*v.scaleY*temp)/v.g_panel.height_slider_height;
    	//	    System.out.println(temp);
    	//	    System.out.println(v.g_panel.height_slider_height);
    	//	    System.out.println(new_height);
    		    
    		    temp1 = e.getX()-(v.g_panel.getWidth()/2);
    		    new_dis = (2*v.scaleX*temp1)/v.g_panel.dis_slider_width;
    		    diff = v.p.getObject().getDistance()-new_dis;
       //  	    System.out.println(temp1);
       //		System.out.println(v.g_panel.dis_slider_width);
       //		System.out.println(new_dis);
    		    
                v.p.setObjectDistanceHeight((double)v.dis_slider.getValue(),new_dis,new_height);
       //		    v.p.setObjectHeight(new_height);
       //		    v.p.setObjectDistance(v.dis_slider.getValue(),new_dis);
                v.calculationPanel.setDetails();
    		}
    	//}

    }

    @Override
    public void mouseMoved(MouseEvent e) {


    }
}
