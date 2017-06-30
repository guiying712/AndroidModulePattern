
# 应用截图
<table>
    <tr>
        <td><img src="/screenshots/Screenshot_1.png"></td>
        <td><img src="/screenshots/Screenshot_2.png"></td>
        <td><img src="/screenshots/Screenshot_3.png"></td>
    </tr>
</table>

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
4. 添加MainActivity；

### girls/news组件功能：
1. 这两个组件都是业务组件，根据产品的业务逻辑独立成一个组件；

### common组件功能：
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