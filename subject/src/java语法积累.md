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

