package top.b0x0.demo.distributedLock.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.b0x0.demo.distributedLock.db.service.DistributedLockService;
import top.b0x0.demo.distributedLock.domain.DistributedLock;

/**
 * @author musui
 */
@RestController
public class DemoController {

    @Autowired
    private DistributedLockService distributedLockService;


    @GetMapping("mysqlLock")
    public Object testLock() throws Exception {
        System.err.println("进入testLock()方法;");

        DistributedLock lock = new DistributedLock();
        lock.setResourceName("task-order");
//        ThreadUtil.concurrencyTest(5, () -> {
//            try {
//                boolean tryLock = distributedLockService.tryLock(lock);
//                System.out.println("tryLock = " + tryLock);
//                if (tryLock) {
//                    System.out.println("获取到分布式锁;");
//
//                    Thread.sleep(10 * 1000);
//
//                } else {
//                    System.out.println("获取分布式锁失败;");
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        boolean tryLock = distributedLockService.tryLock(lock);
        try {
            System.out.println("tryLock = " + tryLock);
            if (tryLock) {
                try {
                    System.err.println("1. 获取到分布式锁;");
                    Thread.sleep(5 * 1000);
                } finally {
                    boolean unlock = distributedLockService.unlock(lock);
                    System.err.println("2. 释放锁: " + unlock);
                }
            } else {
                System.err.println("获取分布式锁失败;");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tryLock;
    }

}