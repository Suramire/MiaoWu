# MiaoWu
毕业设计-流浪猫收养系统
## 过程随笔记录
### 通知：
1.用户关注用户  
2.关注的用户发帖  
3.帖子审核结果  
4.领养申请  
5.领养审核结果  
### 发帖： 
需发帖人有联系方式  
帖子详情页 数据获取顺序调整  
实现头像修改功能 
### 修改记录
1.修改猫咪表，新增被领养时间，领养者id；删除联系方式相关  
2.新建领养记录单  
3.直接使用在猫咪信息 二〇一八年四月十三日 11:04:59  
4.删除帖子详情页中的猫咪信息  
5.取消首页帖子分类显示  
领养申请  
发起  
审核  
管理员不能申请领养  
所有管理员都应能接收到领养申请  
对已批阅的申请不再显示操作按钮  
使用 M 替换 Multi  
删除搜索结果的Fragment
修改note表  
删除type、cid字段
修改user表  
增加address字段
删除noteCat表  
### list
个人中心  
帖子列表  
领养记录列表  
待审核申请列表  
待审核帖子列表  
通知中心列表  
首页帖子列表&猫咪列表  
搜索页搜索结果列表  
#### TODO
搜索结果帖子部分显示用户名  
首页数据冗余 配图路径list  
常用toast使用snackbar代替  
实现帖子修改功能
### 待修改
点击搜索结果的帖子，出现帖子审核项  
用户信息地址无法保存  
通知按时间排序  
等待图片上传完成  
进度对话框统一样式  
发布新回复后刷新帖子的方式  
屏幕翻转问题  
### 后期完善
驳回帖子后删除帖子相关内容  
定期刷新通知数  
~~领养条件文本换行~~  
~~锁定的帖子不能进行评论、点赞等操作~~  
修改密码是所填的手机号要是注册时所填的手机号  
~~修改密码页 输完手机号自动进行判断~~  
删除帖子功能  
### FIX  
普通用户点击领养申请结果的通知会跳转到审核界面
