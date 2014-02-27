



1、select username,default_tablespace,temporary_tablespace,created,account_status
from dba_users
where account_status in ('OPEN','LOCKED')
and default_tablespace not in ('SYSTEM','SYSAUX') and username like upper ('%&username%')
order by username;

USERNAME                       DEFAULT_TABLESPACE
------------------------------ ------------------------------
TEMPORARY_TABLESPACE           CREATED        ACCOUNT_STATUS
------------------------------ -------------- --------------------------------
YJS_PRD_FINAL                  CUN_PRD
CUN_TEMP                       20-1埖 -11     OPEN


2、
select a.owner,a.tablespace_name,sum(a.bytes)/1024/1024
from dba_segments a,dba_users b
where a.owner=b.username and b.account_status in ('OPEN','LOCKED') and username like upper ('%&username%')
and b.default_tablespace not in ('SYSTEM','SYSAUX')
group by a.owner,a.tablespace_name
order by a.owner;

BTBU_PRD	CUN_PRD	483.1875

3、
select a.* from dba_sys_privs a,dba_users b
where a.grantee=b.username and b.account_status in ('OPEN','LOCKED') and username like upper ('%&username%')
and b.default_tablespace not in ('SYSTEM','SYSAUX')
order by a.grantee;

GRANTEE                        PRIVILEGE                                ADM
------------------------------ ---------------------------------------- ---
YJS_PRD_FINAL                  CREATE ANY VIEW                          NO
YJS_PRD_FINAL                  SELECT ANY DICTIONARY                    NO
YJS_PRD_FINAL                  UNLIMITED TABLESPACE                     NO


4、select a.* from dba_role_privs a,dba_users b
where a.grantee=b.username and b.account_status in ('OPEN','LOCKED') and username like upper ('%&username%')
and b.default_tablespace not in ('SYSTEM','SYSAUX')
order by a.grantee;

GRANTEE                        GRANTED_ROLE                   ADM DEF
------------------------------ ------------------------------ --- ---
YJS_PRD_FINAL                  CONNECT                        NO  YES
YJS_PRD_FINAL                  DBA                            NO  YES
YJS_PRD_FINAL                  RESOURCE                       NO  YES


CREATE TABLESPACE CUN_PRD
DATAFILE '/opt/oracle/product/oradata/heer/CUN_PRD.dbf' SIZE 200M 
AUTOEXTEND ON NEXT 10M
MAXSIZE UNLIMITED LOGGING EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO;

CREATE TEMPORARY TABLESPACE CUN_TEMP 
TEMPFILE '/opt/oracle/product/oradata/heer/CUN_TEMP.dbf' SIZE 100M
AUTOEXTEND ON NEXT 5M 
MAXSIZE UNLIMITED EXTENT MANAGEMENT LOCAL UNIFORM SIZE 1M;

7、
create user yjs_prd_bjmu identified by yjsok
default tablespace CUN_PRD
temporary tablespace CUN_TEMP account unlock;


grant connect to yjs_prd_bjmu;
grant dba to yjs_prd_bjmu;
grant resource to yjs_prd_bjmu;


grant select any dictionary to yjs_prd_bjmu;
grant create any view to yjs_prd_bjmu;
revoke unlimited tablespace from yjs_prd_final;
alter user yjs_prd_final quota unlimited on CUN_PRD;


