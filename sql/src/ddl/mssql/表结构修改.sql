---添加字段
alter table S_ENTRUST add TRUSTER_SELECT_TYPE varchar(32);
update KH_RESULT_DETAIL set  addition_Value=0.0;