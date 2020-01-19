# java-concurrent-04
线程间数据交换工具
Exchanger

信号灯 SemaPhore
 * 这个信号灯可以一次性拿多个和释放多个
 * 这样就可以动态地去进行线程的锁的释放和持有
 * 并且SemaPhore还提供有
 * 获取当前灯的个数和当前等待线程数的API
 * 这样就可以在运行过程中对齐进行控制
 
 原子int类型
 AtomicInteger
