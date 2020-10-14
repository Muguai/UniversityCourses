
public class SkipListTest{

    public static void main (String[] args){
        SkipList<Integer> skipList = new SkipList<>(3);
        System.out.println(skipList);
        skipList.insert(40);
        System.out.println(skipList);
        skipList.insert(3);
        System.out.println(skipList);
        skipList.insert(9);
        System.out.println(skipList);
        skipList.insert(10);
        System.out.println(skipList);
        skipList.insert(5);
        System.out.println(skipList);
        skipList.remove(10);
        System.out.println(skipList);
    }
}