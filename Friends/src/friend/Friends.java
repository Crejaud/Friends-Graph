//Corentin Rejaud
//Julia Sutula
package friend;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.HashMap;
import java.io.FileNotFoundException;
public class Friends{
	static Scanner stdin = new Scanner(System.in);
	
	static Graph friendshipGraph = null;    
	
	public static void main (String[] args) throws FileNotFoundException{
		try {
			System.out.print("Enter the name of file needed to build graph: ");
		
			String fileName = stdin.next();
			friendshipGraph = new Graph(fileName);	
		} catch (FileNotFoundException e) {
		    System.err.println("FileNotFoundException: " + e.getMessage());
		    throw new FileNotFoundException();
		}
	
		
		boolean checker = true;
		do{
		char option = getOption();
		if(option =='1')
		{	
			System.out.print("Enter the name of school: ");
			String school = stdin.nextLine();
			Graph resultGraph = getStudents(school);
			if (resultGraph != null) { resultGraph.printInput(); }
		}
		else if(option == '2')
		{	
			System.out.print("Enter the name of person who wants the intro: ");
			String name1 = stdin.nextLine();
			System.out.print("Enter the name of the other person: ");
			String name2 = stdin.nextLine();
			String temp=findShortestPath(name1, name2);
			if(temp == null)
				System.out.println("Sorry, there is no introduction path from " + name1 + " to " + name2);
			else
				System.out.println(temp);
			
			
		}
		else if(option == '3')
		{	
			System.out.print("Enter the name of school: ");
			String school = stdin.nextLine();
			Cliques(school);
		}
		else if(option == '4')
		{	
			friendshipGraph.connectors();
		}
		else if(option == '5')
			checker = false;
	}while(checker == true);
	}
	
	
	
	static char getOption() {
		System.out.println("\tChoose action: ");
		System.out.println("(1) Subgraph: Students at school, ");
		System.out.println("(2) Shortest path: Intro chain, ");
		System.out.println("(3) Connected Islands: Cliques at school, ");
		System.out.println("(4) Connectors: Friends who keep friends together, ");
		System.out.println("(5) Quit");
		char response = stdin.next().charAt(0);
		stdin.nextLine();
		while (response != '1' && response != '2' && response != '3' && response != '4' && response != '5' && response != '6') {
			System.out.print("\tYou must enter a valid number 1-6 =>");
			response = stdin.next().charAt(0);
			stdin.nextLine();
		}
		return response;
	}
	
	public static String findShortestPath(String name1, String name2)
	{
		name1 = name1.toLowerCase();
		name2 = name2.toLowerCase();
		Vertex source = friendshipGraph.findVertex(friendshipGraph, name1);
		Vertex target = friendshipGraph.findVertex(friendshipGraph, name2);
		Vertex current = null;
		String chain = "";
        ArrayList<Vertex> shortestPath = new ArrayList<Vertex>();
        Queue<Vertex> q = new Queue<Vertex>();
        q.enqueue(source);
        HashMap<Vertex, Boolean> visited = new HashMap<Vertex, Boolean>();
        HashMap<Vertex, Vertex> previous = new HashMap<Vertex, Vertex>();
        visited.put(source, true);
        while(!q.isEmpty()) {
          	current = q.dequeue();
        	if(target==null)
              	return null;
          	if(current==null)
              	return null;
         	if (current.equals(target)) {
              	 break;
         	} else {
         		Neighbor nbr = current.adjList;
         		
               	while (nbr != null) {
                   	if (!visited.containsKey(friendshipGraph.findVertexUsingNum(friendshipGraph, nbr.vertexNum))) {       	 
                     	q.enqueue(friendshipGraph.findVertexUsingNum(friendshipGraph, nbr.vertexNum));
                      	visited.put(friendshipGraph.findVertexUsingNum(friendshipGraph, nbr.vertexNum), true);
                     	previous.put(friendshipGraph.findVertexUsingNum(friendshipGraph, nbr.vertexNum), current);               
                   	}      
                	nbr = nbr.next;
               	}	
         	} 
        } if (!current.equals(target)) {
          	return null;
        } else for(Vertex v = target; v != null; v = previous.get(v)) {
                 shortestPath.add(v);
        }
       	Collections.reverse(shortestPath);
         
      	if (shortestPath != null) {
          	if (shortestPath.size() == 0) {
             	chain += "These people cannot meet right now.";
          	} else for (int i = 0; i < shortestPath.size(); i++) {
              	chain += shortestPath.get(i).name + (i < shortestPath.size() - 1 ? "<-->": "");
          	}        
      	}
         
        return chain;
	}
	
