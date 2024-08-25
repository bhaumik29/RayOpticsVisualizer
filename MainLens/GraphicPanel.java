package MainLens;

import MainLens.FrameToolkit.Frame;
import MainLens.GeometricalOptics.Platform;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Dictionary;

public class GraphicPanel extends JPanel{

	Platform p;
	Visualizer vis;
	Image convexImage ;
	Image concaveImage;

	Dimension d;
	int w;
	int h;
	int dis_slider_width;
	int height_slider_height;
	int dis_slider_value;
	int height_slider_value;
	double v1;
	double v2;
	double u1;
	double u2;
	double hi;
	double h0;
	double f;

	Dictionary dict;

	Point obj1;
	Point obj2;
	Point img1;
	Point img2;


	GraphicPanel(Visualizer visualizer, int dis_slider_width, int height_slider_height, int dis_slider_value, int height_slider_value, int f, int u1, int u2,int h0)
	{	this.vis = visualizer;
		this.p = vis.p;
		this.dis_slider_width=dis_slider_width;
		this.height_slider_height=height_slider_height;
		this.dis_slider_value=dis_slider_value;
		this.height_slider_value=height_slider_value;
		this.u1=u1;
		this.u2=u2;
		this.f=f;
		this.h0=h0;


		w= Frame.d.width;
		h= Frame.d.height;

		URL url = Visualizer.class.getResource("/resources2/convex_lens.jpg");
		convexImage = new ImageIcon(url).getImage();
		URL url2=Visualizer.class.getResource("/resources2/concave_lens.jpg");
		concaveImage = new ImageIcon(url2).getImage();

		this.setSize(w-100,height_slider_height);
		this.setLocation(100,h/4);
		this.setBackground(Color.white);
//    	this.setBounds(100,h/4,w-100,height_slider_height);
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d=(Graphics2D)g;


		/*this.u = p.getObject().getDistance();
		this.v = p.getImage().getDistance();

		this.f = p.getLens().getF();

		this.h0 = p.getObject().getHeight();
		this.hi = p.getImage().getHeight();*/

		this.u1 = p.getObject().getDistance();
		this.u2 = p.getObject().getDistance2();
		this.v1 = p.getImage().getDistance();
		this.v2 = p.getImage().getDistance2();
		
//		System.out.println(u2);

		this.f = p.getLens().getF();
		if(this.f==Double.POSITIVE_INFINITY)this.f=50000;
		else if(this.f==Double.NEGATIVE_INFINITY)this.f=-50000;
		if(this.v1==Double.POSITIVE_INFINITY) this.v1=50000;
		else if(this.v1==Double.NEGATIVE_INFINITY) this.v1=-50000;
		if(this.v2==Double.POSITIVE_INFINITY) this.v2=50000;
		else if(this.v2==Double.NEGATIVE_INFINITY) this.v2=-50000;
		this.h0 = p.getObject().getHeight();
		this.hi = p.getImage().getHeight();
		if(this.hi==Double.POSITIVE_INFINITY)this.hi=50000;
		else if(this.hi==Double.NEGATIVE_INFINITY)this.hi=-50000;

		//Lens
//		g2d.setPaint(Color.blue);
//		g2d.drawImage(concaveLensImage,w,h/2,null);
//		g2d.fillArc(w/2-45-110,35,100,h/2,90,180);
//		g2d.fillArc(w/2-45-110,35, 100,h/2, 270,180);
		if(vis.p.getLens().getF()>=0) {
			g2d.drawImage(convexImage,(this.getWidth()/2-114),this.getHeight()/12,null);
			if(vis.p.getLens().getRefractiveIndex()<vis.p.getLens().getRefractiveIndexOfMedium())
				g2d.drawImage(concaveImage,this.getWidth()/2-112,this.getHeight()/12,null);
		}
		else {
			g2d.drawImage(concaveImage,this.getWidth()/2-112,this.getHeight()/12,null);
			if(vis.p.getLens().getRefractiveIndex()<vis.p.getLens().getRefractiveIndexOfMedium())
				g2d.drawImage(convexImage,this.getWidth()/2-114,this.getHeight()/12,null);
		}

		//Reference Line
		g2d.setPaint(Color.black);
		g2d.drawLine(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());//Vertical
		g2d.drawLine(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);//Horizontal

		//Focal Points
		g2d.fillOval((int) (this.getWidth()/2-5-(dis_slider_width*(f)/(vis.scaleX*2))),this.getHeight()/2-5,10,10);
		g2d.fillOval((int) (this.getWidth()/2-5+(dis_slider_width*(f)/(vis.scaleX*2))),this.getHeight()/2-5,10,10);


		int tem=dis_slider_value;
		int tem2=height_slider_value;
		int tem3= (int) (this.getWidth()/2+(dis_slider_width*u1)/(vis.scaleX*2));
		int tem4= (int) (this.getHeight()/2-(height_slider_height*h0)/(vis.scaleY*2));
		int tem5= (int) (this.getWidth()/2+(dis_slider_width*u2)/(vis.scaleX*2));
		obj1 = new Point(tem3,this.getHeight()/2);
		obj2 = new Point(tem5,tem4);

		int xPoints[]= {tem5-10,tem5+10,tem5};
		int yPoints[]=new int[3];
		if(tem2>0) {
			yPoints[0]=tem4+10;
			yPoints[1]=tem4+10;
			yPoints[2]=tem4;
		}
		else {
			yPoints[0]= tem4-10;
			yPoints[1]=tem4-10;
			yPoints[2]=tem4;
		}

		g2d.setStroke(new BasicStroke(2));

		//Object
		g2d.drawLine(tem3,this.getHeight()/2,tem5,tem4);
		g2d.fillPolygon(xPoints, yPoints,3);

//		calculation();
		Stroke dotted=new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0,new float[] {10},0);
		if(p.getObject().getDistance()<0) {
			g2d.drawLine(tem5,yPoints[2],this.getWidth()/2,yPoints[2]);//Object to Lens Parallax Line
			if(p.getLens().getF()<0)
				g2d.setStroke(dotted);
			g2d.drawLine(this.getWidth()/2,yPoints[2], (int) (this.getWidth()/2+5+(dis_slider_width*f)/(vis.scaleX*2)),this.getHeight()/2);//Lens to Focal Point
			g2d.setStroke(new BasicStroke(2));
			g2d.drawLine(tem5,yPoints[2],this.getWidth()/2,this.getHeight()/2);//Object to Origin
			if(Math.abs(p.getObject().getDistance())<p.getLens().getF()) {
				if(p.getLens().getF()>0){
					g2d.setStroke(dotted);
					g2d.drawLine(this.getWidth() / 2, yPoints[2], (int) (this.getWidth() / 2 + (dis_slider_width * v2) / (vis.scaleX*2)), (int) (this.getHeight()/2 - (height_slider_height * hi) / (vis.scaleY*2)));//Lens to Image top
					g2d.drawLine(tem5, yPoints[2], (int) (this.getWidth() / 2 + (dis_slider_width * v2) / (vis.scaleX*2)), (int) (this.getHeight()/2 - (height_slider_height * hi) / (vis.scaleY*2)));//Object Top to image top
				}
			}
			else {
				/*if(p.getLens().getF()>0) {
					g2d.drawLine((int) (this.getWidth() / 2 + 5 + (dis_slider_width * f) / (vis.scaleX*2)), this.getHeight()/2, (int) (this.getWidth() / 2 + (dis_slider_width * v) / (vis.scaleX*2)), (int) (this.getHeight()/2 - (height_slider_height * hi) / (vis.scaleY*2)));//focal to Image top
					g2d.drawLine(this.getWidth() / 2, this.getHeight()/2, (int) (this.getWidth() / 2 + (dis_slider_width * v) / (vis.scaleX*2)), (int) (this.getHeight()/2 - (height_slider_height * hi) / (vis.scaleY*2)));//Origin to Image top
				}*/
				if(p.getLens().getF()>0) {
					g2d.drawLine((int) (this.getWidth() / 2 + 5 + (dis_slider_width * f) / (vis.scaleX*2)), this.getHeight()/2, (int) (this.getWidth() / 2 + (dis_slider_width * v2) / (vis.scaleX*2)), (int) (this.getHeight()/2 - (height_slider_height * hi) / (vis.scaleY*2)));//focal to Image top
					g2d.drawLine(this.getWidth() / 2, this.getHeight()/2, (int) (this.getWidth() / 2 + (dis_slider_width * v2) / (vis.scaleX*2)), (int) (this.getHeight()/2 - (height_slider_height * hi) / (vis.scaleY*2)));//Origin to Image top
				}
			}
		}
		else {
			if(p.getLens().getF()>0) {
				g2d.setStroke(dotted);
				g2d.drawLine(tem5, yPoints[2], this.getWidth() / 2, yPoints[2]);//Horizontal Line Lens to Object Top
				g2d.setStroke(new BasicStroke(2));
//				g2d.drawLine(this.getWidth()/2-500,yPoints[2],this.getWidth()/2,yPoints[2]);//Horizontal Infinite to Lens
				g2d.drawLine(0, yPoints[2], this.getWidth() / 2, yPoints[2]);//Horizontal Infinite to Lens
				g2d.drawLine(this.getWidth() / 2, yPoints[2], (int) (this.getWidth() / 2 - 5 + (dis_slider_width * f) / (vis.scaleX*2)), this.getHeight() / 2);//Lens to focal point
				g2d.drawLine(tem5, yPoints[2], this.getWidth() / 2, this.getHeight() / 2);//Object Top to Origin
			}else{
				if(p.getObject().getDistance()<=Math.abs(f)) {
					g2d.setStroke(new BasicStroke(2));
					g2d.drawLine(0, yPoints[2], this.getWidth() / 2, yPoints[2]);//Horizontal Infinite to Lens
					g2d.drawLine(this.getWidth() / 2, yPoints[2], (int) (this.getWidth() / 2 + (dis_slider_width * v2) / (vis.scaleX*2)), (int) (this.getHeight() / 2 - (height_slider_height * hi) / (vis.scaleY*2)));//Lens To Image Top
					g2d.drawLine(tem5, yPoints[2], this.getWidth() / 2, this.getHeight() / 2);//Object Top to Origin
					g2d.drawLine(tem5, yPoints[2], (int) (this.getWidth() / 2 + (dis_slider_width * v2) / (vis.scaleX*2)), (int) (this.getHeight() / 2 - (height_slider_height * hi) / (vis.scaleY*2)));//Object Top
					g2d.setStroke(dotted);
//					g2d.drawLine(tem3, yPoints[2], this.getWidth() / 2, yPoints[2]);//Horizontal Line Lens to Object Top
					g2d.drawLine(this.getWidth() / 2, yPoints[2], (int) (this.getWidth() / 2 - 5 + (dis_slider_width * f) / (vis.scaleX*2)), this.getHeight() / 2);//Focal To Origin
				}else{
					g2d.setStroke(new BasicStroke(2));
					g2d.drawLine(0, yPoints[2], this.getWidth() / 2, yPoints[2]);//Horizontal Infinite to Lens
					g2d.drawLine(tem5, yPoints[2], this.getWidth() / 2, this.getHeight() / 2);//Object Top to Origin
					g2d.drawLine(this.getWidth() / 2, this.getHeight() / 2,(int) (this.getWidth() / 2 + (dis_slider_width * v2) / (vis.scaleX*2)), (int) (this.getHeight() / 2 - (height_slider_height * hi) / (vis.scaleY*2)));
					g2d.setStroke(dotted);
//					g2d.drawLine(tem3, yPoints[2], this.getWidth() / 2, yPoints[2]);//Horizontal Line Lens to Object Top
					g2d.drawLine(this.getWidth() / 2, yPoints[2], (int) (this.getWidth() / 2 - 5 + (dis_slider_width * f) / (vis.scaleX*2)), this.getHeight() / 2);//Focal To Origin
					g2d.drawLine((int) (this.getWidth() / 2 + (dis_slider_width * v2) / (vis.scaleX*2)), (int) (this.getHeight() / 2 - (height_slider_height * hi) / (vis.scaleY*2)),(int) (this.getWidth() / 2 - 5 + (dis_slider_width * f) / (vis.scaleX*2)), this.getHeight() / 2);

				}



			}
		}

