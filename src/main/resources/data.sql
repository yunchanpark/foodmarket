-- 카테고리
INSERT INTO product_category (category_name) VALUES ('채소');
INSERT INTO product_category (category_name) VALUES ('과일');
INSERT INTO product_category (category_name) VALUES ('해산물');
INSERT INTO product_category (category_name) VALUES ('육류');
INSERT INTO product_category (category_name) VALUES ('건강식품');

-- 회원
INSERT INTO member VALUES(1, now(), now(), "충북 청주시 청원구 내수읍 마산길 72", "진흥아파트 103동 507호", "123",'kdnews77', "김상호", "010-8923-7325", "$2a$10$pG2Q3DrIIdJlmhdsOEAq5OcwTseGRqszChN9nJli//S4JI8SDFbHu","안녕", "ROLE_MEMBER", 12);
INSERT INTO member VALUES(2, now(), now(), "충북 청주시 청원구 내수읍 마산길 72", "진흥아파트 103동 507호", "123",'yongp99', "박윤찬",  "010-8923-7325", "$2a$10$pG2Q3DrIIdJlmhdsOEAq5OcwTseGRqszChN9nJli//S4JI8SDFbHu","안녕", "ROLE_MEMBER", 12);
INSERT INTO member VALUES(3, now(), now(), ".", ".", ".",'yongp9', "관리자",  ".", "$2a$10$pG2Q3DrIIdJlmhdsOEAq5OcwTseGRqszChN9nJli//S4JI8SDFbHu",".", "ROLE_ADMIN", 0);

-- 포인트설정
insert into point_condition values(1, 2000, 5, 1000, 1000, 10000);

-- 공지사항'

-- 상품
/*INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 사과", "[돈시몬] 과일 주스 3종 (200mL X 3팩)", now(), now(), "apple.jpg", "apple.jpg", "[돈시몬] 과일 주스 3종 (200mL X 3팩)", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), "2021-12-12T04:05:16", "맛있는 감", "감", now(), now(), "persimmon.jpg", "persimmon.jpg", "감", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 감귤", "[돈시몬] 과일 주스 3종 (200mL X 3팩)", now(), now(), 10, "won", "citrus.jpg", "citrus.jpg", "[돈시몬] 과일 주스 3종 (200mL X 3팩)", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 감자", "감자", now(), now(), 10, "percent", "potato.jpg", "potato.jpg", "감자", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 건목이버섯", "건목이버섯", now(), now(), 10, "percent", "driedoystermushroom.jpg", "driedoystermushroom.jpg", "건목이버섯", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 건표고버섯", "건표고버섯", now(), now(), 10, "percent", "driedshitakemushrooms.jpg", "driedshitakemushrooms.jpg", "건표고버섯", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 고구마", "고구마", now(), now(), 10, "percent", "sweetpotato.jpg", "sweetpotato.jpg", "고구마", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 고등어", "고등어", now(), now(), 10, "percent", "mackerel.jpg", "mackerel.jpg", "고등어", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 닭가슴살 큐브", "닭가슴살 큐브", now(), now(), 10, "percent", "chickenbreastcubes.jpg", "chickenbreastcubes.jpg", "닭가슴살 큐브", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 닭가슴살", "닭가슴살", now(), now(), 10, "percent", "chickenbreast.jpg", "chickenbreast.jpg", "닭가슴살", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 당근", "당근", now(), now(), 10, "percent", "carrot.jpg", "carrot.jpg", "당근", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 대파", "대파", now(), now(), 10, "percent", "greenonion.jpg", "greenonion.jpg", "대파", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 떡갈비", "떡갈비", now(), now(), 10, "percent", "tteokgalbi.jpg", "tteokgalbi.jpg", "떡갈비", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 메로구이", "메로구이", now(), now(), 10, "percent", "grilledmero.jpg", "grilledmero.jpg", "메로구이", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 명태", "명태", now(), now(), 10, "percent", "pollock.jpg", "pollock.jpg", "명태", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 무화과", "무화과", now(), now(), 10, "percent", "fig.jpg", "fig.jpg", "무화과", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 민어회", "민어회", now(), now(), 10, "percent", "mineosashimi.jpg", "mineosashimi.jpg", "민어회", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 밀크씨슬", "밀크씨슬", now(), now(), 10, "percent", "milkthistle.jpg", "milkthistle.jpg", "밀크씨슬", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 배", "배", now(), now(), 10, "percent", "pear.jpg", "pear.jpg", "배", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 벌꿀", "벌꿀", now(), now(), 10, "percent", "honey.jpg", "honey.jpg", "벌꿀", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 봉지벌꿀", "봉지벌꿀", now(), now(), 10, "percent", "baghoney.jpg", "baghoney.jpg", "봉지벌꿀", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 브로콜리", "브로콜리", now(), now(), 10, "percent", "broccoli.jpg", "broccoli.jpg", "브로콜리", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 비타민", "비타민", now(), now(), 10, "percent", "vitamin.jpg", "vitamin.jpg", "비타민", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 삼겹살", "삼겹살", now(), now(), 10, "percent", "porkbelly.jpg", "porkbelly.jpg", "삼겹살", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 샐러드", "샐러드", now(), now(), 10, "percent", "salad.jpg", "salad.jpg", "샐러드", 1000, 500, 999, 2);
INSERT INTO product
(`created_at`, `updated_at`, `product_description`, `product_detail_content`, `product_discount_end`, `product_discount_start`, `product_discount`,
`product_exchange_rate`, `product_image_orgin`, `product_image_save`, `product_name`, `product_price`, `product_purchase_price`, `product_stock`,
`category_no`)
VALUES
(now(), now(), "맛있는 쌈채소", "쌈채소", now(), now(), 10, "percent", "greenvegetables.jpg", "greenvegetables.jpg", "쌈채소", 1000, 500, 999, 2);*/