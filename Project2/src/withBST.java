import java.util.ArrayList;

public class withBST<AnyType extends Comparable<? super AnyType>> {
    private BinaryNode<AnyType> root;

    private static class BinaryNode<AnyType>{
        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child

        // Constructors
        BinaryNode( AnyType theElement ) {
            this( theElement, null, null );
        }

        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt ) {
            element  = theElement;
            left     = lt;
            right    = rt;
        }

    }

    public void BinarySearchTree(AnyType ip){
        root = new BinaryNode<AnyType>(ip);
    }

    public static String ians = "";
    public String insert( AnyType x ){
        ians = "";
        root = insert( x, root );
        return ians;
    }

    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t ){
        if( t == null )
            return new BinaryNode<>( x, null, null );
        
        ians += t.element+": New node being added with IP:"+x+"\n";
        
        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ;  
        return t;
    }
    
    
    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }
    

    public static String rans = "";
    public String remove( AnyType x )
    {
        
        root = remove( x, root, null );
        return rans;
    }

    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t, BinaryNode<AnyType> p)
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
            t.right = remove( t.element, t.right , t);
            rans = p.element+": Non Leaf Node Deleted; removed: "+x+" replaced: "+t.element+"\n";
        }
        else if ((t.left == null && t.right != null) || (t.left != null && t.right == null)){
            rans = p.element+": Node with single child Deleted: "+x+"\n";
            t = ( t.left != null ) ? t.left : t.right;
        }
        else{
            rans = p.element+": Leaf Node Deleted: "+x+"\n";
            t = null;
        }
        return t;
    }
    private ArrayList<String> traverseSend(BinaryNode<AnyType> t, AnyType new_id, ArrayList<String> res, AnyType send_ip, AnyType receiver_ip){
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
    private BinaryNode<AnyType> send( AnyType send_ip, AnyType receiver_ip, BinaryNode<AnyType> t){
        
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
