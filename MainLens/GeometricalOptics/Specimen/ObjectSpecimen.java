package MainLens.GeometricalOptics.Specimen;

public class ObjectSpecimen{
	double distance1;
    double distance2;
    double height;
    float angle;
    boolean isVirtual;

    public ObjectSpecimen(double distance, double height){
        this.distance1 = distance;
        this.distance2 = distance;
        this.height = height;
        this.angle = 0;
        this.isVirtual = true;
    }
    
    public ObjectSpecimen(double distance1, double distance2, double height) {
    	this.distance1 = distance1;
    	this.distance2 = distance2;
    	this.height = height;
    	this.isVirtual = true;
    }
    
    public boolean isVirtual() {
        return isVirtual;
    }

    public void setVirtual(boolean virtual) {
        isVirtual = virtual;
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
        } return Double.toString(this.height);

    }

    public String getType(){
        return (distance1>0?"Virtual":"Real")+(height==0?", Point Object":"");
    }

}
