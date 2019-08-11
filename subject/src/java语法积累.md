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

4. 调用排序的sort接口
对于数组类，使用Arrays.sort()
对于集合类，使用Collections.sort()

5. 不要使用数组作为HashMap的Key
由于数组并没有重写Hashcode和Object equals 方法，导致相同数值的数组仍不相等
比如：int[] a = {1,2}  int[] b = {1,2}     HashSet.put(a)
但是HashSet.containsKey(b) == false。

所以对于这种需要数组的key，可以转换为string来做key

6. 快速转换为String类型
通过 String str = String.valueOf() 进行转换

7. 多字段排序实现
方法一：使用Comparator.comparing().thenComparing()链式比较语法
如：lp.sort(Comparator.comparing((Pillar pillar) -> -pillar.height).thenComparing(Pillar->-Pillar.pos));
其中-pillar.height代表逆序（从大到小）
方法二：new Comparator重写compare方法

8. TreeSet快速查找
higher(tar)——返回大于tar的最小数
celling(tar)——返回大于等于tar的最小数

lower(tar)——返回小于tar的最大数
floor(tar)——返回小于等于tar的最大数

注意，由于TreeSet中元素使用的是泛型，所以返回的也是对象，如果上面查找的不存在则返回一个null.
所以当我们存的是Integer类型时，我们使用 int res = t.higher(tar)时，其实际上自动进行了拆箱赋值，
但是如果没有找到对应的key，则返回null，而此时自动拆箱肯定不行，会报null错误，所以最保险的方式不进行自动拆箱
而是使用Integer res = t.higher(tar)，这样在判断res是否为空后，在拆箱成int。