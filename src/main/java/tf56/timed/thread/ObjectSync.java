package tf56.timed.thread;

import java.util.LinkedList;
import java.util.Queue;

/***
 * @ClassName ObjectSync
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/25 15:39
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
public class ObjectSync {

    private Queue<Integer> queue = new LinkedList<>();

    private int queueSize = 10;

//    public static void main(String []args) {
//        ObjectSync sync = new ObjectSync();
//        Consumer consumer = sync.new Consumer();
//        Producer producer = sync.new Producer();
//
//        producer.start();
//        consumer.start();
//    }

    class Consumer extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == 0) {
                        try {
                            System.out.println("队列空间无数据，等待数据");
                            queue.wait();
                        } catch(InterruptedException e) {
                            System.out.println("consumer 异常");
                            queue.notify();
                        }
                    }
                    queue.poll();
                    queue.notify();
                    System.out.println("从队列中移除一个数据，剩余数据大小"+queue.size());
                }
            }
        }
    }
    class Producer extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == queueSize) {
                        try {
                            System.out.println("队列空间已满");
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("producer 异常");
                            queue.notify();
                        }
                    }
                    queue.offer(1);
                    queue.notify();
                    System.out.println("队列空间插入一个元素，剩余空间"+(queueSize-queue.size()));
                }

            }
        }
    }

}

