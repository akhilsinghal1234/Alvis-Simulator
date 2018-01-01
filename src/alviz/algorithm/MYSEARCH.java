
package alviz.algorithm;

import alviz.base.algorithm.Algorithm;
import alviz.base.graph.BaseGraph;
import alviz.base.graph.BaseGraph.Edge;
import alviz.util.Pair;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;
import java.util.*;  
/**
 *
 * @author baskaran
 */
public class MYSEARCH extends Algorithm {

    private BaseGraph graph;
    private Stack<Pair<BaseGraph.Node,Integer >> open;
    private LinkedList<Pair<BaseGraph.Node,BaseGraph.Node>> closed;
    private boolean done=false;
    private Stack<Pair<Integer,Integer >> open3;

    public MYSEARCH(BaseGraph graph) {
        super();
        this.graph = graph;
        this.open = null;
        this.closed = null;
    }

    public void execute() throws Exception {
        pairPriority ED= new pairPriority();
        open = new Stack<Pair<BaseGraph.Node,Integer>>();
        closed = new LinkedList<Pair<BaseGraph.Node,BaseGraph.Node>>();
//        open3=new Stack<Pair<Integer, Integer>>();
        BaseGraph.Node G ;
        G = (BaseGraph.Node) graph.getGoalNodes().get(0);
        openNode(graph.getStartNode(), null,G ,0);
        float U=100000;
        int W=4;
        mysearch(G, U ,W);
        generatePath();
//        System.out.println("reached here");
        setStateEnded();
//        System.out.println("reached here too");
        //show();
//        System.out.println("reached here also");
    }

    private void openNode(BaseGraph.Node n, BaseGraph.Node p , BaseGraph.Node G  ,int level) {
        if (n != null) {
            if(p!=null){
            graph.openNode(n, p);
            n.set_f_value(G);
            open.push(new Pair(n,level));
            }
            else{
                graph.openNode(n, p);
                n.dist_from_source=(float) 0.0;
                n.set_Heuristic(G);
                n.f_value=n.heuristic;
                open.push(new Pair(n,level));
            }
        }
    }
    
    private void closeNode(BaseGraph.Node n,BaseGraph.Node p) {
//        BaseGraph.Node n = pn.fst;
        if (n != null) {
            graph.closeNode(n);
            closed.add(new Pair(n,p));
        }
    }

  
    
    public class edgePriority implements Comparator<Edge>{  
        public int compare(Edge o1,Edge  o2){  
        Edge s1 =(Edge )o1;  
        Edge  s2=(Edge )o2;  
        if(s1.cost==s2.cost)  
        return 0;  
        else if(s1.cost>s2.cost)  
        return 1;  
        else  
        return -1;  
        }  

}
    public class baseGraphNodeLevelPriority implements Comparator<Pair<BaseGraph.Node,Integer>>{ 
       public int compare(Pair<BaseGraph.Node, Integer> o1, Pair<BaseGraph.Node, Integer> o2) {
        BaseGraph.Node s1 =(BaseGraph.Node )o1.fst;  
        BaseGraph.Node  s2=( BaseGraph.Node )o2.fst;  
          double s1v;
           s1v = s1.get_f_value();
           double s2v;
           s2v = s2.get_f_value();

        if(s1v==s2v)  
        return 0;  
        else if( s1v > s2v)  
        return 1;  
        else  
        return -1;
       }
    }
    
