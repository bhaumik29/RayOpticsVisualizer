package MainLens;

import MainLens.FrameToolkit.*;
import MainLens.FrameToolkit.Frame;
import MainLens.GeometricalOptics.Lens.Lens;
import MainLens.GeometricalOptics.Platform;
import MainLens.GeometricalOptics.Specimen.ObjectSpecimen;

import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class Visualizer extends Frame /*implements ChangeListener, ActionListener,KeyListener*/ {

	public Platform p = new Platform(new ObjectSpecimen(-50,50),new Lens(30));
	int scaleX=100,scaleY=100;
	int limitOfScale = 10000;
	int w,h;
	boolean isConcaveRunning=false;
	Events events = new Events(this);
	JPanel dis_panel;
	JSlider dis_slider;
	int dis_slider_width;
	JPanel height_panel;
	JSlider height_slider;
	int height_slider_height;
	public GraphicPanel g_panel;
	UpperPanel upperPanel = new UpperPanel();
	Header header = new Header();
	SecondaryPanel secondaryPanel = new SecondaryPanel();
	OptionContainer optionContainer = new OptionContainer();
	InformationContainer informationContainer = new InformationContainer();
	OptionButton concaveButton = new OptionButton("Concave");
	OptionButton convexButton = new OptionButton("Convex");
	CalculationPanel calculationPanel = new CalculationPanel(this);

	OptionButton setScaleButton = new OptionButton("SetScale");
	OptionButton resetButton = new OptionButton("Reset");
	OptionButton infoButton = new OptionButton("Info");
	OptionButton velocityButton = new OptionButton("Velocity");
	OptionButton angleButton = new OptionButton("Angle");
	OptionButton opBackButton = new OptionButton("Back");
	RefractiveIndexFrame frame = new RefractiveIndexFrame(this);
	HeaderButtons backButton = new HeaderButtons("Back",events);
	HeaderButtons exitButton = new HeaderButtons("Exit",events);
	TitlePanel titlePanel = new TitlePanel("Convex Lens",events);
	
	OptionContainerPanel objectAngle = new OptionContainerPanel("Object Angle -",null);
	OptionContainerPanel imageAngle = new OptionContainerPanel("Image Angle -",null);

	public Visualizer(){

		w=d.width;
		h=d.height;

		dis_slider_width=w-100;
		dis_slider=new JSlider(-100,100,-50);
		dis_slider.setBounds(100,0,dis_slider_width,100);
		dis_slider.setBackground(new Color(0x84C6F8));
		dis_slider.setPaintTrack(true);
		dis_slider.setMajorTickSpacing(10);
		dis_slider.setPaintLabels(true);
		dis_slider.setToolTipText("SET OBJECT DISTANCE(IN cm)");
		dis_slider.addChangeListener(events);

		height_slider_height=h-h/4-150;
		height_slider=new JSlider(-100,100,50);
		height_slider.setOrientation(SwingConstants.VERTICAL);
		height_slider.setBounds(0,0,100,height_slider_height);
		height_slider.setBackground(new Color(0xFFFE7979, true));
		height_slider.setPaintTrack(true);
		height_slider.setMajorTickSpacing(10);
		height_slider.setPaintLabels(true);
		height_slider.setToolTipText("SET OBJECT HEIGHT(IN cm)");
		height_slider.addChangeListener(events);

		dis_panel=new JPanel();
		dis_panel.setBackground(Color.white);
		dis_panel.setBounds(0,h-150,w,150);
		dis_panel.setLayout(null);

		height_panel=new JPanel();
		height_panel.setBackground(Color.white);
		height_panel.setBounds(0,h/4,100,height_slider_height);
		height_panel.setLayout(null);

		g_panel=new GraphicPanel(this
				,dis_slider_width
				,height_slider_height
				,dis_slider.getValue()
				,height_slider.getValue()
				,(int) p.getLens().getF()
				,(int) p.getObject().getDistance()
				,(int) p.getObject().getDistance2()
				,(int) p.getObject().getHeight());

		dis_panel.add(dis_slider);
		height_panel.add(height_slider);

		this.add(dis_panel);
		this.add(height_panel);
		this.add(g_panel);

		g_panel.addMouseMotionListener(events);

		convexButton.addActionListener(events);
		concaveButton.addActionListener(events);

		convexButton.setVisible(false);
		convexButton.setLocation((OptionContainer.size.width-OptionButton.size.width)/2,5);
		concaveButton.setLocation((OptionContainer.size.width-OptionButton.size.width)/2,5);
		optionContainer.add(convexButton);
		optionContainer.add(concaveButton);

		setScaleButton.setLocation(OptionContainer.size.width- setScaleButton.getWidth()-5,OptionContainer.size.height- setScaleButton.getHeight()-5);
		setScaleButton.addActionListener(events);

		resetButton.setLocation(5,OptionContainer.size.height- setScaleButton.getHeight()-5);
		resetButton.addActionListener(events);

		infoButton.setLocation(concaveButton.getX(),OptionContainer.size.height-3*resetButton.getHeight()-15);
		infoButton.addActionListener(events);
		
		velocityButton.setLocation(5,OptionContainer.size.height-2*resetButton.getHeight()-10);
		velocityButton.addActionListener(events);
		
		angleButton.setLocation(OptionContainer.size.width-setScaleButton.getWidth()-5,OptionContainer.size.height-2*resetButton.getHeight()-10);
		angleButton.addActionListener(events);
		
		opBackButton.setLocation(resetButton.getX(),OptionContainer.size.height-3*resetButton.getHeight()-15);
		opBackButton.setVisible(false);
		opBackButton.setEnabled(false);
		opBackButton.addActionListener(events);
		
		objectAngle.setVisible(false);
		objectAngle.setLocation(5,OptionContainer.size.height-2*resetButton.getHeight()-10);
		
		imageAngle.setVisible(false);
		imageAngle.setLocation(5,OptionContainer.size.height-resetButton.getHeight()-5);

		optionContainer.add(setScaleButton);
		optionContainer.add(resetButton);
		optionContainer.add(infoButton);
		optionContainer.add(velocityButton);
		optionContainer.add(angleButton);
		optionContainer.add(opBackButton);
		optionContainer.add(objectAngle);
		optionContainer.add(imageAngle);


		upperPanel.setSize(w,h/4);

		backButton.setLocation(5,5);
		exitButton.setLocation(Header.size.width-HeaderButtons.size.width-5,5);
		titlePanel.setLocation((Header.size.width-TitlePanel.size.width)/2,0);

		header.add(backButton);
		header.add(exitButton);
		header.add(titlePanel);

		secondaryPanel.add(calculationPanel,BorderLayout.CENTER);
		secondaryPanel.add(optionContainer,BorderLayout.EAST);

		upperPanel.add(header,BorderLayout.NORTH);
		upperPanel.add(secondaryPanel,BorderLayout.CENTER);

		this.add(upperPanel);

		this.setTitle("Visualizer");
		this.setVisible(true);
	}

	void setSlider(){
		//g_panel.setScale();
//		if(p.getObject().getDistance()<this.scaleX)
			this.dis_slider.setValue((int) p.getObject().getDistance());
//		if(p.getObject().getHeight()<this.scaleY)
			this.height_slider.setValue((int) p.getObject().getHeight());
		if(p.getLens().getF()>=0 && p.getLens().getF()<=50) {
			this.calculationPanel.focalLengthSlider.removeChangeListener(events);
			this.calculationPanel.focalLengthSlider.setMinimum(0);
			this.calculationPanel.focalLengthSlider.setMaximum(50);
			this.calculationPanel.focalLengthSlider.setValue((int) p.getLens().getF());
			this.calculationPanel.focalLengthSlider.setEnabled(true);
			this.calculationPanel.focalLengthSlider.addChangeListener(events);
		}else if(p.getLens().getF()>=(-50) && p.getLens().getF()<0) {
			    this.calculationPanel.focalLengthSlider.removeChangeListener(events);
				this.calculationPanel.focalLengthSlider.setMinimum(-50);
				this.calculationPanel.focalLengthSlider.setMaximum(0);
				this.calculationPanel.focalLengthSlider.setValue((int) p.getLens().getF());
				this.calculationPanel.focalLengthSlider.setEnabled(true);
				this.calculationPanel.focalLengthSlider.addChangeListener(events);
		}else if (Math.abs(p.getLens().getF())>50) {
			this.calculationPanel.focalLengthSlider.setEnabled(false);
		}
	}

	void setSliderLabel(){
		Dictionary dict = new Hashtable();

		dis_slider.removeChangeListener(events);
		height_slider.removeChangeListener(events);

		dis_slider.setMinimum(-scaleX);
		dis_slider.setMaximum(scaleX);

		height_slider.setMinimum(-scaleY);
		height_slider.setMaximum(scaleY);

		int temp1 = scaleX/10;
		/*int temp1;
		if(scaleX==100)temp1=10;
		else if(scaleX==400)temp1=50;
		else if(scaleX==600)temp1=60;
		else if(scaleX==1000)temp1=100;
		else if(scaleX==1200)temp1=150;
		else if(scaleX==1500)temp1=150;
		else if(scaleX==2000)temp1=200;
		else if(scaleX==3000)temp1=300;
		*/
		for (int i = -scaleX; i <= scaleX; i += temp1) {
			dict.put(i, new JLabel("" + i));
		}

		dis_slider.setLabelTable(dict);
		dis_slider.setMajorTickSpacing(temp1);
		dis_slider.setMinorTickSpacing(temp1/10);

		dict = new Hashtable();

		//temp1= scaleY/10;
		if(scaleY==100)temp1=10;
		else if(scaleY==300)temp1=50;
		else if(scaleY==600)temp1=50;
		else if(scaleY==800)temp1=100;
		else if(scaleY==1200)temp1=200;
		else if(scaleY==2000)temp1=200;
		else if(scaleY==2500)temp1=250;
		else if(scaleY==4000)temp1=400;
		else if(scaleY==6000)temp1=600;
		else temp1=1000;
		for (int i = -scaleY; i <= scaleY; i += temp1) {
			dict.put(i, new JLabel("" + i));
		}

		height_slider.setLabelTable(dict);
		height_slider.setMajorTickSpacing(temp1);
		height_slider.setMinorTickSpacing(temp1/10);

		dis_slider.addChangeListener(events);
		height_slider.addChangeListener(events);
	}

	void resetScale(){
		this.scaleX = 100;
		this.scaleY = 100;
	}

	boolean isOutOfRange(){
		boolean neededDis = false;
		boolean neededHei = false;
		/*if(Math.abs(p.getImage().getDistance())>limitOfScale && !Double.isInfinite(p.getImage().getDistance())){
			neededDis = true;
		}
		if (Math.abs(p.getImage().getHeight())>limitOfScale && !Double.isInfinite(p.getImage().getHeight())) {
			neededHei = true;
		}*/
		if(Math.abs(p.getObject().getDistance())>limitOfScale && !Double.isInfinite(p.getImage().getDistance())) {
			neededDis = true;
		}
		if(Math.abs(p.getObject().getHeight())>limitOfScale && !Double.isInfinite(p.getImage().getHeight())) {
			neededHei = true;
		}
		return neededHei || neededDis;
	}
	void isScaleNeeded(){
		boolean isNeed=false;
		if(Double.isInfinite(p.getImage().getDistance())){
			isNeed = false;
		} else if(Math.abs(p.getObject().getDistance())>scaleX){
			isNeed=true;
		}else if (Math.abs(p.getObject().getHeight())>scaleY) {
			isNeed=true;
		}else if (Math.abs(p.getImage().getDistance())>scaleX) {
			isNeed=true;
		}else if (Math.abs(p.getImage().getHeight())>scaleY) {
			isNeed=true;
		}
	}


	public static void main(String[] args) {
		new Visualizer();
	}


}
