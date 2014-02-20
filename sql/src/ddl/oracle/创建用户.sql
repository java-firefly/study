许合森 2013-11-27 17:59:42
--在创建表空间之前可以查看系统的原有表空间物理文件的名称，位置及大小 
/* 
select tablespace_name, file_id, file_name, 
round(bytes/(1024*1024),0) total_space 
from dba_data_files 
order by tablespace_name; 
*/ 

---删除用户
---drop user pro30016 cascade 

create tablespace KY_DATA datafile '放置表空间的位置/KY_DATA.dbf' size 500M autoextend on next 50M maxsize unlimited logging extent management local segment space management auto; 

create temporary tablespace KY_TEMP tempfile '放置表空间的位置/KY_DATA.dbf' size 50M autoextend on next 5M maxsize unlimited extent management local uniform size 1M; 

create user pro30016 identified by eplugger default tablespace PRO30016 temporary tablespace PRO30016_TEMP account unlock; 

grant connect to pro30016; 

grant dba to pro30016; 

grant resource to pro30016; 

grant select any dictionary to pro30016; 

grant create any view to pro30016;