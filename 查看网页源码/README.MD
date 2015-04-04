###ANR application not response 应用程序无响应

anr产生的原因：主线程 需要做很多事情，响应点击事件，更新ui
		如果在主线程里面阻塞过久的时间，应用程序无响应。

为了避免应用程序出现anr  
所以耗时的操作都有放在子线程中进行。

2.3和4.0之间有anr访问网络在主线程上；



View 是被主线程创建和控制的
只有主线程能更改UI；

子线程不能更新UI；


线程问题：同步和互斥
	如果多个线程都可以修改UI

受伤	 -5 A线程
吃了加血 +8 B线程


内部实现：
子线程更新界面的时候，做一个检查。
更新完ui后，检查这个更新的操作是否在主线程的代码执行的，
如果是，没有问题
如果不是，立刻抛出一个运行时异常android.view.ViewRootImpl$CalledFromWrongException:

![img](https://github.com/frankpi/android_demo/blob/master/%E6%9F%A5%E7%9C%8B%E7%BD%91%E9%A1%B5%E6%BA%90%E7%A0%81/handle.png)
