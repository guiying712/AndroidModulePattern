# AndroidModulePattern
Android项目组件化示例代码

博客：http://blog.csdn.net/guiying712/article/details/55213884

### app组件功能：
1. app组件主要用于管理其他组件；
2. app组件中可以初始化全局的库，例如Lib.init(this);
3. 添加 multiDex 功能

### main组件功能：
1. 声明应用的launcherActivity----->android.intent.category.LAUNCHER；
2. 添加SplashActivity;
3. 添加LoginActivity；

### girls/news组件功能：
1. 这两个组件都是业务组件，根据产品功能独立成一个模块；

### common组件功能：
1. common组件是基础库，添加一些公用的类；
2. 例如：网络请求、图片加载、工具类、base类等等；