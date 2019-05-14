package com.lion.common.lock.locker;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * RedissonDistributedLocker
 * 分布式锁，实现类
 *
 * @author Yanzheng
 * @date 2019/05/08
 * Copyright 2019 Yanzheng. All rights reserved.
 */
@Component
public class RedissonDistributedLocker implements DistributedLocker {

    private final static String KEY_PREFIX = "LOCK:";
    private final static TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;    // 默认秒数单位，毫秒

    @Autowired
    private RedissonClient redissonClient;  // RedissonClient已经由配置类生成，这里自动装配即可

    /**
     * 拿不到lock就不罢休，不然线程就一直block
     */
    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(KEY_PREFIX + lockKey);
        lock.lock();
        return lock;
    }

    /**
     * leaseTime为加锁时间，默认单位毫秒
     */
    @Override
    public RLock lock(String lockKey, long leaseTime) {
        RLock lock = redissonClient.getLock(KEY_PREFIX + lockKey);
        lock.lock(leaseTime, TIME_UNIT);
        return lock;
    }

    /**
     * leaseTime为加锁时间，时间单位由unit确定
     */
    @Override
    public RLock lock(String lockKey, TimeUnit unit, long leaseTime) {
        RLock lock = redissonClient.getLock(KEY_PREFIX + lockKey);
        lock.lock(leaseTime, unit);
        return lock;
    }

    /**
     * 马上返回，拿到lock就返回true，不然返回false。
     * 带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false.
     */
    @Override
    public boolean tryLock(String lockKey) {
        RLock lock = redissonClient.getLock(KEY_PREFIX + lockKey);
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(String lockKey, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(KEY_PREFIX + lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, TIME_UNIT);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(KEY_PREFIX + lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(KEY_PREFIX + lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }

}
