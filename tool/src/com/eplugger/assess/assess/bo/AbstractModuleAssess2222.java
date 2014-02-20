package com.eplugger.assess.assess.bo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.eplugger.assess.assess.model.AssessDisplayAttrib;
import com.eplugger.assess.assess.model.AssessObject;
import com.eplugger.assess.assess.model.AssessResultDetail;
import com.eplugger.assess.assess.model.AssessTime;
import com.eplugger.assess.assess.model.ModuleAssess;
import com.eplugger.assess.assess.model.PersonAssess;
import com.eplugger.assess.assess.model.UnitAssess;
import com.eplugger.assess.kyAssess.util.ComputerAssess;
import com.eplugger.business.cfg.model.ModelParam;
import com.eplugger.business.cfg.model.ObjectMeta;
import com.eplugger.business.cfg.model.PageAttrib;
import com.eplugger.business.project.bo.ProjectYearWorkBO;
import com.eplugger.business.project.model.Project;
import com.eplugger.business.project.model.ProjectMember;
import com.eplugger.business.pub.bo.BOFactory;
import com.eplugger.business.pub.bo.BaseBO;
import com.eplugger.business.pub.category.bo.CategoryCreator;
import com.eplugger.business.pub.model.BaseModel;
import com.eplugger.business.pub.model.Product;
import com.eplugger.business.system.bo.SystemParamBO;
import com.eplugger.business.system.util.SystemParam;
import com.eplugger.business.util.StringUtil;
@SuppressWarnings("unchecked")
public abstract class AbstractModuleAssess2222 implements ModuleAssessBO {

  /**
   * 考核对象
   */
  protected AssessObject assessObject;
  /**
   * 考核批次
   */
  protected AssessTime time;
  /**
   * 考核人员
   */
  protected PersonAssess personAssess;
  protected UnitAssess unitAssess;
  protected ComputerAssess computerAssess;
  protected Date beginDate;
  protected Date endDate;
  /**
   * 定义显示列
   */
  String[] titles;
  /**
   * 定义显示列id 如id,name
   */
  String[] ids;
  /**
   * 模块总分值
   */
  float total = 0;

  /**
   * 初始化计算器
   * 
   */
  public void initComputer() {
    if (time != null) {
      computerAssess = new ComputerAssess(time.getSchemeId(), assessObject
          .getId());
    }
  }

  /**
   * 计算考核结果 如果数据库中存在
   * 
   * @param existFlag
   * @return
   */
  public ModuleAssess compute() {
    ModuleAssess moduleAssess = new ModuleAssess();
    try {
      prepareData();
    } catch (Exception e) {
      e.printStackTrace();
    }
    List ls = processAssessResult();
    moduleAssess.setModuleName(assessObject.getName()); // 模块考核 -模块名
    moduleAssess.setTitles(titles); // 模块考核 -表头
    moduleAssess.setValue(StringUtil.formFloatBySystemPram(total)); // 模块考核 -总分
    moduleAssess.setList(ls); // 模块考核 -结果集
    return moduleAssess;
  }

  public ModuleAssess update() {
    ModuleAssess moduleAssess = new ModuleAssess();
    moduleAssess.setModuleName(assessObject.getName());
    updateValue();
    moduleAssess.setTitles(titles);
    moduleAssess.setValue(StringUtil.formFloatBySystemPram(total));
    return moduleAssess;
  }

  /**
   * 从数据库中读取模块考核结果
   * 
   * @return
   */
  public ModuleAssess readModuleResult() {
    ModuleAssess moduleAssess = new ModuleAssess();
    try {
      prepareData();
    } catch (Exception e) {
      e.printStackTrace();
    }
    List ls = new ArrayList();
    if ("1".equals(time.getAssessScheme().getAssessObject())) {
      ls = AssessResultDetailBO.getAsessResultDetail(time.getId(),
          personAssess.getPersonId(), assessObject.getId());
    } else {
      ls = AssessResultDetailBO.getUnitAsessResultDetail(time.getId(),
          String.valueOf(unitAssess.getUnitId()), assessObject.getId());
    }
    moduleAssess.setModuleId(assessObject.getModuleId());
    moduleAssess.setModuleName(assessObject.getName()); // 模块考核 -模块名
    moduleAssess.setTitles(titles); // 模块考核 -表头
    moduleAssess.setValue(StringUtil.formFloatBySystemPram(total)); // 模块考核 -总分
    moduleAssess.setList(ls);
    return moduleAssess;
  }

