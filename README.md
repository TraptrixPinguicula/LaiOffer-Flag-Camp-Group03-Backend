# LaiOffer-flag_camp-team_3-202506

鉴于各位时间并不充裕，本项目鼓励使用AI辅助开发以确保完成进度，但是请在使用AI的同时保证质量，提交之前检查代码和设计是否符合应用逻辑，尽可能减少bug，感谢理解。  
目前的后端设计请看下方章节。组内预计每周一发放任务，<ins>**美东时间周日晚上0点**</ins>为截止日期，请大家按时完成自己的部分。若未能按时完成，可能会视严重程度给予警告或**被踢出小组！！！**  
请生成各自的branch，并在各自branch上工作。在各自branch完成工作后,请github提交pull request，随后由Tech Lead进行检查和merge。不要自己push！不要自己合并！不要用force强制合并！
有任何问题欢迎询问AI,或者私信Tech Lead询问AI，也可以直接在后端群里提出。  
repo内已有炯毅同学建立的Springboot接口， 数据库模板和连接配置在backend/src/main/resources里，各位可在本地自行修改。如果觉得模板有不妥之处欢迎提出。  

## Links

Google Doc link: https://docs.google.com/document/d/1hy0BSl47EQQU-yAn0IQzep72Lrwsj6aQEwrflYmDO-8/edit?usp=sharing

## Environment Design

1. API: RestAPI

2. Database: PostgreSQL + uploaded graphs in back-end server local. (The PostgreSQL records the address only)

3. Platform: Java + Springboot

## Database Design 
<img width="1583" height="1165" alt="image" src="https://github.com/user-attachments/assets/ee9e53ca-d54f-4694-8b50-b0d516af532e" />

前面带钥匙的行是primary key，前面带❉的行是unique key，后面带？的行是nullable的，用线连起来的是foreign key引用。





## Back-end Structure
src/main/java/com/laioffer/flagcamp/backend  
├── controller  
│ ├── UserController.java  
│ ├── ItemController.java  
│ ├── PostController.java  
│ ├── TagController.java  
│ ├── ConversationController.java  
│ └── MessageController.java  
│  
├── service  
│ ├── UserService.java  
│ ├── ItemService.java  
│ ├── PostService.java  
│ ├── TagService.java  
│ ├── ConversationService.java  
│ └── MessageService.java  
│  
├── repository  
│ ├── UserRepository.java  
│ ├── ItemRepository.java  
│ ├── PostRepository.java  
│ ├── TagRepository.java  
│ ├── ConversationRepository.java  
│ └── MessageRepository.java  
│  
├── entity  
│ ├── User.java  
│ ├── Item.java  
│ ├── Post.java  
│ ├── Tag.java  
│ ├── Conversation.java  
│ └── Message.java     
│  
└── BackendApplication.java  
 

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

5. Conversations (会话)

POST	/api/conversations	创建会话（买家发起）

GET	/api/conversations/{userId}	获取该用户的所有会话（作为买家或卖家）

DELETE	/api/conversations/{conversationId}	删除会话（可选）

6. Messages (消息)

POST	/api/messages	发送消息

GET	/api/messages/{conversationId}	获取某个会话的所有消息

DELETE	/api/messages/{messageId}	删除消息（可选）
