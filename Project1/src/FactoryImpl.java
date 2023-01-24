import java.util.HashSet;
import java.util.NoSuchElementException;

public class FactoryImpl implements Factory{
	private Holder first;
	private Holder last;
	private Integer size;
	
	public String printAll() {
		String res = "";
		res += "{";
		
		Holder temptra = first;
		for(int i=0;i<size;i++) {
			res += (temptra.getNextHolder().toString());
			if (i!=(size-1)) {
				res += ",";
			}
			temptra = temptra.getNextHolder();
			
		}
		while(temptra.getNextHolder().getNextHolder()!=null) {
			res += (temptra.getNextHolder().toString());
			temptra = temptra.getNextHolder();
					
		}
		res += "}";
		return res;
	}
	public void generate() {
		first = new Holder(null,null,null);
		last = new Holder(first,null,null);
		first.setNextHolder(last);
		size=0;
	}
	
	@Override
	public void addFirst(Product product) {
		// TODO Auto-generated method stub
		add(0,product);
	}

	@Override
	public void addLast(Product product) {
		// TODO Auto-generated method stub
		add(size, product);
	}

	@Override
	public Product removeFirst() throws NoSuchElementException {
		// TODO Auto-generated method stub
		if (size==0) {
			throw new NoSuchElementException();
		}
		return removeIndex(0);
	}

	@Override
	public Product removeLast() throws NoSuchElementException {
		// TODO Auto-generated method stub
		if (size==0) {
			throw new NoSuchElementException();
		}
		return removeIndex(size-1);
	}

	@Override
	public Product find(int id) throws NoSuchElementException {
		// TODO Auto-generated method stub
		if(size==0) {
			throw new NoSuchElementException();
		}
		Holder curr = first;
		while(curr.getNextHolder()!=null) {
			if (curr.getProduct()!=null && curr.getProduct().getId() == id) {
				break;
			}
			curr = curr.getNextHolder();
		}
		if (curr == last) {
			throw new NoSuchElementException();
		}
		
		return curr.getProduct();
	}

	@Override
	public Product update(int id, Integer value) throws NoSuchElementException {
		// TODO Auto-generated method stub
		if(size==0) {
			throw new NoSuchElementException();
		}
		Holder curr = first;
		while(curr.getNextHolder()!=null) {
			if (curr.getProduct()!=null && curr.getProduct().getId() == id) {
				break;
			}
			curr = curr.getNextHolder();
		}
		if (curr == last) {
			throw new NoSuchElementException();
		}
		int old_id = id;
		int old_val = curr.getProduct().getValue();
		Product tempProduct = new Product(old_id,old_val);
		curr.getProduct().setValue(value);
		return tempProduct;
	}

	@Override
	public Product get(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		if (index<0 || index>=size) {
			throw new IndexOutOfBoundsException();
		}
		Holder curr;
		if (index < size/2) {
			curr = first.getNextHolder();
			for (int i=0;i<index;i++) {
				curr = curr.getNextHolder();
			}
		}else {
			curr = last.getPreviousHolder();
			for (int i=0;i<size-index-1;i++) {
				curr = curr.getPreviousHolder();
				
			}
		}
		return curr.getProduct();
	}

	@Override
	public void add(int index, Product product) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		if (index<0 || index > size) {
			throw new IndexOutOfBoundsException();
			
		}
		
		Holder temp1 = first;
		Holder temp2 = last;
		for (int i=0;i<index;i++) {
			temp1 = temp1.getNextHolder();
		}
		for (int j=0;j<size-index;j++) {
			temp2= temp2.getPreviousHolder();
		}
		Holder newholder = new Holder(temp1, product, temp2);
		temp1.setNextHolder(newholder);
		
		temp2.setPreviousHolder(newholder);
		
		size++;
		
	}

	@Override
	public Product removeIndex(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		if (index<0||index>=size) {
			throw new IndexOutOfBoundsException();
		}
		else {
			Holder curr;
			if (index < size/2) {
				curr = first.getNextHolder();
				for (int i=0;i<index;i++) {
					curr = curr.getNextHolder();
				}
			}else {
				curr = last.getPreviousHolder();
				for (int i=0;i<size-index-1;i++) {
					curr = curr.getPreviousHolder();
					
				}
			}
			
			curr.getPreviousHolder().setNextHolder(curr.getNextHolder());
			curr.getNextHolder().setPreviousHolder(curr.getPreviousHolder());
			
			size--;

			return curr.getProduct();
			
			
		}
		//return null;
	}

	@Override
	public Product removeProduct(int value) throws NoSuchElementException {
		// TODO Auto-generated method stub
		if(size==0) {
			throw new NoSuchElementException();
		}
		Holder curr = first;
		while(curr.getNextHolder()!=null) {
			if (curr.getProduct()!=null && curr.getProduct().getValue() == value) {
				curr.getPreviousHolder().setNextHolder(curr.getNextHolder());
				curr.getNextHolder().setPreviousHolder(curr.getPreviousHolder());
				break;
			}
			curr = curr.getNextHolder();
		}
		if (curr == last) {
			throw new NoSuchElementException();
		}
		size--;
		return curr.getProduct();
	}

	@Override
	public int filterDuplicates() {
		// TODO Auto-generated method stub
		HashSet<Integer> valSet = new HashSet<>();
		int res=0;
		Holder curr = first.getNextHolder();
		for(int i=0;i<size;i++) {
			int curr_val = curr.getProduct().getValue();
			if (!valSet.contains(curr_val)) {
				valSet.add(curr_val);
				curr = curr.getNextHolder();
			}else {
				
				curr.getPreviousHolder().setNextHolder(curr.getNextHolder());
				curr.getNextHolder().setPreviousHolder(curr.getPreviousHolder());
				res++;
				curr = curr.getNextHolder();
			}
		}
		size-=res;
		
		
		return res;
	}

	@Override
	public void reverse() {
		// TODO Auto-generated method stub
		if (size>=2) {
			Holder curr = first.getNextHolder();
			for(int i=0;i<size;i++) {
				Holder pre = curr.getPreviousHolder();
				Holder after = curr.getNextHolder();
				if (i==0) {
					curr.setNextHolder(last);
					last.setPreviousHolder(curr);
				}else {
					curr.setNextHolder(pre);
				}
				if (i==size-1) {
					curr.setPreviousHolder(first);
					first.setNextHolder(curr);
				}else {
					curr.setPreviousHolder(after);
				}
				curr = after;
									
			}
		}
		printAll();
			
	}
		
	
	
}