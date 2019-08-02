1. 将数组转换为ArrayList
    Integer[] array = {1,2};
    ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(array));
    注意，对于基本类型的数组（int doble等），他是不能够直接转的，它只能够转Integer等装箱类型，当然class对象当然是可以的

2. ArrayList删除元素
    list.remove(element)
    注意当element为int类型时，它删的是其对应的下标，若想删除某个int值的话，可以使用Integer类型即
    list.remove(Integer.valueOf(element));

3. 遍历ArrayList时，又删除了其中的元素
    当通过for循环遍历时，若删除了某些元素，则会造成游标超出界限错误

4.调用排序的sort接口
对于数组类，使用Arrays.sort()
对于集合类，使用Collections.sort()

5.java引用赋值问题
    java中引用赋值，实际上是将B指向A所指向对象的实际堆地址，并不是指向A的栈地址，所以A如果指向了其它对象的地址，并不会对A造成影响。
    而如果A修改了原对象，则B也会发生相应改变（A B指向同一对象，A修改了此对象，B也被迫发生变化）
    obj A = new obj(1)
    obj B = A
    A = new obj(2)
    输出
    A:2
    B:1

6. 集合类型的常犯错误
    在集合类型赋值时，以为可以直接通过new的构造函数来赋值，但实际上，集合类型只支持同类型参数的构造函数赋值。比如：
    List<Integer> ls = new ArrayList<>();
    ls.add(0)
    List<Integer> res = new ArrayList<>(ls);——正确，因为其构造函数所给的参数为集合类型
    List<Integer> res = new ArrayList<>(ls.get(0));——错误，虽然不会报语法错误，但是和我们赋初值的想法却不一致，
    因为ls.get(0)是一个整数类型，当其构造函数所接到的参数为整数类型时，相当于是指定了该集合类型的初始容量大小，而非赋初值