	public static String sameSchoolPath(String name1, String name2, String school)
	{
		school = school.toLowerCase();
		Vertex source = friendshipGraph.findVertex(friendshipGraph, name1);
		Vertex target = friendshipGraph.findVertex(friendshipGraph, name2);
		Vertex current = null;
		String chain = "";
        ArrayList<Vertex> shortestPath = new ArrayList<Vertex>();
        Queue<Vertex> q = new Queue<Vertex>();
        q.enqueue(source);
        HashMap<Vertex, Boolean> visited = new HashMap<Vertex, Boolean>();
        HashMap<Vertex, Vertex> previous = new HashMap<Vertex, Vertex>();
        visited.put(source, true);
        while(!q.isEmpty()) {
         	current = q.dequeue();
          	if(target==null)
              	return null;
           	if(current==null)
              	return null;
         	if (current.equals(target)) {
               	break;
         	} else {
             	Neighbor nbr = current.adjList;
             	
               	while (nbr != null) {
                 	if (!visited.containsKey(friendshipGraph.findVertexUsingNum(friendshipGraph, nbr.vertexNum))) {   	 
                     	q.enqueue(friendshipGraph.findVertexUsingNum(friendshipGraph, nbr.vertexNum));
                       	visited.put(friendshipGraph.findVertexUsingNum(friendshipGraph, nbr.vertexNum), true);
                      	previous.put(friendshipGraph.findVertexUsingNum(friendshipGraph, nbr.vertexNum), current);     
                 	}           
                   	nbr = nbr.next;
               	}
         	}
        } if (!current.equals(target)) {
          	return null;
        } else for(Vertex v = target; v != null; v = previous.get(v)) {
          	shortestPath.add(v);
        }
        Collections.reverse(shortestPath);
         
      	if (shortestPath != null) {
          	if (shortestPath.size() == 0) {
              	chain += "These people cannot meet right now.";
          	} else for (int i = 0; i < shortestPath.size(); i++) {
               	chain += shortestPath.get(i).name + (i < shortestPath.size() - 1 ? "<-->": "");
               	if (shortestPath.get(i).isInSchool) {
                  	if (!shortestPath.get(i).schoolName.equals(school)) {
                       	return null;
                  	}
               	} else {
                 	return null;
               	}
          	}        
      	}
         
        return chain;
	}
	
