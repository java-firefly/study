--重命名表名
alter table BIZ_SECOND_REVIEW_LIMITS rename to BIZ_PRIZE_REVIEW_LIMITS;

--修改表字段
alter table S_PAPER_EMBODY alter column AFFACTED_GENE decimal(9, 4) NOT NULL;