-- Sample data for local development

-- users
INSERT INTO users (userId, password, email, phoneNum, address, userIcon, nickname, notes) VALUES
(1, '123456', 'alice@example.com', 1300000001, 'Beijing',  'UserIcon/UserIcon1.png',  'Alice',  'Buyer user'),
(2, 'abcdef', 'bob@example.com',   1300000002, 'Shanghai', 'UserIcon/UserIcon2.png',  'Bob',    'Seller user'),
(3, 'pass01', 'carol@example.com', 1300000003, 'Shenzhen', 'UserIcon/UserIcon3.png',  'Carol',  NULL),
(4, 'pass02', 'dave@example.com',  1300000004, 'Hangzhou', 'UserIcon/UserIcon4.png',  'Dave',   NULL),
(5, 'pass03', 'eve@example.com',   1300000005, 'Guangzhou', 'UserIcon/UserIcon5.png',  'Eve',    NULL),
(6, 'pass04', 'frank@example.com', 1300000006, 'Chengdu',  'UserIcon/UserIcon6.png',  'Frank',  NULL),
(7, 'pass05', 'grace@example.com', 1300000007, 'Nanjing',  'UserIcon/UserIcon7.png',  'Grace',  NULL),
(8, 'pass06', 'heidi@example.com', 1300000008, 'Wuhan',    'UserIcon/UserIcon8.png',  'Heidi',  NULL),
(9, 'pass07', 'ivan@example.com',  1300000009, 'Xian',     'UserIcon/UserIcon9.png',  'Ivan',   NULL),
(10, 'pass08', 'judy@example.com',  1300000010, 'Suzhou',   'UserIcon/UserIcon10.png', 'Judy',   NULL);

SELECT setval('users_userid_seq', (SELECT MAX(userId) FROM users));

-- tags
INSERT INTO tags (tagId, tagContent) VALUES
(1, 'Electronics'),
(2, 'Home'),
(3, 'Fashion'),
(4, 'Books'),
(5, 'Sports');

SELECT setval('tags_tagid_seq', (SELECT MAX(tagId) FROM tags));

-- items
INSERT INTO items (itemId, itemOwnerId, itemName, productDetail, productPrice, productImg, ifSold) VALUES
(1, 2, 'iPhone 13 128GB',    'Blue, good condition',       4999.00, 'Img/Icon1.png',  FALSE),
(2, 2, 'Samsung Galaxy S21', 'Black, 256GB',               3799.00, 'Img/Icon2.png',  TRUE),
(3, 3, 'Nintendo Switch',    'Neon, with case',            1999.00, 'Img/Icon3.png',  FALSE),
(4, 4, 'MacBook Air 2019',   '8GB/256GB, minor scratches', 5299.00, 'Img/Icon4.png',  FALSE),
(5, 5, 'Sony WH-1000XM4',    'Noise cancelling',            899.00, 'Img/Icon5.png',  TRUE),
(6, 6, 'IKEA Desk',          '120x60 cm, white',             399.00, 'Img/Icon6.png',  FALSE),
(7, 7, 'Dyson V8 Vacuum',    'Works great',                1499.00, 'Img/Icon7.png',  FALSE),
(8, 8, 'Canon EOS R6',       'Full-frame mirrorless',      18799.00,'Img/Icon8.png',  FALSE),
(9, 9, 'Kindle Paperwhite',  '10th Gen, waterproof',         899.00,'Img/Icon9.png',  TRUE),
(10,10,'Nike Air Zoom',      'Running shoes, size 42',       999.00,'Img/Icon10.png', FALSE);

SELECT setval('items_itemid_seq', (SELECT MAX(itemId) FROM items));

-- posts
INSERT INTO posts (postId, postedItemId, tagId, postOwnerId) VALUES
(1, 1, 1, 2),
(2, 2, 1, 2),
(3, 3, 1, 3),
(4, 4, 1, 4),
(5, 5, 1, 5),
(6, 6, 2, 6),
(7, 7, 2, 7),
(8, 8, 1, 8),
(9, 9, 4, 9),
(10,10,3,10);

SELECT setval('posts_postid_seq', (SELECT MAX(postId) FROM posts));

-- conversations
INSERT INTO conversations (conversationId, buyerId, sellerId, updatedAt) VALUES
(1, 1, 2, NOW()),
(2, 3, 2, NOW()),
(3, 4, 5, NOW());

SELECT setval('conversations_conversationid_seq', (SELECT MAX(conversationId) FROM conversations));

-- messages
INSERT INTO messages (messageId, senderId, conversationId, messageContent, createdAt) VALUES
(1, 1, 1, '你好,这个商品还在吗?', NOW() - INTERVAL '2 days'),
(2, 2, 1, '在的, 你想了解什么?', NOW() - INTERVAL '1 day'),
(3, 1, 1, '可以优惠一点吗?', NOW() - INTERVAL '23 hours'),
(4, 3, 2, 'Hi, is the Galaxy S21 still available?', NOW() - INTERVAL '12 hours'),
(5, 2, 2, 'Yes, still available.', NOW() - INTERVAL '11 hours'),
(6, 4, 3, 'I am interested in the Sony headphones.', NOW() - INTERVAL '6 hours');

SELECT setval('messages_messageid_seq', (SELECT MAX(messageId) FROM messages));
