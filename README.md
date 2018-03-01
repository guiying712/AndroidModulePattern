
# 应用截图
<table>
    <tr>
        <td><img src="/screenshots/Screenshot_1.png"></td>
        <td><img src="/screenshots/Screenshot_2.png"></td>
        <td><img src="/screenshots/Screenshot_3.png"></td>
        <td><img src="/screenshots/Screenshot_4.png"></td>
    </tr>
</table>

# AndroidModulePattern

Android项目组件化示例代码

**Android组件化方案**：http://blog.csdn.net/guiying712/article/details/55213884

**Android组件化之终极方案**：http://blog.csdn.net/guiying712/article/details/78057120

1. 现在的 AndroidModulePattern 使用 阿里ARouter作为路由；

2. Android组件化方案已经支持 **Fragment组件化**，使用方法请下载Demo查看；

3. 本项目已适配Android Studio 3.0.1版本(Google仓库会带来一定影响)


## 集成开发模式和组件开发模式转换

**1、首先打开Android项目的 gradle.properties 文件，然后将 isModule 改为你需要的开发模式（true/false），
然后点击 "Sync Project" 按钮同步项目；**

**2、![Image](/screenshots/develper.PNG) 在运行之前，请先按照图中选择一个能够运行的组件；**


## 组件功能介绍

### app组件功能（空壳工程）：
1. 配置整个项目的Gradle脚本，例如 混淆、签名等；
2. app组件中可以初始化全局的库，例如Lib.init(this);
3. 添加 multiDex 功能
4. 业务组件管理（组装）；

### main组件功能（业务组件）：
1. 声明应用的launcherActivity----->android.intent.category.LAUNCHER；
2. 添加SplashActivity;
3. 添加LoginActivity；
4. 添加MainActivity；

### girls/news组件功能（业务组件）：
1. 这两个组件都是业务组件，根据产品的业务逻辑独立成一个组件；

### common组件功能（功能组件）：
1. common组件是基础库，添加一些公用的类；
2. 例如：网络请求、图片加载、工具类、base类等等；
3. 声明APP需要的uses-permission；
4. 定义全局通用的主题（Theme）；

## License

    Copyright 2017 guiying712, AndroidModulePattern Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
