# single_sign_on_cookie  
我在 C:\Windows\System32\drivers\etc 中的Hosts文件末尾添加了：  
127.0.0.1    www.xiaoming.com  
127.0.0.1    vip.xiaoming.com  
127.0.0.1    cart.xiaoming.com  
127.0.0.1    login.xiaoming.com  
启动项目中的 CartApplication、LoginApplication、MainApplication、VipApplication模块  
页面访问：  
http://www.xiaoming.com:9010/view/index  
http://cart.xiaoming.com:9012/view/index  
http://vip.xiaoming.com:9011/view/index  
1.访问上面的三个网址，点击任意一个页面的登录都可以访问登录页面（单点登陆）：    
2.在任意一个页面登录成功跳转后，刷新其他两个页面，就已经是登录成功的状态了。    
