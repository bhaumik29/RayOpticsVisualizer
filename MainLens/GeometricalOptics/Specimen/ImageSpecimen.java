package MainLens.GeometricalOptics.Specimen;

public class ImageSpecimen{
    double distance1;
    double distance2;
    double height;
    boolean isVirtual;

    public boolean isVirtual() {
        return isVirtual;
    }

    public void setVirtual(boolean virtual) {
        isVirtual = virtual;
    }


    public ImageSpecimen(double distance1, double distance2, double height){
        this.distance1 = distance1;
        this.distance2 = distance2;
        this.height = height;
        this.isVirtual = false;
    }
    
    public String getStringDistance(){
        if(this.distance1==Double.POSITIVE_INFINITY){
            return Character.toString('\u221e');
        } else if (this.distance1==Double.NEGATIVE_INFINITY) {
            return "-"+Character.toString('\u221e');
        }
        return Double.toString(this.distance1);

    }
    public String getStringHeight(){
        if(this.height==Double.POSITIVE_INFINITY){
            return Character.toString('\u221e');
        } else if (this.height==Double.NEGATIVE_INFINITY) {
            return "-"+Character.toString('\u221e');
        }
        return Double.toString(this.height);

    }

    public String getStringVirtual(){
        return (isVirtual==true?"Virtual":"Real");
    }

    public double getDistance() {
        return distance1;
    }
    
    public double getDistance2() {
    	return distance2;
    }

    public double getHeight() {
        return height;
    }

    public void setDistance(double distance) {
        this.distance1 = distance;
    }
    
    public void setDistance2(double distance) {
    	this.distance2 = distance;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getType(){
        return (isVirtual==true?"Virtual":"Real")+(height==0.0?", Point Image":"");
    }

}