    public class pairPriority implements Comparator<Pair< BaseGraph.Node , BaseGraph.Node >>{  
        public int compare(Pair<BaseGraph.Node, BaseGraph.Node> o1,Pair<BaseGraph.Node, BaseGraph.Node>  o2){  
        Pair<BaseGraph.Node, BaseGraph.Node> s1 =(Pair<BaseGraph.Node, BaseGraph.Node> )o1;  
        Pair<BaseGraph.Node, BaseGraph.Node>  s2=(Pair<BaseGraph.Node, BaseGraph.Node> )o2;  
        int xs1=s1.fst.x;
        int ys1=s1.fst.y;
        int x1s1=s1.snd.x;
        int y1s1=s1.snd.y;
        float cost1=(xs1-x1s1)*(xs1-x1s1)+(ys1-y1s1)*(ys1-y1s1);
        xs1=s2.fst.x;
        ys1=s2.fst.y;
        x1s1=s2.snd.x;
        y1s1=s2.snd.y;
        float cost2=(xs1-x1s1)*(xs1-x1s1)+(ys1-y1s1)*(ys1-y1s1);
        if(cost1==cost2)  
        return 0;  
        else if( cost1> cost2)  
        return 1;  
        else  
        return -1;  
        }  

}

//    private void mysearch( BaseGraph.Node G ) throws Exception {
//        if (!open.isEmpty()) {
//            Pair<BaseGraph.Node, BaseGraph.Node> ph = open.poll();
//            BaseGraph.Node h = ph.fst;
//            if (graph.goalTest(h)){
//                closeNode(ph);
//                h.setGoal();
//                done = true;
//            }
//            else {
//                List<BaseGraph.Node> neighbours = graph.moveGen(h);
//                if (neighbours != null) {
//                    for (BaseGraph.Node n : neighbours) {
//                        if (n.isCandidate()) {
//                            openNode(n, h ,G );
//                        }
//                    }
//                }
//                closeNode(ph);
//            }
//            //show();
//            if (!done) {
//                mysearch( G );
//            }
//        }
//    }
    private void mysearch( BaseGraph.Node G , float U ,int W ) throws Exception {
       
        int i;
        show();
        while(!open.isEmpty())
        {
            LinkedList<Pair<BaseGraph.Node,Integer>> children;
            children = new LinkedList<Pair<BaseGraph.Node,Integer>>();
            i=0;
            int local_level=open.peek().snd;
            show();
//            System.out.println("fhhdgfdhghfgdhfghdbhd");
            while((!(open.isEmpty())) && (open.peek().snd == local_level) && (i <W)  ) // 
            {
                BaseGraph.Node n=open.pop().fst;
//                //show();
                i=i+1;
//                System.out.println(i);
                 closeNode(n,n.mgParent);
                if (graph.goalTest(n)){
                    
                    if(U > n.dist_from_source)
                    {
                        U=n.dist_from_source;
                        generatePath();
//                        generate1Path(n);
                        show();
                    }
                }
                
                else{
//                    closed.push(n);
                    List<BaseGraph.Node> neighbours = graph.moveGen(n);
                     //if (neighbours != null) {
                    for (BaseGraph.Node n1 : neighbours) {
                        if (n1.isCandidate()) {
                            
                            openNode(n1, n ,G ,local_level+1);
                            children.add(new Pair(n1,(local_level+1)));
//                            System.out.println(i);
                        }
                    }
//                    closeNode(n,n.mgParent);

                    ////show();
                        //}
                }
            }
            if(children.isEmpty())
            {
            }
            else
            {
//                local_level++;
                baseGraphNodeLevelPriority LP= new baseGraphNodeLevelPriority();
                children.sort(LP);
                while(!children.isEmpty())
                {
                  Pair<BaseGraph.Node,Integer > temp=children.pollLast();
//                  System.out.println(temp.fst.f_value);
//                  System.out.println();
                  open.push(temp);
//                  temp.setOpen();
                }
                ////show();
                
            }
            ////show();
        }
        //show();
        
        
    }

//    private List<BaseGraph.Node> generatePath() {
//        List<BaseGraph.Node> path=null;
//        if (closed == null) return path;
//        if (closed.isEmpty()) return path;
//
//        Iterator<Pair<BaseGraph.Node,Integer>> closedList = ((LinkedList)closed).descendingIterator();
//        if (!closedList.hasNext()) return path;
//
//        Pair<BaseGraph.Node,Integer> pair = closedList.next();
//        if (!pair.fst.isGoal()) {
//            //System.out.printf("generatePath: pair.fst %s\n", pair.fst.getState().toString());
//            return path;
//        }
//
//        path = new LinkedList<BaseGraph.Node>();
//        // add to path
//        pair.fst.setPath();
//        path.add(pair.fst);
//        while (pair.snd != 0) {
//
//            // add to path
//            BaseGraph.Node temp=pair.fst.mgParent;
//            temp.setPath();
//            path.add(temp);
//
//            // add edge to path
////            BaseGraph.Edge e = graph.getEdge(pair.fst, temp);
//            BaseGraph.Edge e = pair.fst.mgEdge ;
//
//            if (e != null) {
//                e.setPath();
//            }
//
//            // search for predecessor pair
//            BaseGraph.Node n = temp;
//            while (closedList.hasNext()) {
//                pair = closedList.next();
//                if (pair.fst == n) break;
//            }
//        }
//
//        return path;
//    }
    private List<BaseGraph.Node> generatePath() {
        List<BaseGraph.Node> path=null;
        if (closed == null) return path;
        if (closed.isEmpty()) return path;

        Iterator<Pair<BaseGraph.Node,BaseGraph.Node>> closedList = ((LinkedList)closed).descendingIterator();
        if (!closedList.hasNext()) return path;

        Pair<BaseGraph.Node,BaseGraph.Node> pair = closedList.next();
        if (!pair.fst.isGoal()) {
            //System.out.printf("generatePath: pair.fst %s\n", pair.fst.getState().toString());
            return path;
        }

        path = new LinkedList<BaseGraph.Node>();
        // add to path
        pair.fst.setPath();
        path.add(pair.fst);
        while (pair.snd != null) {

            // add to path
            pair.snd.setPath();
            path.add(pair.snd);

            // add edge to path
            BaseGraph.Edge e = graph.getEdge(pair.fst, pair.snd);
            if (e != null) {
                e.setPath();
            }

            // search for predecessor pair
            BaseGraph.Node n = pair.snd;
            while (closedList.hasNext()) {
                pair = closedList.next();
                if (pair.fst == n) break;
            }
        }

        return path;
    }  
    public void generate1Path(BaseGraph.Node n ) {
       if(n==null)
       {
        return ;   
       }
       else 
       {
           n.setPath();
           n.mgParent.setPath();
           BaseGraph.Edge e = graph.getEdge(n, n.mgParent);
            if (e != null) {
                e.setPath();
            }
            generate1Path(n.mgParent);
            return ;
       }
    }  
}
