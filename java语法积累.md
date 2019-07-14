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



