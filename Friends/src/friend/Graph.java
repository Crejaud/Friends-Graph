package friend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Graph {

	Vertex[] adjLists;
	
	boolean undirected=true;
	
	public Graph(String file) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File(file));
		adjLists = new Vertex[sc.nextInt()];
		sc.nextLine();
		// read vertices
		for (int v=0; v < adjLists.length; v++) {
			String s = sc.nextLine();
			StringTokenizer st = new StringTokenizer(s, "|");
			
			String name = st.nextToken().toLowerCase(); //gets name of person, NOT CASE SENSITIVE
			String inSchool = st.nextToken().toLowerCase(); //Should get y or n, NOT CASE SENSITIVE
			while(!inSchool.equals("y") && !inSchool.equals("n")) {
				name = name + inSchool;
				inSchool = st.nextToken().toLowerCase();
			}
			//Inserts value for isInSchool with y or n
			Boolean isInSchool = false;
			if (inSchool.equals("y")) { isInSchool = true; }
			else if (inSchool.equals("n")) { isInSchool = false; }
			else { System.out.println("Error: token does not say y or n"); System.exit(0); } //IF NOT Y OR N
			
			String schoolName = null;
			if(isInSchool) {
				schoolName = st.nextToken().toLowerCase(); //gets school name, NOT CASE SENSITIVE
				while(st.hasMoreTokens()) { //ensures that it gets full name (ex: penn state)
					schoolName = schoolName + " " + st.nextToken().toLowerCase();
				}
			}
			
			
			adjLists[v] = new Vertex(name, null, schoolName, isInSchool);
		}

		// read edges
		while (sc.hasNext()) {
			StringTokenizer st = new StringTokenizer(sc.nextLine().toLowerCase(), "|");
			// read vertex names and translate to vertex numbers
			int v1 = indexForName(st.nextToken().toLowerCase());
			int v2 = indexForName(st.nextToken().toLowerCase());
			
			// add v2 to front of v1's adjacency list and
			// add v1 to front of v2's adjacency list
			adjLists[v1].adjList = new Neighbor(adjLists[v2].name, adjLists[v2].schoolName, adjLists[v2].isInSchool, v2, adjLists[v1].adjList);
			adjLists[v2].adjList = new Neighbor(adjLists[v1].name, adjLists[v1].schoolName, adjLists[v1].isInSchool, v1, adjLists[v2].adjList);
		}
	}
	
	public Graph()
	{
		adjLists = new Vertex[0];
	}
	public Graph(int count)
	{	//constructor for a subgraph
		adjLists = new Vertex[count];
	
	}
	public Graph(Vertex[] adjLists) {
		this.adjLists = adjLists;
	}
	 public void addVertex(String name, Neighbor neighbors, String schoolName, Boolean isInSchool, int index) {
		
         adjLists[index] = new Vertex(name, neighbors, schoolName, isInSchool);
    }
	
	public int countEdges() {
		int edges=0;
		for (int v=0; v < adjLists.length; v++) {
			for (Neighbor n=adjLists[v].adjList;
					n != null; n=n.next) {
				edges++;
			}
		}
		if (undirected) {
			edges /= 2;
		}
		return edges;
		
  	}
	
	int indexForName(String name) {
		for (int v=0; v < adjLists.length; v++) {
			if (adjLists[v].name.equals(name)) {
				return v;
			}
		}
		return -1;
	}	
	
	public void print() {
		System.out.println();
		for (int v=0; v < adjLists.length; v++) {
			System.out.print(adjLists[v].name + " | " + adjLists[v].schoolName);
			for (Neighbor nbr=adjLists[v].adjList; nbr != null;nbr=nbr.next) {
				System.out.print(" --> " + adjLists[nbr.vertexNum].name + " | " + adjLists[nbr.vertexNum].schoolName);
			}
			System.out.println("\n");
		}
	}
	
	public void printInput() {
		System.out.println(adjLists.length);
		for (int v=0; v < adjLists.length; v++) {
			if(adjLists[v].isInSchool) {
				System.out.println(adjLists[v].name + "|y|" + adjLists[v].schoolName);
			} else {
				System.out.println(adjLists[v].name + "|n");
			}
		}
		
		ArrayList<String> dup = new ArrayList<String>(); //stores already printed neighbor pairs to ensure that duplicates are not printed
		for (int i = 0; i < adjLists.length; i++) { //goes through everyone's neighbors and prints them out without duplicates
			Neighbor ptr = adjLists[i].adjList;
			
			
			while(ptr != null) {
				boolean isDup = false;
				for (int j = 0; j < dup.size(); j++) {
					if (isDup) { break; }
					if (dup.get(j).equals(ptr.vertexName + " " + adjLists[i].name) || dup.get(0).equals(adjLists[i].name + " " + ptr.vertexName)){
						isDup = true;
					}
				}
				if (!isDup) {
					System.out.println(adjLists[i].name + "|" + ptr.vertexName);
					dup.add(adjLists[i].name + " " + ptr.vertexName);
				}
				ptr = ptr.next;
			}
		}
		
	}
	
	public Vertex findVertex(Graph graph, String target)
	 {
		 for(int i = 0; i < graph.adjLists.length; i++)
			{
				if(graph.adjLists[i].name.equals(target))
				{
					
					return graph.adjLists[i];				}
			}
		 return null;
	 }
	 
	 public Vertex findVertexUsingNum(Graph graph,  int num)
	 {
		 for(int i = 0; i < graph.adjLists.length; i++)
			{
				if(findVertexNumber(graph.adjLists[i]) == num)
				{
					
					return graph.adjLists[i];				}
			}
		 return null;
	 }
	 public int findVertexNumber(Vertex vertex)
	 {
		 for (int v=0; v < adjLists.length; v++) 
		 {
			 if(adjLists[v].name.equals(vertex.name))
				 return v;
		 }
		 return -1;
	 }
	
	 public void connectors() {
		 int dfsNumCounter = 0, backNumCounter = 0;
         boolean[] visited = new boolean[adjLists.length];
         int[] dfsNum = new int[adjLists.length];
         int[] backNum = new int[adjLists.length];
         int[] connectors = new int[adjLists.length];
         
         for (int i = 0; i < connectors.length; i++) {
        	 connectors[i] = 0;
         }

         for (int i = 0; i < adjLists.length; i++) {
          	if (!visited[i]) {
              	connectors[i] = 1;
               	//3 = connector
              	//2 = starting point that can be a connector
              	//1 = starting point but not necessarily connector
              	//0 = not connector
                         
              	connectorsDFS(adjLists[i], visited, dfsNum, backNum, dfsNumCounter, backNumCounter, connectors);
          	}
         }
         
         System.out.print("Connectors are: ");
         boolean firstTime = true;
         for (int i = 0; i < adjLists.length; i++) {
                 if (connectors[i] == 3) {
                	 if (firstTime) {
                         System.out.print(adjLists[i].name);
                         firstTime = false;
                	 } else {
                		 System.out.print(", " + adjLists[i].name);
                	 }
                 }
         }
         System.out.println();
	 }
 
	 private void connectorsDFS(Vertex vertex, boolean[] visited, int[] dfsNum, int[] backNum,int dfsNumCounter, int backNumCounter, int[] connectors) {
         
	 	int vertexIndex = findVertexNumber(vertex);
	 	visited[vertexIndex] = true;
	 	dfsNum[vertexIndex] = dfsNumCounter;
	 	dfsNumCounter++;        
	 	backNum[vertexIndex] = backNumCounter;
	 	backNumCounter++;
         
	 	Neighbor neighbor = vertex.adjList;
	 	while (neighbor != null) {
                 
	 		if (!visited[neighbor.vertexNum]) {
	 			Vertex next = adjLists[neighbor.vertexNum];
	 			connectorsDFS(next, visited, dfsNum, backNum, dfsNumCounter, backNumCounter, connectors);
                         
	 			if (dfsNum[vertexIndex] > backNum[neighbor.vertexNum]) {
	 				backNum[vertexIndex] = Math.min(backNum[vertexIndex], backNum[neighbor.vertexNum]);
	 			} else {
                         
                	if (connectors[vertexIndex] == 0) {
                     	connectors[vertexIndex] = 3;
                	}
                   	else if (connectors[vertexIndex] == 1) {
                     	connectors[vertexIndex] = 2;
                   	}
                   	else if (connectors[vertexIndex] == 2) {
                     	connectors[vertexIndex] = 3;
                   	}
	 			}
	 		}
         	else {
             	backNum[vertexIndex] = Math.min(backNum[vertexIndex], dfsNum[neighbor.vertexNum]);
         	}
          	neighbor = neighbor.next;
	 	}
	 }
	public Graph (Graph graph) {
		adjLists = graph.adjLists;
	}
}