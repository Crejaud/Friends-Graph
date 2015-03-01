package friend;

public class Neighbor {
    public String vertexName;
    public int vertexNum;
    public String vertexSchool;
    public boolean isInSchool;
    public Neighbor next;
    public Neighbor(String vName, String vSchool, boolean isInSchool, int vertexNum, Neighbor nbr) {
     	this.vertexName = vName;
      	this.vertexNum = vertexNum;
       	this.vertexSchool = vSchool;
       	this.isInSchool = isInSchool;
       	next = nbr;
    }
    public void addToEnd(Neighbor nbr){
	    Neighbor ptr = this;
	    while(ptr.next!=null) {
	    	ptr = ptr.next;
	    }
	    ptr.next = new Neighbor(nbr.vertexName, nbr.vertexSchool, nbr.isInSchool, nbr.vertexNum, null);
	}
}