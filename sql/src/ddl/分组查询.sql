select email,count(email) a from biz_person_impl  group by email having count(1)>1;