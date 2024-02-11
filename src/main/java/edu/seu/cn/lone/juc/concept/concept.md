##### 进程
* 进程由**指令**和**数据**组成，指令要运行，数据要读写，必须将指令加载到CPU，数据加载到内存。在指令运行过程中还需要用到磁盘、网络设备等。进程就是用来加载指令、管理内存、管理IO的。
* 当一个进程被运行，从磁盘加载紫这个程序的代码至内存，这就开启了一个进程。
* 进程可以视为一个程序的实例。大部分程序可以同时运行多个实例进程，也有的程序只能启动一个进程。

#### 线程
* 一个进程内可以分为一个到多个线程
* 一个线程就是一个指令流，将指令流中的一条条指令以一定的顺序交给CPU执行
* JAVA中，线程作为最小的调度单位，进程作为资源分配的最小单位

##### 二者对比
* 进程基本上是相互独立的，线程存在于进程中，是进程的一个子集
* 进程拥有共享的资源，如内存空间等，提供内部线程共享
* 进程间通信较为复杂
    * 同一台计算机的进程通信IPC(Inter-process communication)
    * 不同计算机之间的进程通信，需要通过网络，并遵守共同的协议，如HTTP
* 线程通信相对简单，因为他们是共享进程的内存的(多个线程可以访问同一个共享变量)
* 线程更轻量，线程上下文切换的成本一般比进程低
