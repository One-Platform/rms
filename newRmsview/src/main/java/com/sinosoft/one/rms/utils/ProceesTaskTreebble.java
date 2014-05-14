package com.sinosoft.one.rms.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sinosoft.one.rms.model.Task;
import com.sinosoft.one.uiutil.NodeEntity;
import com.sinosoft.one.uiutil.Treeable;

public class ProceesTaskTreebble {
	
	
	/**
	 * 功能菜单管理 构建功能树
	 * @param topTasks
	 * @param filter
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Treeable<NodeEntity> creatTaskTreeAble(List<Task> topTasks,Map<String,Task> filter){
		List<NodeEntity> nodeEntitys=new ArrayList<NodeEntity>();
		nodeEntitys=creatSubNode(topTasks, filter);
		Treeable<NodeEntity> treeable =new Treeable.Builder(nodeEntitys,"id", "title", "children", "state").classField("classField").urlField("urlField").builder();
		return treeable;
	}
	static List<NodeEntity> creatSubNode(List<Task> topTasks,Map<String,Task> filter){
		ArrayList<NodeEntity> nodeEntitys=new ArrayList<NodeEntity>();
		for (Task geRmsTask : topTasks) {
			if(!filter.containsKey(geRmsTask.getTaskID()))
                continue;
			NodeEntity nodeEntity = new NodeEntity();
			nodeEntity.setId(geRmsTask.getTaskID());
			nodeEntity.setTitle(geRmsTask.getName());
			if(!filter.get(geRmsTask.getTaskID()).getFlag().toString().equals("1")){
				nodeEntity.setClassField("jstree-checked");
			}else{
				nodeEntity.setClassField("jstree-unchecked");
			}
			if(!geRmsTask.getChildren().isEmpty()){
				nodeEntity.setChildren(creatSubNode(geRmsTask.getChildren(),filter));
				int n1 = nodeEntity.getChildren().size();
				int n2 = 0;
				for(NodeEntity no : nodeEntity.getChildren()){
					if(no.getClassField().equals("jstree-checked")){
						n2 ++;
					}
				}
				if(!filter.get(geRmsTask.getTaskID()).getFlag().toString().equals("0")){
					
					if(n2 > 0 && n2 < n1){
						nodeEntity.setClassField("jstree-undetermined");
					}else if(n2 == 0){
						nodeEntity.setClassField("jstree-unchecked");
					}else{
						nodeEntity.setClassField("jstree-checked");
					}
				}
			}
			nodeEntitys.add(nodeEntity);
			}
		return nodeEntitys;
	}

	/**
	 * 人员配置管理  构建功能树 
	 * @param topTasks 父节点 
	 * @param taskIdList 具有的功能集合
	 * @param parentTaskIdList树的长度功能集合
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Treeable<NodeEntity> creatTaskTreeAble(List<Task> topTasks,List<String> taskIdList,List<String> parentTaskIdList){
		List<NodeEntity> nodeEntitys=new ArrayList<NodeEntity>();
		nodeEntitys=creatSubNode(topTasks,taskIdList,parentTaskIdList);
		Treeable<NodeEntity> treeable =new Treeable.Builder(nodeEntitys,"id", "title", "children", "state").classField("classField").urlField("urlField").builder();
		return treeable;
	}
	
	static List<NodeEntity> creatSubNode(List<Task> topTasks,List<String> taskIdList,List<String> parentTaskIdList){
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
}
