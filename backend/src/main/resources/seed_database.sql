-- ===== Create & Switch DB (psql 下有效；若用 SpringBoot schema/data 可忽略) =====
CREATE DATABASE marketplace;
\c marketplace;

-- ===== Drop in dependency order =====
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS conversations;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS users;

-- ======================
-- 1) users
-- ======================
CREATE TABLE users (
    userId    SERIAL PRIMARY KEY,            -- int
    password  VARCHAR(255) NOT NULL,         -- varchar
    email     VARCHAR(255) NOT NULL UNIQUE,  -- ❉ email
    phoneNum  INT UNIQUE,                    -- ❉ phoneNum
    address   VARCHAR(255),                  -- varchar
    userIcon  VARCHAR(255),                  -- varchar? (nullable)
    nickname  VARCHAR(100),                  -- varchar? (nullable)
    notes     VARCHAR(255)                   -- varchar? (nullable)
);

-- ======================
-- 2) items
-- ======================
CREATE TABLE items (
    itemId        SERIAL PRIMARY KEY,        -- int
    itemOwnerId   INT NOT NULL,              -- FK → users.userId
    itemName      VARCHAR(100) NOT NULL,     -- varchar
    productDetail VARCHAR(500),              -- varchar? (nullable)
    productPrice  FLOAT NOT NULL,            -- float
    productImg    VARCHAR(500),              -- varchar
    ifSold        BOOLEAN,                   -- boolean
    CONSTRAINT fk_item_owner
        FOREIGN KEY (itemOwnerId) REFERENCES users(userId) ON DELETE CASCADE
);

-- ======================
-- 3) tags
-- ======================
CREATE TABLE tags (
    tagId       SERIAL PRIMARY KEY,          -- int
    tagContent  VARCHAR(100) NOT NULL        -- varchar
);

-- ======================
-- 4) posts
-- ======================
CREATE TABLE posts (
    postId        SERIAL PRIMARY KEY,        -- int
    postedItemId  INT NOT NULL,              -- FK → items.itemId
    tagId         INT,                       -- FK → tags.tagId (nullable)
    postOwnerId   INT NOT NULL,              -- FK → users.userId
    CONSTRAINT fk_post_item
        FOREIGN KEY (postedItemId) REFERENCES items(itemId) ON DELETE CASCADE,
    CONSTRAINT fk_post_tag
        FOREIGN KEY (tagId)        REFERENCES tags(tagId)   ON DELETE SET NULL,
    CONSTRAINT fk_post_owner
        FOREIGN KEY (postOwnerId)  REFERENCES users(userId) ON DELETE CASCADE
);

-- ======================
-- 5) conversations
-- ======================
CREATE TABLE conversations (
    conversationId SERIAL PRIMARY KEY,       -- int
    buyerId        INT NOT NULL,             -- FK → users.userId
    sellerId       INT NOT NULL,             -- FK → users.userId
    updatedAt      TIMESTAMP DEFAULT NOW(),  -- timestamp
    CONSTRAINT fk_conv_buyer
        FOREIGN KEY (buyerId)  REFERENCES users(userId) ON DELETE CASCADE,
    CONSTRAINT fk_conv_seller
        FOREIGN KEY (sellerId) REFERENCES users(userId) ON DELETE CASCADE
);

-- ======================
-- 6) messages
-- ======================
CREATE TABLE messages (
    messageId      SERIAL PRIMARY KEY,       -- int
    senderId       INT NOT NULL,             -- FK → users.userId
    conversationId INT NOT NULL,             -- FK → conversations.conversationId
    messageContent TEXT NOT NULL,            -- text
    createdAt      TIMESTAMP DEFAULT NOW(),  -- timestamp
    CONSTRAINT fk_msg_sender
        FOREIGN KEY (senderId)      REFERENCES users(userId) ON DELETE CASCADE,
    CONSTRAINT fk_msg_conv
        FOREIGN KEY (conversationId) REFERENCES conversations(conversationId) ON DELETE CASCADE
);

-- =========================================================
-- Seed Data
-- =========================================================

