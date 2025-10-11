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
('123456', 'alice@example.com', 1300000001, 'Beijing',  'https://picsum.photos/seed/u1/200/200',  'Alice',  'Buyer user'),
('abcdef', 'bob@example.com',   1300000002, 'Shanghai', 'https://picsum.photos/seed/u2/200/200',  'Bob',    'Seller user'),
('pass01', 'carol@example.com', 1300000003, 'Shenzhen', 'https://picsum.photos/seed/u3/200/200',  'Carol',  NULL),
('pass02', 'dave@example.com',  1300000004, 'Hangzhou', 'https://picsum.photos/seed/u4/200/200',  'Dave',   NULL),
('pass03', 'eve@example.com',   1300000005, 'Guangzhou','https://picsum.photos/seed/u5/200/200',  'Eve',    NULL),
('pass04', 'frank@example.com', 1300000006, 'Chengdu',  'https://picsum.photos/seed/u6/200/200',  'Frank',  NULL),
('pass05', 'grace@example.com', 1300000007, 'Nanjing',  'https://picsum.photos/seed/u7/200/200',  'Grace',  NULL),
('pass06', 'heidi@example.com', 1300000008, 'Wuhan',    'https://picsum.photos/seed/u8/200/200',  'Heidi',  NULL),
('pass07', 'ivan@example.com',  1300000009, 'Xian',     'https://picsum.photos/seed/u9/200/200',  'Ivan',   NULL),
('pass08', 'judy@example.com',  1300000010, 'Suzhou',   'https://picsum.photos/seed/u10/200/200', 'Judy',   NULL);

-- items (10) —— 图片链接需替换为真实地址
INSERT INTO items (itemOwnerId, itemName, productDetail, productPrice, productImg, ifSold) VALUES
(2,  'iPhone 13 128GB',    'Blue, good condition',       4999.00, 'https://picsum.photos/seed/p1/600/400',  FALSE),
(2,  'Samsung Galaxy S21', 'Black, 256GB',               3799.00, 'https://picsum.photos/seed/p2/600/400',  TRUE),
(3,  'Nintendo Switch',    'Neon, with case',            1999.00, 'https://picsum.photos/seed/p3/600/400',  FALSE),
(4,  'MacBook Air 2019',   '8GB/256GB, minor scratches', 5299.00, 'https://picsum.photos/seed/p4/600/400',  FALSE),
(5,  'Sony WH-1000XM4',    'Noise cancelling',            899.00, 'https://picsum.photos/seed/p5/600/400',  TRUE),
(6,  'IKEA Desk',          '120x60 cm, white',             399.00, 'https://picsum.photos/seed/p6/600/400',  FALSE),
(7,  'Dyson V8 Vacuum',    'Works great',                1499.00, 'https://picsum.photos/seed/p7/600/400',  FALSE),
(8,  'Canon EOS M50',      'With kit lens',              3499.00, 'https://picsum.photos/seed/p8/600/400',  TRUE),
(9,  'AirPods Pro',        '2nd gen, clean',              999.00, 'https://picsum.photos/seed/p9/600/400',  FALSE),
(10, 'Kindle Paperwhite',  '10th gen',                     599.00, 'https://picsum.photos/seed/p10/600/400', FALSE);

-- tags
INSERT INTO tags (tagContent) VALUES
(1, 'electronics'),
(2, 'laptop'),
(3, 'camera'),
(4, 'phone'),
(5, 'furniture'),
(6, 'home'),
(7, 'book'),
(8, 'appliance');

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
