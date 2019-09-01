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

注意对于二维数组，或者多重集合等，需要自己定义排序规则，才能进行排序，因为sort接口都是默认其所要排序的元素均为一维的，
如果直接排序，这样会造成类型错误。
比如，int[][] a = [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]],
直接调用Arrays.sort(a)将会出错，因为这种情况下其排序的元素为一个int[2]的数组，并不能转化为int整数，做大小比较，所以要自
定义比较排序方案，正确做法是Arrays.sort(a,(o1,o2)->o1[0]-o2[0])

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


7. 不要使用数组作为HashMap的Key
由于数组并没有重写Hashcode和Object equals 方法，导致相同数值的数组仍不相等
比如：int[] a = {1,2}  int[] b = {1,2}     HashSet.put(a)
但是HashSet.containsKey(b) == false。

所以对于这种需要数组的key，可以转换为string来做key

8. String相关方法
通过 String str = String.valueOf() 进行转换，可以快速将其他类型转换为String类型
子串查找 str.indexof(subStr,fromIndex)，要想查到子串的所有位置，则需使fromIndex从0到str.length()-subStr.length()进行+1循环


9. 多字段排序实现
方法一：使用Comparator.comparing().thenComparing()链式比较语法
如：lp.sort(Comparator.comparing((Pillar pillar) -> -pillar.height).thenComparing(Pillar->-Pillar.pos));
其中-pillar.height代表逆序（从大到小）
方法二：new Comparator重写compare方法

10. TreeSet快速查找
higher(tar)——返回大于tar的最小数
celling(tar)——返回大于等于tar的最小数

lower(tar)——返回小于tar的最大数
floor(tar)——返回小于等于tar的最大数

注意，由于TreeSet中元素使用的是泛型，所以返回的也是对象，如果上面查找的不存在则返回一个null.
所以当我们存的是Integer类型时，我们使用 int res = t.higher(tar)时，其实际上自动进行了拆箱赋值，
但是如果没有找到对应的key，则返回null，而此时自动拆箱肯定不行，会报null错误，所以最保险的方式不进行自动拆箱
而是使用Integer res = t.higher(tar)，这样在判断res是否为空后，在拆箱成int。

11. 使用Map的put方法时，当key不存在时可以使用map.put(words[i], map.getOrDefault(words[i], 0) + 1)这种方式赋初值，不用
在写成if-else分支形式了。

12. 注意在初始化char数组时，所有值均为'\0'，表示结束符。'\0'!='0'!=' '

13. ArrayList 可以在指定位置插入特定元素 add(index,element)，其后面的元素相应往后移动

