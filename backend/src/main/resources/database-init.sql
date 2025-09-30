-- 如果数据库不存在，则先创建
CREATE DATABASE marketplace;

-- 切换到 marketplace 数据库
\c marketplace;

-- 删除已有表（按外键依赖顺序，避免报错）
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS users;

-- ======================
-- 1. 用户表 (users)
-- ======================
CREATE TABLE users (
    userId SERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(36) NOT NULL UNIQUE,
    phoneNum INT,
    address VARCHAR(255),
  -- 存放头像图片的本地url
    userIcon VARCHAR(255),
    nickname VARCHAR(100),
    notes VARCHAR(255)
);

-- ======================
-- 2. 商品表 (items)
-- ======================
CREATE TABLE items (
    itemId SERIAL PRIMARY KEY,
    itemOwnerId INT NOT NULL,
    itemName VARCHAR(100) NOT NULL,
    productDetail VARCHAR(500),
    productPrice FLOAT NOT NULL,
  -- 存放商品图片的本地链接，链接可能有多个，用';'连接
    productImg VARCHAR(500), 
    ifSold BOOL,
    CONSTRAINT fk_item_owner FOREIGN KEY (itemOwnerId) REFERENCES users(userId) ON DELETE CASCADE
);

-- ======================
-- 3. 标签表 (tags)
-- ======================
CREATE TABLE tags (
    tagId SERIAL PRIMARY KEY,
    tagContent VARCHAR(20) NOT NULL
);

-- ======================
-- 4. 帖子表 (posts)
-- ======================
CREATE TABLE posts (
    postId SERIAL PRIMARY KEY,
    postedItemId INT NOT NULL,
    tagId INT,
    postOwenerId INT NOT NULL,
    CONSTRAINT fk_post_item FOREIGN KEY (postedItemId) REFERENCES items(itemId) ON DELETE CASCADE,
    CONSTRAINT fk_post_tag FOREIGN KEY (tagId) REFERENCES tags(tagId) ON DELETE SET NULL,
    CONSTRAINT fk_post_owner FOREIGN KEY (postOwnerId) REFERENCES users(userId) ON DELETE CASCADE
);

-- ======================
-- 初始化一些数据 (可选)
-- ======================
INSERT INTO users (password, email, phoneNum, address, nickname)
VALUES 
('123456', 'alice@example.com', 13800138000, 'Beijing', 'Alice'),
('abcdef', 'bob@example.com', 13900139000, 'Shanghai', 'Bob');

INSERT INTO items (itemOwnerId, itemName, productDetail, productPrice, productImg)
VALUES
(1, 'iPhone 13', 'Used iPhone 13, good condition', 3999.0, 'iphone.jpg'),
(2, 'MacBook Pro', '2020 model, M1 chip', 8999.0, 'macbook.jpg');

INSERT INTO tags (tagContent)
VALUES
('手机'),
('电脑'),
('二手');

INSERT INTO posts (postedItemId, tagId, postOwenerId)
VALUES
(1, 1, 1),
(2, 2, 2);