	public static Graph getStudents(String school) {
		int numStudents=0;
		school = school.toLowerCase();
		ArrayList<Integer> originalGraphIndexs = new ArrayList<Integer>();
		for(int i = 0; i < friendshipGraph.adjLists.length; i++) {//gets num of students
			if(friendshipGraph.adjLists[i].isInSchool) {
				if(friendshipGraph.adjLists[i].schoolName.equals(school)) {
					originalGraphIndexs.add(i);
					numStudents++;
				}
			}
		}
		
		if (numStudents == 0) {
			System.out.println("There are no students that are attending " + school);
			return null;
		}
		
		Graph subgraph = new Graph(numStudents);
		int newIndex = 0;
		for(int i = 0; i < friendshipGraph.adjLists.length; i++) { //adds all students that go to specific school to subgraph
			Vertex temp = friendshipGraph.adjLists[i];
			if(friendshipGraph.adjLists[i].isInSchool) {
				if(friendshipGraph.adjLists[i].schoolName.equals(school)) {
					subgraph.addVertex(temp.name, null, temp.schoolName, temp.isInSchool, newIndex);
					newIndex++;
				}
			}
		}
		
		for(int i = 0; i < subgraph.adjLists.length; i++) { //gets neighbors for all students to go to a specific school
			Neighbor newRoot = null;
			Neighbor newNbr = newRoot;
			Neighbor originalNbr = friendshipGraph.adjLists[originalGraphIndexs.get(i)].adjList;
			boolean firstTime = true;
			while (originalNbr != null) {
				if (originalNbr.isInSchool) {
					if (originalNbr.vertexSchool.equals(school)) {
						//must find the corresponding vertexNum for newNbr in relation to the subgraph.adjLists[]
						boolean isFound = false;
						int j = 0;
						for (j = 0; j < subgraph.adjLists.length; j++) {
							if (isFound) { break; }
							if (originalNbr.vertexName.equals(subgraph.adjLists[j].name)) {
								if (originalNbr.isInSchool && subgraph.adjLists[j].isInSchool){
									if (originalNbr.vertexSchool.equals(subgraph.adjLists[j].schoolName)) {
										isFound = true;
									}
								}
							}
						}
						if (firstTime) {
							newRoot = new Neighbor(originalNbr.vertexName, originalNbr.vertexSchool, originalNbr.isInSchool, j, null);
							newNbr = newRoot;
							firstTime = false;
						} else {
							newNbr = new Neighbor(originalNbr.vertexName, originalNbr.vertexSchool, originalNbr.isInSchool, j, null);
							newRoot.addToEnd(newNbr);
						}
						newNbr = newNbr.next;
					}
				}
				originalNbr = originalNbr.next;
			}
			subgraph.adjLists[i].adjList = newRoot;
		}
		return subgraph;
	}
	public static ArrayList<Graph> Cliques(String school) {
		int numStudents=0;
		school = school.toLowerCase();
		ArrayList<Integer> originalGraphIndexs = new ArrayList<Integer>();
		for(int i = 0; i < friendshipGraph.adjLists.length; i++) {//gets num of students
			if(friendshipGraph.adjLists[i].isInSchool) {
				if(friendshipGraph.adjLists[i].schoolName.equals(school)) {
					originalGraphIndexs.add(i);
					numStudents++;
				}
			}
		}
		
		if (numStudents == 0) {
			System.out.println("There are no students that attending " + school);
			return null;
		}
		
		
		ArrayList<Graph> arrSubgraph = new ArrayList<Graph>();
		Graph result = getStudents(school);
		for (int i = 0; i < result.adjLists.length; i++) {
			ArrayList<Vertex> island = new ArrayList<Vertex>();
			Vertex temp = result.adjLists[i];
			island.add(temp);
			for (int j = 0; j < result.adjLists.length; j++) {
				if (i != j) {
					if (sameSchoolPath(temp.name, result.adjLists[j].name, school) != null) { //valid path between vertexes
						island.add(result.adjLists[j]);
					}
				}
			}
			if (!isInstanceOf(arrSubgraph, island)) { //if island doesn't exists in graph arraylist, then add to graph arraylist
				//make a subgraph out of island
				Vertex[] newAdjList = new Vertex[island.size()];
				for(int c = 0; c < newAdjList.length; c++) {
					newAdjList[c] = island.get(c);
				}
				Graph subgraph = new Graph(newAdjList);
				arrSubgraph.add(subgraph);
			}
		}
		int cliqueNum = 0;
		
		if (arrSubgraph.size() == 0) { System.out.println("There are no cliques."); }
		
		for (int i = 0; i < arrSubgraph.size(); i++) {
			cliqueNum = i + 1;
			System.out.println("Clique " + cliqueNum + ":");
			arrSubgraph.get(i).printInput();
		}
		
		return arrSubgraph;
	}
	public static boolean isInstanceOf(ArrayList<Graph> arrSubgraph, ArrayList<Vertex> vertexes) {
		boolean isSame;
		for (int i = 0; i < arrSubgraph.size(); i++) {
			isSame = true;
			for (int j = 0; j <  arrSubgraph.get(i).adjLists.length; j++) {
				for (int k = 0; k < vertexes.size(); k++) {
					if (arrSubgraph.get(i).adjLists[j].name.equals(vertexes.get(k).name)) {
						isSame = false;
					}
				}
				if (!isSame) {
					return true;
				}
			}
		}
		return false;
		
	}
	
}
