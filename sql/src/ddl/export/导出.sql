---oracle 生成序列号
dense_rank() over(order by grup.id) orderNo;
rank() over(order by grup.id) orderNo;