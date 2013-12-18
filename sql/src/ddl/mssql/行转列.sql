CREATE TABLE [dbo].[tb](
	[Name] [varchar](10) NULL,
	[Subject] [varchar](10) NULL,
	[Result] [int] NULL
)

INSERT INTO tb (Name,Subject,Result) values ('张三','语文',74);
INSERT INTO tb (Name,Subject,Result) values ('张三','数学',83);
INSERT INTO tb (Name,Subject,Result) values ('张三','物理',93);
INSERT INTO tb (Name,Subject,Result) values ('李四','语文',74);
INSERT INTO tb (Name,Subject,Result) values ('李四','数学',84);
INSERT INTO tb (Name,Subject,Result) values ('李四','物理',94);


select name,
	max(case subject when '语文' then result else 0 end) '语文',
	max(case subject when '数学' then result else 0 end) '数学',
	max(case subject when '物理' then result else 0 end) '物理'
from tb t
group by name;