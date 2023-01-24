import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class withAVL<AnyType extends Comparable<? super AnyType>> {

    public FileWriter fWriter;
    withAVL(FileWriter fWriter){
        this.fWriter = fWriter;
    }

    private static class AvlNode<AnyType>{
            // Constructors
        AvlNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
        }

        AnyType           element;      
        AvlNode<AnyType>  left;         
        AvlNode<AnyType>  right;        
        int               height;       
    }
    private AvlNode<AnyType> root;

    public void avlTree(AnyType ip){
        root = new AvlNode<AnyType>(ip);
    }
    private int height( AvlNode<AnyType> t )
    {
        return t == null ? -1 : t.height;
    }
    private int checkBalance( AvlNode<AnyType> t )
    {
        if (t != null){
            int val = height(t.left) - height(t.right);
            return val;
        }
        return 0;        
    }
    
    
    private AvlNode<AnyType> balance( AvlNode<AnyType> t ) throws IOException
    {
        //TODO
        int valBalance = checkBalance(t);
        if (valBalance>1 && checkBalance(t.left)>=0){
            // left left 
            AvlNode<AnyType> x = t.left;
            AvlNode<AnyType> z = x.right;
            x.right = t; 
            t.left = z;
            t.height = Math.max(height(t.left),height(t.right)) + 1;
            x.height = Math.max(height(x.left),height(x.right)) + 1;
            
            fWriter.write("Rebalancing: right rotation"+"\n");
            return x;
        }
        if (valBalance>1 && checkBalance(t.left)<0){
            // left right
            AvlNode<AnyType> a = t.left;
            AvlNode<AnyType> c = a.right;
            AvlNode<AnyType> T2 = c.left;
            AvlNode<AnyType> T3 = c.right;
            c.left = a;
            c.right = t;
            a.right = T2;
            t.left = T3;
            t.height = Math.max(height(t.left),height(t.right)) + 1;
            a.height = Math.max(height(a.left),height(a.right)) + 1;
            c.height = Math.max(height(c.left),height(c.right)) + 1;

            fWriter.write("Rebalancing: left-right rotation"+"\n");
            return c;
        }
        if (valBalance<-1 && checkBalance(t.right)<=0){
            // right right
            AvlNode<AnyType> x = t.right;
            AvlNode<AnyType> y = x.left;
            x.left = t;
            t.right = y;
            t.height = Math.max(height(t.left),height(t.right)) + 1;
            x.height = Math.max(height(x.left),height(x.right)) + 1;
            
            fWriter.write("Rebalancing: left rotation"+"\n");
            return x;
        }
        if (valBalance<-1 && checkBalance(t.right)>0){
            // right left
            AvlNode<AnyType> a = t.right;
            AvlNode<AnyType> c = a.left;
            AvlNode<AnyType> T2 = c.left;
            AvlNode<AnyType> T3 = c.right;
            c.left = t;
            c.right = a;
            t.right = T2;
            a.left = T3;
            t.height = Math.max(height(t.left),height(t.right)) + 1;
            a.height = Math.max(height(a.left),height(a.right)) + 1;
            c.height = Math.max(height(c.left),height(c.right)) + 1;
            
            fWriter.write("Rebalancing: right-left rotation"+"\n");
            return c;

        }

        return t;
    }
    

    
    public void insert( AnyType x ) throws IOException{
        
        root = insert( x, root );
        
    }

    private AvlNode<AnyType> insert( AnyType x, AvlNode<AnyType> t ) throws IOException{
        if( t == null )
            return new AvlNode<>( x, null, null );

        fWriter.write(t.element+": New node being added with IP:"+x+"\n");
        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ; 
        t.height = 1 + Math.max(height(t.left), height(t.right));
        return balance(t);
    }

    

    public void remove( AnyType x ) throws IOException
    {
        root = remove( x, root, null );
    }

    private AvlNode<AnyType> remove( AnyType x, AvlNode<AnyType> t, AvlNode<AnyType> p) throws IOException
    {
        if( t == null )
            return t; 
                  
        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = remove( x, t.left, t );
        else if( compareResult > 0 )
            t.right = remove( x, t.right, t );
        
        else if( t.left != null && t.right != null ) 
        {   
            t.element = findMin( t.right ).element;
            fWriter.write(p.element+": Non Leaf Node Deleted; removed: "+x+" replaced: "+t.element+"\n");
            t.right = balance(remove2( t.element, t.right , t));
            
        }
        else if ((t.left == null && t.right != null) || (t.left != null && t.right == null)){
            fWriter.write(p.element+": Node with single child Deleted: "+x+"\n");
            t = ( t.left != null ) ? t.left : t.right;
        }
        else{
            fWriter.write(p.element+": Leaf Node Deleted: "+x+"\n");
            t = null;
        }
        if (t != null ){
            t.height = Math.max(height(t.left), height(t.right)) + 1;
        }
        return balance(t);
    }
    private AvlNode<AnyType> remove2( AnyType x, AvlNode<AnyType> t, AvlNode<AnyType> p) throws IOException
    {
        
        if( t == null )
            return t;  
        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = remove2( x, t.left, t );
        else if( compareResult > 0 )
            t.right = remove2( x, t.right, t );
        
        else if( t.left != null && t.right != null ) 
        {   
            t.element = findMin( t.right ).element;
            t.right = remove2( t.element, t.right , t);
        }
        else if ((t.left == null && t.right != null) || (t.left != null && t.right == null)){
            t = ( t.left != null ) ? t.left : t.right;
        }
        else{
            t = null;
        }
        if (t != null ){
            t.height = Math.max(height(t.left), height(t.right)) + 1;
        }
        return balance(t);
    }
    

    private AvlNode<AnyType> findMin( AvlNode<AnyType> t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

    private ArrayList<String> traverseSend(AvlNode<AnyType> t, AnyType new_id, ArrayList<String> res, AnyType send_ip, AnyType receiver_ip) {
        if (new_id == send_ip){
        
            int compareResult = new_id.compareTo( t.element );

            if( compareResult < 0 ){
                res.add(t.element+": Transmission from: "+t.left.element+" receiver: "+receiver_ip+" sender:"+send_ip+"\n");
                return traverseSend(t.left, new_id, res, send_ip, receiver_ip);
            }
            else if( compareResult > 0 ){
                res.add(t.element+": Transmission from: "+t.right.element+" receiver: "+receiver_ip+" sender:"+send_ip+"\n");
                return traverseSend(t.right, new_id, res, send_ip, receiver_ip);
        }
            else{            
                return res;
            }
        }
        if (new_id == receiver_ip){
            int compareResult = new_id.compareTo( t.element );

            if( compareResult < 0 ){
                res.add(t.left.element+": Transmission from: "+t.element+" receiver: "+receiver_ip+" sender:"+send_ip+"\n");
                return traverseSend(t.left, new_id, res, send_ip, receiver_ip);
            }
            else if( compareResult > 0 ){
                res.add(t.right.element+": Transmission from: "+t.element+" receiver: "+receiver_ip+" sender:"+send_ip+"\n");
                return traverseSend(t.right, new_id, res, send_ip, receiver_ip);
            }
            else{            
                return res;
            }
        }
        return null;
        
    }
    public static String sendstr = "";
    public String send(AnyType sender, AnyType receiver){
        sendstr = "";
        sendstr += sender+": Sending message to: "+receiver+"\n";
        send(sender, receiver, root);
        sendstr += receiver+": Received message from: "+sender+"\n";
        return sendstr;
    }
    private AvlNode<AnyType> send( AnyType send_ip, AnyType receiver_ip, AvlNode<AnyType> t){
        
        int compareSend = send_ip.compareTo( t.element );
        int compareReceiver = receiver_ip.compareTo(t.element);
        if (compareSend<0 && compareReceiver<0){
            return send(send_ip, receiver_ip, t.left);
        }else if (compareSend>0 && compareReceiver>0){
            return send(send_ip, receiver_ip, t.right);
        }else if (compareReceiver == 0){
            ArrayList<String> first = new ArrayList<>();
            ArrayList<String> firstPart = traverseSend(t, send_ip, first, send_ip, receiver_ip);
            for(int i=firstPart.size()-1; i>=1; i-- ){
                sendstr += firstPart.get(i);
            }
        }else{
            ArrayList<String> first = new ArrayList<>();
            ArrayList<String> firstPart = traverseSend(t, send_ip, first, send_ip, receiver_ip);
            ArrayList<String> second = new ArrayList<>();
            ArrayList<String> secondPart =  traverseSend(t, receiver_ip, second, send_ip, receiver_ip);
            
            for(int i=firstPart.size()-1; i>=0; i-- ){
                sendstr += firstPart.get(i);
            }
            for(int i=0; i<secondPart.size()-1; i++ ){
                sendstr += secondPart.get(i);
            }
            
        }

        
        
        return null;
    }

}
