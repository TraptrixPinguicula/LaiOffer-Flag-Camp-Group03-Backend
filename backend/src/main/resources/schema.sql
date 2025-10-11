-- Schema initialization for marketplace database
-- Drops existing tables to keep local startup idempotent
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS conversations;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS users;

-- 1) users
CREATE TABLE users (
    userId    BIGSERIAL PRIMARY KEY,
    password  VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL UNIQUE,
    phoneNum  BIGINT UNIQUE,
    address   VARCHAR(255),
    userIcon  VARCHAR(255),
    nickname  VARCHAR(100),
    notes     VARCHAR(255)
);

-- 2) items
CREATE TABLE items (
    itemId        BIGSERIAL PRIMARY KEY,
    itemOwnerId   BIGINT NOT NULL,
    itemName      VARCHAR(100) NOT NULL,
    productDetail VARCHAR(500),
    productPrice  NUMERIC(10,2) NOT NULL,
    productImg    VARCHAR(500),
    ifSold        BOOLEAN,
    CONSTRAINT fk_item_owner
        FOREIGN KEY (itemOwnerId) REFERENCES users(userId) ON DELETE CASCADE
);

-- 3) tags
CREATE TABLE tags (
    tagId       BIGSERIAL PRIMARY KEY,
    tagContent  VARCHAR(100) NOT NULL
);

-- 4) posts
CREATE TABLE posts (
    postId        BIGSERIAL PRIMARY KEY,
    postedItemId  BIGINT NOT NULL,
    tagId         BIGINT,
    postOwnerId   BIGINT NOT NULL,
    CONSTRAINT fk_post_item
        FOREIGN KEY (postedItemId) REFERENCES items(itemId) ON DELETE CASCADE,
    CONSTRAINT fk_post_tag
        FOREIGN KEY (tagId) REFERENCES tags(tagId) ON DELETE SET NULL,
    CONSTRAINT fk_post_owner
        FOREIGN KEY (postOwnerId) REFERENCES users(userId) ON DELETE CASCADE
);

-- 5) conversations
CREATE TABLE conversations (
    conversationId BIGSERIAL PRIMARY KEY,
    buyerId        BIGINT NOT NULL,
    sellerId       BIGINT NOT NULL,
    updatedAt      TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_conv_buyer
        FOREIGN KEY (buyerId) REFERENCES users(userId) ON DELETE CASCADE,
    CONSTRAINT fk_conv_seller
        FOREIGN KEY (sellerId) REFERENCES users(userId) ON DELETE CASCADE
);

-- 6) messages
CREATE TABLE messages (
    messageId      BIGSERIAL PRIMARY KEY,
    senderId       BIGINT NOT NULL,
    conversationId BIGINT NOT NULL,
    messageContent TEXT NOT NULL,
    createdAt      TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_msg_sender
        FOREIGN KEY (senderId) REFERENCES users(userId) ON DELETE CASCADE,
    CONSTRAINT fk_msg_conv
        FOREIGN KEY (conversationId) REFERENCES conversations(conversationId) ON DELETE CASCADE
);