-- users (10) 
INSERT INTO users (password, email, phoneNum, address, userIcon, nickname, notes) VALUES
('123456', 'alice@example.com', 1300000001, 'Beijing',  'UserIcon/UserIcon1.png',  'Alice',  'Buyer user'),
('abcdef', 'bob@example.com',   1300000002, 'Shanghai', 'UserIcon/UserIcon2.png',  'Bob',    'Seller user'),
('pass01', 'carol@example.com', 1300000003, 'Shenzhen', 'UserIcon/UserIcon3.png',  'Carol',  NULL),
('pass02', 'dave@example.com',  1300000004, 'Hangzhou', 'UserIcon/UserIcon4.png',  'Dave',   NULL),
('pass03', 'eve@example.com',   1300000005, 'Guangzhou', 'UserIcon/UserIcon5.png',  'Eve',    NULL),
('pass04', 'frank@example.com', 1300000006, 'Chengdu',  'UserIcon/UserIcon6.png',  'Frank',  NULL),
('pass05', 'grace@example.com', 1300000007, 'Nanjing',  'UserIcon/UserIcon7.png',  'Grace',  NULL),
('pass06', 'heidi@example.com', 1300000008, 'Wuhan',    'UserIcon/UserIcon8.png',  'Heidi',  NULL),
('pass07', 'ivan@example.com',  1300000009, 'Xian',     'UserIcon/UserIcon9.png',  'Ivan',   NULL),
('pass08', 'judy@example.com',  1300000010, 'Suzhou',   'UserIcon/UserIcon10.png', 'Judy',   NULL);

-- items (10) —— 图片链接需替换为真实地址
INSERT INTO items (itemOwnerId, itemName, productDetail, productPrice, productImg, ifSold) VALUES
(2,  'iPhone 13 128GB',    'Blue, good condition',       4999.00, 'Img/Icon1.png',  FALSE),
(2,  'Samsung Galaxy S21', 'Black, 256GB',               3799.00, 'Img/Icon2.png',  TRUE),
(3,  'Nintendo Switch',    'Neon, with case',            1999.00, 'Img/Icon3.png',  FALSE),
(4,  'MacBook Air 2019',   '8GB/256GB, minor scratches', 5299.00, 'Img/Icon4.png',  FALSE),
(5,  'Sony WH-1000XM4',    'Noise cancelling',            899.00, 'Img/Icon5.png',  TRUE),
(6,  'IKEA Desk',          '120x60 cm, white',             399.00, 'Img/Icon6.png',  FALSE),
(7,  'Dyson V8 Vacuum',    'Works great',                1499.00, 'Img/Icon7.png',  FALSE),
(8,  'Canon EOS M50',      'With kit lens',              3499.00, 'Img/Icon8.png',  TRUE),
(9,  'AirPods Pro',        '2nd gen, clean',              999.00, 'Img/Icon9.png',  FALSE),
(10, 'Kindle Paperwhite',  '10th gen',                     599.00, 'Img/Icon10.png', FALSE);

-- tags
INSERT INTO tags (tagContent) VALUES
(1, 'electronics'),
(2, 'laptop'),
(3, 'camera'),
(4, 'phone'),
(5, 'furniture'),
(6, 'home'),
(7, 'book'),
(8, 'appliance'),
(9, 'gaming'),
(10, 'audio');

-- posts (10)
INSERT INTO posts (postedItemId, tagId, postOwnerId) VALUES
(1,  2, 2),
(2,  2, 2),
(3,  6, 3),
(4,  3, 4),
(5,  5, 5),
(6,  7, 6),
(7, 10, 7),
(8,  4, 8),
(9,  1, 9),
(10, 9, 10);

-- conversations (10)
INSERT INTO conversations (buyerId, sellerId) VALUES
(1,2),(3,2),(1,4),(5,4),(9,8),(10,9),(6,7),(3,5),(8,7),(2,10);

-- messages (10)
INSERT INTO messages (senderId, conversationId, messageContent) VALUES
(1, 1, 'Hi, is the iPhone still available?'),
(2, 1, 'Yes, still available.'),
(3, 2, 'Any issues with the S21?'),
(2, 2, 'Minor wear, battery good.'),
(1, 3, 'About the MacBook Air, repair history?'),
(4, 3, 'No repairs, light scratches only.'),
(5, 4, 'Is the price negotiable?'),
(4, 4, 'Slightly negotiable.'),
(9, 5, 'Does the Canon include kit lens?'),
(8, 5, 'Yes, included.');
