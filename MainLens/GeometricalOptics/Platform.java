package MainLens.GeometricalOptics;
import MainLens.GeometricalOptics.Lens.Lens;
import MainLens.GeometricalOptics.Specimen.ImageSpecimen;
import MainLens.GeometricalOptics.Specimen.ObjectSpecimen;

import java.text.DecimalFormat;

class InfiniteValueException extends Exception{
    @Override
    public String toString() {
        return super.toString()+ "Value is Infinite";
    }
}
public class Platform {
    Lens lens;
    ObjectSpecimen object;
    ImageSpecimen image;
    double magnificationFactor;
    double power;

    DecimalFormat df=new DecimalFormat("#.##");

    public Platform(ObjectSpecimen object, Lens lens){
        object.setDistance(Double.parseDouble(df.format(object.getDistance())));
        object.setDistance2(Double.parseDouble(df.format(object.getDistance2())));
        object.setHeight(Double.parseDouble(df.format(object.getHeight())));
        this.object = object;
        this.lens = lens;
        double u1 = object.getDistance();
        double u2 = object.getDistance2();
        double f = lens.getF();
        double v1 = calculateV(u1,f);
        double v2 = calculateV(u2,f);
        this.magnificationFactor = v1/u1;
//        double h2 = magnificationFactor*object.getHeight();
        double h2 = (v2/u2)*object.getHeight();
        try{
//            if(Double.isInfinite(v))

            if(v1==Double.POSITIVE_INFINITY||v1==Double.NEGATIVE_INFINITY){
                throw new InfiniteValueException();
            }
            v1 = Double.parseDouble(df.format(v1));
            this.magnificationFactor = Double.parseDouble(df.format(this.magnificationFactor));
            h2 = Double.parseDouble(df.format(h2));
            this.image = new ImageSpecimen(v1,v2,h2);
        }catch(InfiniteValueException e){
            this.image = new ImageSpecimen(v1,v2,h2);
        }
        setRealOrNot();
    }

    private double calculateV(double u,double f){
        double one_over_v = (1/f)+(1/u);
        return 1/one_over_v;
    }

    public Lens getLens() {
        return lens;
    }

    public void setLens(Lens lens) {
        this.lens = lens;
        double u1 = object.getDistance();
        double u2 = object.getDistance2();
        double f = lens.getF();
        double v1 = calculateV(u1,f);
        double v2 = calculateV(u2,f);
        this.magnificationFactor = v1/u1;
//        double h2 = magnificationFactor*object.getHeight();
        double h2 = (v2/u2)*object.getHeight();
        try {
            if(v1==Double.POSITIVE_INFINITY||v1==Double.NEGATIVE_INFINITY){
                throw new InfiniteValueException();
            }
            v1 = Double.parseDouble(df.format(v1));
            this.magnificationFactor = Double.parseDouble(df.format(this.magnificationFactor));
            h2 = Double.parseDouble(df.format(h2));
            this.image = new ImageSpecimen(v1,v2,h2);
        }catch (InfiniteValueException e){
            this.image = new ImageSpecimen(v1,v2,h2);
        }
        setRealOrNot();
    }
    public void setObjectDistance(double u){
       this.setObject(new ObjectSpecimen(u, object.getHeight()));
    }
    
    public void setObjectDistance(double u1,double u2) {
    	this.setObject(new ObjectSpecimen(u1,u2,object.getHeight()));
    }
    
    public void setObjectDistanceHeight(double u1, double u2, double h0) {
    	this.setObject(new ObjectSpecimen(u1,u2,h0));
    }

    public ObjectSpecimen getObject() {
        return object;
    }

    public void setObjectHeight(double u){
        //this.setObject(new ObjectSpecimen(object.getDistance(),u));
        this.object.setHeight(u);
        this.setObject(object);
    }

    public void setObject(ObjectSpecimen object) {
        object.setDistance(Double.parseDouble(df.format(object.getDistance())));
        object.setDistance2(Double.parseDouble(df.format(object.getDistance2())));
        object.setHeight(Double.parseDouble(df.format(object.getHeight())));
        this.object = object;
        double u1 = Double.parseDouble(String.format("%.3f",object.getDistance()));
        double u2 = Double.parseDouble(String.format("%.3f",object.getDistance2()));
        double f = lens.getF();
        double v1 = calculateV(u1,f);
        double v2 = calculateV(u2,f);
        this.magnificationFactor = v1/u1;
//        double h2 = magnificationFactor*object.getHeight();
        double h2 = (v2/u2)*object.getHeight();
        try {
            if(v1==Double.POSITIVE_INFINITY||v1==Double.NEGATIVE_INFINITY){
                throw new InfiniteValueException();
            }
            v1 = Double.parseDouble(df.format(v1));
            this.magnificationFactor = Double.parseDouble(df.format(this.magnificationFactor));
            h2 = Double.parseDouble(df.format(h2));
            this.image = new ImageSpecimen(v1,v2,h2);
        }catch (InfiniteValueException e){
            this.image = new ImageSpecimen(v1,v2,h2);
        }
        setRealOrNot();
    }

    public ImageSpecimen getImage() {
        return image;
    }
    
    public String getStringMagnificationFactor(){
        if(this.magnificationFactor==Double.POSITIVE_INFINITY){
            return Character.toString('\u221e');
        } else if (this.magnificationFactor==Double.NEGATIVE_INFINITY) {
            return "-"+Character.toString('\u221e');
        }
        return Double.toString(this.magnificationFactor);
    }

    public void display(){
        System.out.println("Lens Properties:");
        lens.displayProperties();
        System.out.println("Object :"
                +"\nObject Distance - " + getObject().getStringDistance()
                +"\nObject Height - " + getObject().getStringHeight()
                +"\nImage :"
                +"\nImage Distance - " + getImage().getStringDistance()
                +"\nImage Height - " + getImage().getStringHeight()
                +"\n-->Lateral Magnification Factor "+getStringMagnificationFactor()
                +"\nImage is "+getImage().getStringVirtual()
        );
    }

    private void setRealOrNot(){
        if(lens.isDiverging()){
            if(object.getDistance()>0 && object.getDistance()<Math.abs(lens.getF()))image.setVirtual(false);
            else image.setVirtual(true);
        }
        else {
            if(object.getDistance()<0 && Math.abs(object.getDistance())<lens.getF()) image.setVirtual(true);
            else image.setVirtual(false);
        }
//        if(((this.lens.getF()>0 && Math.abs(this.object.getDistance())<Math.abs(this.lens.getF())) && this.getObject().getDistance()<0)|| this.lens.getF()<0){
//            this.image.setVirtual(true);
//        }else {
//            this.image.setVirtual(false);
//        }
    }
}
