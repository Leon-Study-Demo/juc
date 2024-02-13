ThreadPoolExecutor(implement ExecutorService)
    1. 线程池状态
    使用int的高三位来表示线程迟的状态，低29为表示线程数量
        状态名       高三位         接受新任务     处理阻塞队列任务     说明
        RUNNING     111             Y               Y
        SHUTDOWN    000             N               Y             不接受新任务，但会处理阻塞队列的任务
        STOP        001             N               N             中断正在执行的任务，并抛弃阻塞队列的任务
        TIDYING     010             -               -             任务全部执行完毕，活动线程为0，即将进入终结
        TERMINATED  011             -               -             终结状态
    从数字上比较  TERMINATED > TIDYING > STOP > SHUTDOWN > RUNNING (高三位1代表负数)
    用一个整数表示，在赋值时只需要对一个变量赋值，执行一次cas操作

    2. 构造方法
       public ThreadPoolExecutor(int corePoolSize,
                                 int maximumPoolSize,
                                 long keepAliveTime,
                                 TimeUnit unit,
                                 BlockingQueue<Runnable> workQueue,
                                 ThreadFactory threadFactory,
                                 RejectedExecutionHandler handler)

       * corePoolSize 核心线程数（最多保留的线程数）
       * maximumPoolSize 最大线程数目
       * keepAliveTime 生存时间-针对救急线程
       * unit 时间单位-针对救急线程
       * workQueue 阻塞队列
       * threadFactory 线程工厂-创建时给线程起个好名字
       * handler 拒绝策略

       执行逻辑：
       任务进来 线程数量没有到 核心线程数 创建线程执行任务
       任务数超过核心线程 则放入阻塞队列
       阻塞队列放满（有界队列）再进来任务 则创建救急线程（救急线程数 + 核心线程数 = 最大线程数）
       构造参数的时间和时间单位都是针对于救急线程的
       救急线程达到数量后则执行 拒绝策略
       注意：如果队列选择的是无界，则一直往队列里面放，没有救急线程的创建了


      3.拒绝策略，JDK提供了4中实现
        * AbortPolicy 让调用者抛出RejectedExecutionException异常，这是默认策略
        * CallerRunsPolicy 调用者运行任务
        * DiscardPolicy 放弃本次任务
        * DiscardOldestPolicy 放弃任务队列中最早的任务，本任务取而代之

        其他的框架自己的实现
        Dubbo的实现 抛出 RejectedExecutionException 时会有详细的日志，方便定位问题
        Netty的实现，创建一个新线程
        ActiveMQ的实现，带超时时间（60s）尝试放入队列
        PinPoint的实现，采用拒绝策略链，可以逐一尝试策略链中的每种拒绝策略


      4. 工厂方法
      newFixedThreadPool

      public static ExecutorService newFixedThreadPool(int nThreads) {
              return new ThreadPoolExecutor(nThreads,
                                            nThreads,
                                            0L,
                                            TimeUnit.MILLISECONDS,
                                            new LinkedBlockingQueue<Runnable>())}

      特点：
        * 核心线程数等于最大线程数，没有救急线程，因此也无需超时时间
        * 阻塞队列是无界的，可以放任意数量的任务

      评价：
        适用于任务量已知，但相对耗时的任务


      newCachedThreadPool

      public static ExecutorService newCachedThreadPool() {
              return new ThreadPoolExecutor(0,
                                            Integer.MAX_VALUE,
                                            60L,
                                            TimeUnit.SECONDS,
                                            new SynchronousQueue<Runnable>())}

      特点：
      * 核心线程是0， 最大线程是Integer.MAX_VALUE 救急线程的空闲生存时间是60s，意味着：
        * 全部都是救急线程（60秒可以回收）
        * 救急线程可以无限创建

      评价：
      * 整个线程池表现为线程数会根据任务量不断增长，没有上限，当任务执行完毕，空闲1分钟释放线程
      * 适合任务较密集，但是每个任务执行较短的情况



      newSingleThreadPool

      public static ExecutorService newSingleThreadExecutor() {
              return new FinalizableDelegatedExecutorService
                  (new ThreadPoolExecutor(1,
                                          1,
                                          0L,
                                          TimeUnit.MILLISECONDS,
                                          new LinkedBlockingQueue<Runnable>()))}


      使用场景：
        希望多个任务排队执行。线程固定数为1，任务多于1时，任务会进入无界队列排队。任务执行完毕，这是唯一的线程也不会被释放

      区别：
      * 自己创建单线程串行执行任务时，如果任务执行失败而终止那么没有任何的不就措施，而线程池还会创建新的一个线程，保证池的正常工作
      * Executors.newSingleThreadExecutor()线程数始终为1，不能修改
        * FinalizableDelegatedExecutorService应用的是装饰器模式，只对外暴露了ExecutorService接口，因此不能调用ThreadPoolExecutor
            中的特有放法
      * Executors.newFixedThreadExecutor(1)初始时为1，以后还可以改
        * 对外暴露的是 ThreadPoolExecutor 对象，可以强转后调用 setCorePoolSize等方法就行修改


