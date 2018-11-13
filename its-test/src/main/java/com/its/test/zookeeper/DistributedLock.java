package com.its.test.zookeeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * zookeeper分布式事务锁
 *
 */
public class DistributedLock implements Lock, Watcher {
	private ZooKeeper zk = null;
	private String ROOT_LOCK = "/locks";// 根节点
	private String lockName;// 竞争的资源
	private String WAIT_LOCK;// 等待的前一个锁
	private String CURRENT_LOCK;// 当前锁
	private CountDownLatch countDownLatch;// 计数器
	private int sessionTimeout = 30000;
//	private List<Exception> exceptionList = new ArrayList<Exception>();

	/**
	 * 配置分布式锁
	 * 
	 * @param config
	 *            连接的url
	 * @param lockName
	 *            竞争资源
	 */
	public DistributedLock(String config, String lockName) {
		this.lockName = lockName;
		try {
			zk = new ZooKeeper(config, sessionTimeout, this);// 连接zookeeper
			Stat stat = zk.exists(ROOT_LOCK, false);
			if (stat == null) {// 如果根节点不存在，则创建根节点
				zk.create(ROOT_LOCK, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void process(WatchedEvent event) {// 节点监视器
		if (this.countDownLatch != null) {
			this.countDownLatch.countDown();
		}
	}

	public void lock() {
//		if (exceptionList.size() > 0) {
//			throw new LockException(exceptionList.get(0));
//		}
		try {
			if (this.tryLock()) {
				System.out.println(Thread.currentThread().getName() + " " + lockName + "获得了锁");
				//处理业务
				return;
			} else {
				waitForLock(WAIT_LOCK, sessionTimeout);// 等待锁
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean tryLock() {
		try {
			String splitStr = "_lock_";
			if (lockName.contains(splitStr)) {
				throw new LockException("锁名有误");
			}
			// 创建临时有序节点
			CURRENT_LOCK = zk.create(ROOT_LOCK + "/" + lockName + splitStr, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL_SEQUENTIAL);
			System.out.println(CURRENT_LOCK + " 已经创建");
			// 取所有子节点
			List<String> subNodes = zk.getChildren(ROOT_LOCK, false);
			// 取出所有lockName的锁
			List<String> lockObjects = new ArrayList<String>();
			for (String node : subNodes) {
				String _node = node.split(splitStr)[0];
				if (_node.equals(lockName)) {
					lockObjects.add(node);
				}
			}
			Collections.sort(lockObjects);
			System.out.println(Thread.currentThread().getName() + " 的锁是 " + CURRENT_LOCK);
			// 若当前节点为最小节点，则获取锁成功
			if (CURRENT_LOCK.equals(ROOT_LOCK + "/" + lockObjects.get(0))) {
				return true;
			}
			// 若不是最小节点，则找到自己的前一个节点
			String prevNode = CURRENT_LOCK.substring(CURRENT_LOCK.lastIndexOf("/") + 1);
			WAIT_LOCK = lockObjects.get(Collections.binarySearch(lockObjects, prevNode) - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean tryLock(long timeout, TimeUnit unit) {
		try {
			if (this.tryLock()) {
				return true;
			}
			return waitForLock(WAIT_LOCK, timeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean waitForLock(String prev, long waitTime) throws KeeperException, InterruptedException {
		Stat stat = zk.exists(ROOT_LOCK + "/" + prev, true);
		if (stat != null) {
			System.out.println(Thread.currentThread().getName() + "等待锁 " + ROOT_LOCK + "/" + prev);
			this.countDownLatch = new CountDownLatch(1);
			// 计数等待，若等到前一个节点消失，则precess中进行countDown，停止等待，获取锁
			this.countDownLatch.await(waitTime, TimeUnit.MILLISECONDS);
			this.countDownLatch = null;
			System.out.println(Thread.currentThread().getName() + " 等到了锁");
		}
		return true;
	}

	public void unlock() {
		try {
			System.out.println("释放锁 " + CURRENT_LOCK);
			zk.delete(CURRENT_LOCK, -1);
			CURRENT_LOCK = null;
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Condition newCondition() {
		return null;
	}

	public void lockInterruptibly() throws InterruptedException {
		this.lock();
	}

	public class LockException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public LockException(String e) {
			super(e);
		}

		public LockException(Exception e) {
			super(e);
		}
	}

	static int n = 500;

	public static void main(String[] args) {
		Runnable runnable = new Runnable() {
			public void run() {
				DistributedLock lock = null;
				try {
					lock = new DistributedLock("vm-01-ip:2181", "test_vm_01");
					lock.lock();
					System.out.println(--n);
					System.out.println(Thread.currentThread().getName() + "正在运行");
				} finally {
					if (lock != null) {
						lock.unlock();
					}
				}
			}
		};

		// 开启10个线程
		for (int i = 0; i < 2; i++) {
			Thread t = new Thread(runnable);
			t.start();
		}
	}
}