  /**
   * 准备显示数据
   * 
   * @throws Exception
   * @throws Exception
   */
  public void prepareData() throws Exception {
    // 个人科研详情
    if (time == null) {
      List list = SystemParamBO.getPageAttribList(assessObject.getModuleId(),
          "objectMeta.listFlag", "1");
      ids = new String[list.size()];
      titles = new String[list.size()];
      for (int i = 0; i < list.size(); i++) {
        PageAttrib pageAttrib = (PageAttrib) list.get(i);
        ids[i] = pageAttrib.getObjectMeta().getAttributeName();
        titles[i] = pageAttrib.getObjectMeta().getDisplayName();
      }
    } else {
      // 如果集中设置考核时间，时间从批次信息中获得
      if ("1".equals(time.getAssessScheme().getDateFlag())) {
        beginDate = time.getBeginDate();
        endDate = time.getEndDate();
      }
      // 如果个人设置考核时间，时间从个人考核信息中获取
      else {
        beginDate = personAssess.getBeginDate();
        beginDate = personAssess.getEndDate();
      }
      try {
        AssessDisplayAttrib attribs = new AssessDisplayAttribBO().getAttribIds(
            time.getSchemeId(), assessObject.getId());
        // 如果考核结果列表字段为设置，从objectMeta表中获取listFlag=1的数据，保存进考核结果列表
        if (attribs == null) {
          List objectMetaList = SystemParamBO.getObjectMetaList(assessObject
              .getModuleId(), "objectMeta.listFlag", "1");
          StringBuffer attribIds = new StringBuffer();
          StringBuffer attribNames = new StringBuffer();
          for (int i = 0; i < objectMetaList.size(); i++) {
            ObjectMeta objectMeta = (ObjectMeta) objectMetaList.get(i);
            attribIds.append(objectMeta.getAttributeName());
            attribIds.append(",");
            attribNames.append(objectMeta.getDisplayName());
            attribNames.append(",");
          }
          attribs = new AssessDisplayAttrib(time.getSchemeId(), assessObject
              .getId());
          attribs.setAttribIds(attribIds.toString());
          attribs.setAttribNames(attribNames.toString());
          new AssessDisplayAttribBO().baseAddBean(attribs);
        }
        ids = attribs.getAttribIds().split(",");
        titles = attribs.getAttribNames().split(",");
      } catch (Exception e) {
        System.out.println("得到考核结果表头报错！");
        e.printStackTrace();
      }
    }

  }

  /**
   * 得到查询考核数据的sql
   * 
   * @return
   */
  public abstract String getSql();

  public abstract String getAttrValue(BaseModel baseModel, String attrName);

