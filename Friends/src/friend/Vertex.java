package friend;

public class Vertex {
    String name;
    String schoolName;
    Boolean isInSchool;
    Neighbor adjList;
    Vertex(String name, Neighbor neighbors, String schoolName, Boolean isInSchool) {
     	this.name = name;
      	this.adjList = neighbors;
      	this.isInSchool = isInSchool;
    	if (isInSchool) {
          	this.schoolName = schoolName;
    	} else {
          	schoolName = null;
    	}
    }
}