# LaiOffer-flag_camp-team_3-202506

鉴于各位时间并不充裕，本项目鼓励使用AI辅助开发以确保完成进度，但是请在使用AI的同时保证质量，提交之前检查代码和设计是否符合应用逻辑，尽可能减少bug，感谢理解。
目前的后端设计请看下方章节。组内预计每周一发放任务，<ins>**美东时间下周一凌晨0点**</ins>为截止日期，请大家按时完成自己的部分。若未能按时完成，可能会视严重程度给予警告或**被踢出小组！！！**
完成工作后请直接提交pull request，留给Tech Lead进行检查和merge。不要自己push！不要自己合并！
有任何问题欢迎询问AI,或者私信Tech Lead询问AI，也可以直接在后端群里提出。

## Links

Google Doc link: https://docs.google.com/document/d/1hy0BSl47EQQU-yAn0IQzep72Lrwsj6aQEwrflYmDO-8/edit?usp=sharing

## Environment Design

1. API: RestAPI

2. Database: PostgreSQL + uploaded graphs in back-end server local. (The PostgreSQL records the address only)

3. Platform: Java + Springboot

## Database Design 
<img width="1406" height="1030" alt="image" src="https://github.com/user-attachments/assets/10205523-9e50-4d43-b6d5-19094effa675" />




## Back-end Structure
src/main/java/com/laioffer/flagcamp/backend
├── controller       # 负责处理 API 请求
│   ├── UserController.java
│   ├── ItemController.java
│   ├── PostController.java
│   └── TagController.java
│
├── service          # 业务逻辑层
│   ├── UserService.java
│   ├── ItemService.java
│   ├── PostService.java
│   └── TagService.java
│
├── repository       # 数据库交互层 (JPA/Hibernate)
│   ├── UserRepository.java
│   ├── ItemRepository.java
│   ├── PostRepository.java
│   └── TagRepository.java
│
├── model            # 实体类 (对应数据库表)
│   ├── User.java
│   ├── Item.java
│   ├── Post.java
│   └── Tag.java
│
│
└── BackendApplication.java  # 启动类

## API structure
1. 用户 (Users)

POST /api/users/register → 注册新用户

POST /api/users/login → 登录

GET /api/users/{id} → 获取用户信息

PUT /api/users/{id} → 更新用户信息

DELETE /api/users/{id} → 删除用户

2. 商品 (Items)

POST /api/items → 发布商品（需要用户登录）

GET /api/items → 获取所有商品（支持分页 & 搜索）

GET /api/items/{id} → 获取某个商品详情

PUT /api/items/{id} → 更新商品

DELETE /api/items/{id} → 删除商品

3. 帖子 (Posts)

POST /api/posts → 发布帖子（绑定商品 + 标签）

GET /api/posts → 获取所有帖子（支持按标签筛选）

GET /api/posts/{id} → 获取某个帖子详情

DELETE /api/posts/{id} → 删除帖子

4. 标签 (Tags)

POST /api/tags → 创建新标签

GET /api/tags → 获取所有标签