  /**
   * 得到考核结果并将结果填充到特定对象中
   * 
   * @return
   */
  public List processAssessResult() {
    ModelParam modelParam = (ModelParam) SystemParam.modelParams
        .get(assessObject.getModuleId());
    BaseBO baseBO = BOFactory.createBO(modelParam.getBoClass());
    List ls = new ArrayList();
    try {
      String sql = this.getSql();
      List<Product> resultList1 = baseBO.list(sql);
      List<Product> resultList = new ArrayList<Product>(new HashSet<Product>(
          resultList1));
      resultList = pretreatData(resultList);
      // 考核
      if (time.getTimeTypeId() == 0) {
        ls = processKhResult(resultList);
      }
      // 奖励
      else {
        ls = processHonorResult(resultList);
      }
      // 保存考核结果
      AssessResultDetailBO detailBO = new AssessResultDetailBO();
      for (int i = 0; i < ls.size(); i++) {
        AssessResultDetail detail = (AssessResultDetail) ls.get(i);
        detailBO.baseAddBean(detail);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return ls;
  }

  /**
   * 处理考核结果
   * 
   * @param resultList
   *          参与考核数据
   * @return
   */
  public List processKhResult(List resultList) {
    List<AssessResultDetail> ls = new ArrayList<AssessResultDetail>();
    if ("1".equals(time.getAssessScheme().getAssessObject())) {
      // 得到单个考核对象的考核结果
      for (Iterator iter = resultList.iterator(); iter.hasNext();) {
        BaseModel baseModel = (BaseModel) iter.next();
        AssessResultDetail detail = new AssessResultDetail();
        detail.setPersonId(personAssess.getPersonId());
        detail.setObjectId(assessObject.getId());
        if (time != null)
          detail.setTimeId(time.getId());
        detail.setRecordId(baseModel.getId());
        String record = "";
        for (int j = 0; j < titles.length; j++) {
          try {
            record += "," + getAttrValue(baseModel, ids[j]);
          } catch (Exception e) {
            record += ",";
          }
        }
        if (record.length() > 0)
          record = record.substring(1);
        // 避免末项记录为空时，无法解析到
        if (record.endsWith(","))
          record += " ";
        detail.setRecordContent(record);
        if (time != null) {
          float distributeValue = 0f;
          if ("1".equals(time.getAssessScheme().getIsCompute())) {
            distributeValue = distributer((Product) baseModel, personAssess
                .getPersonId(), assessObject.getKeyId());
          }
          detail.setSystemValue(distributeValue);
          detail.setAdjustValue(distributeValue);
          total = total + detail.getAdjustValue();
        }
        ls.add(detail);
      }
    } else {
      for (Iterator iter = resultList.iterator(); iter.hasNext();) {
        BaseModel baseModel = (BaseModel) iter.next();
        AssessResultDetail detail = new AssessResultDetail();
        detail.setUnitId(unitAssess.getUnitId());
        detail.setObjectId(assessObject.getKeyId());
        if (time != null)
          detail.setTimeId(time.getKeyId());
        detail.setRecordId(baseModel.getId());
        String record = "";
        for (int j = 0; j < titles.length; j++) {
          try {
            record += "," + getAttrValue(baseModel, ids[j]);
          } catch (Exception e) {
            record += ",";
          }
        }
        if (record.length() > 0)
          record = record.substring(1);
        // 避免末项记录为空时，无法解析到
        if (record.endsWith(","))
          record += " ";
        detail.setRecordContent(record);
        if (time != null) {
          float distributeValue = 0f;
          if ("1".equals(time.getAssessScheme().getIsCompute())) {
            distributeValue = computer((Product) baseModel);
          }
          detail.setSystemValue(distributeValue);
          detail.setAdjustValue(distributeValue);
          total = total + detail.getAdjustValue();
        }
        ls.add(detail);
      }
    }
    return ls;
  }

  /**
   * 处理奖励结果
   * 
   * @param resultList
   *          参与奖励数据
   * @return
   */
  public List processHonorResult(List resultList) {
    List<AssessResultDetail> ls = new ArrayList<AssessResultDetail>();
    // 得到单个考核对象的奖励结果
    if ("1".equals(time.getAssessScheme().getAssessObject())) {
      for (Iterator iter = resultList.iterator(); iter.hasNext();) {
        BaseModel baseModel = (BaseModel) iter.next();
        AssessResultDetail detail = new AssessResultDetail();
        detail.setPersonId(personAssess.getPersonId());
        detail.setObjectId(assessObject.getKeyId());
        if (time != null)
          detail.setTimeId(time.getKeyId());
        detail.setRecordId(baseModel.getId());
        String record = "";
        for (int j = 0; j < titles.length; j++) {
          try {
            record += "," + getAttrValue(baseModel, ids[j]);
          } catch (Exception e) {
            record += ",";
          }
        }
        if (record.length() > 0)
          record = record.substring(1);
        // 避免末项记录为空时，无法解析到
        if (record.endsWith(","))
          record += " ";
        detail.setRecordContent(record);
        if (time != null) {
          float distributeValue = 0f;
          if ("1".equals(time.getAssessScheme().getIsCompute())) {
            distributeValue = computer((Product) baseModel);
          }
          detail.setSystemValue(distributeValue);
          detail.setAdjustValue(distributeValue);
          total = total + detail.getAdjustValue();
        }
        ls.add(detail);
      }
    } else {
      for (Iterator iter = resultList.iterator(); iter.hasNext();) {
        BaseModel baseModel = (BaseModel) iter.next();
        AssessResultDetail detail = new AssessResultDetail();
        detail.setUnitId(unitAssess.getUnitId());
        detail.setObjectId(assessObject.getKeyId());
        if (time != null)
          detail.setTimeId(time.getKeyId());
        detail.setRecordId(baseModel.getId());
        String record = "";
        for (int j = 0; j < titles.length; j++) {
          try {
            record += "," + getAttrValue(baseModel, ids[j]);
          } catch (Exception e) {
            record += ",";
          }
        }
        if (record.length() > 0)
          record = record.substring(1);
        // 避免末项记录为空时，无法解析到
        if (record.endsWith(","))
          record += " ";
        detail.setRecordContent(record);
        if (time != null) {
          float distributeValue = 0f;
          if ("1".equals(time.getAssessScheme().getIsCompute())) {
            distributeValue = computer((Product) baseModel);
          }
          detail.setSystemValue(distributeValue);
          detail.setAdjustValue(distributeValue);
          total = total + detail.getAdjustValue();
        }
        ls.add(detail);
      }
    }
    return ls;
  }

  public void updateValue() {
    ModelParam modelParam = (ModelParam) SystemParam.modelParams
        .get(assessObject.getModuleId());
    BaseBO baseBO = BOFactory.createBO(modelParam.getBoClass());
    AssessResultDetailBO detailBO = new AssessResultDetailBO();
    try {
      String sql = this.getSql();
      List<Product> resultList1 = baseBO.list(sql);
      Set<Product> set = new HashSet<Product>(resultList1);
      List<Product> resultList = new ArrayList<Product>(set);
      for (Iterator iter = resultList.iterator(); iter.hasNext();) {
        BaseModel baseModel = (BaseModel) iter.next();
        String recordId = baseModel.getId();
        String personId = personAssess.getPersonId();
        String timeId = time.getId();
        String objectId = assessObject.getId();
        AssessResultDetail detail = detailBO.getPersonAssessResultDetail(
            timeId, personId, recordId, objectId);
        total = total + detail.getAdjustValue();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 得到业务对象属性值 如果是下拉框进行自动转换
   * 
   * @param baseModel
   *          业务对象
   * @param objectMeta
   *          元数据
   * @return 业务字段值
   */
  public String getAttribValue(ObjectMeta objectMeta, String value) {
    if (objectMeta == null)
      return ""; // 处理无效配置
    // 直接输出到页面
    if ("list".equals(objectMeta.getDisplayType())) {
      value = CategoryCreator.getIdName(objectMeta.getCategoryKey(), value);
    }
    if (value == null) {
      value = "";
    }
    // “，”号在字符串中有特殊含义，将此处的“，”号用“ ”替代
    if (value.indexOf(",") >= 0) {
      value = value.replaceAll(",", " ");
    }
    return value;
  }

  public AssessObject getAssessObject() {
    return assessObject;
  }

  public void setAssessObject(AssessObject assessObject) {
    this.assessObject = assessObject;
  }

  public AssessTime getTime() {
    return time;
  }

  public void setTime(AssessTime time) {
    this.time = time;
  }

  public PersonAssess getPersonAssess() {
    return personAssess;
  }

  public void setPersonAssess(PersonAssess personAssess) {
    this.personAssess = personAssess;
  }

  public ComputerAssess getComputerAssess() {
    return computerAssess;
  }

  public void setComputerAssess(ComputerAssess computerAssess) {
    this.computerAssess = computerAssess;
  }

  public UnitAssess getUnitAssess() {
    return unitAssess;
  }

  public void setUnitAssess(UnitAssess unitAssess) {
    this.unitAssess = unitAssess;
  }

  /**
   * 实现 根据指定的计分方案获得每个项目的考核分
   */
  public float computer(Product productObj) {
    return computerAssess.computer(productObj);
  }

  /**
   * 对外提供的主要接口 该接口先调用computer得到成果得分, 再调用distributer并传递成果得分的分值得到该人员在该成果上的最终考核得分
   * 
   * @param productObj
   * @param authorId
   * @return
   */
  public float distributer(Product productObj, String authorId, String objectId) {
    return this.distributer(productObj, computer(productObj), authorId,
        objectId);
  }

  /**
   * 根据考核得分和分配方案 获得该人员的实际得分
   */
  @SuppressWarnings("deprecation")
public float distributer(Product productObj, float computeValue,
		String authorId, String objectId) {
    if (productObj instanceof Project) {
      ProjectMember projectMember = (ProjectMember) new com.eplugger.assess.kyAssess.util.FormulaResolve()
          .findAuthor(productObj, authorId);
      float workRatio = new ProjectYearWorkBO().getMemberProjectYearWorks(
          productObj.getId(), time.getEndDate()
              .getYear() + 1900, projectMember.getPersonId());
      projectMember.setWorkRatio(workRatio);
    }
    return computerAssess.distributer(productObj, computeValue, authorId,
        objectId);
  }

  /**
   * 对参与考核的数据进行预处理
   * 
   * @param list
   * @return
   */
  public List<Product> pretreatData(List<Product> list) {
    return list;
  }

}
