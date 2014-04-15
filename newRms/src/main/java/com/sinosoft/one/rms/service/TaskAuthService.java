package com.sinosoft.one.rms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.rms.User;
import com.sinosoft.one.rms.model.Company;
import com.sinosoft.one.rms.model.Task;
import com.sinosoft.one.rms.model.TaskAuth;
import com.sinosoft.one.rms.repositories.CompanyDao;
import com.sinosoft.one.rms.repositories.GeRmsTaskAuthRepository;
import com.sinosoft.one.rms.repositories.GeRmsTaskRepository;
import com.sinosoft.one.uiutil.NodeEntity;
import com.sinosoft.one.uiutil.Treeable;
@Component
public class TaskAuthService {

	@Autowired
	private GeRmsTaskAuthRepository geRmsTaskAuthRepository;
	@Autowired
	private GeRmsTaskRepository geRmsTaskRepository;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private Invocation inv;

	//把满足条件的task存入Treeable对象，并返回
	public Treeable<NodeEntity> treeAble(String comCode) {
		Company company = companyDao.findOne(comCode);
		
		String parentComCode = company.getUpperComCode();

		List<Task> topList = new ArrayList<Task>();
		List<Task>showTasks= (List<Task>) geRmsTaskRepository.findAll();
		for (Task task : showTasks) {
			if (task.getParent() == null) {
				topList.add(task);
			}
		} 
		
		List<String> taskIdList = geRmsTaskAuthRepository.findAllTaskIdByComCode(comCode);
		List<String> parentTaskIdList = geRmsTaskAuthRepository.findAllTaskIdByComCode(parentComCode);
		
		Treeable<NodeEntity> treeable = creatTaskTreeAble(topList,taskIdList,parentTaskIdList);
		return treeable;

	}
	
	//保存修改后的功能授权
	public void save(String strId, String comCode, TaskAuth taskAuth) {
		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String name = user.getUserName();
		
		//查询当前机构已有的功能
		List<String> taskIdAuth = geRmsTaskAuthRepository.findAllTaskIdByComCode(comCode);
		
		//从页面传到后台的功能ID
		String[] taskId = strId.split(",");
		List<String> taskID = new ArrayList<String>();
		
		for(String id : taskId){
			taskID.add(id);
		}
		
		for (int i = 0; i < taskId.length; i++) {
			//新增功能			
			if (!taskIdAuth.contains(taskId[i])) {
				List<String> grandId = new ArrayList<String>();
				List<String> grand = new ArrayList<String>();
				//取得此功能的上辈功能
				grandId = grandTaskId(grand,taskId[i]);

				//如果没有上辈功能，先保存子功能的上辈功能
				if(!grandId.isEmpty())
					for(String id : grandId){
						if (!taskIdAuth.contains(id)) {
							TaskAuth grandtaskAuth = new TaskAuth();
							grandtaskAuth.setComCode(comCode);
							grandtaskAuth.setOperateUser(name);
							grandtaskAuth.setTask(geRmsTaskRepository.findOne(id));
							taskIdAuth.add(id);
							geRmsTaskAuthRepository.save(grandtaskAuth);
						}
					}
				TaskAuth grandtaskAuth = new TaskAuth();
				grandtaskAuth.setComCode(comCode);
				grandtaskAuth.setOperateUser(name);
				grandtaskAuth.setTask(geRmsTaskRepository.findOne(taskId[i]));
				taskIdAuth.add(taskId[i]);
				geRmsTaskAuthRepository.save(grandtaskAuth);
			}
		}
		
		//删除功能
		for(String id : taskIdAuth){
			
			if(!taskID.contains(id)){
				List<String> childComCode = new ArrayList<String>();
				
				//查出后代comCode
				childComCode = childComId(childComCode,comCode);
				//添加当前机构的comCode
				childComCode.add(comCode);
				
				List<TaskAuth> delTaskAuth = new ArrayList<TaskAuth>();
				for(String childId : childComCode){
					//查出功能授权ID
					String taskAuthId = geRmsTaskAuthRepository.findTaskAuthIdByComCodeTaskId(childId, id);
					if(taskAuthId != null){
						//将将要删除的功能授权存入delTaskAuth集合中
						delTaskAuth.add(geRmsTaskAuthRepository.findOne(taskAuthId));
					}
				}
				//删除功能授权
				geRmsTaskAuthRepository.delete(delTaskAuth);
			}
			
		}
		
	}
	
	/**
	 * 构建功能树 topTasks父节点 filter所有节点
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  Treeable<NodeEntity> creatTaskTreeAble(List<Task> topTasks,List<String> taskIdList,List<String> parentTaskIdList){
		List<NodeEntity> nodeEntitys=new ArrayList<NodeEntity>();
		nodeEntitys=creatSubNode(topTasks,taskIdList,parentTaskIdList);
		Treeable<NodeEntity> treeable =new Treeable.Builder(nodeEntitys,"id", "title", "children", "state").classField("classField").urlField("urlField").builder();
		return treeable;
	}
	
	List<NodeEntity> creatSubNode(List<Task> topTasks,List<String> taskIdList,List<String> parentTaskIdList){
		ArrayList<NodeEntity> nodeEntitys=new ArrayList<NodeEntity>();
		for (Task geRmsTask : topTasks) {
			if(!parentTaskIdList.contains(geRmsTask.getTaskID()))
                continue;
				NodeEntity nodeEntity = new NodeEntity();
				nodeEntity.setId(geRmsTask.getTaskID());
				nodeEntity.setTitle(geRmsTask.getName());
				if(taskIdList.contains(geRmsTask.getTaskID())){
					nodeEntity.setClassField("jstree-checked");
				}else{
					nodeEntity.setClassField("");
				}

				if(!geRmsTask.getChildren().isEmpty()){
					nodeEntity.setChildren(creatSubNode(geRmsTask.getChildren(),taskIdList,parentTaskIdList));
					int count = 0;
					
					//判断父节点的checkbox的状态
					if(!nodeEntity.getChildren().isEmpty()){
						for(NodeEntity ne : nodeEntity.getChildren()){
							if(ne.getClassField().equals("jstree-checked")){
								count++;
							}
						}
					//子节点全部被选中，父节点为选中
					if(count == nodeEntity.getChildren().size()){
						nodeEntity.setClassField("jstree-checked jstree-open");
						
						//子节点部分被选中，父节点的checkbox的状态为jstree-undetermined
					}else if(count>0){
						nodeEntity.setClassField("jstree-undetermined jstree-open");
					}else{
						//子节点全没选中，父节点的checkbox的状态为jstree-unchecked
						nodeEntity.setClassField("jstree-unchecked");
					}
					}
				}
				
				nodeEntitys.add(nodeEntity);
			}
		return nodeEntitys;
	}
	
	//查询此功能的上辈功能ID
	List<String> grandTaskId(List<String> grandId,String taskId){
		String parentId = geRmsTaskRepository.findParentIdByTaskId(taskId);
		if(parentId != null){
			grandId.add(parentId);
			grandTaskId(grandId,parentId);
		}
		return grandId;
	}
	
	//查询此机构的后代机构ID
	List<String> childComId(List<String> childComCode,String comCode){
		List<String> childId = companyDao.findComCodeByUppercomcode(comCode);
		if(!childId.isEmpty()){
			
			childComCode.addAll(childId);
			for(String id : childId){
				childComId(childComCode,id);
			}
		}
		return childComCode;
	}	

}
