前言
        最近项目要增加识别身份证和二维码的需求，大概在网上搜了一下，没有合适的开源库。有一个开源的叫 tesseract 的扫描库，下载demo运行后的效果并不理想，识别耗费的时间过长，而且识别度不怎么高。随又参考别人的博客，接入了百度文字识别的API，但是网上搜出来的大多是直接调用百度文字识别已有的功能，护照的识别百度并未集成，这里需要自定义模板，稍后会写到具体自定义的方法。

接入指南：
1. 准备工作

    1）需要先去百度云官网注册账号，或者你已经有任何百度平台的账号，直接登录即可。

    2）找到你需要的服务类型，识别身份证和护照属于文字识别，附图。

    3）然后创建测试demo

    4）创建成功后，百度云会为每一个应用生成唯一的apiKey和secrecKey。

    5）接下来可以粗略浏览一下API文档和一些注意事项。链接点这里https://cloud.baidu.com/doc/OCR/OCR-API.html#.E8.AF.B7.E6.B1.82.E9.99.90.E5.88.B6

2.sdk接入

   下载官方sdk,下载链接https://ai.baidu.com/sdk#ocr

   具体接入指南，在这里http://ai.baidu.com/docs#/OCR-Android-SDK/top，可以结合文档和我的文章来进行操作。


   我觉得 sdk用起来还是比较灵活的，我这次是只用了他的UI部分，然后具体的请求操作只要请求对应的接口就好了。

   1）将sdk导入工程里，然后进行依赖。（可以全部依赖，也可以单独依赖UI）具体操作看下图：

   2）在清单文件加入项目会用到的权限：

   3）在工程下的jniLibs 文件夹下，将sdk里的so类库拷贝进去，如果忘记加了，你初始化的时候就会提示你初始化失败。

   4）切换目录，将ocr-sdk.jar包拷贝进去


身份证识别

3.撸代码

   准备工作都做好了，现在就可以调用API接口了。

   1）首先，要先进行初始化，获取AccessToken

   2）身份证的识别还是蛮简单的，因为官方已经集成了这个功能，只需要拍完照调用接口。（  我自己写的界面还蛮简单的，只有两个按钮，一个是识别身份证，一个是识别护照，这里就不贴照片了。）下面是识别身份证的界面，也就是下面代码跳转的界面，就长这个样子。

   3）拍完照会返回信息，主要是要获得图片路径信息，然后请求接口进行解析。

   4）CameraActivity识别身份证正反面的界面，界面的显示类型是跟CameraActivity.KEY_CONTENT_TYPE来决定的。具体有这几种，可灵活运用。

   5）拿到解析身份证后的数据，想干嘛就干嘛。


护照识别

   前面说了，护照官方没有集成，所以就要用到自定义模板了。需要注意的是sdk并不能使用自定义的模板的接口，API里也只是写了接口，但官方文档并没有更新，所以API里面说的方法，你是无论如何也走不通的，我也是加上官方开发群才知道的。具体模板我会在下面用到的地方给出来：

  1.自定义模板的文档http://ai.baidu.com/docs#/OCR-API/8b83b775

  2.创建自定义模板链接http://ai.baidu.com/iocr#/templatelist


   自定义模板需要的注意事项，上传的图片尽量清晰全面

  3.如果你试一试识别率已经很高了，那就可以发布了。发布成功后，每一个模版都有一个模板签名。

  4.模板有了，就可以去请求解析数据了。（注意：如果识别出来的信息，返回的只有参照点的信息，那就代表识别失败，说明你的模板还要改进）。

  5.依然是跳转CameraActivity界面，同身份证跳转是一样的，不一样的地方是这次不要传证件类型那个参数，也就是红框标注的。（不传类型的界面就是相机的界面）

  6.拍完照返回图片进行至关重要的图片解析了，这时候就要用到我们的模板了
  
  7.处理请求结果，可用json工具，gson库进行转换，拿到需要的信息。

完毕。

如果有不解之处，或者不同见解，欢迎分享。
https://www.jianshu.com/p/75f0a9cfcfd3

