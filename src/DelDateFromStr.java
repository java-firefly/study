import java.util.List;

import io.In;


public class DelDateFromStr {
	public static void main(String[] args) {
		new DelDateFromStr().execute();
	}
	public void execute(){
		In in = new In();
		List<String> delUnits = in.getFileStrAsLine("c:/sql.txt");
		String str = "'基础学院', '信息学院', '软件学院', '中荷学院', '生命科学与健康学院', '体育部', '秦皇岛分校', '网络教育学院', '继续教育学院', '计算中心（网络中心）', '软件中心', '研究院', '流程工业实验室', '文法学院', 'RAL', 'EPM', 'ATM', '多金属共生矿生态化冶金教育部重点实验室', '医学影像计算教育部重点实验室', '校长办公室', '党委办公室', '组织部', '宣传部', '纪委', '外国语学院', '保密委员会办公室', '工会', '团委', '研究生院', '学科建设与发展处', '教务处', '科学技术处', '人事处', '学生工作处（武装部）', '计划财经处', '艺术学院', '审计处', '资产管理处', '国际合作与交流处（国际交流学院）（港澳台事务办公室）', '公安处', '后勤管理处', '基建管理处', '离退休工作处（离退休党委）', '管理学院', '理学院', '学报编辑部', '出版社', '研究院', '自动化研究中心', '转岗分流服务中心', '保密办公室', '科技产业集团', '资土学院', '图书馆', '学生创新中心', '材冶学院', '机械学院'";
		System.out.println(str.length());
		for (String delUnit : delUnits) {
			if(str.contains(delUnit)){
				str = str.replace("'"+delUnit+"', ", "");
			}else{
				System.out.println(delUnit);
			}
		}
		System.out.println(str.length());
		System.out.println(str);
	}
}
