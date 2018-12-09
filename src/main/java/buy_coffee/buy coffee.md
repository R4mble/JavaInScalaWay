# 怎样买一杯咖啡?
最普通的想法:
> 卡掏出去,咖啡拿回来.
没问题. 但是随之而来的副作用:`钱少了`
这一点都不*酷*:
- 难以被复用
- 难以测试

> 考虑下: 卡掏出去,拿回来的是咖啡和账单.

嗯, 这样做的话, 
- 方便多买几杯,合成一个账单.
- 一张卡买的几次,合成一个账单.

用Scala敲了一遍代码.很酷炫!不错.
这么酷,用Java来实现怎么样?
- Scala可以有多返回值,而Java不行. 那就把多返回值合成一个对象.
- Java的弱鸡函数式也可以跟Scala相提并论吗?  可以的.

当我把这段代码拼凑出来的时候,忍不住大笑.
```java
charges.stream().collect(Collectors.groupingBy(c -> c.creditCard.name)).values().stream()
                 .map(c -> c.stream().reduce(Charge::combine).orElse(null)).collect(Collectors.toList());
```
有点遗憾老是stream和collection转来转去的,不过也很cooooool了.