		tem= (int) v1;
		tem2= (int) hi;
		tem3= (int) (this.getWidth()/2+(dis_slider_width*v1)/(vis.scaleX*2));//Location of image X axis
//		tem4= (int) (this.getHeight()/2-(height_slider_height*hi)/200); // Location of image Y axis
		tem4= (int) (this.getHeight()/2-(height_slider_height*hi)/(vis.scaleY*2)); // Location of image Y axis
		tem5= (int) (this.getWidth()/2+(dis_slider_width*v2)/(vis.scaleX*2));

		img1 = new Point(tem3,this.getHeight()/2);
		img2 = new Point(tem5,tem4);

		//Image Line
		g2d.setStroke(new BasicStroke(2));
		g2d.setPaint(Color.gray);
		g2d.drawLine(tem3,this.getHeight()/2, tem5, tem4);




		xPoints[0]=tem5-10;
		xPoints[1]=tem5+10;
		xPoints[2]=tem5;

		if(hi<0) {
			yPoints[0]=tem4-10;
			yPoints[1]=tem4-10;
			yPoints[2]=tem4;
		}
		else {
			yPoints[0]=tem4+10;
			yPoints[1]=tem4+10;
			yPoints[2]=tem4;
		}

		g2d.fillPolygon(xPoints,yPoints,3);//Arrow of Image
		//repaint();
	
	}


	void setScale(){
		System.out.println(1);
		boolean forImg = Math.abs(p.getImage().getDistance()) > Math.abs(p.getObject().getDistance());

		if((forImg?Math.abs(p.getImage().getDistance()):Math.abs(p.getObject().getDistance()))>vis.scaleX &&
				(forImg?Math.abs(p.getImage().getDistance()):Math.abs(p.getObject().getDistance()))<vis.limitOfScale){

			System.out.println(2);
			double temp;
			if (forImg) {
				//temp = NoOfDigits(Math.abs(p.getImage().getDistance()));
				temp=Math.abs(p.getImage().getDistance());
				System.out.println(forImg +"  "+ temp);
			} else {
				//temp = NoOfDigits(Math.abs(p.getObject().getDistance()));
				temp=Math.abs(p.getObject().getDistance());
				System.out.println(forImg +"  "+ temp);
			}
			//vis.scaleX = (int) Math.pow(10, temp);
			if(temp>100 && temp<=200)vis.scaleX=250;
			else if(temp>200 && temp<=300)vis.scaleX=350;
			else if(temp>300 && temp<=400)vis.scaleX=500;
			else if(temp>400 && temp<=600)vis.scaleX=800;
			else if(temp>600 && temp<=700)vis.scaleX=1000;
			else if(temp>700 && temp<=1000)vis.scaleX=1200;
			else if(temp>1000 && temp<=1500)vis.scaleX=3000;
			else if(temp>1500 && temp<=2000)vis.scaleX=4000;
			else if(temp>2000 && temp<=3000)vis.scaleX=8000;
			else vis.scaleX=10000;

		}
		forImg = (Math.abs(p.getImage().getHeight()) > Math.abs(p.getObject().getHeight()));
		if((forImg?Math.abs(p.getImage().getHeight()):Math.abs(p.getObject().getHeight()))>vis.scaleY &&
				(forImg?Math.abs(p.getImage().getHeight()):Math.abs(p.getObject().getHeight()))<vis.limitOfScale) {
			System.out.println(3);
			double temp;
			if (forImg) {
				//temp = NoOfDigits(Math.abs(p.getImage().getHeight()));
				temp=Math.abs(p.getImage().getHeight());
				System.out.println(forImg +"  "+ temp);
			} else {
				//temp = NoOfDigits(Math.abs(p.getObject().getHeight()));
				temp=Math.abs(p.getObject().getHeight());
				System.out.println(forImg +"  "+ temp);
			}
			//vis.scaleY = (int) Math.pow(10, temp);
			if(temp>100 && temp<=200)vis.scaleY=300;
			else if(temp>200 && temp<=300) vis.scaleY=600;
			else if(temp>300 && temp<=400) vis.scaleY=800;
			else if(temp>400 && temp<=500) vis.scaleY=800;
			else if(temp>500 && temp<=700) vis.scaleY=1200;
			else if(temp>700 && temp<=1000)vis.scaleY=2000;
			else if(temp>1000 && temp<=1500)vis.scaleY=2500;
			else if(temp>1500 && temp<=2500)vis.scaleY=4000;
			else if(temp>2500 && temp<=4000)vis.scaleY=6000;
			else vis.scaleY=10000;
		}

		vis.setSliderLabel();

		System.out.println("ScaleX = "+vis.scaleX);
		System.out.println("ScaleY = "+vis.scaleY);
	}

	private int NoOfDigits(double x){
		int count=0;
		while(x>=1){
			x = x/10;
			count++;
		}
		return count;
	}

}
