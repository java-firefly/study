--重命名表名
alter table BIZ_SECOND_REVIEW_LIMITS rename to BIZ_PRIZE_REVIEW_LIMITS;
rename test to test_new;

--修改表字段
alter table S_PAPER_EMBODY alter column AFFACTED_GENE decimal(9, 4) NOT NULL;

--添加表字段
alter table BIZ_CFG_FORM add(PROMPT varchar2(255));

--删除字段
alter table biz_expert drop (RSRCHDOMAIN);