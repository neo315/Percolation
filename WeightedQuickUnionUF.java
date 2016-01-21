public class WeightedQuickUnionUF
{
    private int[] id;
    private int[] sz;
    private int count;
    
    public WeightedQuickUnionUF(int N)
    {
        id = new int[N];
        sz = new int[N];
        count = N;
        
        for (int i = 0; i < N; i++) 
        {
            id[i] = i;
            sz[i] = 1;
        }
    }
    
    private int find(int i)
    {
        while (id[i] != i) i = id[i];
        return i;
    }
    
    public boolean connected(int i, int j)
    {
        return find(i) == find(j);
    }
    
    public void union(int i, int j)
    {
        int p = find(i);
        int q = find(j);
        if (p == q) return;
        
        if (sz[i] < sz[j])
        {
            id[p] = q;
            sz[q] += sz[p];
        }
        
        else 
        {
            id[q] = p;
            sz[p] += sz[q];
        }
        count--;
    }
    
    public int count()
    {
        return count;
    }
    
    public static void main(String[] args)
    {
        int N = StdIn.readInt();
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
        
        while (!StdIn.isEmpty())
        {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
    }
    
        